package facades;

import ui.Loadable_Interfaces;
import uielements.UI;

public class UIFacade {
	private CommunicationManager communicationManager;
	private UI ui = new UI();
	
	public UIFacade(CommunicationManager c) {
		communicationManager = c;
	}
	
	public void setCommunicationManager(CommunicationManager c) {
		communicationManager = c;
	}
	
	public CommunicationManager getCommunicationManager() {
		return communicationManager;
	}
	
	public void loadUI(Loadable_Interfaces i) {
		ui.setCommunicationManager(getCommunicationManager());
		switch (i) {
		case TABLE_DESIGN: ui.loadTableDesignInterface(); break;
		case TABLE_ROWS: ui.loadTableRowsInterface(); break;
		case TABLES: ui.loadTablesInterface(); break;
		case TEST: ui.loadTestInterface(); break;
		}
		
	}
	
	

}
