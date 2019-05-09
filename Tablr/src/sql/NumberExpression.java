package sql;

public class NumberExpression extends Expression {
	public NumberExpression(double value) {
		this.value = value;
	}
	
	private double value;
	
	public double eval() {
		return value;
	}
}
