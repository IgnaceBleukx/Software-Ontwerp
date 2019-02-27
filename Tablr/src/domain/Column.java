package domain;

import java.util.ArrayList;

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
	public Column(String name, Type type, Boolean allowsBlanks, String defaultValue, Table table, ArrayList<Cell> cells) {
		this.name= name;
		this.type = type;
		this.allowsBlanks = allowsBlanks;
		this.defaultValue = defaultValue;
		this.table = table;
		this.cells = cells;
	}
	
	/**
	 * Constructor of the column
	 * @param newTable
	 * @param newCells
	 */
	public Column(Table newTable, Cell[] newCells) {
		type = Type.STRING;
		allowsBlanks = true;
		defaultValue = "";
		this.table = newTable;
		//TODO: this.name: Hangt af van de Table!
		//TODO: cells kunnen ook hier gecreëerd worden, moeten niet noodzakelijk meegegeven worden.
	}
	
	/**
	 * the name of the Column
	 */
	private String name;
	
	/**
	 * holds the Type of the Column as a string value
	 * default value: "String"
	 */
	private Type type = Type.STRING;
	
	/**
	 * does the column allow a blank field
	 */
	private Boolean allowsBlanks = true;

	/**
	 * the default value of a column which allows blanks.
	 */
	private String defaultValue = "";
	
	/**
	 * the parent of the column
	 */
	private Table table;
	
	/**
	 * the cells this column contains
	 */
	private ArrayList<Cell> cells;
	
	
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

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	public void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}
}
