package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;

public class SmallerThanExpression extends Expression<Boolean> {
	public SmallerThanExpression(Expression<Integer> expression1, Expression<Integer> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Integer> expression1;
	private Expression<Integer> expression2;
	
	public Boolean eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
		return ((int)expression1.eval(tables, rowNb, tableNames) < (int)expression2.eval(tables, rowNb, tableNames));
	}
	
	public String toString() {
		return "PlusExpression("+expression1.toString()+" < "+expression2.toString()+")";
	}
}
