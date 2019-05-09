package sql;

import java.util.ArrayList;
import java.util.HashMap;

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
	
}
