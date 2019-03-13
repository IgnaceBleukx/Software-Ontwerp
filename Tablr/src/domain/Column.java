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
	 * @throws InvalidNameException 
	 */
	public Column(String newName, ArrayList<Cell<?>> newCells, Type type, Object defaultValue) throws InvalidNameException{
		this.setName(newName);
		this.setDefaultValue(defaultValue);
		try {
			setColumnType(type);
		} catch (InvalidTypeException e) {
			//This exception is never thrown because the column is still empty.
		}
		allowsBlanks = true;
		addAllcells(newCells);
	}
	
	/**
	 * Constructor of the column. This is the standard constructor that will mostly be used in the program.
	 * @param newTable
	 * @param newCells
	 * @throws InvalidNameException 
	 */
	public Column(String newName, ArrayList<Cell<?>> newCells) throws InvalidNameException {
		try {
			setColumnType(Type.STRING);
		} catch (InvalidTypeException e) {
			//This exception is never thrown because the column is still empty.
		}
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
	 * @throws InvalidNameException 
	 */
	public void setName(String name) throws InvalidNameException {
		if (isValidName(name)){
			this.name = name;
		}else throw new InvalidNameException();
	}

	/**
	 * This method checks whether the given column name is valid for the current table.
	 * @param name 	The name to be checked.
	 */
	public boolean isValidName(String name){
		return table == null ? true : name != "" && !this.getTable().getColumnNames().contains(name);
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
	 * @throws InvalidTypeException 
	 */
	public void setColumnType(Type type) throws InvalidTypeException {
		for (Cell<?> cell : getCells()){
			if (!isValidValue(type,cell.getValue().toString())) {
				throw new InvalidTypeException();
			}
		}
		System.out.println("[Column.java:106]: Trying to set type "+ type + "while default value is: " + getDefault());
		if (getDefault() != null && !isValidValue(type,getDefault().toString())){
			System.out.println("[Column.java:108] Throwing invalidTypeException" );
			throw new InvalidTypeException();
		}else{
			this.type = type;
			if (getDefault() == null) return;
			this.defaultValue = parseValue(type,getDefault().toString());
		}
	}
	
	/**
	 * This method sets the next type for the column, the order of which is: STRING -> EMAIL -> BOOLEAN -> INTEGER
	 * @throws InvalidTypeException 
	 * 
	 */
	public void setNextType() throws InvalidTypeException{
		this.setColumnType(getNextType(getColumnType()));
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

	public void toggleBlanks() throws Exception{
		if (allowsBlanks && getDefault() == null) throw new Exception("The column default is blank");
		allowsBlanks = !allowsBlanks;
	}
	
	private Object defaultValue;
		
	/**
	 * This method returns the default value of the current columnType.
	 */
	public Object getDefault(){
		return defaultValue;
	}
	
	/**
	 * Sets the default value for this column. 
	 * Succeeds only when the new default value is a valid value for the current column type.
	 * @param v		New default value
	 */
	public void setDefaultValue(Object v){
		if (v == null) {
			this.defaultValue = null; 
			return;
		}
		if (isValidValue(getColumnType(),v.toString())) this.defaultValue = v;
		else throw new IllegalArgumentException();
	}
		
	/**
	 * List of cells in this Column.
	 */
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
		if (newCells == null) return;
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
	 * Returns the cell at the position specified by 'index'.
	 * @param index		Position of the returned cell
	 * @return			Cell c
	 */
	public Cell<?> getCell(int index){
		return this.cells.get(index);
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
	
	/**
	 * Adds a blank cell to the column. 
	 * This new blank cell gets its default value from the column type.
	 * @throws 		ClassCastException
	 * 				The column's type is set to an incorrect value. 
	 * 				As a result, no sensible value for the new cell can be found.
	 */
	public void addBlankCell() {
		Cell<?> newCell;
		switch(getColumnType()){
			case BOOLEAN : newCell = new Cell<Boolean>((Boolean) getDefault()); break;
			case INTEGER : newCell = new Cell<Integer>((Integer) getDefault()); break;
			case EMAIL   : newCell = new Cell<String> ((String)  getDefault()); break;
			case STRING  : newCell = new Cell<String> ((String)  getDefault()); break;
			default: throw new ClassCastException();
		}	
		cells.add(newCell);
		newCell.setCommunicationManager(getCommunicationManager());
	}
	
	/**
	 * Sets he communication manager for this column.
	 * Also sets the communication manager for all cells in this column.
	 * @param c		New CommunicationManager instance for this column.
	 */
	@Override
	public void setCommunicationManager(CommunicationManager c) {
		this.communicationManager = c;
		for (Cell<?> e : getCells()) {
			e.setCommunicationManager(c);
		}
	}
	
	/**
	 * Changes the value of a cell to a value supplied by a string.
	 * This involves casting the string to the correct value
	 * @param i			Index of the cell to change
	 * @param string	String representation of the new value
	 * @throws ClassCastException
	 * 					The column's type is not set correctly.
	 */
	public void changeCellValue(int i, String string) throws ClassCastException{
		Cell<?> newCell;
		Object v = parseValue(getColumnType(),string);
		switch(getColumnType()){
			case BOOLEAN : newCell = new Cell<Boolean>((Boolean) v);break;
			case INTEGER : newCell = new Cell<Integer>((Integer) v);break;
			case EMAIL:  	newCell = new Cell<String>(string); break;
			case STRING: 	newCell = new Cell<String>(string); break;
			default : throw new ClassCastException();
		}
		newCell.setCommunicationManager(getCommunicationManager());
		newCell.setColumn(this);
		cells.remove(i);
		cells.add(i,newCell);		
	}
	
	/**
	 * Returns whether a value supplied in a string is a valid value for a certain column type.
	 * @param type		Column type
	 * @param string	Value 
	 */
	public static boolean isValidValue(Type type, String string){
		if (string == "" || string == null) return true;
		switch(type){
			case BOOLEAN : {
				return (string.equals("False") || string.equals("false") || string.equals("True") || string.equals("true"));
			}
			case INTEGER : try {
				Integer.parseInt(string);
				return true;
			}catch (NumberFormatException e){
				return false;
			}
			case STRING : return true;
			case EMAIL : return (string.indexOf("@") == string.lastIndexOf("@") && string.indexOf("@") != -1);
			default :  return false;
		}
	}
	
	/**
	 * Parses a value in a string to a certain type, if possible.
	 * @param type		Type to parse to
	 * @param string	Value in string form
	 * @throws ClassCastException
	 * 					The string cannot be parsed to the supplied type.
	 * 					E.G. "abc" cannot be parsed to Int
	 */
	public static Object parseValue(Type type, String string) throws ClassCastException {
		if (string == "" && type != Type.STRING && type != Type.EMAIL || string == null) return null;
		else if (!isValidValue(type, string)) throw new ClassCastException();
		else{
			switch(type){
				case BOOLEAN: {
					return Boolean.parseBoolean(string); 
				}
				case INTEGER : {
					return Integer.parseInt(string);
				}
				case STRING : return string;
				case EMAIL: return string;
				default : return string;
			}
			 
		}
	}

	public void toggleDefaultBoolean() {
		if (getColumnType() != Type.BOOLEAN) return;
//		if (getDefault() == null) setDefaultValue(true);
//		else if ((boolean)getDefault() == true) setDefaultValue(false);
//		else if ((boolean)getDefault() == false && getBlankingPolicy()) setDefaultValue(null);
//		else if ((boolean)getDefault() == false && !getBlankingPolicy()) setDefaultValue(true);
		System.out.println("[Column.java:377] : current default value = " + getDefault());
		Boolean current;
		if (getDefault() == null) current = (Boolean) null;
		else current = (boolean) getDefault();
		setDefaultValue(
				nextValueBoolean(
						current,
						getBlankingPolicy()));
	}
	
	public static Type getNextType(Type type){
		switch (type) {
			case STRING: 	return (Type.EMAIL);   
			case EMAIL: 	return (Type.BOOLEAN); 
			case BOOLEAN: 	return (Type.INTEGER); 
			case INTEGER: 	return (Type.STRING); 
			default:  return null;
		}
	}

	public void toggleCellValueBoolean(int index){
		if (this.getColumnType() != Type.BOOLEAN) return;
		boolean value = nextValueBoolean((Boolean)getCell(index).getValue(),getBlankingPolicy());
		changeCellValue(index,Boolean.toString(value));
	}
	
	public static Boolean nextValueBoolean(Boolean current, boolean blanks){
		if (current == (Boolean) null) return true;
		if (current == true) return false;
		if (current == false && blanks) return null;
		return true;
	}

	
}
