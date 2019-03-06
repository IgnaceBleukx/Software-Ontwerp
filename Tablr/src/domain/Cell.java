package domain;

public class Cell<T> extends DomainElement {

	/**
	 * The constructor of the Cell
	 * @param v: the value of the cell
	 */
	public Cell(T v){
		this.setValue(v);
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
	
//	/**
//	 * This method returns the row of the current Cell.
//	 */
//	public Row getRow() {
//		return row;
//	}
//	
//	/**
//	 * This method sets the row of the current row.
//	 * @param row: The row to be set.
//	 */
//	public void setRow(Row row) {
//		this.row = row;
//	}

//	/**
//	 * This method returns the table of the current Cell.
//	 */
//	public Table getTable() {
//		return table;
//	}
//
//	/**
//	 * This method sets the table of the current Cell.
//	 * @param table: the table to be set.
//	 */
//	public void setTable(Table table) {
//		this.table = table;
//	}

	/**
	 * This method returns the column of the current Cell.
	 */
	public Column getColumn() {
		return column;
	}

	/**
	 * This method sets the column of the current cell.
	 * @param column
	 */
	public void setColumn(Column column) {
		this.column = column;
	}

	private Column column;
	
	
	/**
	 * This method terminates the cell and all its connections to other objects.
	 */
	public void terminate(){
		if (getColumn() != null) this.getColumn().removeCell(this);
		
	}
	

	private T value;
}
