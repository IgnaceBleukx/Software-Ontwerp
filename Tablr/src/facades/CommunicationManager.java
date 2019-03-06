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
	
		

	
}
