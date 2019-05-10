package sql;

import java.util.ArrayList;

import domain.Table;

public class CellIDExpression extends Expression<String> {
	public CellIDExpression(String rowID, String columnName) {
		this.rowID = rowID;
		this.columnName = columnName;
	}
	
	@Override
	public String eval(ArrayList<Table> tables, int rowNb) {
		throw new RuntimeException("CellIDExpression should not eval(), use getRowID() and getColumnName() instead.");
	}
	
	public String getRowID() {
		return rowID;
	}
	
	public String getcolumnName() {
		return columnName;
	}
	
	private String rowID;
	private String columnName;
	
	public String toString() {
		return "cellIDExpression("+rowID+"."+columnName+")";
	}

}
