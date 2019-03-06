package domain;

import java.util.ArrayList;
import java.util.HashMap;

import facades.CommunicationManager;

public class Column extends DomainElement {
	
	/**
	 * Extended constructor of the column.
	 * @param newName
	 * @param newColumnType
	 * @param newAllowsBlanks
	 * @param newDefaultValue
	 * @param newTable
	 * @param newCells
	 */
//	public Column(String name, Type type, Boolean allowsBlanks, HashMap<Type, Object> defaultValues, Table table, ArrayList<Cell<?>> newCells) {
//		this.name= name;
//		this.type = type;
//		this.allowsBlanks = allowsBlanks;
//		this.defaultValues = defaultValues;
//		this.table = table;
//		this.cells = newCells;
//	}
	
	/**
	 * Constructor of the column. This is the standard constructor that will mostly be used in the program.
	 * @param newTable
	 * @param newCells
	 */
	public Column(String newName, ArrayList<Cell<?>> newCells) {
		setColumnType(Type.STRING);
		allowsBlanks = true;
		setName(newName);
		addAllcells(newCells);
	}

	
	/**
	 * the name of the Column
	 * default value: "Column"
	 */
	private String name = "Column";
	
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
		return table == null ? true : !this.getTable().getColumnNames().contains(name);
	}
	
	
	/**
	 * Holds the Type of the Column
	 * default value: String
	 */
	private Type type = Type.STRING;
	
	/**
	 * This method returns the type of the current column.
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
	 * This method sets the next type for the column, the order of which is: STRING -> EMAIL -> BOOLEAN -> INTEGER
	 * 
	 */
	public void setNextType(){
		Type t = this.getColumnType();
		switch (t) {
			case STRING: 	setColumnType(Type.EMAIL);   break;
			case EMAIL: 	setColumnType(Type.BOOLEAN); break;
			case BOOLEAN: 	setColumnType(Type.INTEGER); break;
			case INTEGER: 	setColumnType(Type.STRING);  break;
		}
	}
	
	
	/**
	 * does the column allow a blank field
	 * default value: true
	 */
	private Boolean allowsBlanks = true;
	
	/**
	 * This method returns whether the column allows blanks or not.
	 * @return
	 */
	public Boolean getBlankingPolicy() {
		return allowsBlanks;
	}

	public void toggleBlanks(){
		allowsBlanks = !allowsBlanks;
	}
	
	
	/**
	 * The default value of a column which allows blanks.
	 */
	private HashMap<Type, Object> defaultValues = new HashMap<Type, Object>(){{ //TODO: nog steeds probleem dat je gewoon "banaan" kunt ingeven in INTEGER
													put(Type.STRING,  "");
													put(Type.BOOLEAN, false);
													put(Type.EMAIL,   "");
													put(Type.INTEGER, 0);//TODO: dit moet eigenlijk null zijn, nee?
	}};
	
	/**
	 * This method sets the default value for a given type.
	 * @param t 	The type of which the default value must be set.
	 * @param o 	The Value of the type.
	 * @throws ClassCastException 	This exception is thrown when the given value is non-valid for the given Type.
	 */
	public void setDefault(Type t, Object o) throws ClassCastException{
		switch (t){
				case STRING : defaultValues.put(Type.STRING,(String)o); break;
				case BOOLEAN : defaultValues.put(Type.BOOLEAN, (Boolean) o); break;
				case EMAIL : defaultValues.put(Type.EMAIL,(String)o); break;
				case INTEGER : defaultValues.put(Type.INTEGER, (Integer) o); break;
			}
	}
		
	/**
	 * This method returns the default value of the current columnType.
	 */
	public Object getDefault(){
		return defaultValues.get(getColumnType());
	}
		
	
	private ArrayList<Cell<?>> cells = new ArrayList<Cell<?>>();
	
	/**
	 * This method adds a cell to the column and updates its table.
	 * @param c: The cell to be added.
	 */
	public void addCell(Cell<?> c){
		c.setColumn(this);
		cells.add(c);
	}
	
	/**
	 * This method adds a collection of cells to the current column.
	 * @param newCells: The cells to be added to the column.
	 */
	private void addAllcells(ArrayList<Cell<?>> newCells) {
		for (Cell<?> c: newCells){
			addCell(c);
		}
	}

	/**
	 * This method returns a copy of the cells the column contains.
	 */
	public ArrayList<Cell<?>> getCells() {
		return (ArrayList<Cell<?>>) this.cells.clone();
	}
	
	/**
	 * This method removes a cell from the Column.
	 * @param cell 	The cell to be removed.
	 */
	public void removeCell(Cell<?> cell){
		removeCell(cells.indexOf(cell));
	}
	
	/**
	 * This method removes a cell from the column based on the index of the cell
	 * @param index 	The index on which the cell must be removed.
	 * @return 	The removed cell.
	 */
	public Cell<?> removeCell(int index){
		Cell<?> c = this.cells.remove(index);
		if (c != null){
			c.setColumn(null);
		}
		return c;
	}
	

	/**
	 * The parent table of the column
	 */
	private Table table;
	
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
	}
	

	/**
	 * Delete all the contents of the column and the references to it.
	 */
	public void terminate(){
		table.removeColumn(this);
		for (Cell<?> cell: this.getCells()){
			cell.terminate();
		}
	}

	public void addBlankCell() {
		switch(getColumnType()){
			case BOOLEAN : cells.add(new Cell<Boolean>((Boolean) getDefault())); break;
			case INTEGER : cells.add(new Cell<Integer>((Integer) getDefault())); break;
			case EMAIL   : cells.add(new Cell<String> ((String)  getDefault())); break;
			case STRING  : cells.add(new Cell<String> ((String)  getDefault())); break;
		}		
	}
	
	@Override
	public void setCommunicationManager(CommunicationManager c) {
		this.communicationManager = c;
		for (Cell e : getCells()) {
			e.setCommunicationManager(c);
		}
	}

	

	

		
	
}
