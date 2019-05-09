package sql;

public class ORExpression extends Expression<Boolean> {
	public ORExpression(Expression<Boolean> expression1, Expression<Boolean> expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	private Expression<Boolean> expression1;
	private Expression<Boolean> expression2;
	
	public Boolean eval() {
		return ((Boolean)expression1.eval()||(Boolean)expression2.eval());
	}
	
	public String toString() {
		return "OrExpression("+expression1.toString()+" OR "+expression2.toString()+")";
	}
	
	
}
