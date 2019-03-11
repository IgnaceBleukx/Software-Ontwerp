package uielements;

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
	public void setText(String t){
		this.text = t;
	}

	@Override
	public void paint(Graphics g) {
		drawCenteredText(g, this.getText());
	    // Drawing button
	    int arcWidth = 8;
		int arcHeight = 8;
		g.drawRoundRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), arcWidth, arcHeight);
	}

	@Override
	public void handleSingleClick() {
		if(!getCommunicationManager().getLockedElement().equals(null) && !getCommunicationManager().getLockedElement().equals(this)){
			return;
		}
		for (Runnable r : this.singleClickListeners) {
			r.run();
		}
	} 

	@Override
	public void handleDoubleClick() {		
		for (Runnable r : this.doubleClickListeners ) {
			r.run();
		}
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if (keyboardListeners.get(keyCode) == null)
			return;
		
		for (Runnable r : keyboardListeners.get(keyCode)) {
			r.run();
		}
	}

	
}
