package elements;

public class Checkbox extends UIElement {

	/*
	 * Constructor of the checkbox.
	 * @param x: The x position of the left top corner of the checkbox.
	 * @param y: The y position of the left top corner of the checkbox.
	 * @param checked: Whether the checkbox is checked or not.
	 */
	public Checkbox(double x, double y, boolean checked){
		super(x, y);
		this.checked = checked;
	}
	
	private boolean checked = false;
	
	@Override
	public void paint(){
		// TODO Auto-generated method stub
		
	}

}
