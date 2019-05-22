package domain;

import java.util.HashMap;
import java.util.Map;

import Utils.DebugPrinter;
import sql.ColumnSpec;
import sql.Query;

public class ComputedTable extends Table {

	public ComputedTable(String name, Query query) {
		super(name);
	}

	@Override
	public String getQueryString() {
		return q.toSQL();
	}
	
	private Query q;
	
	public void setQuery(Query q) {
		this.q = q;
	}
	
	public Query getQuery() {
		return q;
	}

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
