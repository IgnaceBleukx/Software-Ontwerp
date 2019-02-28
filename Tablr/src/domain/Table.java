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
	private ArrayList<Column> columns;
	
	/**
	 * the table's rows from up to down
	 */
	private ArrayList<Row> rows;
	
	public void addRow(Row r){
		this.rows.add(r);
		r.setTable(this);
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
	private ArrayList<Cell<?>> cells;
	
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
		for(int i=0;i<=this.getColumns().size(); i++){
			Cell<Blank> cell = new Cell<Blank>(new Blank());
			columnCells.add(cell);
		}
		Column newCol = new Column(newColumnName(), this, columnCells);
		this.columns.add(newCol);
		
	}
	
	//TODO: voor de volgende 2, errors catchen als de column niet in de lijst staat!
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
	
	/**
	 * puts all the names of the table's columns into one ArrayList
	 * @return that list
	 */
	private ArrayList<String> getColumnNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (Column column: this.getColumns()){
			names.add(column.getName());
		}
		return names;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Column> getColumns() {
		return columns;
	}


	public ArrayList<Row> getRows() {
		return rows;
	}

}
