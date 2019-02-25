package elements;

public class Button extends UIElement {

	/* Constructor of the Button.
	 * @param x: The x position of the left top corner of the Button.
	 * @param y: The y position of the left top corner of the Button.
	 * @param text: The text of the button.
	 */
	public Button(double x, double y, String text){
		super(x,y);
		setText(text);
	}
	
	private String text ;
	
	/*
	 * This method returns the text of the current Button.
	 */
	public String getText(){
		return this.text;
	}
	
	/*
	 * @param t: The text to be set to the current Button.
	 * This method sets the text of the current Button.
	 */
	public void setText(String t){
		this.text = t;
	}

	@Override
	public void paint() {
		// TODO Auto-generated method stub
		
	}
}
