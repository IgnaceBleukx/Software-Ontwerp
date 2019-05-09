package sql;

public class NumberExpression extends Expression<Double> {
	public NumberExpression(double value) {
		this.value = value;
	}
	
	private double value;
	
	public Double eval() {
		return value;
	}
	
	public String toString() {
		return "NumberExpression("+value+")";
	}
}
