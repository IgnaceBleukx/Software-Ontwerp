package elements;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Button extends UIElement {

	/* Constructor of the Button.
	 * @param x: The x position of the left top corner of the Button.
	 * @param y: The y position of the left top corner of the Button.
	 * @param text: The text of the button.
	 */
	public Button(int x, int y,int w, int h, String text){
		super(x,y, w, h);
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
	public void paint(Graphics g) {
		super.drawCenteredText(g, this.getText());
	    // Drawing button
	    int arcWidth = (int) Math.round(super.getWidth() / 4);
		int arcHeight = (int) Math.round(super.getHeight() / 4);
		g.drawRoundRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), arcWidth, arcHeight);
	}
}
