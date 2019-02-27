package domain;

public class Cell {
	
	public Cell(String value) {
		value = value;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public Type getType(){
		return this.type;
	}
	
	public void setType(){
		this.type = type;
	}
	
	private Type type;
	
	
	
	
}
