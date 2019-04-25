package facades;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Column;
import domain.Table;
import domain.Type;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;
import ui.TableDesignModeUI;
import ui.TableRowsModeUI;
import ui.TablesModeUI;
import ui.UI;
import uielements.UIElement;

/**
 * Class holding all methods that are used by UIs to modify the domain.
 * Tablr delegates its calls to domainFacade or WindowManager to reduce coupling between
 * UI and Domain, because UIs only need a reference to Tablr.
 *
 */
public class Tablr {
	
	private DomainFacade domainFacade;
	private WindowManager windowManager;
	
	/**
	 * Creates a new Tablr instance, creates the necessary objects to access domain and UI.
	 */
	public Tablr() {
		domainFacade = new DomainFacade();
		windowManager = new WindowManager(this);
	}
	
	/**
	 * @return	The active table.
	 */
	public Table getActiveTable() {
		return domainFacade.getActiveTable();
	}
	
	
	/**
	 * Returns whether the given table is empty, i.e. contains no columns (and thus no rows).
	 * @param t		Table
	 * @return		Is this table empty?
	 */
	public boolean isEmptyTable(Table t) {
		return (domainFacade.getColumns(t).size() == 0);
	}
	

	public void addTablesModeUI() {
		windowManager.addTablesModeUI();
	}
	
	public void loadTableRowsModeUI(Table table){
		windowManager.loadTableRowsModeUI(table);
	}
	
	public void loadTableDesignModeUI(Table table) {
		windowManager.loadTableDesignModeUI(table);
	}
	
	/**
	 * Adds an empty table to the domain.
	 * @return	The newly added table.
	 */
	public Table addEmptyTable() {
		Table t = domainFacade.addEmptyTable();
		windowManager.addTableDesignModeUI(t, new TableDesignModeUI(300,0,300,300,this));
		windowManager.addTableRowsModeUI(t, new TableRowsModeUI(300, 300, 300, 300, this));
		domainChanged();
		return t;
	}
	
	/**
	 * Returns a copy of the list of all tables.
	 * @return	A list containing all tables the domain knows of.
	 */
	public ArrayList<Table> getTables() {
		return new ArrayList<Table>(domainFacade.getTables());
	}
	
	/**
	 * Gets the UI at a given point. The currently selected UI will always be found first. 
	 * Behaviour is unpredictable when multiple non-selected UI's overlap.
	 * @param x		X coordinate
	 * @param y		Y coordinate
	 * @return		UI at point (x,y)
	 */
	public UI getUIAt(int x,int y) {
		return windowManager.getUIAt(x,y);
	}
	
	/**
	 * Removes a given table from the domain.
	 * @param t		Table to delete.
	 */
	public void removeTable(Table t) {
		domainFacade.removeTable(t);
		domainChanged();
	}
	
	/**
	 * Returns a list of tables identified by their name.
	 * @param name		Name to search for
	 * @return			List containing all tables with the given name.
	 */
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
	
	/**
	 * Renames a table.
	 * @param t		Table to rename
	 * @param name	New name for the table
	 */
	public void renameTable(Table t, String name) {
		domainFacade.renameTable(t, name);
	}
	
	/**
	 * Toggles whether blanks are allowed for a given column.
	 * @param col				Column to modify.
	 * @throws Exception		When toggling this option brings the column to an invalid state. 
	 * 							For details, see 'toggleBlanks()' in domain/Column
	 */
	public void toggleBlanks(Column col) throws Exception{
		domainFacade.toggleBlanks(col);
		domainChanged();
	}
	
	/**
	 * Adds an empty column to a table.
	 * @param table			Table
	 * @param type			Type of the new column (see domain/Type)
	 * @param defaultValue	Default value for the new column.
	 */
	public void addEmptyColumn(Table table, Type type, Object defaultValue) {
		domainFacade.addEmptyColumn(table,type, defaultValue);
		domainChanged();
	}

	/**
	 * Sets the name of a column
	 * @param col						Column to modify
	 * @param text						New name for column
	 * @throws InvalidNameException		When the name is invalid - see domain/Column::isValidName()
	 */
	public void setColumnName(Column col, String text) throws InvalidNameException {
		domainFacade.setColumnName(col,text);
	}
	
	/**
	 * Adds a row to a table
	 * @param tab		Table
	 */
	public void addRow(Table tab) {
		domainFacade.addRow(tab);
		domainChanged();
	}
	
	/**
	 * Returns all columns that belong to a given table.
	 * @param tab		Table
	 * @return			List containing all columns of that table.
	 */
	public ArrayList<Column> getColumns(Table tab) {
		return domainFacade.getColumns(tab);
	}

	/**
	 * Gets the value of a cell as a String
	 * @param col		Column the cell belongs to.
	 * @param i			Index of cel within column
	 * @return			Contents of the cell as a String
	 */
	public String getValueString(Column col, int i) {
		return domainFacade.getValueString(col,i);
	}
	
	/**
	 * Returns all names of columns in a certain table
	 * @param table 	Table
	 */
	public ArrayList<String> getColumnNames(Table table) {
		return domainFacade.getColumnNames(table);
	}

	/**
	 * Returns all rows in a certain table
	 * @param tab		Table
	 */
	public int getRows(Table tab) {
		return domainFacade.getRows(tab);
	}
	
	/**
	 * Removes a row from a table
	 * @param tab		Table
	 * @param index		Index of row within Table
	 */
	public void removeRow(Table tab, int index) {
		domainFacade.removeRow(tab,index);
		domainChanged();
	}
	
	/**
	 * Removes a column from a table.
	 * @param table		Table
	 * @param index		Index of Column within Table
	 */
	public void removeColumn(Table table, int index) {
		domainFacade.removeColumn(table, index);
		domainChanged();
	}
	
