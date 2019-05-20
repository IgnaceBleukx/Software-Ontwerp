package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;

/**
 * Class containg a BooleanExpression
 * A BooleanExpression is a constant expression
 * that evaluates to either true or false.
 *
 */
public class BooleanExpression extends Expression<Boolean> {
	
	/**
	 * Creates a new BooleanExpression
	 * @param value		Value of the expression
	 */
	public BooleanExpression(Boolean value) {
		this.value = value;
	}
	
	/**
	 * Evaluates this expression.
	 */
	public Boolean eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
		return value;
	}
	
	private Boolean value;
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "BooleanExpression("+value+")";

	}
}
