package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Abstract class containing an Expression, parametrized in its type T.
 * @param <T>		Return value of eval() method
 */
public abstract class Expression<T> {
	/**
	 * Evaluates the Expression in the context of the current domain.
	 * @param tables		List of tables
	 * @param rowNb			Row number
	 * @param tableNames	Map (alias->name) of table name aliases
	 * @throws InvalidQueryException 
	 */
	public abstract T eval(ArrayList<Table> tables, int rowNb,HashMap<String,String> tableNames) throws InvalidQueryException;
}
