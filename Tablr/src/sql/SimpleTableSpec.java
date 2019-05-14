package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Column;
import domain.Table;
import exceptions.InvalidQueryException;

public class SimpleTableSpec extends TableSpec {
	public SimpleTableSpec(String tableName, String rowID) {
		this.tableName = tableName;
		this.rowID = rowID;
	}
	
	private String tableName;
	private String rowID;
	
	public String getTableName() {
		return tableName;
	}
	
	public String getRowID() {
		return rowID;
	}
	
	public String toString() {
		return "SimpleTableSpec("+tableName+" AS "+rowID+")";
	}

	/**
	 * Creates map (alias -> tableName)
	 */
	@Override
	public HashMap<String, String> findTableNameAliases() {
		HashMap<String,String> m = new HashMap<String,String>();
		m.put(rowID,tableName);
		return m;
	}

	@Override
	public Table resolve(ArrayList<Table>  tables) throws InvalidQueryException {
		Table t2 = null;
		for (Table t : tables) {
			if (t.getName().equals(tableName)) {
				t2 = t.clone(t.getName());
			}
		}
		if (t2 == null)
			throw new InvalidQueryException("Table name not found in WHERE clause");

		
		for (Column c : t2.getColumns()) {
			if (!c.getName().contains("."))
				c.setName(tableName+"."+c.getName());
		}
		return t2;
	}
}
