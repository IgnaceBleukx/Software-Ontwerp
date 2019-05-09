package domain;

import java.util.ArrayList;
import java.util.stream.Collectors;

import exceptions.InvalidNameException;


public class Table {
	
	/**
	 * Creates a new empty stored Table
	 * @param name		The name of this table
	 */
	public Table(String name) {
		setName(name);
		setQuery("");
	}
	
	/**
	 * Creates a new empty computated Table
	 */
	public Table(String name, String query) {
		setName(name);
		setQuery(query);
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
	
	private String query;
	
	/**
	 * This method sets the query of the current Table.
	 * @param query 	The Query of the table.
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	/**
	 * This method returns the query of the current Table.
	 * @return
	 */
	public String getQuery() {
		return this.query;
	}
	
	/**
	 * The table's columns from left to right
	 */
	private ArrayList<Column> columns = new ArrayList<Column>();
	
	/**
	 * This method returns the columns of the current Table.
	 * @return	A Collection of Columns where each column is guaranteed to have this table set as its table.
	 */
 	public ArrayList<Column> getColumns() {
		return columns;
	}

 	/**
 	 * Adds an empty column to the table
 	 * @param type			Column type
 	 * @param defaultValue	Default value of the new column
 	 * @return				The newly added column
 	 */
 	public Column addEmptyColumn(Type type, Object defaultValue){
 		Column col;
		try {
			col = new Column(newColumnName(), null,type,defaultValue);
			while(col.getCells().size() != nbOfRows){
				col.addBlankCell();
			}
			col.setTable(this);
			this.columns.add(col);
			return col;
		} catch (InvalidNameException e) {
			return null;
		}
 	}

	
	/**
	 * This method removes a Column from the current table.
	 * @param column	Reference to the column to delete
	 */
	public void removeColumn(Column column) {
		removeColumn(columns.indexOf(column));
	}
	
	/**
	 * This method removes a Column from the current table based on an index.
	 * @param index 	The index of the column which is to be removed.
	 * @return 			The removed Column.
	 */
	public Column removeColumn(int index){
		Column column = columns.get(index);
		columns.remove(column);
		column.setTable(null);
		return column;
	}
	
	/**
	 * The number of rows in this table.
	 */
	private int nbOfRows;
	
	/**
	 * This method returns the number of rows of the current Table.
	 */
	public int getRows() {
		return nbOfRows;
	}
	
	/**
	 * This method adds a blank row to the current Table.
	 */
	public void addRow(){
		nbOfRows++;
		columns.stream().forEach(col -> col.addBlankCell());
	}
		
	/**
	 * This method removes a row from the table based on index.
	 * @param index 	The index of the row to be removed.
	 * @return 			The removed row.
	 */
	public void removeRow(int index){
		nbOfRows--;
		for (Column c: columns){
			c.removeCell(index);
		}
	}
	
	/**
	 * This method returns the next standard column name for the current table.
	 * Standard column names are 'ColumnXYZ' where XYZ is the smallest integer
	 * value that is not used yet as a column name in this table.
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
	 */
	public ArrayList<String> getColumnNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (Column column: this.getColumns()){
			names.add(column.getName());
		}
		return names;
	}
	
}
