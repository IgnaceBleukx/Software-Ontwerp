package uielements;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Button extends UIElement {

	/** Constructor of the Button.
	 * @param x: The x position of the left top corner of the Button.
	 * @param y: The y position of the left top corner of the Button.
	 * @param text: The text of the button.
	 */
	public Button(int x, int y,int w, int h, String text){
		super(x,y, w, h);
		setText(text);
	}
	
	/**
	 * The (optional) text inside the button
	 */
	private String text ;
	
	/**
	 * This method returns the text of the current Button.
	 */
	public String getText(){
		return this.text;
	}
	
	/**
	 * @param t: The text to be set to the current Button.
	 * This method sets the text of the current Button.
	 */
	private void setText(String t){
		this.text = t;
	}

	@Override
	public void paint(Graphics g) {
	    // Drawing button
		g.setColor(getColor());
		int arcWidth = 8;
		int arcHeight = 8;
		g.fillRoundRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), arcWidth, arcHeight);
		g.setColor(Color.BLACK);
		g.drawRoundRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), arcWidth, arcHeight);
		//Drawing text on button:
		drawCenteredText(g, this.getText());
	}

	@Override
	public void handleSingleClick() {
		singleClickListeners.stream().forEach(l -> l.run());
	} 

	@Override
	public void handleDoubleClick() {
		doubleClickListeners.stream().forEach(l -> l.run());
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if (keyboardListeners.get(keyCode) == null)
			return;
		keyboardListeners.get(keyCode).stream().forEach(l -> l.run());
	}
	
	@Override
	public void handleDrag(int x, int y) {
		dragListeners.stream().forEachOrdered(r -> r.accept(x, y));
	}
	

	
}
