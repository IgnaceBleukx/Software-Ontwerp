package domain;

import java.util.ArrayList;

import facades.CommunicationManager;

public class Table extends DomainElement {
	
	public Table(String name) {
		setName(name);
	}
	
	/**
	 * The table's name
	 */
	private String name;

	/**
	 * This method returns the name of the current Table.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name of the current Table.
	 * @param name 	The name to be set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * The table's columns from left to right
	 */
	private ArrayList<Column> columns = new ArrayList<Column>();
	
	/**
	 * This method returns the columns of the current Table.
	 * @return
	 */
 	public ArrayList<Column> getColumns() {
		return columns;
	}
	
	/**
	 * Add a new Column at the end of the list of columns (e.g. to the right)
	 */
	public Column addEmptyColumn(){
		ArrayList<Cell<?>> columnCells = new ArrayList<Cell<?>>();
		for(int i=0;i<this.getRows().size(); i++){
			Cell<String> cell = new Cell<String>(null);
			columnCells.add(cell);
		}
		Column newCol = new Column(newColumnName(), columnCells);
		newCol.setTable(this);
		this.columns.add(newCol);
		return newCol;
	}
	
	/**
	 * This method adds a column to the current table.s
	 * @param col 	The column to be added.
	 */
	public void addColumn(Column col){
		this.columns.add(col);
	}
	
	/**
	 * This method removes a Column from the current table.
	 * @param column
	 */
	public void removeColumn(Column column) {
		removeColumn(columns.indexOf(column));
	}
	
	/**
	 * This method removes a Column from the current table based on an index.
	 * @param index 	The index on which the column must be removed.
	 * @return 	The removed Column.
	 */
	public Column removeColumn(int index){
		Column column = columns.get(index);
		columns.remove(column);
		column.setTable(null);
		return column;
	}
	
	
	/**
	 * The table's rows from up to down
	 */
	private ArrayList<Row> rows = new ArrayList<Row>();
	
	/**
	 * This method returns the rows of the current Table.
	 */
	public ArrayList<Row> getRows() {
		return rows;
	}
	
	/**
	 * This method adds a row to the current Table.
	 * @param r 	The row to be added.
	 */
	public void addRow(Row r){
		this.rows.add(r);
		for (Column col: columns){
			col.addBlankCell();
		}
	}
	
	/**
	 * This method removes a row from the current table.
	 * @param row 	The row to be removed.
	 */
	public void removeRow(Row row) {
		removeRow(rows.indexOf(row));
	}
		
	/**
	 * This method removes a row from the table based on index.
	 * @param index 	The index of the row to be removed.
	 * @return The removed row.
	 */
	public Row removeRow(int index){
		Row r = rows.get(index);
		for (Column c: columns){
			c.removeCell(index);
		}
		r.setTable(null);
		return rows.remove(index);
	}
	
//	/**
//	 * The table's cells
//	 */
//	private ArrayList<Cell<?>> cells = new ArrayList<Cell<?>>();
//	
//	/**
//	 * add a cell to the collection of cells in this table (not in order)
//	 * @param cell
//	 */
//	public void addCell(Cell<?> cell){
//		this.cells.add(cell);
//	}
//	
//	/**
//	 * This method removes a cell of the table.
//	 * @param cell
//	 */
//	public void removeCell(Cell<?> cell){
//		this.cells.remove(cell);
//		cell.setTable(null);
//	}
//	
	
	/**
	 * This method returns the next logic column name for the current table.
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
	 * This method returns an ArrayList of all the names of the columns of the current Table.
	 * @return 
	 */
	public ArrayList<String> getColumnNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (Column column: this.getColumns()){
			names.add(column.getName());
		}
		return names;
	}
	
	
	/**
	 * This method removes the table and all its contents.
	 */
	public void terminate(){
		for (Column c: columns){
			c.terminate();
		}
		for (Row r: rows){
			r.terminate();
		}
	}
	
	@Override
	public void setCommunicationManager(CommunicationManager c) {
		this.communicationManager = c;
		for (Column e : getColumns()) {
			e.setCommunicationManager(c);
		}
	}
	
}
