package facades;

import java.util.ArrayList;

import domain.Column;
import domain.InvalidNameException;
import domain.InvalidTypeException;
import domain.Row;
import domain.Table;
import domain.Type;
import uielements.UI;

public class DomainFacade {
	
	/**
	 * Tables
	 */
	ArrayList<Table> tables = new ArrayList<Table>();
	
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
	 * Adds a new table to the list of tables
	 * @param table		Table to add
	 */
	public void addTable(Table table) {
		System.out.println("Adding table: "+table);
		this.tables.add(table);
		System.out.println("Number of tables: "+tables.size());
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
		System.out.println("Removing table: "+table);
		this.tables.remove(table);
		System.out.println("Number of tables: "+tables.size());
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

	public void toggleBlanks(Column col) {
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

	public Object getValue(Column col, int i) {
		return col.getCell(i).getValue();
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
		Object o = Column.parseValue(col.getColumnType(), def);
		col.changeDefaultValue(col.getColumnType(), o);
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

	public Object getDefault(Column col) {
		return col.getDefault();
	}
	

}
