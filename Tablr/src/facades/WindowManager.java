package facades;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import ui.TableDesignModeUI;
import ui.TableModeUI;
import ui.TableRowsModeUI;
import ui.UI;
import uielements.Text;
import uielements.UIElement;

/**
 * Facade for the UI part of the program. 
 * Methods should always be called via a reference to the CommunicationManager,
 * and not directly via this class.
 */
public class WindowManager {
	private Tablr communicationManager;
	private UI ui = new UI();
	
	public WindowManager(Tablr c) {
		communicationManager = c;
	}
	
	public Tablr getCommunicationManager() {
		return communicationManager;
	}
	
	private TableModeUI tableModeUI;
	private HashMap<Table,TableRowsModeUI> tableRowsModeUIs = new HashMap<Table,TableRowsModeUI>();
	private HashMap<Table,TableDesignModeUI> tableDesignModeUIs = new HashMap<Table,TableDesignModeUI>();
	
	
	public void loadTablesModeUI(){
		tableModeUI.loadUI();
		throw new RuntimeException("Not Implemented yet");
	}
	
	public void loadTableRowsModeUI(Table table){
		//TODO
		throw new RuntimeException("Not Implemented yet");
	}
	
	public void loadTableDesignModeUI(Table table){
		//TODO
		throw new RuntimeException("Not Implemented yet");
	}
	
//	public void loadUI(String tableName) {
//		ui.setCommunicationManager(getCommunicationManager());
//		switch (i) {
//		case TABLE_DESIGN: ui.loadTableDesignInterface(); break;
//		case TABLE_ROWS: ui.loadTableRowsInterface(); break;
//		case TABLES: ui.loadTablesInterface(); break;
//		case TEST: ui.loadTestInterface(); break;
//		}
//		ui.setCommunicationManager(getCommunicationManager());
//		
//	}

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

	public void getLock(UIElement e) {
		getActiveUI().hardLockedElement = e;
		
	}

	public void releaseLock(UIElement e) {
		if (getActiveUI().hardLockedElement != e)
			throw new IllegalArgumentException("Trying to release hard lock from non-selected element");
		getActiveUI().hardLockedElement = null;
		
	}

	public UIElement getLockedElement() {
		return getActiveUI().hardLockedElement;
	}

	public ArrayList<UIElement> getActiveUIElements() {
		return new ArrayList<UIElement>(getActiveUI().getElements());
	}
}
