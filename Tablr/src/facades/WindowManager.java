package facades;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import Utils.DebugPrinter;
import domain.StoredTable;
import domain.Table;
import exceptions.InvalidNameException;
import ui.FormsModeUI;
import ui.TableDesignModeUI;
import ui.TablesModeUI;
import ui.TableRowsModeUI;
import ui.UI;
import uielements.UIElement;
import uielements.VoidElement;

/**
 * Facade for the UI part of the program. 
 * Methods should always be called via a reference to the CommunicationManager,
 * and not directly via this class.
 */
public class WindowManager {
	private Tablr tablr;
	
	public WindowManager(Tablr c) {
		tablr = c;
		keyListener = new VoidElement(-1, -1, 300, 300, null);
		
//		// 'Z' is pressed
//		keyListener.addKeyboardListener(90, () -> {
//			System.out.println("Z is pressed");
//			if (recentCtrl()) {
//				if(recentShift()) {
//					DebugPrinter.print("CTRL + SHIFT + Z is pressed");
//					tablr.redo();
//				} else {
//					System.out.println("CTRL + Z is pressed in windowmanager");
//					tablr.undo();
//				}
//			}
//		});
		keyListener.addKeyboardListener(85, () -> {
			if (recentCtrl()) {
				DebugPrinter.print("UNDO");
				tablr.undo();
			}

			
		});
		keyListener.addKeyboardListener(82,() -> {
			if (recentCtrl()) {
				DebugPrinter.print("REDO");
				tablr.redo();
			}
		});
	}
	
	public Tablr getTablr() {
		return tablr;
	}
	
	private HashMap<Table,ArrayList<UI>> uis = new HashMap<Table,ArrayList<UI>>();
	private VoidElement keyListener = null;
	
	/**
	 * The selectedUI is the only UI that receives keyboard input
	 */
	private UI selectedUI;
	
	/**
	 * The previously selected UI is reselected when the current UI is closed.
	 */
	private UI prevSelectedUI;

	
	public ArrayList<UI> getUIs() {
		ArrayList<UI> allUIs = new ArrayList<>();
		this.uis.values().stream().forEach(l -> allUIs.addAll(l));
		return allUIs;
	}
	
	/**
	 * Returns all UIElements in ALL UI's
	 */
	private ArrayList<UIElement> getAllElements() {
		ArrayList<UIElement> elements = new ArrayList<>();
		uis.values().stream().forEach(l -> l.stream().forEach(u -> elements.addAll(u.getElements())));
		return elements;
	}
	
	public void addTablesModeUI() {
		TablesModeUI ui = null;
		if (!uis.containsKey(null)) {
			uis.put(null, new ArrayList<UI>());
			ui = new TablesModeUI(0,0,300,300,tablr);			
		}else {
			for (TablesModeUI tui : getTablesModeUIs()) {
				if (!tui.isActive()) {
					tui.setActive();
					this.selectUI(tui);
					tui.setTablr(tablr);
					tui.setWindowManager(this);
					return;
				}
			}
			if (ui == null)
				ui = getTablesModeUIs().get(0).clone();
		}
		uis.get(null).add(ui);
		loadTablesModeUI(ui);
		ui.move(-ui.getX(), -ui.getY());
	}

	public void addTableDesignModeUI(Table table, TableDesignModeUI ui){
		if (!uis.containsKey(table))
			uis.put(table, new ArrayList<UI>());
		this.uis.get(table).add(ui);
	}
	
	public void addTableRowsModeUI(Table table, TableRowsModeUI ui){
		if (!uis.containsKey(table))
			uis.put(table, new ArrayList<UI>());
		this.uis.get(table).add(ui);
	}

	public void addFormModeUI(Table table, FormsModeUI ui){
		if(!uis.containsKey(table)) 
			uis.put(table, new ArrayList<UI>());
		this.uis.get(table).add(ui);
	}
	
	public void loadTablesModeUI(TablesModeUI ui){
		ui.setTablr(tablr);
		ui.setWindowManager(this);
		this.selectUI(ui);
		ui.loadUI();
		lastLoad = System.currentTimeMillis();
	}

