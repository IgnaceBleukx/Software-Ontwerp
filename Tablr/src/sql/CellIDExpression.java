package sql;


public class CellIDExpression extends Expression {
	public CellIDExpression(String rowID, String columnName) {
		this.rowID = rowID;
		this.columnName = columnName;
	}
	
	public String eval() {
		throw new RuntimeException("CellIDExpression should not eval(), use getRowID() and getColumnName() instead.");
	}
	
	public String getRowID() {
		return rowID;
	}
	
	public String columnName() {
		return columnName();
	}
	
	private String rowID;
	private String columnName;
	
	public String toString() {
		return "cellIDExpression("+rowID+"."+columnName+")";
	}
}
