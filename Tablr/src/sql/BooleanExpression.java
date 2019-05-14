package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;

public class BooleanExpression extends Expression<Boolean> {
	
	public BooleanExpression(Boolean value) {
		this.value = value;
	}
	
	public Boolean eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
		return value;
	}
	
	private Boolean value;
	
	public String toString() {
		return "BooleanExpression("+value+")";

	}
}
