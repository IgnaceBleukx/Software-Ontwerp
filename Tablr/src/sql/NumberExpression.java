package sql;

import java.util.ArrayList;

import domain.Table;

public class NumberExpression extends Expression<Integer> {
	public NumberExpression(int value) {
		this.value = value;
	}
	
	private int value;
	
	public Integer eval(ArrayList<Table> tables, int rowNb) {
		return value;
	}
	
	public String toString() {
		return "NumberExpression("+value+")";
	}
}
