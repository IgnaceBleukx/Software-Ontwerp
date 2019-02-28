package domain;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Column {

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
	
	private ArrayList<Cell<?>> cells;
	
	/**
	 * @return the current default value for the current column Type.
	 */
	public String getDefault(){
		return (String) defaultValues.get(getColumnType());
	}
	
	public void setDefault(Type t, Object o){
		try {
			switch (t){
				case STRING : defaultValues.put(Type.STRING,(String)o);
				case BOOLEAN : defaultValues.put(Type.BOOLEAN, (Boolean) o);
				case EMAIL : defaultValues.put(Type.EMAIL,(String)o);
				case INTEGER : defaultValues.put(Type.INTEGER, (Integer) o);
			}
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * add a cell to the end of this column
	 * @param cell
	 */
	public void add(Cell cell){
		cells.add(cell);
	}	
	
	/**
	 * removes a cell from the list of cells
	 * @param cell
	 */
	public void remove(Cell cell){
		cells.remove(cell);
	}

	/**
	 * delete this column
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

	public void setName(String name) {
		this.name = name;
	}

	public Type getColumnType() {
		return this.type;
	}

	public void setColumnType(Type type) {
		this.type = type;
	}
	public Boolean getAllowsBlanks() {
		return allowsBlanks;
	}

	public void setAllowsBlanks(Boolean allowsBlanks) {
		this.allowsBlanks = allowsBlanks;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public ArrayList<Cell<?>> getCells() {
		return cells;
	}

		
	
}
