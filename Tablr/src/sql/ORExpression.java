package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Creates a new ORExpression.
 * An ORExpression is disjunction of two BooleanExpression
 *
 */
public class ORExpression extends Expression<Boolean> {
	/**
	 * Creates a new ORExpression
	 * @param expression1	Left part	
	 * @param expression2	Right part
	 */
	public ORExpression(Expression<Boolean> expression1, Expression<Boolean> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Boolean> expression1;
	private Expression<Boolean> expression2;
	
	/**
	 * Evaluates the Expression.
	 * True iff one or more expressions evaluate to true.
	 */
	public Boolean eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) throws InvalidQueryException {
		boolean val1;
		boolean val2;
		try {
			val1 = (Boolean)expression1.eval(tables, rowNb, tableNames);
			val2 = (Boolean)expression2.eval(tables, rowNb, tableNames);
		} catch (ClassCastException e) {
			throw new InvalidQueryException("Invalid types in ORExpression");
		}
		return (val1 || val2);
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "OrExpression("+expression1.toString()+" OR "+expression2.toString()+")";
	}
	
	
}
