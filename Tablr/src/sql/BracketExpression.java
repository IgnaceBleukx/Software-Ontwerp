package sql;

import java.util.ArrayList;

import domain.Table;

public class BracketExpression<T> extends Expression<T> {
	public BracketExpression(Expression e) {
		this.e = e;
	}
	
	@Override
	public T eval(ArrayList<Table> tables, int rowNb) {
		return (T) e.eval(tables, rowNb);
	}
	
	private Expression e;
	
	public String toString() {
		return "BracketExpression("+e.toString()+")";
	}
}
