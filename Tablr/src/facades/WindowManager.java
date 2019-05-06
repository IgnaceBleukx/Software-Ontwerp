package facades;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import domain.Table;
import ui.TableDesignModeUI;
import ui.TablesModeUI;
import ui.TableRowsModeUI;
import ui.UI;
import uielements.Text;
import uielements.UIElement;
import uielements.UIRow;

/**
 * Facade for the UI part of the program. 
 * Methods should always be called via a reference to the CommunicationManager,
 * and not directly via this class.
 */
public class WindowManager {
	private Tablr tablr;
	
	public WindowManager(Tablr c) {
		tablr = c;
		tablesModeUIs = new ArrayList<TablesModeUI>();
		tableRowsModeUIs = new HashMap<Table,ArrayList<TableRowsModeUI>>();
		tableDesignModeUIs = new HashMap<Table,ArrayList<TableDesignModeUI>>();
		
	}
	
	public Tablr getCommunicationManager() {
		return tablr;
	}
	
	private ArrayList<TablesModeUI> tablesModeUIs;
	private HashMap<Table,ArrayList<TableRowsModeUI>> tableRowsModeUIs;
	private HashMap<Table,ArrayList<TableDesignModeUI>> tableDesignModeUIs;
	
	/**
	 * The selectedUI is the only UI that receives keyboard input
	 */
	private UI selectedUI;
	
	/**
	 * The previously selected UI is reselected when the current UI is closed.
	 */
	private UI prevSelectedUI;

	
	private ArrayList<UI> getUIs() {
		ArrayList<UI> uis = new ArrayList<>();
		tablesModeUIs.stream().forEach(x -> uis.add(x));
		tableDesignModeUIs.values().stream().forEach(designUIs -> designUIs.stream().forEach(ui -> uis.add(ui)));
		tableRowsModeUIs.values().stream().forEach(rowUIs -> rowUIs.stream().forEach(ui -> uis.add(ui)));
		
//		tableRowsModeUIs.values().stream().forEach(x -> uis.add(x));
//		tableDesignModeUIs.values().stream().forEach(x -> uis.add(x));
		return uis;
	}
	
	/**
	 * Returns all UIElements in ALL UI's
	 */
	private ArrayList<UIElement> getAllElements() {
		ArrayList<UIElement> elements = new ArrayList<>();
		tablesModeUIs.stream().forEach(ui -> elements.addAll(ui.getElements()));
		tableDesignModeUIs.values().stream().forEach(designUIs -> designUIs.stream().forEach(ui -> elements.addAll(ui.getElements())));
		tableRowsModeUIs.values().stream().forEach(rowUIs -> rowUIs.stream().forEach(ui -> elements.addAll(ui.getElements())));
		
//		tableRowsModeUIs.values().stream().forEach(ui -> elements.addAll(ui.getElements()));
//		tableDesignModeUIs.values().stream().forEach(ui -> elements.addAll(ui.getElements()));
		return elements;
	}
	
//	/**
//	 * Returns all UIElements in ALL UI's
//	 */
//	private ArrayList<UIElement> getAllActiveElements() {
//		ArrayList<UIElement> elements = new ArrayList<>();
//		tablesModeUIs.stream().filter(e -> e.isActive()).forEach(ui -> elements.addAll(ui.getElements()));
//		tableRowsModeUIs.values().stream().filter(e -> e.isActive()).forEach(ui -> elements.addAll(ui.getElements()));
//		tableDesignModeUIs.values().stream().filter(e -> e.isActive()).forEach(ui -> elements.addAll(ui.getElements()));
//		return elements;
//	}
	
	public void addTablesModeUI() {
		TablesModeUI ui = null;
		if (tablesModeUIs.isEmpty()) {
			ui = new TablesModeUI(0,0,300,300,tablr);			
		}else {
			for (TablesModeUI tui : tablesModeUIs) {
				if (!tui.isActive()) {
					tui.setActive();
					this.selectUI(tui);
					tui.setTablr(tablr);
					tui.setWindowManager(this);
					return;
				}
			}
			if (ui == null)
				ui = tablesModeUIs.get(0).clone();
		}
		tablesModeUIs.add(ui);
		loadTablesModeUI(ui);
		ui.move(-ui.getX(), -ui.getY());
		
	}
	
	public void loadTablesModeUI(TablesModeUI ui){
//		if (System.currentTimeMillis()-lastLoad<100) return;
		ui.setTablr(tablr);
		ui.setWindowManager(this);
		this.selectUI(ui);
		ui.loadUI();
		lastLoad = System.currentTimeMillis();

	}
	