	public void loadTableDesignModeUI(StoredTable table){
		TableDesignModeUI ui = null;
		ArrayList<TableDesignModeUI> uis = getTableDesignModeUIs(table);
		if (uis.isEmpty()) throw new RuntimeException("No TableDesignModeUI for table " + table);
		for (TableDesignModeUI designMode : uis) {
			if (!designMode.isActive()) {
				ui = designMode;
				this.selectUI(ui);
				ui.setTablr(tablr);
				ui.setWindowManager(this);
				ui.setActive();
				if (uis.size() == 1)
					ui.loadUI(table);
				return;
			}
		}
		if (ui == null) {
			ui = uis.get(0).clone();
			this.addTableDesignModeUI(table, ui);
		}
		this.selectUI(ui);
		ui.setTablr(tablr);
		ui.setWindowManager(this);
		ui.loadUI(table);
		ui.move(-ui.getX()+300, -ui.getY());
		lastLoad = System.currentTimeMillis();
	}

	public void loadTableRowsModeUI(Table table){
		TableRowsModeUI ui = null;
		ArrayList<TableRowsModeUI> uis = getTableRowsUIs(table);
		if (uis.isEmpty()) throw new RuntimeException("No TableRowsModeUI for table " + table);
		for (TableRowsModeUI rowMode : uis) {
			if (!rowMode.isActive()){
				ui = rowMode;
				this.selectUI(ui);
				ui.setTablr(tablr);
				ui.setWindowManager(this);
				ui.setActive();
				if (uis.size() == 1)
					ui.loadUI(table);
				return;
			}
		}
		if (ui == null) {
			ui = uis.get(0).clone();
			this.addTableRowsModeUI(table, ui);
		}
		this.selectUI(ui);
		ui.setTablr(tablr);
		ui.setWindowManager(this);
		ui.loadUI(table);
		ui.move(-ui.getX() + 300, -ui.getY()+300);
		lastLoad = System.currentTimeMillis();
	}
	
	public void loadFormsModeUI(Table table) {
		DebugPrinter.print("Loading FormsModeUI");
		FormsModeUI ui = null;
		ArrayList<FormsModeUI> uis = getFormsModeUIs(table);
		if (uis.isEmpty()) throw new RuntimeException("No FormModeUI for the table " + table);
		for (FormsModeUI formMode : uis) {
			if (!formMode.isActive()) {
				ui = formMode;
				this.selectUI(ui);
				ui.setTablr(tablr);
				ui.setWindowManager(this);
				ui.setActive();
				if (uis.size() == 1)
					ui.loadUI(table);
				return;
			}
		}
		if (ui == null) {
			ui = uis.get(0).clone();
			this.addFormModeUI(table, ui);
		}
		this.selectUI(ui);
		ui.setTablr(tablr);
		ui.setWindowManager(this);
		ui.loadUI(table);
		ui.move(-ui.getX(), -ui.getY()+300);
	}

	/**
	 * Variable holding the number of milliseconds the last time a new subwindow was loaded.
	 */
	private long lastLoad = 0;

	public void notifyTablesModeUIsColResized(int delta, int index) {
		getTablesModeUIs().forEach(u -> u.columnChanged(delta, index));
	}
	public void notifyTableDesignModeUIsColResized(int delta, int index, Table table) {
		getTableDesignModeUIs(table).forEach(u -> u.columnChanged(delta, index));
	}
	public void notifyTableRowsModeUIsColResized(int delta, int index, Table table) {
		getTableRowsUIs(table).forEach(u -> u.columnChanged(delta, index));
	}
	public void notifyFormsModeUIsColResized(int delta, int index,Table table) {
		getFormsModeUIs(table).forEach(u -> u.columnChanged(delta, index));	
	}
	
