package sql;

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
}