	public void loadTableRowsModeUI(Table table){
//		if (System.currentTimeMillis()-lastLoad<100) return;

		TableRowsModeUI ui = null;
		ArrayList<TableRowsModeUI> uis = tableRowsModeUIs.get(table);
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
	
	/**
	 * Variable holding the number of milliseconds the last time a new subwindow was loaded.
	 */
	private long lastLoad = 0; 
	
	public void loadTableDesignModeUI(Table table){
//		if (System.currentTimeMillis()-lastLoad<100) return;

		TableDesignModeUI ui = null;
		ArrayList<TableDesignModeUI> uis = tableDesignModeUIs.get(table);
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
	
	public void addTableDesignModeUI(Table table, TableDesignModeUI ui){
		if (!tableDesignModeUIs.containsKey(table))
			tableDesignModeUIs.put(table,new ArrayList<TableDesignModeUI>());
		this.tableDesignModeUIs.get(table).add(ui);
	}
	
	public void addTableRowsModeUI(Table table, TableRowsModeUI ui){
		if (!tableRowsModeUIs.containsKey(table))
			tableRowsModeUIs.put(table, new ArrayList<TableRowsModeUI>());
		this.tableRowsModeUIs.get(table).add(ui);
	}

	public void notifyTablesModeUIsColResized(int delta, int index) {
		tablesModeUIs.stream().forEach(e -> e.getColumnResizeListeners().stream().forEach(l -> l.accept(delta,index)));
	}
	public void notifyTableDesignModeUIsColResized(int delta, int index, Table table) {
		tableDesignModeUIs.get(table).stream().forEach(e -> e.getColumnResizeListeners().stream().forEach(l -> l.accept(delta,index)));
	}
	public void notifyTableRowsModeUIsColResized(int delta, int index, Table table) {
		tableRowsModeUIs.get(table).stream().forEach(e -> e.getColumnResizeListeners().stream().forEach(l -> l.accept(delta,index)));

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
		for (UI ui : getUIs()) {
			System.out.println("[WindowManager.java:121] " + ui + ui.isActive());
			if (ui.isActive() && ui.containsPoint(x,y)) 
				return ui;
		}
		return null;
	}
	
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
		//Paint all UI's that are active
//		tablesModeUIs.stream().filter(u->u.isActive() && !u.equals(selectedUI)).forEach(e -> e.paint(g));
//		tableDesignModeUIs.values().stream().forEach(designUIs -> designUIs.stream().filter(ui -> ui.isActive() && !ui.equals(selectedUI)).forEach(ui -> ui.paint(g)));
//		tableRowsModeUIs.values().stream().forEach(rowUIs -> rowUIs.stream().filter(ui -> ui.isActive() && !ui.equals(selectedUI)).forEach(ui -> ui.paint(g)));
		
		
//		tableRowsModeUIs.values().stream().filter((u) -> u.isActive() && !u.equals(selectedUI)).forEach((e) -> e.paint(g));
//		tableDesignModeUIs.values().stream().filter((u) -> u.isActive() && !u.equals(selectedUI)).forEach((e) -> e.paint(g));
		getUIs().stream().filter(ui -> ui.isActive()).forEach(ui -> ui.paint(g));
		if (selectedUI != null) selectedUI.paint(g);
 	}
	
	public ArrayList<TablesModeUI> getTablesModeUIs() {
		return new ArrayList<TablesModeUI>(tablesModeUIs);
	}
	
	public HashMap<Table,ArrayList<TableDesignModeUI>> getTableDesignUIs() {
		return new HashMap<Table,ArrayList<TableDesignModeUI>>(tableDesignModeUIs);
	}
	
	public HashMap<Table,ArrayList<TableRowsModeUI>> getTableRowsUIs() {
		return new HashMap<Table,ArrayList<TableRowsModeUI>>(tableRowsModeUIs);
	}
	
	public UI getSelectedUI() {
		return this.selectedUI;
	}
	
	public void selectUI(UI u) {
		if (u != null && !u.equals(selectedUI))
			this.prevSelectedUI = selectedUI;
		if (selectedUI != null)
			selectedUI.deselect();
		this.selectedUI = u;
		if (u != null) u.select();
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
	 * @return	true iff the last time Ctrl was pressed is less than 1000 milliseconds ago.
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
	
	// Command methods to fix undo and redo
	
	// TODO: Variabele die bijhoudt welke commandos er recent zijn uitgevoerd
	
	public interface Command {
		void execute();
		void undo();
	}
	
	private ArrayList<Command> undoStack = new ArrayList<>();
	int nbCommandsUndone;
	
	void undo() {
		if(undoStack.size() > nbCommandsUndone)
			undoStack.get(undoStack.size() - ++nbCommandsUndone).undo();
	}
	
	void redo(){
		if(nbCommandsUndone > 0)
			undoStack.get(undoStack.size() - nbCommandsUndone--).execute();
	}

	void execute(Command command){
		System.out.println(undoStack);
		undoStack.add(command);
		command.execute();
	}
	
	void characterTyped(char c) {
		execute(new Command() {
			public void execute() { System.out.println("Command getyped (execute) => " + c); }
			public void undo() { System.out.println("Command getyped (undo) => " + c);}
		});
	}
	
}


