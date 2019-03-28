package ui;

import java.util.ArrayList;

import uielements.Button;
import uielements.ListView;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import domain.Table;
import facades.Tablr;

public class TablesModeUI extends UI {
	
	public TablesModeUI(Tablr t) {
		this.setTablr(t);
	}
	
	public void loadUI(){
		setActive();
		
		ArrayList<Table> tables = this.getTablr().getTables();
		ListView list = loadFromTables(tables);
		addUIElement(list);
		
		list.addSingleClickListener(() -> {
			for (UIElement e : list.getElements()){
				if (e.isSelected()) e.setNotSelected();
			}
		});		
		
		//Add new table
		list.addDoubleClickListener(() -> {
			for (UIElement e : getElements()){
				if (e.getError()) return;
			}
			tablr.addEmptyTable();
		});

		
		//Reload listview when domain changed
		tablr.addDomainChangedListener(() -> {
			getElements().remove(list);
			addUIElement(loadFromTables(tablr.getTables()));
		});
	}
	
	private ListView loadFromTables(ArrayList<Table> tables) {
		ArrayList<UIElement> rows = new ArrayList<>();
		ListView list = new ListView(getX()+10, getY()+10, 580, 560, rows);
		
		for (int i=0;i<tables.size();i++) { 
			Table curr = tables.get(i);
			UIRow currRow = new UIRow(getX()+11, getY()+11+i*40, 548, 40, new ArrayList<UIElement>());
			
			Button deleteButton = new Button(getX()+12, getY()+12+i*40,38,38,"");
			deleteButton.addSingleClickListener(() -> {
				for (UIElement e : getElements()){
					if (e.getError()) return;
				}
				//this.setSelectedElement(currRow);
			});
			
			deleteButton.addKeyboardListener(127, () -> {
				if (list.getSelectedElement() == currRow && list.getError() == false) {
					list.removeElement((UIElement) currRow); //Remove row from ListView
					tablr.removeTable(curr); //Remove table from list of tables
					list.setSelectedElement(null);
				}
			});
			currRow.addElement(deleteButton);
			
			TextField tableNameLabel = new TextField(getX()+50, getY()+12+i*40, 520, 38, curr.getName());
			//Table name textfields listen to alphanumeric keyboard input
			tableNameLabel.addKeyboardListener(-1, () -> {
				tablr.renameTable(curr, tableNameLabel.getText());
				ArrayList<Table> tablesSameName = tablr.getTablesByName(curr.getName());
			
				if ((tablesSameName.size() > 1 && tableNameLabel.isSelected()) | tableNameLabel.getText().length() == 0) {
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
					this.getWindowManager().loadTableDesignModeUI(curr);;
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
		return list;
	}
	
	

}
