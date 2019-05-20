package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Class containing a BracketExpression.
 * A BracketExpression is an expression
 * enclosed in brackets.
 *
 */
public class BracketExpression<T> extends Expression<T> {
	/**
	 * Creates a new BracketExpression
	 * @param e		The expression to enclose in brackets
	 */
	public BracketExpression(Expression e) {
		this.e = e;
	}
	
	/**
	 * Evaluates this expression.
	 * A bracketexpression will always
	 * evaluate to the same value as
	 * its internal expression
	 * @throws InvalidQueryException 
	 */
	@Override
	public T eval(ArrayList<Table> tables, int rowNb,HashMap<String,String> tableNames) throws InvalidQueryException {
		return (T) e.eval(tables, rowNb,tableNames);
	}
	
	private Expression e;
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "BracketExpression("+e.toString()+")";
	}
}
