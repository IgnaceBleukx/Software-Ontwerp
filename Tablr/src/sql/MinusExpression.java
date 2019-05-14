package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;

public class MinusExpression extends Expression<Double> {
	public MinusExpression(Expression<Double> expression1, Expression<Double> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Double> expression1;
	private Expression<Double> expression2;
	
	public Double eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
		return ((Double)expression1.eval(tables, rowNb, tableNames) - (Double)expression2.eval(tables, rowNb, tableNames));
	}
	
	public String toString() {
		return "MinusExpression("+expression1.toString()+" - "+expression2.toString()+")";
	}
}