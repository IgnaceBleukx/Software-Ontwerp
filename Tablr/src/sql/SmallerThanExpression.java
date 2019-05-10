package sql;

import java.util.ArrayList;

import domain.Table;

public class SmallerThanExpression extends Expression<Boolean> {
	public SmallerThanExpression(Expression<Double> expression1, Expression<Double> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Double> expression1;
	private Expression<Double> expression2;
	
	public Boolean eval(ArrayList<Table> tables, int rowNb) {
		return ((Double)expression1.eval(tables, rowNb) < (Double)expression2.eval(tables, rowNb));
	}
	
	public String toString() {
		return "PlusExpression("+expression1.toString()+" < "+expression2.toString()+")";
	}
}