	/**
	 * Notifies all elements that a new UIElement has been selected.
	 * @param e		Selected element
	 */
	public void notifyNewSelected(UIElement e) {
		windowManager.newSelected(e);
	}
	
	/**
	 * Changes the contents of a cell
	 * @param col					Column
	 * @param i						Index within column
	 * @param string				String representation of new cell contents
	 * @throws ClassCastException	Invalid content for Column type - e.g. '1234' is not a valid EMAIL
	 */
	public void changeCellValue(Column col, int i, String string) throws ClassCastException {
		domainFacade.changeCellValue(col,i,string);
		//domainChanged();
	}

	/**
	 * Toggles the type of a column
	 * @param col						Column
	 * @throws InvalidTypeException		Some value in the column cannot be interpreted as the new Type
	 */
	public void toggleColumnType(Column col) throws InvalidTypeException {
		domainFacade.toggleColumnType(col);
		domainChanged();
	}
	
	/**
	 * Sets the default value of a column
	 * @param col					Column
	 * @param def					New default value
	 * @throws ClassCastException	Not a valid default value
	 */
	public void setDefault(Column col, String def) throws ClassCastException {
		domainFacade.setDefault(col,def);
	}

	/**
	 * Returns the type of a column
	 * @param col			Column
	 */
	public Type getColumnType(Column col) {
		return domainFacade.getColumnType(col);
	}

	/**
	 * Returns the name of a column
	 * @param col		Column	
	 * @return			The column's name
	 */
	public String getColumnName(Column col) {
		return domainFacade.getColumnName(col);
	}

	/**
	 * Returns whether a column allows blank values.
	 * @param col	Column
	 */
	public boolean getBlankingPolicy(Column col) {
		return domainFacade.getBlankingPolicy(col);
	}

	/**
	 * Returns the default value of a column as a String
	 * @param col 	Column
	 */
	public String getDefaultString(Column col) {
		return domainFacade.getDefaultString(col);
	}
	
	/**
	 * Returns the default value of a Column as an Object
	 * @param col	Column
	 */
	public Object getDefaultValue(Column col) {
		return domainFacade.getDefaultValue(col);
	}
	
	/**
	 * Sets the selection lock to a new UIElement.
	 * When a UIElement has the selectionLock, no other elements can be selected until the lock is released.
	 * @param e		UIElement
	 */
	public void getSelectionLock(UIElement e) {
		windowManager.getSelectionLock(e);
	}
	
	/**
	 * Releases the selection lock from an element
	 * @param e		Selected element
	 */
	public void releaseSelectionLock(UIElement e) {
		windowManager.releaseSelectionLock(e);
	}

	/**
	 * Toggles the default value for a Column. Only has an effect on BOOLEAN columns
	 * @param col	Column to change default value
	 */
	public void toggleDefault(Column col) {
		domainFacade.toggleDefault(col);
		domainChanged();
	}
	
	/**
	 * Sets a hardlock on an element. A hard lock prevents any input to reach UIElements other than the locked elements.
	 */
	public void getLock(UIElement e) {
		windowManager.getLock(e);
	}

	/**
	 * Releases the hard lock from an object.
	 * @param e		Hardlocked element
	 */
	public void releaseLock(UIElement e) {
		windowManager.releaseLock(e);
		
	}

	/**
	 * Sets the type for a certain column
	 * @param col						Column
	 * @param type						New type
	 * @throws InvalidTypeException		Values in this colum prevent the new type.
	 */
	public void setColumnType(Column col, Type type) throws InvalidTypeException {
		domainFacade.setColumnType(col,type);
		domainChanged();
	}
	
	/**
	 * A list of action to be performed when the domain changes.
	 */
	private ArrayList<Runnable> DomainChangedListeners = new ArrayList<Runnable>();
	

	/**
	 * Toggles the value of a cell. Only affects cells that belong to a BOOLEAN column
	 * @param col		Column
	 * @param i			Index of cell within column
	 */
	public void toggleCellValueBoolean(Column col, int i) {
		domainFacade.toggleCellValueBoolean(col,i);
		domainChanged();
	}

	/**
	 * Called when the domain has changed. Runs all necessary actions.
	 */
	public void domainChanged(){
		DomainChangedListeners.stream().forEach(l -> l.run());
		
	}
	
	/**
	 * Adds a Runnable to the list of actions that is executed when the domain changes.
	 * @param r		Runnable
	 */
	public void addDomainChangedListener(Runnable r){
		this.DomainChangedListeners.add(r);
	}

	/**
	 * Paints the canvas.
	 * @param g
	 */
	public void paint(Graphics g) {
		windowManager.paint(g);
	}
	
	/**
	 * Returns the currently selected UI
	 * @return	UI
	 */
	public UI getSelectedUI() {
		return windowManager.getSelectedUI();
	}
	
	/**
	 * Selects a new UI.
	 * @param e		New active UI.
	 */
	public void selectUI(UI e) {
		windowManager.selectUI(e);
	}
	
	/**
	 * Returns the UIElement that has a hard lock.
	 * @return	UIElement with hard lock.
	 */
	public UIElement getLockedElement() {
		return windowManager.getLockedElement();
	}
	
	public ArrayList<TablesModeUI> getTablesModeUIs() {
		return windowManager.getTablesModeUIs();
	}
	
	public HashMap<Table,ArrayList<TableDesignModeUI>> getTableDesignUIs() {
		return windowManager.getTableDesignUIs();
	}
	
	public HashMap<Table,ArrayList<TableRowsModeUI>> getTableRowsUIs() {
		return windowManager.getTableRowsUIs();
	}
	
	
	/**
	 * Notifies the windowManager that Ctrl has been pressed.
	 */
	public void controlPressed() {
		windowManager.controlPressed();
	}
	
}
