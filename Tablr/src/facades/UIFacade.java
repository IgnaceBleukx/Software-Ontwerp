package facades;

import domain.Table;
import ui.Loadable_Interfaces;
import uielements.Text;
import uielements.UI;
import uielements.UIElement;

/**
 * Facade for the UI part of the program. 
 * Methods should always be called via a reference to the CommunicationManager,
 * and not directly via this class.
 */
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
		ui.setCommunicationManager(getCommunicationManager());
		
	}

	public UI getActiveUI() {
		return ui;
	}

	public void newSelected(UIElement e) {
		getActiveUI().selectElement(e);
	}
	
	public void getSelectionLock(UIElement e) {
		getActiveUI().lockedSelectedElement = e;
	}
	
	public void releaseSelectionLock(UIElement e) {
		if (getActiveUI().lockedSelectedElement != e)
			throw new IllegalArgumentException("Trying to release selection lock from non-selected element");
		getActiveUI().lockedSelectedElement = null;
	}
	
	public void clearUI() {
		getActiveUI().clear();
	}
}
