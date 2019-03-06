package domain;

import java.util.ArrayList;

public class Row {
	
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
	
	
//	/**
//	 * The row's cells from left to right
//	 */
//	private ArrayList<Cell<?>> cells = new ArrayList<Cell<?>>();
//	
//	/**
//	 * This method returns a copy of the cells contained in the current row.
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public ArrayList<Cell<?>> getCells(){
//		return (ArrayList<Cell<?>>) this.cells.clone();
//	}
//	
//	/**
//	 * This method adds a cell to the row at the back of the row.
//	 * @param cell
//	 */
//	public void addCell(Cell<?> cell){
//		cell.setTable(this.getTable());
//		cell.setRow(this);
//		cells.add(cell);
//	}
//	
//	/**
//	 * This method adds a collection of cells to the current row.
//	 */
//	public void addAllCells(ArrayList<Cell<?>> newCells){
//		for (Cell<?> c : newCells){
//			this.addCell(c);
//		}
//	}
//
//	/**
//	 * This method removes a cell of the row.
//	 * @param cell	The cell to be removed.
//	 */
//	public void removeCell(Cell<?> cell){
//		removeCell(cells.indexOf(cell));
//	}
//	
//	/**
//	 * This method removes a cell from the row, based on an index.
//	 * @param index 	The index on which the cell must be removed from the row. 
//	 * @return The cell removed from the row.
//	 */
//	public Cell<?> removeCell(int index){
//		Cell<?> c = this.cells.remove(index);
//		if(c != null){
//			c.setRow(null);
//		}
//		return c;
//	}
	
	
	/**
	 * This method terminates the Row and all it's cells
	 */
	public void terminate(){
		table.removeRow(this);
		this.setTable(null);
	}
}
