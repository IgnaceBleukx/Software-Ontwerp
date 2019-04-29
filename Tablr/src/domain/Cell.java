package domain;

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
}
