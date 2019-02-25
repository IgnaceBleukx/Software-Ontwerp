package elements;

public class Text extends UIElement {

	/*
	 * Constructor of the Text.
	 * @param x: The x position of the left top corner of the Text.
	 * @param y: The y position of the left top corner of the Text.
	 * @param t: The text contained by the current Text.
	 */
	public Text(double x, double y, String t) {
		super(x, y);
		this.setText(t);
	}
	
	private String text;
	
	/*
	 * This method sets the text of the current Text.
	 * @param t: the text to be set.
	 */
	public void setText(String t){
		this.text = t;
	}
	
	/*
	 * This method returns the text the current Text returns.
	 */
	public String getText(){
		return this.text;
	}

	@Override
	public void paint() {
		// TODO Auto-generated method stub

	}

}
