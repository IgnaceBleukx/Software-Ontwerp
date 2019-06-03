package domain;

/**
 * Class containing a Cell.
 * A Cell is a simple objects that holds a Value.
 * Cells can be added to Columns, however the Cell
 * is not aware of this (i.e. it's a unidirectional
 * assocation).
 *
 * @param <T>	Type of the Value this cell Holds.
 * 				Can be String, Boolean, Integer
 */
public class Cell<T> {

	/**
	 * The constructor of the Cell
	 * @param v: the value of the cell
	 */
	public Cell(T v){
		this.setValue(v);
	}
	
	/**
	 * This method returns the value of the current cell.
	 */
	public T getValue(){
		return this.value;
	}
	
	/**
	 * This method sets the value of the current cell.
	 * @param v: the value to be set.
	 */
	public void setValue (T v){
		this.value = v;
	}

	
	/**
	 * Value in this cell. Null iff this cell is 'blank'.
	 */
	private T value;


	public String getValueString() {
		if (value == null) return null;
		return value.toString();
	}
}
