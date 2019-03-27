package ui;

import java.util.ArrayList;

import uielements.Button;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import domain.Table;

public class TableModeUI extends UI {
	
	public void loadUI(){
		for (int i=0;i<tables.size();i++) { 
			Table curr = tables.get(i);
			UIRow currRow = new UIRow(getX()+1, getY()+1+i*40, getWidth()-2, 40, new ArrayList<UIElement>());
			
			Button deleteButton = new Button(getX()+2, getY()+2+i*40,38,38,"");
			deleteButton.addSingleClickListener(() -> {
				for (UIElement e : getElements()){
					if (e.getError()) return;
				}
				this.setSelectedElement(currRow);
			});
			
			deleteButton.addKeyboardListener(127, () -> {
				if (getSelectedElement() == currRow && this.getError() == false) {
					removeElement((UIElement) currRow); //Remove row from ListView
					c.removeTable(curr); //Remove table from list of tables
					setSelectedElement(null);
					loadFromTables(c.getTables());
				}
			});
			currRow.addElement(deleteButton);
			
			TextField tableNameLabel = new TextField(getX()+40, getY()+2+i*40, 520, 38, curr.getName());
			//Table name textfields listen to alphanumeric keyboard input
			tableNameLabel.addKeyboardListener(-1, () -> {
				c.renameTable(curr, tableNameLabel.getText());
				ArrayList<Table> tablesSameName = c.getTablesByName(curr.getName());
			
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
				getCommunicationManager().setActiveTable(curr);
				for (UIElement e : getElements()){
					if (e.getError()) return;
				}
				if (getCommunicationManager().isEmptyTable(curr)) {
					getCommunicationManager().loadUI(Loadable_Interfaces.TABLE_DESIGN);
					getCommunicationManager().changeTitle("Table Design Mode: "+curr.getName());
				}
				else {
					getCommunicationManager().loadUI(Loadable_Interfaces.TABLE_ROWS);
					getCommunicationManager().changeTitle("Table Rows Mode: "+curr.getName());

				}
			});

			currRow.addElement(tableNameLabel);
			
			addElement(currRow);
		}	
	}

}
