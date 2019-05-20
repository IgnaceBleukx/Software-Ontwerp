package sql;

import java.util.ArrayList;
import java.util.HashMap;

import Utils.DebugPrinter;
import domain.Column;
import domain.Table;

/**
 * Class containing a CellIDExpression.
 * A CellIDExpression is an expression that
 * points to a certain column in a certain table,
 * e.g Table1.column1. 
 * Evaluating a CellIDExpression returns the value 
 * of a specific cell in the column. See CellIDExpression::eval.
 *
 */
public class CellIDExpression extends Expression<Object> {
	/**
	 * Creates a new CellIDExpression
	 * @param rowID			Table name
	 * @param columnName	Column name
	 */
	public CellIDExpression(String rowID, String columnName) {
		this.rowID = rowID;
		this.columnName = columnName;
	}
	
	/**
	 * Evaluates this CellIDExpression.
	 * Evaluating a CellIDExpression means specifying a row number i,
	 * and the return value will be equal to the cell at index i in the
	 * column referred to by this CellIDExpression.
	 */
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
		orElseThrow(() -> new RuntimeException("No column "+ colName + " in table"));
		return col.getValueAt(rowNb);
	}
	
	/**
	 * Returns the table name
	 * @return		Table name
	 */
	public String getRowID() {
		return rowID;
	}
	
	/**
	 * Returns the column name
	 * 
	*/
	public String getcolumnName() {
		return columnName;
	}
	
	private String rowID;
	private String columnName;
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "cellIDExpression("+rowID+"."+columnName+")";
	}

}
