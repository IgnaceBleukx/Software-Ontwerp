package domain;

public class Cell {
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	public Cell(String newValue) {
		value = newValue;
	}
}
