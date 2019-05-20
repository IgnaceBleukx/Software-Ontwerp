package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;

/**
 * Class containing a NumberExpression.
 * A NumberExpression evaluates to a constant numeric value.
 *
 */
public class NumberExpression extends Expression<Integer> {
	/**
	 * Creates a new NumberExpression
	 * @param value		Value
	 */
	public NumberExpression(int value) {
		this.value = value;
	}
	
	private int value;
	
	/**
	 * Evaluates this NumberExpression to its value.
	 */
	public Integer eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
		return value;
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "NumberExpression("+value+")";
	}
}
