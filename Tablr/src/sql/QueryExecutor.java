package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.ComputedTable;
import domain.Table;
import exceptions.InvalidQueryException;

public class QueryExecutor {
	public static ComputedTable executeQuery(Query q, ArrayList<Table> tables) throws InvalidQueryException {
	
		//Order of execution:
		//1. Get necessary tables (FROM+JOIN)
		HashMap<String,String> tableNames = q.findTableNameAliases();
		Table t = q.resolveFrom(tables);
		
		// --> tableSpecs
		//2. Filter rows (WHERE)
		// --> expression
		//3. Return columns (SELECT)
		// --> columnSpecs
		
		
		
		
		return null;
	}
	
	
	
}
