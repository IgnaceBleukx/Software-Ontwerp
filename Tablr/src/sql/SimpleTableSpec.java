package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Column;
import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Class containing a SimpleTableSpec.
 * A SimpleTableSpec is a tableName and an alias in a FROM clause.
 *
 */
public class SimpleTableSpec extends TableSpec {
	/**
	 * Creates a new SimpleTableSpec
	 * @param tableName		Original Table name
	 * @param rowID			Table name alias
	 */
	public SimpleTableSpec(String tableName, String rowID) {
		this.tableName = tableName;
		this.rowID = rowID;
	}
	
	private String tableName;
	private String rowID;
	
	/**
	 * Returns the original table name
	 */
	public String getTableName() {
		return tableName;
	}
	
	
	/**
	 * Returns the table alias
	 */
	public String getRowID() {
		return rowID;
	}
	
	/**
	 * Returns a string representation of this object
	 */
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
	
	/**
	 * Returns a list containing the name of the table
	 */
	@Override
	public ArrayList<String> getName(){
		ArrayList<String> l = new ArrayList<String>();
		l.add(tableName);
		return l;
	}

	/**
	 * Returns the table specified by this tableSpec
	 */
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
