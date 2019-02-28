package domain;

import java.util.ArrayList;

/**
	 * Manages a collection of tables
	 */
	public class TableManager {
		
		/**
		 * Tables
		 */
		ArrayList<Table> tables = new ArrayList<Table>();
		
		/**
		 * Default constructor, no tables
		 */
		public TableManager() {}
		
		/**
		 * Creates a TableManager from a list of Tables
		 * @param l		List of tables
		 */
		public TableManager(ArrayList<Table> l) {
			this.tables = l;
		}
		
		/**
		 * Adds a new table to the list of tables
		 * @param table		Table to add
		 */
		public void addTable(Table table) {
			this.tables.add(table);
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

}
