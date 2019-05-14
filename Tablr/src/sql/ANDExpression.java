package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;

public class ANDExpression extends Expression<Boolean> {
	public ANDExpression(Expression<Boolean> expression1, Expression<Boolean> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Boolean> expression1;
	private Expression<Boolean> expression2;
	
	public Boolean eval(ArrayList<Table> tables, int rowNb,HashMap<String,String> tableNames) {
		return ((Boolean)expression1.eval(tables, rowNb,tableNames)&&(Boolean)expression2.eval(tables, rowNb,tableNames));
	}
	
	public String toString() {
		return "ANDExpression("+expression1.toString()+" AND "+expression2.toString()+")";
	}
}
