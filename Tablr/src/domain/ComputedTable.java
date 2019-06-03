package domain;

import java.util.HashMap;
import java.util.Map;

import Utils.DebugPrinter;
import sql.ColumnSpec;
import sql.Query;

/**
 * Class containing a ComputedTable.
 * A ComputedTable is the result of executing a 
 * query and should not be edited directly.
 * Use StoredTable for an editable Table.
 *
 */
public class ComputedTable extends Table {

	/**
	 * Creates a new ComputedTable.
	 * ComputedTables are created by the QueryExecutor
	 * and should normally not be created manually.
	 * @param name		Name of the Table
	 * @param query		The Query this ComputedTable is based on.
	 */
	public ComputedTable(String name, Query query) {
		super(name);
	}

	/**
	 * Returns the SQL string this ComputedTable
	 * is the result of.
	 */
	@Override
	public String getQueryString() {
		return q.toSQL();
	}
	
	/**
	 * The query used to compute this table
	 */
	private Query q;
	
	/**
	 * This method sets the query of the current table
	 * @param q 	The query to be set
	 */
	public void setQuery(Query q) {
		this.q = q;
	}
	
	/**
	 * This method returns the query of this table.
	 * @return
	 */
	public Query getQuery() {
		return q;
	}

	/**
	 * This method checks if the query of the current table contains the given column.
	 * @param  column 	The column that must be checked
	 */
	@Override
	public boolean queryContainsColumn(Column column) {
		Query q = this.getQuery();
		HashMap<String, String> tableNames = q.findTableNameAliases();
		DebugPrinter.print(tableNames);
		for(int j = 0; j < q.getColumnSpecs().size(); j++) {
			ColumnSpec spec = q.getColumnSpecs().get(j);
			if (spec.getCellID().getcolumnName().equals(column.getName())) {
				DebugPrinter.print("Query contains column");
				return true;
			}
		}
		return false;
	}
}
