package sql;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import domain.Column;
import domain.ComputedTable;
import domain.Table;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;

public class QueryExecutor {
	public static ComputedTable executeQuery(Query q, ArrayList<Table> tables) throws InvalidQueryException, InvalidNameException {
	
		//Order of execution:
		//1. Get necessary tables (FROM+JOIN)
		HashMap<String,String> tableNames = q.findTableNameAliases();
		Table t = q.resolveFrom(tables);
		
		// --> tableSpecs
		//2. Filter rows (WHERE)
		t = q.resolveWhere(t);
		// --> expression
		//3. Return columns (SELECT)
		// --> columnSpecs
		
		//[
		//	 ColumnSpec(cellIDExpression(student.name) AS name), 
		//   ColumnSpec(cellIDExpression(student.program) AS program)
		//]
		t = q.selectColumns(t, tableNames);
		
		
		return null;
	}
	
	
	
}
