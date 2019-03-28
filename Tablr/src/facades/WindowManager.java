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
	
	private ArrayList<UI> getUIs() {
		ArrayList<UI> uis = new ArrayList<UI>();
		uis.add(tablesModeUI);
		tableRowsModeUIs.values().stream().map(x -> uis.add(x));
		tableDesignModeUIs.values().stream().map(x -> uis.add(x));
		return uis;
	}
	
	public void loadTablesModeUI(){
		tablesModeUI.loadUI();
	}
	
	public void loadTableRowsModeUI(Table table){
		tableRowsModeUIs.get(table).loadUI();
	}
	
	public void loadTableDesignModeUI(Table table){
		tableDesignModeUIs.get(table).loadUI(table);
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

	public UI getUIAt(int x, int y) {
		for (UI ui : getUIs()) {
			if (ui.containsPoint(x,y)) return ui;
		}
		return null;
	}
	
	public void newSelected(UIElement e) {
		getUIAt(e.getX(),e.getY()).selectElement(e);
	}
	
	public void getSelectionLock(UIElement e) {
		getUIAt(e.getX(),e.getY()).lockedSelectedElement = e;
	}
	
	public void releaseSelectionLock(UIElement e) {
		if (getUIAt(e.getX(),e.getY()).lockedSelectedElement != e)
			throw new IllegalArgumentException("Trying to release selection lock from non-selected element");
		getUIAt(e.getX(),e.getY()).lockedSelectedElement = null;
	}
	
	public void clearUIAt(int x,int y) {
		getUIAt(x,y).clear();
	}

	public void getLock(UIElement e) {
		getUIAt(e.getX(),e.getY()).hardLockedElement = e;
	}

	public void releaseLock(UIElement e) {
		if (getUIAt(e.getX(),e.getY()).hardLockedElement != e)
			throw new IllegalArgumentException("Trying to release hard lock from non-selected element");
		getUIAt(e.getX(),e.getY()).hardLockedElement = null;
		
	}

	public UIElement getLockedUIElementAt(int x, int y) {
		return getUIAt(x,y).hardLockedElement;
	}

	public ArrayList<UIElement> getElementsUIAt(int x, int y) {
		return new ArrayList<UIElement>(getUIAt(x,y).getElements());
	}

	public void paint(Graphics g) {
		//Paint all UI's that are active
		if (tablesModeUI.isActive())
			tablesModeUI.paint(g);
		
		tableRowsModeUIs.values().stream().filter((e) -> e.isActive()).forEach((e) -> e.paint(g));
		tableDesignModeUIs.values().stream().filter((e) -> e.isActive()).forEach((e) -> e.paint(g));

 	}
}
