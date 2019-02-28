package domain;

public class Cell {

	private String value;
	
	private Column column;
	private Row row;
	private Table table;
	
	
	public Cell(String newValue) {
		value = newValue;
	}
	
	
	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
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
	
	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}
	
	public void delete(){
		
	}


	private Type type;
}
