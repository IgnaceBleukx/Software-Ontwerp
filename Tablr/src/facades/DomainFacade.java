package facades;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Utils.DebugPrinter;
import domain.Column;
import domain.StoredTable;
import domain.Table;
import domain.Type;
import exceptions.BlankException;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;
import ui.UI;

/**
 * Facade for the Domain part of the program. 
 * Methods should always be called via a reference to Tablr,
 * and not directly via this class.
 */
public class DomainFacade {
	
//	TODO: toggleBlanks fixen: checkbox moet rood worden bij default is leeg en dan klikken op checkbox
//			removeRow de undo action is nog niet in orde!!
	
	/**
	 * Tables
	 */
	private ArrayList<Table> tables = new ArrayList<>();

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
		String name = nextTableName();
		StoredTable table = new StoredTable(name);
		
		try {
			execute(new Command() {			
				public void execute() { 		
						addTable(table);
						DebugPrinter.print(table); 
					}

				public void undo() { removeTable(table); }
				
			});
		} catch (InvalidNameException e) {
			throw new RuntimeException("InvalidNameExcpetion while adding emptytable");
		}
		return table;
	}
	
	/**
	 * Remove tables from the list of tables
	 * @param table		Table to remove
	 */
	public void removeTable(Table table) {		
		try {
			execute(new Command() {
				public void execute() { 		
					getTablesPure().remove(table);
				}
				public void undo() { addTable(table); }
			});
		} catch (InvalidNameException e) {
			throw new RuntimeException("InvalidNameException while removing table");
		}
	}
	
	/**
	 * Returns a clone of the tables
	 */
	public ArrayList<Table> getTables() {
		return new ArrayList<Table>(tables);
	}
	
	/**
	 * Returns the tables (no clone)
	 */
	public ArrayList<Table> getTablesPure() {
		return tables;
	}
	
	/**
	 * Returns the next logical name for a table.
	 * Logical names are 'TableX' where X is the smallest integer
	 * not yet used as a table name.
	 */
	private String nextTableName(){
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
	 * @throws InvalidNameException 
	 */
	public void renameTable(Table t, String newName) {
		String currentName = t.getName();
		execute(new Command() {
			public void execute() { 		
				t.setName(newName);;
			}
			public void undo() { t.setName(currentName); }
		});
	}
	
	/**
	 * Toggles allowing blank values in a column
	 * @param col			The column
	 * @throws Exception	When trying to disallow blanks while the default value is blank
	 */
	public void toggleBlanks(Column col) throws BlankException {
		execute(new Command() {
			public void execute() { 		
				col.toggleBlanks();
			}			
			public void undo() { 
				try {
					col.toggleBlanks();
				} catch (Exception e) {
					DebugPrinter.print("column default is blank");
				} 
			}
		});
	}
	
	/**
	 * Adds an empty column to a table
	 * @param table				Table
	 * @param type				Type of the new column
	 * @param defaultValue		The default value of the new column 
	 */
	public void addEmptyColumn(StoredTable table, Type type, Object defaultValue) {	
		execute(new Command() {
			Column column = null;
			public void execute() { column = table.addEmptyColumn(type, defaultValue); }
			public void undo() { table.removeColumn(column);}
		});
	}
	
	public String getTableQuery(Table table) {
		return table.getQueryString();
	}
	
	/**
	 * Sets the name of a column.
	 * @param col						Column
	 * @param text						Name
	 * @throws InvalidNameException		When this name is already in use in the relevant table.
	 */
	public void setColumnName(Column col, String text) throws InvalidNameException {
		execute(new Command(){
			String curText = col.getName();
			public void execute() {
				col.setName(text);
			}
			public void undo() {
				col.setName(curText);
			}
		});
	}
	
	/**
	 * Adds an empty row to a table
	 * @param table		Table 
	 */
	public void addRow(StoredTable table) {
		execute(new Command() {
			int index = table.getRows();
			public void execute() { table.addRow(); }
			public void undo() { table.removeRow(index);}
		});
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
	public void removeRow(StoredTable tab, int index) {
		execute(new Command(){
			public void execute() {tab.removeRow(index);}
			public void undo() {DebugPrinter.print("undo remove a row");}			
		});
	}
	
	/**
	 * Remove a column specified by its index from a table
	 * @param tab		Table
	 * @param index		Index
	 * @throws InvalidNameException 
	 */
	public void removeColumn(Table table, int index) throws InvalidNameException {
		execute(new Command(){
			Column column;
			public void execute() { column = table.removeColumn(index); }
			public void undo() throws InvalidNameException { table.addColumn(column); }	
		});
	}
	
	/**
	 * changes the value of a cell in a column
	 * @param col					Column
	 * @param i						Index
	 * @param string				New Value
	 * @throws ClassCastException	The new value is not valid for the column's type
	 */
	public void changeCellValue(Column col, int i, String string) throws ClassCastException {
		execute(new Command() {
			String prevValue;
			public void execute() { 
				prevValue = col.getCell(i).getValueString(); 
				col.changeCellValue(i,string); 
			}
			public void undo() { 
				col.changeCellValue(i, prevValue); 
			}
		});
	}
	
	/**
	 * Changes the type of a column to its next value.
	 * @param col						Column
	 * @throws InvalidTypeException		When changing the type results in invalid value of cells within the column.
	 */
	public void toggleColumnType(Column col) {
		execute(new Command() {
			public void execute() { 
					col.setNextType();
			 }
			public void undo() { 
				col.setPreviousType();
			}
		});
	}
	
	/**
	 * Sets the default value for a column
	 * @param col
	 * @param def
	 * @throws ClassCastException
	 */
	public void setDefault(Column col, String def) throws ClassCastException {
		execute(new Command() {
				String prev = col.getDefault() == null ? null : col.getDefault().toString();	
				public void execute() { col.setDefaultValue(col.getColumnType().parseValue(def)); }
				public void undo() { col.setDefaultValue(col.getColumnType().parseValue(prev)); }
			});
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
		execute(new Command() {
				public void execute() { col.toggleDefaultBoolean(); }
				public void undo() { col.togglePreviousDefaultBoolean(); }
			});
		}
	
	/**
	 * Sets the type of this column
	 * @param col						Column
	 * @param type						New Type
	 * @throws InvalidTypeException		Changing the type bring the column into an invalid state
	 */
	public void setColumnType(Column col, Type type){
		execute(new Command() {
			public void execute() { 
				col.setColumnType(type);
			}
			public void undo() { 
				col.setColumnType(Column.getPreviousType(type));
			}
		});
		}
	
	/**
	 * Toggles the value of a boolean cell, accounting for its current value and column's blanking policy
	 * @param col		Column	
	 * @param i			Index of the cell
	 */
	public void toggleCellValueBoolean(Column col, int i) {
		try {
			execute(new Command() {
				public void execute() { col.toggleCellValueBoolean(i); DebugPrinter.print("toggle value boolean"); }
				public void undo() {  col.togglePreviousCellValueBoolean(i);  }
			});
		} catch (InvalidNameException e) {
			throw new RuntimeException("InvalidNameException while toggeling cell value boolean");
		}
	}
	
	/**
	 * Returns the value of a cell in a column
	 * @param col		The Column
	 * @param index		Index of the cell
	 */
	public Object getValue(Column col, int index) {
		return col.getCell(index).getValue();
		
	}
	
	// Command methods to fix undo and redo

	
	public interface Command {
		void execute();
		void undo();
	}
	
	private ArrayList<Command> undoStack = new ArrayList<>();
	int nbCommandsUndone = 0;
	
	void undo(){
		DebugPrinter.print(undoStack);
		DebugPrinter.print(nbCommandsUndone);
		if(undoStack.size() > nbCommandsUndone) {
			undoStack.get(undoStack.size() - ++nbCommandsUndone).undo();
		}
	}
	
	void redo(){
		if(nbCommandsUndone > 0)
			undoStack.get(undoStack.size() - nbCommandsUndone--).execute();
	}

	void execute(Command command){
		for(; nbCommandsUndone > 0; nbCommandsUndone--)
			undoStack.remove(undoStack.size() - 1);
		undoStack.add(command);
		command.execute();
	}
	
	void replaceTable(int index, Table newTable) {
		tables.set(index, newTable);
	}

}
