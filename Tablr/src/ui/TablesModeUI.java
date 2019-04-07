package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import uielements.Button;
import uielements.CloseButton;
import uielements.ListView;
import uielements.TextField;
import uielements.Titlebar;
import uielements.UIElement;
import uielements.UIRow;
import uielements.VoidElement;
import domain.Table;
import facades.Tablr;

public class TablesModeUI extends UI {
	
	public TablesModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
	}
	
	public void loadUI(){
		setActive();
		this.clear();
		int titleHeight = 15;
		
		//Creating background:
		addUIElement(new VoidElement(getX(), getY(), getWidth(), getHeight(), new Color(230,230,230,230)));
		
		Titlebar titleBar = new Titlebar(getX(),getY(),getWidth()-30,titleHeight,"Tables Mode");
		CloseButton close = new CloseButton(getX()+getWidth()-30,getY(),30,titleHeight,4);
		this.addUIElement(close);
		this.addUIElement(titleBar);
		
		//Adding listeners:
		titleBar.addDragListener((newX,newY) -> { 
			System.out.println("[TablesModeUI.java:35]: Attempting drag");
			if (!titleBar.getDragging()) return;
			
			int deltaX = newX - titleBar.getGrabPointX();
			int deltaY = newY - titleBar.getGrabPointY();
			System.out.println("[TablesModeUI.java:44] : deltaX = " + deltaX);
			System.out.println("[TablesModeUI.java:45] : detlaY = " + deltaY);
			
			this.move(deltaX, deltaY);
//			loadUI();
			getWindowManager().selectUI(this);
		});
		close.addSingleClickListener(() -> {
			setInactive();
			getWindowManager().selectUI(null);
		});		
		
		ArrayList<Table> tables = this.getTablr().getTables();
		ListView list = loadFromTables(tables, titleHeight);
		addUIElement(list);
		
		//Reload listview when domain changed
		tablr.addDomainChangedListener(() -> {
			//Remove the old listview
			Optional<UIElement> l = getElements().stream().filter(e -> e instanceof ListView).findFirst();
			this.getElements().remove(l.orElseThrow(() -> new RuntimeException("No listview to bind listener to.")));
			
			//Load new ListView from tables
			addUIElement(loadFromTables(tablr.getTables(), titleHeight));
		});
	}

	
	private ListView loadFromTables(ArrayList<Table> tables, int titleHeight) {
		int rowHeigth = 35;
		ArrayList<UIElement> rows = new ArrayList<>();
		System.out.println("[TableModeUI.java:78] : Dimensions of UI: X=" + getX() + " Y= " + getY() + " W=" + getWidth() + " H=" + getHeight());
		ListView list = new ListView(getX(), getY()+15, getWidth(), getHeight()-titleHeight, rows);
		
		for (int i=0;i<tables.size();i++) { 
			Table curr = tables.get(i);
			UIRow currRow = new UIRow(getX(), getY()+i*rowHeigth+titleHeight, getWidth()-10,rowHeigth, new ArrayList<UIElement>());
			list.addElement(currRow);

			int buttonSize = currRow.getHeight();
			Button deleteButton = new Button(getX(), getY()+i*rowHeigth+titleHeight,buttonSize,buttonSize,"");
			currRow.addElement(deleteButton);
			
			deleteButton.addSingleClickListener(() -> {
				for (UIElement e : getElements())
					if (e.getError()) return;
				if(currRow.isSelected()) currRow.deselect();
				else currRow.select();
			});
			
			deleteButton.addKeyboardListener(127, () -> {
				if (currRow.isSelected() && list.getError() == false) {
					list.removeElement((UIElement) currRow); //Remove row from ListView
					tablr.removeTable(curr); //Remove table from list of tables
					list.setSelectedElement(null);
				}
			});
			
			
			TextField tableNameLabel = new TextField(getX()+buttonSize, getY()+titleHeight+i*rowHeigth, getWidth()-buttonSize-10, rowHeigth, curr.getName());
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
			
			tableNameLabel.addKeyboardListener(10,() -> tablr.domainChanged());

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
		}	
		
		//Selects a row in the listview
		list.addSingleClickListener(() -> {
			for (UIElement e : list.getElements()){
				if (e.isSelected()) e.setNotSelected();
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
	
	

}
