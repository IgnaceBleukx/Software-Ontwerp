package sql;

public class ColumnSpec {
	public ColumnSpec(CellIDExpression cellID, String name) {
		this.cellID = cellID;
		this.name = name;
	}
	
	private CellIDExpression cellID;
	private String name;
	
	public CellIDExpression getCellID() {
		return cellID;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return "ColumnSpec("+cellID.toString()+" AS "+name.toString()+")";
	}
}
