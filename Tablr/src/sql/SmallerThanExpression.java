package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Class containing a SmallerThanExpression,
 * e.g. t.id < t2.id
 *
 */
public class SmallerThanExpression extends Expression<Boolean> {
	/**
	 * Creates a new SmallerThanExpression
	 * @param expression1	Left subexpression
	 * @param expression2	Right subexpression
	 */
	public SmallerThanExpression(Expression<Integer> expression1, Expression<Integer> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Integer> expression1;
	private Expression<Integer> expression2;
	
	/**
	 * Evaluates this SmallerThanExpression. True iff
	 * the value of the left subexpression is smaller than 
	 * the value of thr right subexpression.
	 */
	public Boolean eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) throws InvalidQueryException {
		int val1;
		int val2;
		try {
			val1 = (int)expression1.eval(tables, rowNb, tableNames);
			val2 = (int)expression2.eval(tables, rowNb, tableNames);
		} catch (ClassCastException e) {
			throw new InvalidQueryException("operator < cannot be applied to tese types");
		}
		return (val1 < val2);
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "SmallerThanExpression("+expression1.toString()+" < "+expression2.toString()+")";
	}
}
