package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Class containing a GreaterThanExpression.
 * A GreaterThanExpression evaluates to true iff
 * its left expression evaluates to a value greater than its 
 * right subexpression.
 *
 */
public class GreaterThanExpression extends Expression<Boolean> {
	/**
	 * Creates a new GreaterThanExpression
	 * @param expression1		Left subexpression
	 * @param expression2		Right subexpression
	 */
	public GreaterThanExpression(Expression<Integer> expression1, Expression<Integer> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Integer> expression1;
	private Expression<Integer> expression2;
	
	/**
	 * Evaluates the GreaterThanExpression.
	 * @throws InvalidQueryException 
	 */
	public Boolean eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) throws InvalidQueryException {
		try {
			int val1 = (int)expression1.eval(tables, rowNb, tableNames);
			int val2 = (int)expression2.eval(tables, rowNb, tableNames);
		} catch (ClassCastException e) {
			throw new InvalidQueryException("> can not be used on these values");
		}
		
		return ((int)expression1.eval(tables, rowNb, tableNames) > (int)expression2.eval(tables, rowNb, tableNames));
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "GreaterThanExpression("+expression1.toString()+" > "+expression2.toString()+")";
	}
}