	/**
	 * Returns the UI at point (x,y).
	 * If multiple UI's overlap, the current active UI will be returned.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public UI getUIAt(int x, int y) {
		if (selectedUI != null && selectedUI.containsPoint(x, y)) {
			return selectedUI;
		}
		for (UI ui : UIHistory) {
			DebugPrinter.print(ui +"-"+ ui.isActive());
			if (ui.isActive() && ui.containsPoint(x,y)) 
				return ui;
		}
		for (UI ui : getUIs()) {
			DebugPrinter.print(ui +"-"+ ui.isActive());
			if (ui.isActive() && ui.containsPoint(x,y)) 
				return ui;
		}
		return null;
	}
	
	private ArrayList<UI> UIHistory = new ArrayList<UI>();
	
	public void newSelected(UIElement e) {
		getAllElements().stream().forEach(el -> el.selectElement(e));
	}
	
	public void getSelectionLock(UIElement e) {
		this.lockedSelectedElement = e;
	}
	
	public void releaseSelectionLock(UIElement e) {
		if (lockedSelectedElement != e)
			throw new IllegalArgumentException("Trying to release selection lock from non-selected element");
		lockedSelectedElement = null;
	}
	
	public void clearUIAt(int x,int y) {
		getUIAt(x,y).clear();
	}

	public void getLock(UIElement e) {
		hardLockedElement = e;
	}

	public void releaseLock(UIElement e) {
		if (hardLockedElement != e)
			throw new IllegalArgumentException("Trying to release hard lock from non-selected element");
		hardLockedElement = null;	
	}


	public ArrayList<UIElement> getElementsUIAt(int x, int y) {
		return new ArrayList<UIElement>(getUIAt(x,y).getElements());
	}

	public void paint(Graphics g) {
		getUIs().stream().filter(ui -> ui.isActive()).forEach(ui -> ui.paint(g));
		if (selectedUI != null) selectedUI.paint(g);
 	}
	
	public ArrayList<TablesModeUI> getTablesModeUIs() {
		return new ArrayList<TablesModeUI>(uis.get(null).stream().map(u -> (TablesModeUI) u).collect(Collectors.toList()));
	}
	
	public ArrayList<TableDesignModeUI> getTableDesignModeUIs(Table table) {
		DebugPrinter.print(uis.get(table));
		return new ArrayList<TableDesignModeUI>(uis.get(table).stream().filter(u -> u instanceof TableDesignModeUI).map(u -> (TableDesignModeUI) u).collect(Collectors.toList()));
	}
	
	public ArrayList<TableRowsModeUI> getTableRowsUIs(Table table) {
		return new ArrayList<TableRowsModeUI>(uis.get(table).stream().filter(u -> u instanceof TableRowsModeUI).map(u -> (TableRowsModeUI) u).collect(Collectors.toList()));
	}
	
	public ArrayList<FormsModeUI> getFormsModeUIs(Table table){
		return new ArrayList<FormsModeUI>(uis.get(table).stream().filter(ui -> ui instanceof FormsModeUI).map(ui -> (FormsModeUI) ui).collect(Collectors.toList()));
	}

	public UI getSelectedUI() {
		return this.selectedUI;
	}
	
	public void selectUI(UI u) {
		if (u != null && u.equals(selectedUI))
			return;
		if (u != null && !u.equals(selectedUI))
			this.prevSelectedUI = selectedUI;
		if (selectedUI != null) {
			selectedUI.deselect();
		}
		this.selectedUI = u;
		if (u != null) {
			UIHistory.add(0,u);
			u.select();
		}
	}
	
	/**
	 * Selects a new UI from the list of UI's. 
	 * If possible, the previously selected UI becomes selected again.
	 * If for some reason this is not possible (it has been closed, no previously selected UI exists, ...),
	 * the most recently created UI is selected.
	 */
	public void selectNewUI() {
		if (prevSelectedUI != null && prevSelectedUI.isActive()) {
			selectUI(prevSelectedUI);
			return;
		}
		
		ArrayList<UI> reversedUI = (ArrayList<UI>) getUIs().clone();
		Collections.reverse(reversedUI);
		Optional<UI> newSelectedUI = reversedUI.stream().filter(e -> (!e.equals(selectedUI) && e.isActive())).findFirst();
		
		selectUI(newSelectedUI.orElse(null));
	}
	
