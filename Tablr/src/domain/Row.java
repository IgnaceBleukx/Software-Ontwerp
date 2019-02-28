package domain;

import java.util.ArrayList;

public class Row {
	
	public Row(ArrayList<Cell<?>> cells){
		for (Cell<?> c: cells){
			c.setRow(this);
			c.setTable(this.table);
		}
		this.cells.addAll(cells);
	}
	
	
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
	public void add(Cell<?> cell){
		cell.setTable(this.getTable());
		cell.setRow(this);
		cells.add(cell);
	}
	
	public Table getTable() {
		return this.table;
	}


	/**
	 * This method terminates the Row and all it's cells
	 */
	public void terminate(){
		table.remove(this);
		for (Cell<?> cell: cells){
			cell.terminate();
		}
	}
	
	public ArrayList<Cell<?>> getCells(){
		return this.cells.clone();
	}
}
