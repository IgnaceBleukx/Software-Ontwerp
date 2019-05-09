package sql;

public class StringExpression extends Expression {

	public StringExpression(String value) {
		this.value = value;
	}
	
	public String eval() {
		return value;
	}
	
	private String value;
	
	public String toString() {
		return "StringExpression(\""+value+"\")";
	}
}
