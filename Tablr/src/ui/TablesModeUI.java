package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;

import uielements.BottomUIEdge;
import uielements.Button;
import uielements.CloseButton;
import uielements.RightUIEdge;
import uielements.TopUIEdge;
import uielements.ListView;
import uielements.TextField;
import uielements.Titlebar;
import uielements.UIElement;
import uielements.UIRow;
import uielements.LeftUIEdge;
import uielements.VoidElement;
import domain.Table;
import facades.Tablr;

public class TablesModeUI extends UI {
	
	/**
	 * Height of rows in the list of tables
	 */
	static int tableRowHeight = 35;
	
	/**
	 * Width of the scrollbars.
	 */
	static int scrollBarWidth = 10;
	
	/**
	 * Creates a new TablesModeUI
	 * @param x		X Coordinate
	 * @param y		Y Coordinate
	 * @param w		Width
	 * @param h		Height
	 * @param t		Tablr reference to interact with the domain
	 */
	public TablesModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
	}
	
	/**
	 * Loads all necessary UIElement and loads the names of a list of tables.
	 * Also activates this UI (so it will be drawn on the Canvas).
	 */
	public void loadUI(){
		setActive();
		this.clear();		
		
		//Creating background:
		addUIElement(new VoidElement(getX(), getY(), getWidth(), getHeight(), new Color(230,230,230,230)));
		
		//Creating window layout
		titleBar.setText("Tables mode");
		
		loadUIAttributes();

		ListView list = loadFromTables();
		addUIElement(list);
		
		//Reload listview when domain changed
		tablr.addDomainChangedListener(() -> {
			//Remove the old listview
			Optional<UIElement> l = getElements().stream().filter(e -> e instanceof ListView).findFirst();
			this.getElements().remove(l.orElseThrow(() -> new RuntimeException("No listview to bind listener to.")));
			
			//Load new ListView from tables
			addUIElement(loadFromTables());
		});
	}
	
	/**
	 * Load all tables into a listview
	 */
	private ListView loadFromTables() {
		ArrayList<UIElement> rows = new ArrayList<>();
		ListView list = new ListView(getX()+edgeW, getY()+edgeW+titleHeight, getWidth()-2*edgeW, getHeight()-2*edgeW-titleHeight, rows);

		ArrayList<Table> tables = this.getTablr().getTables();
		int y = list.getY();
		for (int i=0;i<tables.size();i++) { 
			Table curr = tables.get(i);
			UIRow currRow = new UIRow(list.getX(), y, getWidth()-scrollBarWidth,tableRowHeight, new ArrayList<UIElement>());
			list.addElement(currRow);

			int buttonSize = currRow.getHeight();
			Button deleteButton = new Button(list.getX(), y,buttonSize,buttonSize,"");
			currRow.addElement(deleteButton);
			
			//Listener to select rows
			deleteButton.addSingleClickListener(() -> {
				for (UIElement e : list.getElements()){
					System.out.println("[TablesModeUI.java:77]: "  + e);
					if (e.getError() || (e.isSelected() && !e.equals(currRow))) return;
				}
				if(currRow.isSelected()) 
					currRow.deselect();
				else 
					currRow.select();
			});
			
			//Listener to remove row
			deleteButton.addKeyboardListener(127, () -> {
				if (currRow.isSelected() && list.getError() == false) {
					list.removeElement((UIElement) currRow); //Remove row from ListView
					tablr.removeTable(curr); //Remove table from list of tables
				}
			});
			
			
			TextField tableNameLabel = new TextField(deleteButton.getEndX(), y, getWidth()-buttonSize-scrollBarWidth, tableRowHeight, curr.getName());
			currRow.addElement(tableNameLabel);
			//Table name textfields listen to alphanumeric keyboard input
			tableNameLabel.addKeyboardListener(-1, () -> {
				tablr.renameTable(curr, tableNameLabel.getText());
				ArrayList<Table> tablesSameName = tablr.getTablesByName(curr.getName());
			
				if ((tablesSameName.size() > 1 && tableNameLabel.isSelected()) || tableNameLabel.getText().length() == 0) {
					tableNameLabel.isError();
					//c.getSelectionLock(tableNameLabel);
				}
				else if (tableNameLabel.getError() == true){
					tableNameLabel.isNotError();
					//c.releaseSelectionLock(tableNameLabel);
				}
			});
			
			tableNameLabel.addKeyboardListener(10,() -> {
				if (list.getError()) return;
				tablr.domainChanged();
			});

			//Table name textfields listen to double click events to switch modes
			tableNameLabel.addDoubleClickListener(() -> {
				for (UIElement e : getElements()){
					if (e.getError()) return;
				}
				if (tablr.isEmptyTable(curr)) {
					System.out.println("[TablesModeUI.java:125]: Opening an empty table -> DesignMode");
				//	tablr.changeTitle("Table Design Mode: "+curr.getName());
					this.getWindowManager().loadTableDesignModeUI(curr);
				}
				else {
					System.out.println("[TablesModeUI.java:130]: Opening a table rows mode");
					this.getWindowManager().loadTableRowsModeUI(curr);
					tablr.changeTitle("Table Rows Mode: "+curr.getName());

				}
			});
			
			y = currRow.getEndY();
		}	
		
		//Deselects a row in the listview
		list.addSingleClickListener(() -> {
			for (UIElement e : list.getElements()){
				if (e.isSelected()) e.deselect();
			}
		});		
		
		//Adding a new table
		list.addDoubleClickListener(() -> {
			for (UIElement e : getElements()){
				if (e.getError()) return;
			}
			tablr.addEmptyTable();
		});
		
		return list;
	}
	
	@Override
	public TablesModeUI clone(){
		TablesModeUI clone = new TablesModeUI(getX(),getY(),getWidth(),getHeight(),getTablr());
		ArrayList<UIElement> clonedElements = new ArrayList<UIElement>();
		elements.stream().forEach(e -> clonedElements.add(e.clone()));
		clone.elements = clonedElements;
		clone.titleBar = titleBar;
		clone.leftResize = leftResize;
		clone.rightResize = rightResize;
		clone.topResize = topResize;
		clone.bottomResize = bottomResize;
		clone.topLeft = topLeft;
		clone.topRight = topRight;
		clone.bottomLeft = bottomLeft;
		clone.bottomRight = bottomRight;
		return clone;
	}

}
