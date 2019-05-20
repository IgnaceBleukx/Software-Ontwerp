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
public class MinusExpression extends Expression<Double> {
	/**
	 * Creates a new MinusExpression.
	 * @param expression1		Left subexpression
	 * @param expression2		Right subexpression
	 */
	public MinusExpression(Expression<Double> expression1, Expression<Double> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Double> expression1;
	private Expression<Double> expression2;
	
	/**
	 * Evalutes this MinusExpression
	 */
	public Double eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) throws InvalidQueryException {
		double val1;
		double val2;
		try {
			val1 = (Double)expression1.eval(tables, rowNb, tableNames);
			val2 = (Double)expression2.eval(tables, rowNb, tableNames);
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