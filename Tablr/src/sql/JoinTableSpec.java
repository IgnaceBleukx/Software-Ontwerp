package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Cell;
import domain.Column;
import domain.StoredTable;
import domain.Table;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;

public class JoinTableSpec extends TableSpec {
	public JoinTableSpec(TableSpec leftTable, SimpleTableSpec rightTable, 
						 CellIDExpression leftCell, CellIDExpression rightCell) {
		this.leftTable = leftTable;
		this.rightTable = rightTable;
		this.leftCell = leftCell;
		this.rightCell = rightCell;

	}
	
	TableSpec leftTable;
	SimpleTableSpec rightTable;

	
	CellIDExpression leftCell;
	CellIDExpression rightCell;
	
	public TableSpec getLeftTable() {
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

	/**
	 * Creates map (alias -> tableName)
	 */
	@Override
	public HashMap<String, String> findTableNameAliases() {
		HashMap<String,String> s = new HashMap<String,String>();
		
		HashMap<String,String> sLeft = leftTable.findTableNameAliases();
		HashMap<String,String> sRight = rightTable.findTableNameAliases();
		
		s.putAll(sLeft);
		s.putAll(sRight);
		
		return s;
	}

	/**
	 * Resolves this TableSpec into a Table
	 * @throws InvalidQueryException 
	 * @throws InvalidNameException 
	 */
	@Override
	public Table resolve(ArrayList<Table> tables) throws InvalidQueryException {
		Table left = leftTable.resolve(tables);
		Table right = rightTable.resolve(tables);
		
		String columnNameLeft = leftCell.getcolumnName();
		String columnNameRight = rightCell.getcolumnName();
		
		ArrayList<Column> columnsLeft = left.getColumns();
		ArrayList<Column> columnsRight = right.getColumns();
		
		Column leftColumn = columnsLeft.
							stream().
							filter(c -> c.getName().equals(columnNameLeft)).
							findFirst().
							orElseThrow(() -> new InvalidQueryException("Unable to find column "+columnNameLeft+" in JOIN clause"));
		
		Column rightColumn = columnsRight.
				stream().
				filter(c -> c.getName().equals(columnNameRight)).
				findFirst().
				orElseThrow(() -> new InvalidQueryException("Unable to find column "+columnNameRight+" in JOIN clause"));
		
		ArrayList<RowPair> matches = new ArrayList<>();
		
		//Find rows that match, save as tuples (i1, i2)
		for (int i=0; i< leftColumn.getCells().size();i++) {
			for (int j=0; i<rightColumn.getCells().size();i++) {
				if (leftColumn.getCells().get(i).getValue().equals(rightColumn.getCells().get(j))){
					matches.add(new RowPair(i,j));
				}
			}
		}
		
		ArrayList<Column> newColumns = new ArrayList<Column>();
			columnsLeft.stream()
					   .forEach((c) -> createDuplicateColumn(c, newColumns));

		
		//Build new table from the list of matches (i1,i2)
		for (RowPair p : matches) {
			ArrayList<Cell> leftCells = left.getRowByIndex(p.getLeftIndex(),"");
			ArrayList<Cell> rightCells = right.getRowByIndex(p.getRightIndex(),columnNameRight);
			ArrayList<Cell> newRow = new ArrayList<Cell>(leftCells);
			newRow.addAll(rightCells);
		}
		
		return null;
	}
	
	private void createDuplicateColumn(Column c, ArrayList<Column> list) {
		try {
			list.add(new Column(c.getName(),null,c.getColumnType(),c.getDefault()));
		} catch (InvalidNameException e) {
			//
		}
	}
	
}

class RowPair {
	public RowPair(int i1, int i2) {
		this.i1 = i1;
		this.i2 = i2;
	}
	int i1;
	int i2;
	
	public int getLeftIndex() {
		return i1;
	}
	
	public int getRightIndex() {
		return i2;
	}
	
}
