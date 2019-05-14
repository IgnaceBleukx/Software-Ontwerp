package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;

public class NumberExpression extends Expression<Integer> {
	public NumberExpression(int value) {
		this.value = value;
	}
	
	private int value;
	
	public Integer eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
		return value;
	}
	
	public String toString() {
		return "NumberExpression("+value+")";
	}
}
