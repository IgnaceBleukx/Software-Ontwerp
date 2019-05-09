package domain;

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
}
