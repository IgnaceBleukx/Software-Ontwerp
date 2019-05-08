package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Utils.DebugPrinter;
import uielements.BottomUIEdge;
import uielements.Button;
import uielements.CloseButton;
import uielements.Dragger;
import uielements.RightUIEdge;
import uielements.Text;
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
		legendWidth = getWidth()-2*edgeW-scrollBarWidth;
		
		columnResizeListeners.add((delta,index) -> {
			getLegend().resizeElementR(delta, index);
			getListview().getElements().stream().filter(e -> e instanceof UIRow).forEach(e -> ((UIRow) e).resizeElementR(delta,index+1));
		});
	}
	
	/**
	 * Height of rows in the list of tables
	 */
	private static int tableRowHeight = 35;
	
	/**
	 * Width of the scrollbars.
	 */
	private static int scrollBarWidth = 10;
	
	private int legendWidth;
	
	/**
	 * Loads all necessary UIElement and loads the names of a list of tables.
	 * Also activates this UI (so it will be drawn on the Canvas).
	 */
	public void loadUI(){
		setActive();
		UIRow legend = getLegend();
		this.clear();		
		
		//Creating background:
		addUIElement(new VoidElement(getX(), getY(), getWidth(), getHeight(), new Color(230,230,230,230)));
		
		//Creating window layout
		titleBar.setText("Tables mode");
		loadUIAttributes();

		if (legend == null) {
			legend = new UIRow(getX()+edgeW,getY()+edgeW+titleHeight,legendWidth,15,new ArrayList<UIElement>());
			Dragger tableNameDragger = new Dragger(legend.getEndX()-2,legend.getY(),4,legend.getHeight());
			Text tableName = new Text(legend.getX()+tableRowHeight,legend.getY(),legend.getWidth()-2-tableRowHeight,legend.getHeight(),"Table name");
			legend.addElement(tableNameDragger);
			legend.addElement(tableName);
		}
		
		this.addUIElement(legend);
		ArrayList<UIElement> legendElementsCopy = getLegend().getElements();
		legendElementsCopy.sort((UIElement e1, UIElement e2) -> e1.getX() - e2.getX());
		Text tableName = (Text) legendElementsCopy.get(0);
		Dragger tableNameDragger = (Dragger) legendElementsCopy.get(1);
		
		tableNameDragger.addDragListener((newX,newY) -> {
			int delta = newX - tableNameDragger.getGrabPointX();
			int deltaFinal = delta;
			if (tableName.getWidth() + delta < minimumColumnWidth)
				deltaFinal = minimumColumnWidth - tableName.getWidth();
			getWindowManager().notifyTablesModeUIsColResized(deltaFinal,0);
		});
		
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
	
	private ListView getListview() {
		return (ListView) elements.stream().filter(e -> e instanceof ListView).findFirst().orElse(null);
	}
	
	private UIRow getLegend() {
		return (UIRow) elements.stream().filter(e -> e instanceof UIRow).findFirst().orElse(null);
	}
	
	/**
	 * Load all tables into a listview
	 */
	private ListView loadFromTables() {
		ArrayList<UIElement> rows = new ArrayList<>();
		ListView list = new ListView(getLegend().getX(),getLegend().getEndY(),getWidth()-2*edgeW, getEndY()-edgeW-getLegend().getEndY(), rows);

		ArrayList<Table> tables = this.getTablr().getTables();
		
		list.addKeyboardListener(70, () -> {
			if (!getWindowManager().recentCtrl())
				return;
			ArrayList<UIRow> r = new ArrayList<UIRow>(list.getElements().stream().filter(e -> e instanceof UIRow).map(e -> (UIRow) e).collect(Collectors.toList()));
			for (int i=0;i<tables.size();i++) {
				if(r.get(i).isSelected()) {
					tablr.loadFormsModeUI(tables.get(i));
					return;
				}
			}
		});
		
		
		int y = list.getY();
		for (int i=0;i<tables.size();i++) { 
			Table curr = tables.get(i);
			UIRow currRow = new UIRow(list.getX(), y, getLegend().getWidth(),tableRowHeight, new ArrayList<UIElement>());
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
			
			
			TextField tableNameLabel = new TextField(deleteButton.getEndX(), y, getLegend().getWidth()-buttonSize, tableRowHeight, curr.getName());
			currRow.addElement(tableNameLabel);
			//Table name textfields listen to alphanumeric keyboard input
			tableNameLabel.addKeyboardListener(-1, () -> {
				
				ArrayList<Table> tablesSameName = tablr.getTablesByName(tableNameLabel.getText());
				System.out.println("[TablesModeUI.java:122] "+tablesSameName);
			
				if ((tablesSameName.size() > 0 && tableNameLabel.isSelected()) || tableNameLabel.getText().length() == 0) {
					if (!tableNameLabel.getText().equals(curr.getName()))
						tableNameLabel.isError();
					//c.getSelectionLock(tableNameLabel);
				}
				else if (tableNameLabel.getError() == true){
					tableNameLabel.isNotError();
					tablr.renameTable(curr,tableNameLabel.getText());
					//c.releaseSelectionLock(tableNameLabel);
				}
				else {
					tablr.renameTable(curr,tableNameLabel.getText());
				}
			});
			
			tableNameLabel.addKeyboardListener(10,() -> {
				if (list.getError()) return;
				//tablr.renameTable(curr, tableNameLabel.getText());
				tablr.domainChanged();
			});

			//Table name textfields listen to double click events to switch modes
			tableNameLabel.addDoubleClickListener(() -> {
				for (UIElement e : getElements()){
					if (e.getError()) return;
				}
				if (tablr.isEmptyTable(curr)) {
					System.out.println("[TablesModeUI.java:125]: Opening an empty table -> DesignMode");
					this.getWindowManager().loadTableDesignModeUI(curr);
				}
				else {
					System.out.println("[TablesModeUI.java:130]: Opening a table rows mode");
					this.getWindowManager().loadTableRowsModeUI(curr);
				}
			});
			
			tableNameLabel.addDeselectionListener(() -> {
				tablr.domainChanged();
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
		TablesModeUI clone = new TablesModeUI(getX(),getY(),getWidth(),getHeight(), getTablr());
		ArrayList<UIElement> clonedElements = new ArrayList<>(elements.stream().map(e -> e.clone()).collect(Collectors.toList()));
		clone.elements = clonedElements;
		return clone;
	}

}
