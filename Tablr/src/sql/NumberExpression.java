package sql;

import java.util.ArrayList;

import domain.Table;

public class NumberExpression extends Expression<Double> {
	public NumberExpression(double value) {
		this.value = value;
	}
	
	private double value;
	
	public Double eval(ArrayList<Table> tables, int rowNb) {
		return value;
	}
	
	public String toString() {
		return "NumberExpression("+value+")";
	}
}
