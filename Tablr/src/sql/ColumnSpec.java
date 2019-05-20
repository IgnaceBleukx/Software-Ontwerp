package sql;

/**
 * Class containing a Columnspec.
 * A Columnspec is a specification of a column in SQL,
 * e.g t.Column1 AS result.
 * In this example, t.Column1 is a CellIDExpression,
 * and result is a name given to the resulting column.
 * @author quinten
 *
 */
public class ColumnSpec {
	/**
	 * Creates a new ColumnSpec
	 * @param cellID	Column
	 * @param name		Name for Column
	 */
	public ColumnSpec(CellIDExpression cellID, String name) {
		this.cellID = cellID;
		this.name = name;
	}
	
	private CellIDExpression cellID;
	private String name;
	
	/**
	 * Returns the CellID (column name)
	 */
	public CellIDExpression getCellID() {
		return cellID;
	}
	
	/**
	 * Returns the resulting column's name/alias.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "ColumnSpec("+cellID.toString()+" AS "+name.toString()+")";
	}
}
