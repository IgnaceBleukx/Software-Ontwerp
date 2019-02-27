package uielements;

import java.awt.Font;
import java.awt.Graphics;

public class Text extends UIElement {

	/*
	 * Constructor of the Text.
	 * @param x: The x position of the left top corner of the Text.
	 * @param y: The y position of the left top corner of the Text.
	 * @param t: The text contained by the current Text.
	 */
	public Text(int x, int y,int w, int h, String t) {
		super(x, y, w, h);
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
	public void paint(Graphics g) {
		super.drawCenteredText(g, this.getText());

	}

}
