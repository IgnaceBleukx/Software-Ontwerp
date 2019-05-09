package sql;

public class ANDExpression extends Expression<Boolean> {
	public ANDExpression(Expression<Boolean> expression1, Expression<Boolean> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Boolean> expression1;
	private Expression<Boolean> expression2;
	
	public Boolean eval() {
		return ((Boolean)expression1.eval()&&(Boolean)expression2.eval());
	}
	
	public String toString() {
		return "ANDExpression("+expression1.toString()+" AND "+expression2.toString()+")";
	}
}
