package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Column {
	
	/**
	 * Extended constructor of the column.
	 * @param newName
	 * @param newColumnType
	 * @param newAllowsBlanks
	 * @param newDefaultValue
	 * @param newTable
	 * @param newCells
	 */
	public Column(String name, Type type, Boolean allowsBlanks, HashMap<Type, Object> defaultValues, Table table, ArrayList<Cell<?>> newCells) {
		this.name= name;
		this.type = type;
		this.allowsBlanks = allowsBlanks;
		this.defaultValues = defaultValues;
		this.table = table;
		this.cells = cells;
	}
	
	/**
	 * Constructor of the column. This is the standard constructor that will mostly be used in the program.
	 * @param newTable
	 * @param newCells
	 */
	public Column(String newName, Table newTable, ArrayList<Cell<?>> newCells) {
		type = Type.STRING;
		allowsBlanks = true;
		
		//TODO: mijn instinct=dit is lelijk, maar hoe doe je dit anders
		defaultValues.put(Type.STRING, "");
		defaultValues.put(Type.BOOLEAN, null);
		defaultValues.put(Type.EMAIL, "");
		defaultValues.put(Type.INTEGER, null);
		table = newTable;
		name = newName;
		addAllcells(newCells);
		//cells = newCells;
	}

	/**
	 * This method adds a cell to the column and updates its table.
	 * @param c: The cell to be added.
	 */
	public void addCell(Cell<?> c){
		c.setColumn(this);
		c.setTable(this.getTable());
		cells.add(c);
	}
	
	/**
	 * This method adds a collection of cells to the current column.
	 * @param newCells: The cells to be added to the column.
	 */
	private void addAllcells(ArrayList<Cell<?>> newCells) {
		for (Cell<?> c: cells){
			addCell(c);
		}
	}

	
	public void removeCell(Cell<?> cell){
		this.cells.remove(cell);
		cell.setColumn(null);
	}
	/**
	 * the name of the Column
	 * default value: "Column"
	 */
	private String name = "Column";
	
	/**
	 * holds the Type of the Column
	 * default value: String
	 */
	private Type type = Type.STRING;
	
	/**
	 * does the column allow a blank field
	 * default value: true
	 */
	private Boolean allowsBlanks = true;

	/**
	 * the default value of a column which allows blanks.
	 * default value: "", or blank
	 */
	private HashMap<Type, Object> defaultValues = new HashMap<>();
	
	
	/**
	 * the parent table of the column
	 */
	private Table table;
	
	private ArrayList<Cell<?>> cells;
	
	/**
	 * This method returns the default value of the current columnType.
	 */
	public String getDefault(){
		return (String) defaultValues.get(getColumnType());
	}
	
	public void setDefault(Type t, Object o) throws ClassCastException{
		switch (t){
				case STRING : defaultValues.put(Type.STRING,(String)o);
				case BOOLEAN : defaultValues.put(Type.BOOLEAN, (Boolean) o);
				case EMAIL : defaultValues.put(Type.EMAIL,(String)o);
				case INTEGER : defaultValues.put(Type.INTEGER, (Integer) o);
			}
	}
	
	/**
	 * This method sets the next type for the column, the order of which is: STRING -> EMAIL -> BOOLEAN -> INTEGER
	 * 
	 */
	public void setNextType(){
		switch(getColumnType()){
			case STRING : setColumnType(Type.EMAIL);
			case EMAIL: setColumnType(Type.BOOLEAN);
			case BOOLEAN: setColumnType(Type.INTEGER);
			case INTEGER: setColumnType(Type.STRING);
		}
	}
	
	/**
	 * Delete all the contents of the column and the references to it.
	 */
	public void terminate(){
		table.removeColumn(this);
		for (Cell cell: this.getCells()){
			cell.terminate();
		}
	}
	
//	private void changeAllowsBlanks(){
//		if (allowsBlanks && defaultValue){
//			
//		}
//	}

	/**
	 * This method returns the name of the Column
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name of the column.
	 * @param name	The name to be set.
	 */
	public void setName(String name) {
		if (isValidName(name)){
			this.name = name;
		}
	}

	/**
	 * This method checks whether the given column name is valid for the current table.
	 * @param name 	The name to be checked.
	 */
	public boolean isValidName(String name){
		return this.getTable().getColumnNames().contains(name);
	}
	
	/**
	 * This method returns the type of the current column.-
	 */
	public Type getColumnType() {
		return this.type;
	}

	/**
	 * This method sets the column type of the current column.
	 */
	public void setColumnType(Type type) {
		this.type = type;
	}
	
	/**
	 * This method returns whether the column allows blanks or not.
	 * @return
	 */
	public Boolean getBlankingPolicy() {
		return allowsBlanks;
	}

	/**
	 * This methods allows the column to have blanks.
	 */
	public void allowBlanks() {
		this.allowsBlanks = true;
	}
	
	/**
	 * This method forbids the method to have blanks.
	 */
	public void forbidBlanks(){
		this.allowsBlanks = false;
	}

	/**
	 * This method returns the table of the current column.
	 * @return
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * This method sets the table of the current column, as well as the table of all its containing cells.
	 * @param table
	 */
	public void setTable(Table table) {
		this.table = table;
		for (Cell c: this.getCells()){
			c.setTable(table);
		}
	}

	/**
	 * This method returns a copy of the cells the column contains.
	 */
	public ArrayList<Cell<?>> getCells() {
		return (ArrayList<Cell<?>>) this.cells.clone();
	}

		
	
}
