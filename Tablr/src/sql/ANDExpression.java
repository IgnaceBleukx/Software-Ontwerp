package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidQueryException;

/**
 * Class containing an AND expression.
 * An AND expression is a conjunction of two sub-expressions,
 * e.g t1.name = "foo" AND t2.name = "bar"
 * A AND expression evaluates to true iff its two sub-expressions
 * evaluate to true
 * 
 */
public class ANDExpression extends Expression<Boolean> {
	/**
	 * Creates a new AND expression
	 * @param expression1	Left sub-expression
	 * @param expression2 	Right sub-expression
	 */
	public ANDExpression(Expression<Boolean> expression1, Expression<Boolean> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Boolean> expression1;
	private Expression<Boolean> expression2;
	
	/**
	 * Returns true iff its two subexpressions return true
	 * @param tables		List of tables
	 * @param rowNb			Current row number to check expression
	 * @param tableNames	Map (alias->name) containing all necessary
	 * 						table name aliases to evaluate this query.
	 * @throws InvalidQueryException 
	 * 
	 */
	public Boolean eval(ArrayList<Table> tables, int rowNb,HashMap<String,String> tableNames) throws InvalidQueryException {
		return ((Boolean)expression1.eval(tables, rowNb,tableNames)&&(Boolean)expression2.eval(tables, rowNb,tableNames));
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "ANDExpression("+expression1.toString()+" AND "+expression2.toString()+")";
	}
}
