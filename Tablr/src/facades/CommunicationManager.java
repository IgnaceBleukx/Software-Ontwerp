package facades;

import java.util.ArrayList;

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
	
	public boolean isValidTableName(String name) {
		return domainFacade.isValidTableName(name);
	}
	
	public ArrayList<Table> getTablesByName(String name) {
		ArrayList<Table> tlist = new ArrayList<Table>();
		ArrayList<Table> tables = domainFacade.getTables();
		for (Table t : tables) {
			if (t.getName().equals(name)) {
				tlist.add(t);
			}
		}
		return new ArrayList<Table>(tlist);
	}
	
	public int getNumberOfTablesByName(String name) {
		int count = 0;
		ArrayList<Table> tables = domainFacade.getTables();
		for (Table t : tables) {
			if (t.getName().equals(name)) {
				count++;
			}
		}
		return count;
	}
	
	public void renameTable(Table t, String name) {
		domainFacade.renameTable(t, name);
	}
	
		

	
}
