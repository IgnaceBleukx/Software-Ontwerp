package sql;

import java.util.ArrayList;

import domain.Table;
import Utils.DebugPrinter;


public class Query {
	private ArrayList<ColumnSpec> columnSpecs;
	private TableSpec tableSpecs;
	private Expression<Boolean> expression;
	
	public String toString() {
		return "Query: \n"
				+ "SELECT \n"
				+ "		"+columnSpecs.toString() +"\n"
				+ "FROM \n"
				+ "		"+tableSpecs.toString() +"\n"
				+ "WHERE \n"
				+ "		"+expression.toString() +"\n";
	}
	
	public Table run() {
		return null;}
	
	public void addColumnSpec(ColumnSpec columnSpec) {
		this.columnSpecs.add(columnSpec);
	}
	
	public void setTableSpecs(TableSpec tableSpecs) {
		this.tableSpecs = tableSpecs;
	}
	
	public void setExpression(Expression e) {
		this.expression = e;
	}
	
}
