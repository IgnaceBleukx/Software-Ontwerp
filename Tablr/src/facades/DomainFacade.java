package facades;

import java.util.ArrayList;

import domain.Column;
import domain.Table;
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
	public void addEmptyTable() {
		String name = nextName();
		addTable(new Table(name));
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

	public void toggleBlanks(Column col) {
		System.out.println("Toggeling blanks");
		col.toggleBlanks();
		
	}

	public void addEmptyColumn(Table table) {
		table.addEmptyColumn();
		
	}

	

}
