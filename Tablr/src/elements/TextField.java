package elements;

public class TextField extends UIElement {

	/*
	 * Constructor of the TextField.
	 * @param x: The x position of the left top corner of the TextField.
	 * @param y: The y position of the left top corner of the TextField.
	 * @param checked: Whether the TextField is checked or not.
	 */
	public TextField(double x, double y, String t) {
		super(x, y);
		setText(t);
	}
	
	private String text;
	
	/*
	 * This method sets the text of the current TextBox
	 * @param t: The text to be set.
	 */
	public void setText(String t){
		this.text = t;
	}
	
	/*
	 * This method returns the text of the current TextBox.
	 */
	public String getText(){
		return this.text;
	}

	@Override
	public void paint() {
		// TODO Auto-generated method stub

	}

}