	/**
	 * The hardlockedElement is the only element that receives
	 * Mouse and keyboard input. Used to lock the UI when an
	 * element is in an incorrent state (e.g. invalid text value).
	 */
	public UIElement hardLockedElement = null;
	
	
	/**
	 * A lockedSelectedElement prevents other elements from being selected.
	 * It does not affect keyboard and mouse input.
	 */
	public UIElement lockedSelectedElement;
	
	/**
	 * @return	null if no element has a hard lock,
	 * 			the locked element otherwise
	 */
	public UIElement getLockedElement() {
		return hardLockedElement;
	}
	
	/**
	 * Variable holding the number of milliseconds since the epoch at the moment Ctrl was pressed.
	 */
	private long lastCtrl = 0;
	
	/**
	 * Returns whether Ctrl has been pressed in the last second
	 * @return	true iff the last time Ctrl was pressed is less than 500 milliseconds ago.
	 */
	public boolean recentCtrl() {
		return (System.currentTimeMillis() - lastCtrl < 500);
	}
	
	/**
	 * Gets called by Tablr to notify the windowManager that Ctrl has been pressed.
	 * Updates the timestamp that keeps track of the last time Ctrl was pressed.
	 */
	public void controlPressed() {
		lastCtrl = System.currentTimeMillis();
	}
	
	/**
	 * Variable holding the number of milliseconds since the epoch at the moment Shift was pressed.
	 */
	private long lastShift = 0;
	
	/**
	 * Returns whether Shift has been pressed in the last second
	 * @return	true iff the last time Shift was pressed is less than 500 milliseconds ago.
	 */
	public boolean recentShift() {
		return (System.currentTimeMillis() - lastShift < 500);
	}
	
	public void shiftPressed() {
		lastShift = System.currentTimeMillis();
	}
	
	/**
	 * Deletes a UI from the list of UI's.
	 * This method only affects the list if there
	 * were multiple UIs of the same type and the same table.
	 * @param ui	The UI to delete
	 */
	public void deleteUI(UI ui) {
		Class c = ui.getClass();
		Set<Table> keys = uis.keySet();
		for (Table t : keys) {
			ArrayList<UI> activeUIs = uis.get(t);
			if (activeUIs.contains(ui)) {
				if (activeUIs.stream().filter((e)->e.getClass().equals(c)).count() > 1) {
					DebugPrinter.print(activeUIs);
					DebugPrinter.print("Multiple UI's for key "+(t==null ? "null" : t.getName())+", removing");
					activeUIs.remove(ui);
					UIHistory.remove(ui);
				}
				else {
					DebugPrinter.print("One UI, no effect");
				}
			}
		}
	}

	/**
	 * Called when a Table has been removed from the domain.
	 * This deactivates all UIs depending on the removed tables.
	 * @param t
	 */
	public void tableRemoved(Table t) { //TODO: nu worden uis wel nooit meer gedelete, ook niet als de tabel definitief weg is (dwz undo zijn creation en dan new command). Solution ergens bij execute new command in domainfacade oplossen?
		FormsModeUI ui = null;
		ArrayList<FormsModeUI> formUIs = getFormsModeUIs(t);
		ArrayList<TableRowsModeUI> rowUIs = getTableRowsUIs(t);
		ArrayList<TableDesignModeUI> designUIs = getTableDesignModeUIs(t);
		if (uis.isEmpty()) throw new RuntimeException("No FormModeUI for the table " + t);
		for (FormsModeUI formMode : formUIs) {
			if (formMode.isActive()) {
				formMode.deactivate();
			}
		}
		for (TableRowsModeUI rowMode : rowUIs) {
			if (rowMode.isActive()) {
				rowMode.deactivate();
			}
		}
		for (TableDesignModeUI designMode : designUIs) {
			if (designMode.isActive()) {
				designMode.deactivate();
			}
		}
	}

	public void notifyKeyListener(int keyCode, char keyChar) {
		keyListener.handleKeyboardEvent(keyCode, keyChar);
		
	}
}


