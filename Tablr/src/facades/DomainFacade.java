package facades;

import java.util.ArrayList;

import domain.Column;
import domain.Table;
import domain.Type;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;
import ui.UI;

/**
 * Facade for the Domain part of the program. 
 * Methods should always be called via a reference to Tablr,
 * and not directly via this class.
 */
public class DomainFacade {
	
	/**
	 * Tables
	 */
	ArrayList<Table> tables = new ArrayList<>();

	/**
	 * The active table is the table that is used when in
	 * TABLE_DESIGN or TABLE_ROWS mode. UI Elements need to
	 * manually set a new table as active before switching modes.
	 * While in TABLES mode, the variable activeTable may be set
	 * but is not used.
	 */
	private Table activeTable;

	/**
	 * Sets the active table to some table t
	 */
	public void setActiveTable(Table t) {
		this.activeTable = t;
	}

	public Table getActiveTable() {
		return this.activeTable;
	}
	/**
	 * Adds a new table to the list of tables
	 * @param table		Table to add
	 */
	public void addTable(Table table) {
		this.tables.add(table);
	}
	
	/**
	 * Adds an empty table to the list of tables
	 */
	public Table addEmptyTable() {
		String name = nextName();
		Table t = new Table(name);
		addTable(t);
		return t;
	}
	
	/**
	 * Remove tables from the list of tables
	 * @param table		Table to remove
	 */
	public void removeTable(Table table) {
		this.tables.remove(table);
	}
	
	/**
	 * Returns the table
	 */
	public ArrayList<Table> getTables() {
		return new ArrayList<Table>(tables);
	}
	
