package domain;

import java.util.ArrayList;

public class Row {
	/**
	 * the row's parent table
	 */
	private Table table;
	
	/**
	 * the row's cells from left to right
	 */
	private ArrayList<Cell> cells;
	
	/**
	 * add a cell to the end of this row
	 * @param cell
	 */
	public void add(Cell cell){
		cells.add(cell);
	}
	
	public void delete(){
		table.remove(this);
		for (Cell cell: cells){
			cell.delete();
		}
	}
}
