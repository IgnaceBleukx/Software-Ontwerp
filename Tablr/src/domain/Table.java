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
}
