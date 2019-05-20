package sql;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import Utils.DebugPrinter;
import domain.Column;
import domain.ComputedTable;
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
		t.printTable();
		DebugPrinter.print("==== ");

		return t2;
	}
	
	
	
}
