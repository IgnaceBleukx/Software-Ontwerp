package domain;

public class Column {
	/**
	 * the name of the Column
	 */
	private String name;
	/**
	 * holds the Type of the Column as a string value
	 * default value: "String"
	 */
	private String columnType = "String";
	/**
	 * does the column allow a blank field
	 */
	private Boolean allowsBlanks = true;

	/**
	 * the default value of a column which allows blanks.
	 */
	private String defaultValue = "";
	
	/**
	 * the parent of the column
	 */
	private Table table;
	/**
	 * the cells this column contains
	 */
	private Cell[] cells;
	
	/**
	 * uitgebreide constructor, meer als voorbeeld dan om echt te gebruiken.
	 * @param newName
	 * @param newColumnType
	 * @param newAllowsBlanks
	 * @param newDefaultValue
	 * @param newTable
	 * @param newCells
	 */
	public Column(String newName, String newColumnType, Boolean newAllowsBlanks, String newDefaultValue, Table newTable, Cell[] newCells) {
		this.name= newName;
		this.columnType = newColumnType;
		this.allowsBlanks = newAllowsBlanks;
		this.defaultValue = newDefaultValue;
		this.table = newTable;
		this.cells = newCells;
	}
	
	/**
	 * dit wordt waarschijnlijk de constructor die meestal gebruikt gaat worden: zie default values in opgave
	 * @param newTable
	 * @param newCells
	 */
	public Column(Table newTable, Cell[] newCells) {
		columnType = "String";
		allowsBlanks = true;
		defaultValue = "";
		this.table = newTable;
		//TODO: this.name: Hangt af van de Table!
		//TODO: cells kunnen ook hier gecreëerd worden, moeten niet noodzakelijk meegegeven worden.
	}
	
	/**
	 * bij een single click gaat het type van de column ééntje opschuiven volgens deze lijst (zie opgave)
	 */
	void NextType() {
		switch (getColumnType()) {
			case "String": setColumnType("Email");
			case "Email": setColumnType("Boolean");
			case "Boolean": setColumnType("Integer");
			case "Integer": setColumnType("String");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public Boolean getAllowsBlanks() {
		return allowsBlanks;
	}

	public void setAllowsBlanks(Boolean allowsBlanks) {
		this.allowsBlanks = allowsBlanks;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Cell[] getCells() {
		return cells;
	}

	public void setCells(Cell[] cells) {
		this.cells = cells;
	}
}
