package domain;

public class Cell<T> {

	/**
	 * The constructor of the Cell
	 * @param v: the value of the cell
	 */
	public Cell(T v, Row r, Column c, Table t){
		this.setValue(v);
		this.row = r;
		this.column = c;
		this.table = t;
	}
	
	/**
	 * This method returns the value of the current cell.
	 */
	public T getValue(){
		return this.value;
	}
	
	/**
	 * This method sets the value of the current cell.
	 * @param v: the value to be set.
	 */
	public void setValue(T v){
		this.value = v;
	}
	
	/**
	 * This method returns the row of the current Cell.
	 */
	public Row getRow() {
		return row;
	}

	/**
	 * This method returns the table of the current Cell.
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * This method returns the column of the current Cell.
	 */
	public Column getColumn() {
		return column;
	}

	private final Row row;
	private final Table table;
	private final Column column;
	
	
	public void delete(){
		
	}
	
	private T value;
}
