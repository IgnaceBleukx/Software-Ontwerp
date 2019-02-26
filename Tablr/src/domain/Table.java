package domain;

public class Table {
	/**
	 * the table's name
	 */
	private String name;
	
	/**
	 * the table's columns from left to right
	 */
	private Column[] columns;
	/**
	 * the table's rows from up to down
	 */
	private Row[] rows;
	/**
	 * the table's cells
	 */
	private Cell[] cells;
}
