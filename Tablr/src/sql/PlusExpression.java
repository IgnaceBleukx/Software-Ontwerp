package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Class containing a PlusExpression.
 * A PlusExpression evaluates to the sum of its
 * two subexpressions
 *
 */
public class PlusExpression extends Expression<Integer> {
	/**
	 * Creates a new PlusExpression
	 * @param expression1 	Left subexpression
	 * @param expression2	Right subexpression
	 */
	public PlusExpression(Expression<Integer> expression1, Expression<Integer> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Integer> expression1;
	private Expression<Integer> expression2;
	
	/**
	 * Evaluates this SumExpression to the sum of its parts.
	 */
	public Integer eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) throws InvalidQueryException {
		int val1;
		int val2;
		try {
			val1 = (Integer)expression1.eval(tables, rowNb, tableNames);
			val2 = (Integer)expression2.eval(tables, rowNb, tableNames);
		} catch (ClassCastException e) {
			throw new InvalidQueryException("Invalid types in for + operator");
		}
		return (val1 + val2);
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "PlusExpression("+expression1.toString()+" + "+expression2.toString()+")";
	}
}
