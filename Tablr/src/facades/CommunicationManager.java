package facades;

import java.util.ArrayList;

import domain.Column;
import domain.Table;
import ui.Loadable_Interfaces;
import uielements.UI;

public class CommunicationManager {
	
	DomainFacade domainFacade;
	UIFacade UIFacade;
	
	public CommunicationManager() {
		domainFacade = new DomainFacade(this);
		UIFacade = new UIFacade(this);
	}
	
	public void loadUI(Loadable_Interfaces i) {
		UIFacade.loadUI(i);
	}
	
	public void addEmptyTable() {
		domainFacade.addEmptyTable();
	}
	
	public ArrayList<Table> getTables() {
		return new ArrayList<Table>(domainFacade.getTables());
	}
	
	public UI getActiveUI() {
		return UIFacade.getActiveUI();
	}
	
	public void removeTable(Table t) {
		domainFacade.removeTable(t);
	}
	
	public void toggleBlanks(Column col){
		domainFacade.toggleBlanks(col);
	}
	
	public Table getTable(String name){
		return domainFacade.getTable(name);
	}

	public void addEmptyColumn(Table table) {
		domainFacade.addEmptyColumn(table);
		
	}

	public void setColumnName(Column col, String text) {
		domainFacade.changeColumnName(col,text);
		
	}

	public void addRow(Table tab) {
		domainFacade.addRow(tab);		
	}

	public ArrayList<Column> getColumns(Table tab) {
		return domainFacade.getColumns(tab);
	}

	public Object getValue(Column col, int i) {
		return domainFacade.getValue(col,i);
	}
	
		

	
}
