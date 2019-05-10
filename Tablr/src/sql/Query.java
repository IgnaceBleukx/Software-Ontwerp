package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Column;
import domain.ComputedTable;
import domain.Table;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;
import Utils.DebugPrinter;


public class Query {
	public Query() {
		
	}
	private ArrayList<ColumnSpec> columnSpecs = new ArrayList<ColumnSpec>();
	private TableSpec tableSpecs;
	private Expression<Boolean> expression;
	private String sql;
	
	public String toString() {
		String columnSpecsStr = columnSpecs == null? "Null" : columnSpecs.toString();
		String tableSpecsStr = tableSpecs == null? "Null" : tableSpecs.toString();
		String expressionStr = expression == null? "Null" : expression.toString();
		
		return "Query: \n"
				+ "SELECT \n"
				+ "		"+columnSpecsStr +"\n"
				+ "FROM \n"
				+ "		"+tableSpecsStr +"\n"
				+ "WHERE \n"
				+ "		"+expressionStr +"\n";
	}
	

	public void addColumnSpec(ColumnSpec columnSpec) {
		this.columnSpecs.add(columnSpec);
	}
	
	public void setTableSpecs(TableSpec tableSpecs) {
		this.tableSpecs = tableSpecs;
	}
	
	public void setExpression(Expression e) {
		this.expression = e;
	}
	
	public String toSQL() {
		return sql;
	}
	
	public void setSQL(String sql) {
		this.sql = sql;
	}
	
	public  HashMap<String,String> findTableNameAliases() {
		return (tableSpecs.findTableNameAliases());
	}
	
	public TableSpec getTableSpecs() {
		return this.tableSpecs;
	}


	public Table resolveFrom(ArrayList<Table> tables) throws InvalidQueryException {
		return tableSpecs.resolve(tables);
	}
	
	public ArrayList<ColumnSpec> getColumnSpecs() {
		return this.columnSpecs;
	}
	
	public Table selectColumns(Table oldTable, HashMap<String,String> tableNameAliases) throws InvalidNameException {
		ArrayList<Column> cols = oldTable.getColumns();
		ArrayList<Column> newCols = new ArrayList<>();
		for (Column c : cols) {
			for (ColumnSpec spec : getColumnSpecs()) {
				//Given a columnSpec tableName.columnName,
				//A column should be kept if:
				//1. a columnSpec is tableName.columnName
				//2. a columnSpec is tableNameAlias.columnName and 
				//	 an alias tableNameAlias -> tableName exists.
				if (spec.getCellID().getRowID().equals(c.getName())
						|| tableNameAliases.get(spec.getCellID().getRowID()).equals(c.getName())) {
					//Keep column
					newCols.add(c);
				}
			}
			
		}
		
		//Rename columns according to columnSpecs
		for (int i=0; i<getColumnSpecs().size(); i++) {
			newCols.get(i).setName(getColumnSpecs().get(i).getName());
		}
		
		Table newTable = new ComputedTable("Result",this);
		newTable.addAllColumns(newCols);
		return newTable;
	}
	
}
