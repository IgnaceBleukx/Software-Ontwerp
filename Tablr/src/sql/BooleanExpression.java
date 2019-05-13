package sql;

import java.util.ArrayList;

import domain.Table;

public class BooleanExpression extends Expression<Boolean> {
	
	public BooleanExpression(Boolean value) {
		this.value = value;
	}
	
	public Boolean eval(ArrayList<Table> tables, int rowNb) {
		return value;
	}
	
	private Boolean value;
	
	public String toString() {
		return "BooleanExpression("+value+")";

	}
}
