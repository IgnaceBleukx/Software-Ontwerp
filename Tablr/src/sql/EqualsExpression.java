package sql;

import java.util.ArrayList;
import java.util.HashMap;

import Utils.DebugPrinter;
import domain.Table;

public class EqualsExpression extends Expression<Boolean> {
	public EqualsExpression(Expression<?> expression1, Expression<?> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<?> expression1;
	private Expression<?> expression2;
	
	public Boolean eval(ArrayList<Table> tables, int rowNb, HashMap<String,String> tableNames) {
		Object e1 = expression1.eval(tables, rowNb, tableNames);
		Object e2 = expression2.eval(tables, rowNb, tableNames);
		if (e1 == null && e2 == null)
			return true;
		else if (e1 == null && e2 != null || e1 != null && e2 == null)
			return false;
		else
			return (e1.equals(e2));
	}
	
	public String toString() {
		return "EqualsExpression("+expression1.toString()+" = "+expression2.toString()+")";
	}
}
