package domain;


public class Row {
	
	public Row() {}
	
	/**
	 * The row's parent table
	 */
	private Table table;
	
	/**
	 * This method sets the table of the current Row.
	 * @param t 	The table to be set.
	 */
	public void setTable(Table t){
		this.table = t;
	}
	
	/**
	 * This method returns the table of the current Row.
	 * @return
	 */
	public Table getTable() {
		return this.table;
	}
	
	/**
	 * This method terminates the Row and all its cells
	 */
	public void terminate(){
		table.removeRow(this);
		this.setTable(null);
	}
	
}
