package sql;

import java.util.ArrayList;
import java.util.HashMap;

import Utils.DebugPrinter;
import domain.Table;

/**
 * Class containing a StringExpression
 *
 */
public class StringExpression extends Expression<String> {
	
	/**
	 * Creates a new StringExpression containing a string value.
	 * @param value		String value
	 */
	public StringExpression(String value) {
		this.value = value;
	}
	
	/**
	 * Evaluates this expression to its string value
	 */
	public String eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
		return value;
	}
	
	private String value;
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "StringExpression(\""+value+"\")";
	}
}
