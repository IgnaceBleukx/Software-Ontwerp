package sql;

import java.util.ArrayList;

import Utils.DebugPrinter;
import domain.Table;

public class StringExpression extends Expression<String> {

	public StringExpression(String value) {
		this.value = value;
	}
	
	public String eval(ArrayList<Table> tables, int rowNb) {
		return value;
	}
	
	private String value;
	
	public String toString() {
		return "StringExpression(\""+value+"\")";
	}
}
