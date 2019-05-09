package sql;

public class BracketExpression<T> extends Expression<T> {
	public BracketExpression(Expression e) {
		this.e = e;
	}
	
	@Override
	public T eval() {
		return (T) e.eval();
	}
	
	private Expression e;
	
	public String toString() {
		return "BracketExpression("+e.toString()+")";
	}
}
