package domain;

import java.util.ArrayList;

public class Table {
	
	/**
	 * the table's name
	 */
	private String name;

	/**
	 * the table's columns from left to right
	 */
	private ArrayList<Column> columns = new ArrayList<Column>();
	
	/**
	 * the table's rows from up to down
	 */
	private ArrayList<Row> rows = new ArrayList<Row>();
	
	public void addRow(Row r){
		if (isValidRow(r)){
			this.rows.add(r);
			r.setTable(this);
		}
	}
	
	public boolean isValidRow(Row r){
		return r.getCells().size() == this.getColumns().size();
	}
	
	/*
	private void createCell(Row row, Column column){
		Cell cell = new Cell(column.getDefault(), row, column, this);
		this.add(cell);
		row.add(cell);
		column.add(cell);
	}*/

	/**
	 * the table's cells
	 */
	private ArrayList<Cell<?>> cells = new ArrayList<Cell<?>>();
	
	/**
	 * add a cell to the collection of cells in this table (not in order)
	 * @param cell
	 */
	public void add(Cell<?> cell){
		this.cells.add(cell);
	}
	
	/**
	 * add a new Column at the end of the list of columns (e.g. to the right)
	 */
	public void addColumn(){
		ArrayList<Cell<?>> columnCells = new ArrayList<Cell<?>>();
		for(int i=0;i<this.getRows().size(); i++){
			Cell<String> cell = new Cell<String>(null);
			columnCells.add(cell);
			this.getRows().get(i).addCell(cell);
		}
		Column newCol = new Column(newColumnName(), this, columnCells);
		this.columns.add(newCol);
		
	}
	
	/**
	 * removes a column from the list
	 * @param column
	 */
	public void removeColumn(Column column) {
		columns.remove(column);
	}
	
	/**
	 * removes a row from the list
	 * @param row
	 */
	public void removeRow(Row row) {
		rows.remove(row);
	}
	
	/**
	 * This method removes a cell of the table.
	 * @param cell
	 */
	public void removeCell(Cell<?> cell){
		this.cells.remove(cell);
		cell.setTable(null);
	}
	/**
	 * Method can be used to generate a column name that is not yet in use for a column.
	 * This is used when creating new columns.
	 */
	private String newColumnName() {
		ArrayList<String> names = getColumnNames();
		String name = "Column";
		int i = 0;
		while(name == "Column") {
			if (!names.contains("Column"+i)) {
				name=("Column"+i);
			}
			i++;
		}
		return name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * puts all the names of the table's columns into one ArrayList
	 * @return that list
	 */
	public ArrayList<String> getColumnNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (Column column: this.getColumns()){
			names.add(column.getName());
		}
		return names;
	}
	
	/**
	 * This method returns the columns of the current Table.
	 * @return
	 */
 	public ArrayList<Column> getColumns() {
		return columns;
	}

	/**
	 * This method returns the rows of the current Table.
	 */
	public ArrayList<Row> getRows() {
		return rows;
	}

	/**
	 * This method removes the table and all its contents.
	 */
	public void terminate(){
		for (Column c: columns){
			c.terminate();
		}
		for (Row r: rows){
			r.terminate();
		}
		//TODO: remove table from top level table collection.
	}
}
