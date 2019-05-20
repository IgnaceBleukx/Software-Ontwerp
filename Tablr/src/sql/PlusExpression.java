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
public class PlusExpression extends Expression<Double> {
	/**
	 * Creates a new PlusExpression
	 * @param expression1 	Left subexpression
	 * @param expression2	Right subexpression
	 */
	public PlusExpression(Expression<Double> expression1, Expression<Double> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Double> expression1;
	private Expression<Double> expression2;
	
	/**
	 * Evaluates this SumExpression to the sum of its parts.
	 */
	public Double eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) throws InvalidQueryException {
		double val1;
		double val2;
		try {
			val1 = (Double)expression1.eval(tables, rowNb, tableNames);
			val2 = (Double)expression2.eval(tables, rowNb, tableNames);
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