	/**
	 * This method returns a table from the list of tables, specified using a name.
	 * @param name 	The name of the Table to be found.
	 * @return 	Returns the table with the corresponding name
	 * @return 	Returns null if the table name does not exist in the table manager.s
	 */
	public Table getTable(String name){
		for (Table t: getTables()){
			if (t.getName().equals(name)){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Returns the next logical name for a table.
	 * Logical names are 'TableX' where X is the smallest integer
	 * not yet used as a table name.
	 */
	private String nextName(){
		String name = "Table";
		int i = 0;
		while(name == "Table"){
			if (!getNames().contains(name+i)){
				name = "Table"+i;
			}
			else{
				i++;
			}
		}
		return name;
	}
	
	/**
	 * Returns a list of all table names
	 */
	private ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<String>();
		for(Table t : getTables()){
			names.add(t.getName());
		}
		return names;
	}
	
	/**
	 * Renames a table.
	 * @param t			Table
	 * @param newName	New name
	 */
	public void renameTable(Table t, String newName) {
		t.setName(newName);
	}
	
	/**
	 * Toggles allowing blank values in a column
	 * @param col			The column
	 * @throws Exception	When trying to disallow blanks while the default value is blank
	 */
	public void toggleBlanks(Column col) throws Exception {
		col.toggleBlanks();
		
	}
	
	/**
	 * Adds an empty column to a table
	 * @param table				Table
	 * @param type				Type of the new column
	 * @param defaultValue		The default value of the new column 
	 */
	public void addEmptyColumn(Table table, Type type, Object defaultValue) {
		table.addEmptyColumn(type, defaultValue);
		
	}
	
	/**
	 * Sets the name of a column.
	 * @param col						Column
	 * @param text						Name
	 * @throws InvalidNameException		When this name is already in use in the relevant table.
	 */
	public void setColumnName(Column col, String text) throws InvalidNameException {
		col.setName(text);
		
	}
	
	/**
	 * Adds an empty row to a table
	 * @param tab		Table 
	 */
	public void addRow(Table tab) {
		tab.addRow();
		
	}
	
	/**
	 * Returns a list of all columns in a table
	 * @param tab		Tablr
	 */
	public ArrayList<Column> getColumns(Table tab) {
		return tab.getColumns();
	}

	/**
	 * Gets the value of a cell specified by its index, as a string.
	 * @param col		Column
	 * @param i			Index of the cell
	 * @return			String representation of the column's value
	 */
	public String getValueString(Column col, int i) {
		Object v = col.getCell(i).getValue();
		return v == null ? "" : v.toString();
	}
	
	/**
	 * Returns a list of all column names in a table.
	 * @param table		Table
	 */
	public ArrayList<String> getColumnNames(Table table) {
		return table.getColumnNames();
	}

	/**
	 * Returns the number of rows in a table
	 * @param tab		Table
	 */
	public int getRows(Table tab) {
		return tab.getRows();
	}
	
	/**
	 * Remove a row specified by its index from a table
	 * @param tab		Table
	 * @param index		Index
	 */
	public void removeRow(Table tab, int index) {
		tab.removeRow(index);
		
	}
	
	/**
	 * Remove a column specified by its index from a table
	 * @param tab		Table
	 * @param index		Index
	 */
	public void removeColumn(Table table, int index) {
		table.removeColumn(index);
	}
	
	/**
	 * changes the value of a cell in a column
	 * @param col					Column
	 * @param i						Index
	 * @param string				New Value
	 * @throws ClassCastException	The new value is not valid for the column's type
	 */
	public void changeCellValue(Column col, int i, String string) throws ClassCastException {
		col.changeCellValue(i,string);
		
	}
	
	/**
	 * Changes the type of a column to its next value.
	 * @param col						Column
	 * @throws InvalidTypeException		When changing the type results in invalid value of cells within the column.
	 */
	public void toggleColumnType(Column col) throws InvalidTypeException {
		col.setNextType();
		
	}
	
	/**
	 * Sets the default value for a column
	 * @param col
	 * @param def
	 * @throws ClassCastException
	 */
	public void setDefault(Column col, String def) throws ClassCastException {
		System.out.println("[DomainFacade.java: 208] : Trying to set default value to : " + def);
		col.setDefaultValue(Column.parseValue(col.getColumnType(),def));
	}
	
	/**
	 * Returns the type of this column
	 */
	public Type getColumnType(Column col) {
		return col.getColumnType();
		
	}
	
	/**
	 * Returns the name of a column
	 */
	public String getColumnName(Column col) {
		return col.getName();
	}
	
	/**
	 * Whether this column allows blank values
	 * @param col	Column
	 */
	public boolean getBlankingPolicy(Column col) {
		return col.getBlankingPolicy();
	}
	
	/**
	 * Returns the default value of this column as a string
	 * @param col	Column
	 */
	public String getDefaultString(Column col) {
		Object v = col.getDefault();
		return v == null ? "" : v.toString();
	}
	
	/**
	 * Returns the default value of this column as an Object
	 * @param col	Column
	 */
	public Object getDefaultValue(Column col) {
		return col.getDefault();
	}
	
	/**
	 * Toggles the default value for boolean columns
	 * @param col	Column
	 */
	public void toggleDefault(Column col) {
		col.toggleDefaultBoolean();
		
	}
	
	/**
	 * Sets the type of this column
	 * @param col						Column
	 * @param type						New Type
	 * @throws InvalidTypeException		Changing the type bring the column into an invalid state
	 */
	public void setColumnType(Column col, Type type) throws InvalidTypeException {
		col.setColumnType(type);
		
	}
	
	/**
	 * Toggles the value of a boolean cell, accounting for its current value and column's blanking policy
	 * @param col		Column	
	 * @param i			Index of the cell
	 */
	public void toggleCellValueBoolean(Column col, int i) {
		col.toggleCellValueBoolean(i);
		
	}
	
	/**
	 * Returns the value of a cell in a column
	 * @param col		The Column
	 * @param index		Index of the cell
	 */
	public Object getValue(Column col, int index) {
		return col.getCell(index).getValue();
		
	}
}
