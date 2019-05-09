package sql;

public class BooleanExpression extends Expression {
	
	public BooleanExpression(boolean value) {
		this.value = value;
	}
	
	public boolean eval() {
		return value;
	}
	
	private boolean value;

}
