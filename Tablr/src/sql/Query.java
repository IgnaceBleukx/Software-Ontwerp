package sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import domain.Cell;
import domain.Column;
import domain.ComputedTable;
import domain.StoredTable;
import domain.Table;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;
import Utils.DebugPrinter;

/**
 * Class containing a Query object.
 * A query as described consists of only three parts, as it only
 * support SELECT, FROM and WHERE. Each part is set by the SQLParser.
 * 
 * columnSpecs: describes which columns to SELECT.
 * tableSpecs: describes which tables to select FROM.
 * expression: describes an expression that has to be true for all resulting rows.
 *
 * A Query also keeps a String representation of its SQL command.
 */
public class Query {
	public Query() {
	}
	
	private ArrayList<ColumnSpec> columnSpecs = new ArrayList<ColumnSpec>();
	private TableSpec tableSpecs;
	private Expression<Boolean> expression;
	private String sql;
	
	/**
	 * Returns a string representation of this object
	 */
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
	
	/**
	 * Adds a column to the columnSpecs
	 * @param columnSpec	ColumnSpecs of desired column
	 */
	public void addColumnSpec(ColumnSpec columnSpec) {
		this.columnSpecs.add(columnSpec);
	}
	
	/**
	 * Sets the tableSpecs for this query
	 * @param tableSpecs		TableSpec for the tables to select from
	 */
	public void setTableSpecs(TableSpec tableSpecs) {
		this.tableSpecs = tableSpecs;
	}
	
	/**
	 * Sets the necessary expression
	 * @param e		Expression
	 */
	public void setExpression(Expression e) {
		this.expression = e;
	}
	
	/**
	 * Returns the SQL command this query was based on.
	 */
	public String toSQL() {
		return sql;
	}
	
	/**
	 * Sets the SQL command this query was based on.
	 */
	public void setSQL(String sql) {
		this.sql = sql;
	}
	
	/**
	 * Resolves any table name aliases into a map (alias->name)
	 * based on AS phrases in the FROM part of the query.
	 * @return		A map that can be indexed using an alias (e.g. t)
	 * 				to find the real table name (e.g. Table0) from Returns the columnspecs of this querya clause
	 * 				FROM Table0 AS t.
	 */
	public  HashMap<String,String> findTableNameAliases() {
		return (tableSpecs.findTableNameAliases());
	}
	
	/**
	 * Returns all table names used in this query.
	 */
	public ArrayList<String> tableNames(){
		return (tableSpecs.getName());
	}
	
	/**
	 * Returns the tableSpec of this query.
	 */
	public TableSpec getTableSpecs() {
		return this.tableSpecs;
	}

	/**
	 * Returns the table that is a result of the SELECT and JOIN operations
	 * @param tables						List of tables 
	 * @throws InvalidQueryException		See SimpleTableSpec::resolve and JoinTableSpec::resolve
	 */
	public Table resolveFrom(ArrayList<Table> tables) throws InvalidQueryException {
		return tableSpecs.resolve(tables);
	}
	
	/**
	 * Returns the columnspecs of this query
	 */
	public ArrayList<ColumnSpec> getColumnSpecs() {
		return this.columnSpecs;
	}
	
	/**
	 * Selects a number of columns from a table based on columnspecs
	 * @param oldTable				Initial table
	 * @param tableNameAliases		Map (alias->name) of table name aliases
	 */
	public ComputedTable selectColumns(Table oldTable, HashMap<String,String> tableNameAliases) throws InvalidNameException {
		ArrayList<Column> cols = oldTable.getColumns();
		ArrayList<Column> newCols = new ArrayList<>();
		for (Column c : cols) {
			for (ColumnSpec spec : getColumnSpecs()) {
				//Given a columnSpec tableName.columnName,
				//A column should be kept if:
				//a columnSpec is tableNameAlias.columnName and 
				//an alias tableNameAlias -> tableName exists.
				//DebugPrinter.print(tableNameAliases.get(spec.getCellID().getRowID())+"."+spec.getCellID().getcolumnName());
				//DebugPrinter.print(c.getName());
				if ((tableNameAliases.get(spec.getCellID().getRowID())+"."+spec.getCellID().getcolumnName()).equals(c.getName())) {
					//Keep column
					newCols.add(c);
				}
			}
			
		}
		
		//Rename columns according to columnSpecs
		for (int i=0; i<getColumnSpecs().size(); i++) {
			newCols.get(i).setName(getColumnSpecs().get(i).getName());
		}
		
		int keep = getColumnSpecs().size();
		newCols = new ArrayList<Column>(newCols.subList(0, keep));
		
		ComputedTable newTable = new ComputedTable("Result",this);
		newTable.addAllColumns(newCols);
		return newTable;

	}
	
	/**
	 * Removes any rows that do not fulfill the WHERE expression of this query
	 * @param table						Initial table
	 * @param tableNames				Map (alias->name) of table name aliases
	 * @return							New Table where every rows fulfills the WHERE expression 
	 * @throws InvalidQueryException 	Error when evaluating the expression on a certain row
	 */
	public Table resolveWhere(Table table, HashMap<String, String> tableNames) throws InvalidQueryException {
		ArrayList<Integer> keep = new ArrayList<Integer>();
		ArrayList<Table> t = new ArrayList<Table>(Arrays.asList(table));
		for (int i=0;i < table.getRows();i++) {
			if (expression.eval(t, i,tableNames)) {
				keep.add(i);
			}
		}
		
		StoredTable resolved = new StoredTable("t");
		for (Column c : table.getColumns()) {
			ArrayList<Cell<?>> newVals = new ArrayList<Cell<?>>();
			for (int index : keep) {
				newVals.add(new Cell(c.getValueAt(index)));
			}
			try {
				resolved.addColumn(new Column(c.getName(),newVals,c.getColumnType(),c.getDefault()));
			} catch (InvalidNameException e) {
				e.printStackTrace();
			}
		}
		
		return resolved;
	}
	
}
