package domain;

import java.util.ArrayList;

public class Row {
	
	/**
	 * Constructor of the row
	 * @param cells 	The cells of the row.
	 */
	public Row(ArrayList<Cell<?>> cells){
		for (Cell<?> c: cells){
			c.setRow(this);
			c.setTable(this.table);
		}
		this.cells.addAll(cells);
	}
	
	/**
	 * This method sets the table of the current Row.
	 * @param t 	The table to be set.
	 */
	public void setTable(Table t){
		this.table = t;
		for (int i=0; i < t.getColumns().size(); i++){
			t.getColumns().get(i).addCell(this.getCells().get(i));
		}
	}
	
	/**
	 * the row's parent table
	 */
	private Table table;
	
	/**
	 * the row's cells from left to right
	 */
	private ArrayList<Cell<?>> cells;
	
	/**
	 * add a cell to the end of this row
	 * @param cell
	 */
	public void addCell(Cell<?> cell){
		cell.setTable(this.getTable());
		cell.setRow(this);
		cells.add(cell);
	}

	/**
	 * This method removes a cell of the row.
	 * @param cell	The cell to be removed.
	 */
	public void removeCell(Cell<?> cell){
		this.cells.remove(cell);
		cell.setRow(null);
	}
	
	/**
	 * This method returns the table of the current Row.
	 * @return
	 */
	public Table getTable() {
		return this.table;
	}


	/**
	 * This method terminates the Row and all it's cells
	 */
	public void terminate(){
		table.removeRow(this);
		for (Cell<?> cell: cells){
			cell.terminate();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Cell<?>> getCells(){
		return (ArrayList<Cell<?>>) this.cells.clone();
	}
}
