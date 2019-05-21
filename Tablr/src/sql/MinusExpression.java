package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Class containing a MinusExpression.
 * A MinusExpression evaluates to the difference between its left and right subexpression
 *
 */
public class MinusExpression extends Expression<Integer> {
	/**
	 * Creates a new MinusExpression.
	 * @param expression1		Left subexpression
	 * @param expression2		Right subexpression
	 */
	public MinusExpression(Expression<Integer> expression1, Expression<Integer> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Integer> expression1;
	private Expression<Integer> expression2;
	
	/**
	 * Evalutes this MinusExpression
	 */
	public Integer eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) throws InvalidQueryException {
		Integer val1;
		Integer val2;
		try {
			val1 = (Integer)expression1.eval(tables, rowNb, tableNames);
			val2 = (Integer)expression2.eval(tables, rowNb, tableNames);
		} catch (ClassCastException e) {
			throw new InvalidQueryException("Can't subtract values in MinusExpression: invalid types");
		}
		return (val1 - val2);
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "MinusExpression("+expression1.toString()+" - "+expression2.toString()+")";
	}
}