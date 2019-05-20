package sql;

import java.util.ArrayList;
import java.util.HashMap;

import Utils.DebugPrinter;
import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Class containing an EqualsExpression.
 * An EqualsExpression evaluates to true
 * iff its two subexpressions evaluate to the same value.
 * Java's builtin equals() is used to determine whether
 * this is the case.
 *
 */
public class EqualsExpression extends Expression<Boolean> {
	/**
	 * Creates a new EqualsExpression.
	 * @param expression1		Left expression
	 * @param expression2		Right expression
	 */
	public EqualsExpression(Expression<?> expression1, Expression<?> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<?> expression1;
	private Expression<?> expression2;
	
	/**
	 * Evaluates this EqualsExpression.
	 * If the results of evaluating the two subexpressions are true,
	 * the return value of this method will be true.
	 * @throws InvalidQueryException 
	 */
	public Boolean eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) throws InvalidQueryException {
		Object e1 = expression1.eval(tables, rowNb, tableNames);
		Object e2 = expression2.eval(tables, rowNb, tableNames);
		if (e1 == null && e2 == null)
			return true;
		else if (e1 == null && e2 != null || e1 != null && e2 == null)
			return false;
		else
			return (e1.equals(e2));
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "EqualsExpression("+expression1.toString()+" = "+expression2.toString()+")";
	}
}
