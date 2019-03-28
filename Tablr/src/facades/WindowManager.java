package facades;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import ui.TableDesignModeUI;
import ui.TablesModeUI;
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
	private Tablr tablr;
	
	public WindowManager(Tablr c) {
		tablr = c;
		tablesModeUI = new TablesModeUI(tablr);
		tableRowsModeUIs = new HashMap<Table,TableRowsModeUI>();
		tableDesignModeUIs = new HashMap<Table,TableDesignModeUI>();
		
		loadTablesModeUI();
	}
	
	public Tablr getCommunicationManager() {
		return tablr;
	}
	
	private TablesModeUI tablesModeUI;
	private HashMap<Table,TableRowsModeUI> tableRowsModeUIs;
	private HashMap<Table,TableDesignModeUI> tableDesignModeUIs;
	
	
	public void loadTablesModeUI(){
		tablesModeUI.loadUI();
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

	public void paint(Graphics g) {
		//Paint all UI's that are active
		if (tablesModeUI.isActive())
			tablesModeUI.paint(g);
		
		tableRowsModeUIs.values().stream().filter((e) -> e.isActive()).forEach((e) -> e.paint(g));
		tableDesignModeUIs.values().stream().filter((e) -> e.isActive()).forEach((e) -> e.paint(g));

 	}
}
