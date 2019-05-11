package sql;

import java.util.ArrayList;

import Utils.DebugPrinter;
import domain.Table;

public class EqualsExpression extends Expression<Boolean> {
	public EqualsExpression(Expression<?> expression1, Expression<?> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<?> expression1;
	private Expression<?> expression2;
	
	public Boolean eval(ArrayList<Table> tables, int rowNb) {
		DebugPrinter.print(expression1);
		DebugPrinter.print(expression1.eval(tables, rowNb));
		DebugPrinter.print(expression2);
		DebugPrinter.print(expression2.eval(tables, rowNb));
		return (expression1.eval(tables, rowNb).equals(expression2.eval(tables, rowNb)));
	}
	
	public String toString() {
		return "EqualsExpression("+expression1.toString()+" = "+expression2.toString()+")";
	}
}
