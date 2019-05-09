package sql;

public class BooleanExpression extends Expression<Boolean> {
	
	public BooleanExpression(boolean value) {
		this.value = value;
	}
	
	public Boolean eval() {
		return value;
	}
	
	private boolean value;
	
	public String toString() {
		return "BooleanExpression("+value+")";

	}
}
