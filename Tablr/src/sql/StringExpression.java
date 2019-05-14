package sql;

import java.util.ArrayList;
import java.util.HashMap;

import Utils.DebugPrinter;
import domain.Table;

public class StringExpression extends Expression<String> {

	public StringExpression(String value) {
		this.value = value;
	}
	
	public String eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
		return value;
	}
	
	private String value;
	
	public String toString() {
		return "StringExpression(\""+value+"\")";
	}
}
