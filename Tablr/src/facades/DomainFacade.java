package facades;

import java.util.ArrayList;

import domain.Column;
import domain.InvalidNameException;
import domain.InvalidTypeException;
import domain.Row;
import domain.Table;
import domain.Type;
import uielements.UI;

/**
 * Facade for the Domain part of the program. 
 * Methods should always be called via a reference to the communicationManager,
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
	 * Reference to the communication manager used to pass this reference
	 * to domain objects.
	 */
	private CommunicationManager communicationManager;
	
	public DomainFacade(CommunicationManager c) {
		communicationManager = c;
	}
	
	public void setCommunicationManager(CommunicationManager c) {
		communicationManager = c;
	}
	
	public CommunicationManager getCommunicationManager() {
		return communicationManager;
	}

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
		t.setCommunicationManager(getCommunicationManager());
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

	private ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<String>();
		for(Table t : getTables()){
			names.add(t.getName());
		}
		return names;
	}
	
	public boolean isValidTableName(String name) {
		int c = 0;
		for (Table t : getTables()) {
			if (t.getName().equals(name)) {
				c++;
			}
		}
		return (c>1);
	}
	
	public void renameTable(Table t, String newName) {
		t.setName(newName);
	}

	public void toggleBlanks(Column col) throws Exception {
		col.toggleBlanks();
		
	}

	public void addEmptyColumn(Table table, Type type, Object defaultValue) {
		table.addEmptyColumn(type, defaultValue);
		
	}

	public void setColumnName(Column col, String text) throws InvalidNameException {
		col.setName(text);
		
	}

	public void addRow(Table tab) {
		tab.addRow();
		
	}

	public ArrayList<Column> getColumns(Table tab) {
		return tab.getColumns();
	}

	public String getValueString(Column col, int i) {
		Object v = col.getCell(i).getValue();
		return v == null ? "" : v.toString();
	}

	public ArrayList<String> getColumnNames(Table table) {
		return table.getColumnNames();
	}

	public ArrayList<Row> getRows(Table tab) {
		return tab.getRows();
	}

	public void removeRow(Table tab, int index) {
		tab.removeRow(index);
		
	}

	public int getIndexOfColumn(Table table, String name) {
		return table.getColumnNames().indexOf(name);
	}

	public void removeColumn(Table table, int index) {
		table.removeColumn(index);
		
	}

	public void changeCellValue(Column col, int i, String string) throws ClassCastException {
		col.changeCellValue(i,string);
		
	}

	public void toggleColumnType(Column col) throws InvalidTypeException {
		col.setNextType();
		
	}

	public void setDefault(Column col, String def) throws ClassCastException {
		System.out.println("[DomainFacade.java: 208] : Trying to set default value to : " + def);
		col.setDefaultValue(Column.parseValue(col.getColumnType(),def));
	}

	public Type getColumnType(Column col) {
		return col.getColumnType();
		
	}

	public String getColumnName(Column col) {
		return col.getName();
	}

	public boolean getBlankingPolicy(Column col) {
		return col.getBlankingPolicy();
	}

	public String getDefaultString(Column col) {
		Object v = col.getDefault();
		return v == null ? "" : v.toString();
	}

	public void toggleDefault(Column col) {
		col.toggleDefaultBoolean();
		
	}

	public void setColumnType(Column col, Type type) throws InvalidTypeException {
		col.setColumnType(type);
		
	}

	public void toggleCellValueBoolean(Column col, int i) {
		col.toggleCellValueBoolean(i);
		
	}

	public Object getValue(Column col, int index) {
		return col.getCell(index).getValue();
		
	}
	

}
