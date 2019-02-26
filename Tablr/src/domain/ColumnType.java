package domain;

public class ColumnType{
	/**
	 * type holds the Type of the Column as a string value
	 * default value: "String"
	 */
	private String type = "String";
	
	public ColumnType(String s) {
		switch (s) {
			case "String": type = s;
			case "Email": type = s;
			case "Boolean": type = s;
			case "Integer": type = s;
		}
	}
}
