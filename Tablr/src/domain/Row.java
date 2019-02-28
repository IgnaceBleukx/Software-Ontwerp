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
	
	
	//TODO: is dit hoe dit werkt? Niet zeker van
	/**
	 * returns a copy of the list of cells in row
	 * @return
	 */
	public ArrayList<Cell> getCells(){
		ArrayList<Cell> newList = new ArrayList<Cell>(cells);
		return newList;
	}
	
	/**
	 * TODO: error check voor cell niet in cells
	 * removes a cell from the list of cells
	 * @param cell
	 */
	public void remove(Cell cell){
		cells.remove(cell);
	}
	
	public void terminate(){
		table.remove(this);
		for (Cell cell: this.getCells()){
			cell.terminate();
		}
	}
}
