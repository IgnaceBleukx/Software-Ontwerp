package sql;

public class EqualsExpression extends Expression<Boolean> {
	public EqualsExpression(Expression<?> expression1, Expression<?> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<?> expression1;
	private Expression<?> expression2;
	
	public Boolean eval() {
		return (expression1.eval().equals(expression2.eval()));
	}
	
	public String toString() {
		return "EqualsExpression("+expression1.toString()+" = "+expression2.toString()+")";
	}
}
