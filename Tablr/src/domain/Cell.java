package domain;

public class Cell {

	private String value;
	
	public Cell(String newValue) {
		value = newValue;
	}
	/*
	 * TODO: deze 3 moeten volgens het domeinmodel in de opgave ook bewaard worden, maar dat lijkt me maar vreemd
	 * 
	private Column column;
	private Row row;
	private Table table;
	*/
	
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public Type getType(){
		return this.type;
	}
	
	public void setType(Type newType){
		this.type = newType;
	}
	
	private Type type;
}
