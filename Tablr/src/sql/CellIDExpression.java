package sql;

import java.util.ArrayList;

import domain.Column;
import domain.Table;

public class CellIDExpression extends Expression<String> {
	public CellIDExpression(String rowID, String columnName) {
		this.rowID = rowID;
		this.columnName = columnName;
	}
	
	@Override
	public String eval(ArrayList<Table> tables, int rowNb) {
		Table tab = tables.stream().filter(t -> t.getName().equals(rowID)).findFirst().orElseThrow(() -> new RuntimeException("The table " + getRowID() + "could not be found"));
		Column col = tab.getColumns().stream().filter(c -> c.getName().equals(columnName)).findFirst().orElseThrow(() -> new RuntimeException("The column " + columnName + "could not be found in " + tab.getName()));
		return col.getValueAtString(rowNb);
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
