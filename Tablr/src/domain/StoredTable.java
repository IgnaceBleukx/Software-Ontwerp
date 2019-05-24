package domain;

import java.util.ArrayList;
import exceptions.InvalidNameException;

/**
 * Class containing a StoredTable.
 * A StoredTable is created manually and can be
 * edited.
 *
 */
public class StoredTable extends Table {

	/**
	 * Creates a new StoredTable with a given name
	 * @param name		Name of the StoredTable
	 */
	public StoredTable(String name) {
		super(name);
	}

	/**
	 * Returns the SQL Query this StoredTable
	 * was based on. A StoredTable always has a query String ""
	 */
	@Override
	public String getQueryString() {
		return "";
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
 	 * Adds a Column at a certain position.
 	 * @param column		Column to add	
 	 * @param index			Index to add the Column at
 	 */
	public void addColumnAt(Column column, int index) {
		this.columns.add(index,column);
		column.setTable(this);
	}
 	
	
	
	/**
	 * This method adds a row with values to the bottom of the current table
	 * @param values 	The values to add to fill the new row.
	 */
	public void addFilledRowAt(ArrayList<Object> values,int i) {
		int index = 0;
		for (Object v : values) {
			getColumns().get(index++).addCellAt(new Cell(v),i);
		}
	}
	
	/**
	 * Adds a given column to the table
	 * @param column
	 */
	public void addColumn(Column column) {
		this.columns.add(column);
		column.setTable(this);
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
	 * This method adds a blank row to the current Table.
	 */
	public void addRow(){
		nbOfRows++;
		columns.stream().forEach(col -> col.addBlankCell());
	}
	
	/**
	 * This method adds a row to the current Table.
	 */
	public void addRow(ArrayList<Cell> c){
		if (c.size() != getColumns().size())
			throw new RuntimeException();
		
		nbOfRows++;
		for (int i=0;i<getColumns().size();i++) {
			getColumns().get(i).addCell(c.get(i));
		}
	}
	
	/**
	 * This method checks if any of the derived tables contain the given column.
	 */
	@Override
	public boolean queryContainsColumn(Column c) {

		for(ComputedTable t : getDerivedTables()) {
			if (t.queryContainsColumn(c))
				return true;
		}
		return false;
	}
}
