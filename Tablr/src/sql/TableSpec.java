package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;

/**
 * Abstract class for TableSpec. A Tablespec is used to 
 * select the right tables in a FROM clause.
 */
public abstract class TableSpec {

	public abstract HashMap<String, String> findTableNameAliases();

	public abstract Table resolve(ArrayList<Table> tables) throws InvalidQueryException;

	public abstract ArrayList<String> getName();

}
