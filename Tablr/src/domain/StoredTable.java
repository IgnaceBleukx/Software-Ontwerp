package domain;

import java.util.ArrayList;

import exceptions.InvalidNameException;
import sql.Query;

public class StoredTable extends Table {

	public StoredTable(String name) {
		super(name);
		
	}

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
	

}
