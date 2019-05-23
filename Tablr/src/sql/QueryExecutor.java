package sql;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import Utils.DebugPrinter;
import domain.Column;
import domain.ComputedTable;
import domain.StoredTable;
import domain.Table;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;

/**
 * Class that contains all logic to execute a query.
 *
 */
public class QueryExecutor {
	/**
	 * Executes a query
	 * @param q							Query
	 * @param tables					List of tables
	 * @return							ComputedTable containing the result of the query
	 * @throws InvalidQueryException	Error during execution of query
	 */
	public static ComputedTable executeQuery(Query q, ArrayList<Table> tables) throws InvalidQueryException, InvalidNameException {
		//If an empty query was entered,
		//return a null table
		if (q == null)
			return null;
		
		HashMap<Table, ArrayList<String>> columnNames = new HashMap<Table,ArrayList<String>>();
		for (Table table : tables){
			ArrayList<String> names = new ArrayList<String>(table.getColumnNames());
			columnNames.put(table, names);
		}
		//Delete references of tables
		HashMap<Table,ArrayList<ComputedTable>> tableReferences = new HashMap<Table,ArrayList<ComputedTable>>();
		for (Table table : tables) {
			tableReferences.put(table,table.removeDerivatives());
		}
		
		//Order of execution:
		//1. Get necessary tables (FROM+JOIN)
		HashMap<String,String> tableNames = q.findTableNameAliases();
		Table t = q.resolveFrom(tables);
		
		DebugPrinter.print("==== Temporary table after JOIN: ");
		t.printTable();
		DebugPrinter.print("====");
		
		// --> tableSpecs
		//2. Filter rows (WHERE)
		t = q.resolveWhere(t,tableNames);
		
		DebugPrinter.print("==== Temporary table after WHERE: ");
		t.printTable();
		DebugPrinter.print("====");
		// --> expression
		//3. Return columns (SELECT)
		// --> columnSpecs
		
		//[
		//	 ColumnSpec(cellIDExpression(student.name) AS name), 
		//   ColumnSpec(cellIDExpression(student.program) AS program)
		//]
		ComputedTable t2 = q.selectColumns(t, tableNames);
		
		DebugPrinter.print("==== Final table after SELECT: ");
		t2.printTable();
		DebugPrinter.print("==== ");

		//Restoring columns to original names
		for(Table table : tables){
			for (int i=0;i<table.getColumns().size();i++){
				table.getColumns().get(i).setName(columnNames.get(table).get(i));
			}
		}
		//Restoring table references
		for (Table table : tables) {
			table.addDerivatives(tableReferences.get(table));
		}
		
		return t2;
	}
	
	
	
}
