package sql;

import java.util.ArrayList;

import domain.Table;

public class SmallerThanExpression extends Expression<Boolean> {
	public SmallerThanExpression(Expression<Integer> expression1, Expression<Integer> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Integer> expression1;
	private Expression<Integer> expression2;
	
	public Boolean eval(ArrayList<Table> tables, int rowNb) {
		return ((int)expression1.eval(tables, rowNb) < (int)expression2.eval(tables, rowNb));
	}
	
	public String toString() {
		return "PlusExpression("+expression1.toString()+" < "+expression2.toString()+")";
	}
}
