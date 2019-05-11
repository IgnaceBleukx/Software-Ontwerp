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
		String colName;
		Table table;
		if (tables.size() == 1) {
			table = tables.get(0);
			colName = columnName;
		}
		else {
			table = tables.stream().filter(t -> t.getName().equals(rowID)).findFirst().orElseThrow(() -> new RuntimeException("Table " + rowID + "not found"));
			colName = rowID+"."+columnName;
		}
		Column col = table.getColumns().stream().filter(c -> c.getName().equals(colName)).
				findFirst().orElseThrow(() -> new RuntimeException("No column "+ colName + "in table"));
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
