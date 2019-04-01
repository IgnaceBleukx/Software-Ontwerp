package ui;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import uielements.Button;
import uielements.CloseButton;
import uielements.ListView;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import domain.Table;
import facades.Tablr;

public class TablesModeUI extends UI {
	
	public TablesModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
	}
	
	public void loadUI(){
		setActive();
		
		int titleHeigth = 15;
		
		Button titleBar = new Button(getX(),getY(),getWidth()-30,titleHeigth,"Tables Mode");
		CloseButton close = new CloseButton(getX()+getWidth()-30,getY(),30,titleHeigth,4);
		this.addUIElement(close);
		this.addUIElement(titleBar);
		//Adding listeners:
		titleBar.addDragListener((x,y) -> { 
			this.setX(x);
			this.setY(y);
		});
		close.addSingleClickListener(() -> {
			this.setInactive();
		});		
		
		ArrayList<Table> tables = this.getTablr().getTables();
		ListView list = loadFromTables(tables, titleHeigth);
		addUIElement(list);
		
		//Reload listview when domain changed
		tablr.addDomainChangedListener(() -> {
			//Remove the old listview
			Optional<UIElement> l = getElements().stream().filter(e -> e instanceof ListView).findFirst();
			this.getElements().remove(l.orElseThrow(() -> new RuntimeException("No listview to bind listener to.")));
			
			//Load new ListView from tables
			addUIElement(loadFromTables(tablr.getTables(), titleHeigth));
		});
	}

	
	private ListView loadFromTables(ArrayList<Table> tables, int titleHeigth) {
		int rowHeigth = 30;
		
		ArrayList<UIElement> rows = new ArrayList<>();
		System.out.println("[TableModeUI.java:60] : Dimensions of UI: X=" + getX() + " Y= " + getY() + " W=" + getWidth() + " H=" + getHeight());
		ListView list = new ListView(getX(), getY()+15, getWidth(), getHeight(), rows);
		
		for (int i=0;i<tables.size();i++) { 
			Table curr = tables.get(i);
			UIRow currRow = new UIRow(getX(), getY()*i*rowHeigth+titleHeigth, getWidth(),rowHeigth, new ArrayList<UIElement>());
			
			int buttonSize = currRow.getHeight();
			Button deleteButton = new Button(getX(), getY()+i*rowHeigth+titleHeigth,buttonSize,buttonSize,"");
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
//					loadFromTables(tables, titleHeigth);
				}
			});
			currRow.addElement(deleteButton);
			
			TextField tableNameLabel = new TextField(getX()+buttonSize, getY()+titleHeigth+i*rowHeigth, getWidth()-buttonSize, rowHeigth, curr.getName());
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

			//Table name textfields listen to double click events to switch modes
			tableNameLabel.addDoubleClickListener(() -> {
				for (UIElement e : getElements()){
					if (e.getError()) return;
				}
				if (tablr.isEmptyTable(curr)) {
					this.getWindowManager().loadTableDesignModeUI(curr);
					tablr.changeTitle("Table Design Mode: "+curr.getName());
				}
				else {
					tablr.loadTableRowsModeUI(curr);;
					tablr.changeTitle("Table Rows Mode: "+curr.getName());

				}
			});

			currRow.addElement(tableNameLabel);
			
			list.addElement(currRow);
		}	
		
		/**
		 * Selects a row in the listview
		 */
		list.addSingleClickListener(() -> {
			for (UIElement e : list.getElements()){
				if (e.isSelected()) e.setNotSelected();
			}
		});		
		
		/**
		 * Adds a new table
		 */
		list.addDoubleClickListener(() -> {
			for (UIElement e : getElements()){
				if (e.getError()) return;
			}
			tablr.addEmptyTable();
		});
		
		
		
		
		return list;
	}
	
	

}
