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
	
	/**
	 * the table's cells
	 */
	private ArrayList<Cell> cells;
	
	/**
	 * create a new cell in the intersection of Row row and Column column
	 * @param row
	 * @param column
	 */
	private void createCell(Row row, Column column){
		Cell cell = new Cell(, row, column, this);
		this.add(cell);
		row.add(cell);
		column.add(cell);
	}
	
	/**
	 * add a cell to the collection of cells in this table (not in order)
	 * @param cell
	 */
	private void add(Cell cell){
		this.cells.add(cell);
	}
	
	/**
	 * add a new Column at the end of the list of columns (e.g. to the right)
	 */
	private void addColumn(){
		Column newColumn = new Column(newColumnName(), this, new ArrayList<Cell>());
		for (int i = 0; i<rows.size(); i++){
			createCell(rows.get(i), newColumn);
		}
		columns.add(newColumn);
	}
	
	//TODO: voor de volgende 2, errors catchen als de column niet in de lijst staat!
	/**
	 * removes a column from the list
	 * @param column
	 */
	public void remove(Column column) {
		columns.remove(column);
	}
	
	/**
	 * removes a row from the list
	 * @param row
	 */
	public void remove(Row row) {
		rows.remove(row);
	}
	
	/**
	 * Method can be used to generate a column name that is not yet in use for a column.
	 * This is used when creating new columns.
	 */
	String newColumnName() {
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

	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}

	public ArrayList<Row> getRows() {
		return rows;
	}

	public void setRows(ArrayList<Row> rows) {
		this.rows = rows;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	public void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}
}
