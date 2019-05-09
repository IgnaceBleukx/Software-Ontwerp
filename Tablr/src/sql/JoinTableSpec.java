package sql;

public class JoinTableSpec extends TableSpec {
	public JoinTableSpec(TableSpec leftTable, SimpleTableSpec rightTable, 
						 CellIDExpression leftCell, CellIDExpression rightCell) {
		this.leftTable = leftTable;
		this.rightTable = rightTable;
		this.leftCell = leftCell;
		this.rightCell = rightCell;

	}
	
	SimpleTableSpec leftTable;
	SimpleTableSpec rightTable;

	
	CellIDExpression leftCell;
	CellIDExpression rightCell;
	
	public SimpleTableSpec getLeftTable() {
		return this.leftTable;
	}
	
	public SimpleTableSpec getRightTable() {
		return this.rightTable;
	}
	
	public CellIDExpression getLeftCell() {
		return leftCell;
	}
	
	public CellIDExpression getRightCell() {
		return rightCell;
	}
	
	public String toString() {
		return "JoinTableSpec("+leftTable.toString()+" INNER JOIN "+rightTable.toString()
				+" ON "+leftCell.toString()+" = "+rightCell.toString()+")";
	}
	
}
