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
	
	//TODO: voor de volgende 2, errors catchen als de column niet in de lijst staat!
	/**
	 * removes a column from the list
	 * @param column
	 */
	private void removeColumn(Column column) {
		this.getColumns().remove(column);
	}
	
	/**
	 * removes a column from the list, based on its place in the list
	 * @param column
	 */
	private void removeColumn(int column) {
		this.getColumns().remove(column);
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
