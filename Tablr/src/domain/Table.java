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
	
	/**
	 * This method adds a row to the current Table.
	 * @param r 	The row to be added.
	 * @throws IllegealDimensionException
	 */
	public void addRow(Row r) throws IllegealDimensionException{
		if (isValidRow(r)){
			this.rows.add(r);
			r.setTable(this);
			for (int i=0;i<columns.size();i++){
				columns.get(i).addCell(r.getCells().get(i));
			}
		}
		else{
			throw new IllegealDimensionException();
		}
	}
	
	/**
	 * This method returns whether the row given is a valid one for the current Table.
	 * @param r 	The row to be checked.
	 * @return
	 */
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
	public void addCell(Cell<?> cell){
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
	 * This method removes a Column from the current table.
	 * @param column
	 */
	public void removeColumn(Column column) {
		int index = this.columns.indexOf(column);
		columns.remove(column);
		for (Row r: rows){
			r.removeCell(index);
		}
		column.setTable(null);
		columns.remove(column);
	}
	
	/**
	 * This method removes a Column from the current table based on an index.
	 * @param index 	The index on which the column must be removed.
	 * @return 	The removed Column.
	 */
	public Column removeColumn(int index){
		Column column = columns.get(index);
		columns.remove(column);
		for (Row r: rows){
			r.removeCell(index);
		}
		column.setTable(null);
		columns.remove(column);
		return column;
	}
	
	
	/**
	 * This method removes a row from the current table.
	 * @param row 	The row to be removed.
	 */
	public void removeRow(Row row) {
		int index = this.rows.indexOf(row);
		for (Column c: columns){
			c.removeCell(index);
		}
		row.setTable(null);
		rows.remove(row);
	}
	
	
	/**
	 * This method removes a row from the table based on index.
	 * @param index 	The index of the row to be removed.
	 * @return The removed row.
	 */
	public Row removeRow(int index){
		Row r = rows.get(index);
		for (Column c: columns){
			c.removeCell(index);
		}
		r.setTable(null);
		return rows.remove(index);
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
