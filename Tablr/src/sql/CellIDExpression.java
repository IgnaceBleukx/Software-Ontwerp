package sql;

import java.util.ArrayList;
import java.util.HashMap;

import Utils.DebugPrinter;
import domain.Column;
import domain.Table;

public class CellIDExpression extends Expression<Object> {
	public CellIDExpression(String rowID, String columnName) {
		this.rowID = rowID;
		this.columnName = columnName;
	}
	
	@Override
	public Object eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
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
		Column col = table.getColumns().stream().filter(c -> {
			String tableName = tableNames.get(rowID);
			DebugPrinter.print("Found: "+c.getName());
			DebugPrinter.print("Searched for: "+tableName+"."+colName);

			return c.getName().equals(tableName+"."+colName);
		})
		.findFirst().
		orElseThrow(() -> new RuntimeException("No column "+ colName + "in table"));
		return col.getValueAt(rowNb);
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
