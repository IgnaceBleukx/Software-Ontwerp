package domain;

import java.util.ArrayList;

import Utils.BooleanCaster;
import Utils.DebugPrinter;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;


public class Column {
	
	/**
	 * Extended constructor of the column.
	 * @param newName					The name of this new Column
	 * @param newCells					A Collection of cells that belong to this Column
	 * @param type						The type that values in this Column will have. 
	 * 									Can be any of the following: BOOLEAN, STRING, EMAIL, INT.
	 * 									Types can be found in an Enum under domain/Type.java.
	 * @param defaultValue				The default value for newly added empty cells in this Column.
	 * @throws InvalidNameException 	When another Column has the same proposed name.
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
	 * Constructor of the column. 
	 * @param newName					The name of this Column		
	 * @param newCells					A Collection of cells to be added to this Column
	 * @throws InvalidNameException 	When another Column has the same proposed name.
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
	 * The name of the Column
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
	 * @param name						The name of this Column.
	 * @throws InvalidNameException 	When the Column's table already has a column with this name.
	 */
	public void setName(String name) throws InvalidNameException {
		if (isValidName(name)){
			this.name = name;
		}else throw new InvalidNameException();
	}

	/**
	 * This method checks whether the given column name is valid for the current table.
	 * @param name 		The name to be checked.
	 */
	public boolean isValidName(String name){
		if (this.getTable() == null) return true;
		if (name == "") return false;
		ArrayList<String> columnNames = this.getTable().getColumnNames();
		columnNames.remove(this.getName());
		columnNames.add(name);
		return columnNames.indexOf(name) == columnNames.lastIndexOf(name);
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
	 * @throws InvalidTypeException 	A Cell in this column cannot be interpreted as the new type.
	 * 									For example, a Column filled with strings is not likely to be
	 * 									properly converted to a Column of ints.
	 */
	public void setColumnType(Type type) throws InvalidTypeException {
		for (Cell<?> cell : getCells()){
			String value = cell.getValue()==null ? "" : cell.getValue().toString();
			if (!type.isValidValue(value)) {
				throw new InvalidTypeException();
			}
		}
		if (getDefault() != null && !type.isValidValue(getDefault().toString())){
			throw new InvalidTypeException();
		}else{
			this.type = type;
			if (getDefault() == null) return;
			this.defaultValue = type.parseValue(getDefault().toString());
		}
		for(Cell<?> cell: getCells()) {
			Object value = cell.getValue();
			cells.set(cells.indexOf(cell), new Cell(type.parseValue(value.toString())));
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
	 * This method sets the previous type for the column, the order of which is: INTEGER -> STRING -> EMAIL -> BOOLEAN
	 * @throws InvalidTypeException 
	 * 
	 */
	public void setPreviousType() throws InvalidTypeException{
		this.setColumnType(getPreviousType(getColumnType()));
	}
	
	/**
	 * Does the column allow a blank field
	 * Default value: true
	 */
	private Boolean allowsBlanks = true;
	
	/**
	 * This method returns whether the column allows blanks or not.
	 * @return
	 */
	public Boolean getBlankingPolicy() {
		return allowsBlanks;
	}
	
	/**
	 * Changes whether this column allows blank values
	 * @throws Exception		When trying to disallow blanks while the default value is blank
	 */
	public void toggleBlanks() throws Exception{
		if (allowsBlanks && getDefault() == null || allowsBlanks && getDefault() == "") throw new Exception("The column default is blank");
		for (Cell c : getCells()) {
			if (allowsBlanks && c.getValue() instanceof String && ((String) c.getValue()).isEmpty())
				throw new Exception("A cell is blank!");
			if (allowsBlanks && c.getValue() == null)
				throw new Exception("A cell is blank!");
		}
		allowsBlanks = !allowsBlanks;
	}
	
	/**
	 * The default value for new cells in this Column
	 */
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
	 * @param v							New default value, in a string representation
	 * @throws ClassCastException		When setting a blank default when blanks are not allowed
	 * @throws ClassCastException		If the proposed default value is not valid for the column's type
	 */
	public void setDefaultValue(Object v) throws ClassCastException{
		if ((v == null || v.equals("")) && !getBlankingPolicy()) throw new ClassCastException();
		else if(v == null) this.defaultValue = v;
		else if (this.getColumnType().isValidValue(v.toString())) this.defaultValue = v;
		else throw new ClassCastException();
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
		cells.add(c);
	}
	
	/**
	 * This method adds a collection of cells to the current column.
	 * @param newCells: The cells to be added to the column.
	 */
	public void addAllcells(ArrayList<Cell<?>> newCells) {
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
	 * @return 			The removed cell.
	 */
	public Cell<?> removeCell(int index){
		Cell<?> c = this.cells.remove(index);
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
	}
	
	/**
	 * Adds a blank cell to the column. 
	 * This new blank cell gets its default value from the column type.
	 * @throws 		ClassCastException
	 * 				The column's type is set to an incorrect value. 
	 * 				As a result, no sensible value for the new cell can be found.
	 */
	public void addBlankCell() {
		Cell<?> newCell = null;
		switch(getColumnType()){
			case BOOLEAN : newCell = new Cell<Boolean>((Boolean) getDefault()); break;
			case INTEGER : newCell = new Cell<Integer>((Integer) getDefault()); break;
			case EMAIL   : newCell = new Cell<String> ((String)  getDefault()); break;
			case STRING  : newCell = new Cell<String> ((String)  getDefault()); break;
		}	
		cells.add(newCell);
	}
	

	
	/**
	 * Changes the value of a cell to a value supplied by a string.
	 * This involves casting the string to the correct value
	 * @param i						Index of the cell to change
	 * @param string				String representation of the new value
	 * @throws ClassCastException	The column's type is not set correctly.
	 */
	public void changeCellValue(int i, String string) throws ClassCastException{
		if((string == null || string.isEmpty()) && !getBlankingPolicy()) throw new ClassCastException();
		Cell<?> newCell = null;
		Object v = getColumnType().parseValue(string);
		switch(getColumnType()){
			case BOOLEAN : newCell = new Cell<Boolean>((Boolean) v);break;
			case INTEGER : newCell = new Cell<Integer>((Integer) v);break;
			case EMAIL:  	newCell = new Cell<String>(string); break;
			case STRING: 	newCell = new Cell<String>(string); break;
		}
		cells.remove(i);
		cells.add(i,newCell);		
	}
		
	/**
	 * Toggles whether default values are allowed for a boolean Column
	 */
	public void toggleDefaultBoolean() throws ClassCastException {
		if (!getColumnType().equals(Type.BOOLEAN)) return;
		System.out.println("[Column.java:377] : current default value = " + getDefault());
		Boolean current = (Boolean) getDefault();
		setDefaultValue(nextValueBoolean(current,getBlankingPolicy()));
	}
	
	/**
	 * Returns the next type in the standard order (STRING->EMAIL->BOOLEAN->INTEGER->STRING->...)
	 * @param type		Some type.
	 * @return			Next type
	 */
	public static Type getNextType(Type type){
		switch (type) {
			case STRING: 	return (Type.EMAIL);   
			case EMAIL: 	return (Type.BOOLEAN); 
			case BOOLEAN: 	return (Type.INTEGER); 
			case INTEGER: 	return (Type.STRING); 
			default:  return null;
		}
	}

	/**
	 * Returns the previous type in the standard order ( STRING -> INTEGER -> BOOLEAN -> EMAIL -> STRING ...)
	 * @param type		Some type.
	 * @return			Next type
	 */
	public static Type getPreviousType(Type type){
		switch (type) {
			case STRING: 	return (Type.INTEGER);   
			case EMAIL: 	return (Type.STRING); 
			case BOOLEAN: 	return (Type.EMAIL); 
			case INTEGER: 	return (Type.BOOLEAN); 
			default:  return null;
		}
	}
	
	/**
	 * Toggles the value of a boolean cell based in <i>index</i>, accounting for the Column's blanking policy.
	 * @param index			Index of the cell
	 */
	public void toggleCellValueBoolean(int index){
		if (this.getColumnType() != Type.BOOLEAN) return;
		System.out.println(getCell(index).getValue());
		
		DebugPrinter.print(getCell(index).getValue());
		Boolean cellValue = (Boolean) getCell(index).getValue();
		changeCellValue(index,Type.BOOLEAN.toString(nextValueBoolean(cellValue,getBlankingPolicy())));
//		String cellValueString;
//		if (cellValue == null)
//			cellValueString = null;
//		else cellValueString = cellValue.toString();
//		Boolean value;
//		if (cellValueString == null)
//			value = nextValueBoolean(null,getBlankingPolicy());
//		else
//			value = nextValueBoolean(BooleanCaster.cast(cellValueString),getBlankingPolicy());
//		if (value == null) changeCellValue(index,null);
//		else changeCellValue(index,Boolean.toString(value));
	}
	
	/**
	 * Returns the next correct boolean value given the current value and blanking policy
	 * @param current		Current boolean value
	 * @param blanks		Whether blanks are allowed
	 * @return				The next boolean value
	 */
	public static Boolean nextValueBoolean(Boolean current, boolean blanks){
		if (current == (Boolean) null) return true;
		if (current == true) return false;
		if (current == false && blanks) return null;
		return true;
	}

	public Object getValueAt(int rowN) {
		return cells.get(rowN).getValue();
	}

	public Object getValueAtString(int rowN) {
		return cells.get(rowN).getValue();
	}

	
}
