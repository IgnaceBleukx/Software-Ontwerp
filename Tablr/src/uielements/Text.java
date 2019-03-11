package uielements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Text extends UIElement {

	/**
	 * Constructor of the Text.
	 * @param x: The x position of the left top corner of the Text.
	 * @param y: The y position of the left top corner of the Text.
	 * @param t: The text contained by the current Text.
	 */
	public Text(int x, int y,int w, int h, String t) {
		super(x, y, w, h);
		this.setText(t);
	}
	
	/**
	 * The text held in this Text class
	 */
	private String text;
	
	/**
	 * Holds whether or not a border is displayed around the text
	 */
	private boolean border = false;
	
	/**
	 * Holds whether or not the Text is displayed as an error (e.g. it has an incorrect value for its position
	 * in the table)
	 */
	private boolean error;
	
	/**
	 * turns the border on or off (true or false)
	 * @param b: the value of the border
	 */
	public void setBorder(boolean b){
		border = b;
	}
	
	/**
	 * Sets the Text as an error (its text is not correct)
	 */
	public void isError() {
		this.error = true;
	}
	
	/**
	 * Sets the Text as not an error (its text is correct)
	 */
	public void isNotError(){
		this.error = false;
	}

	/**
	 * This method sets the text of the current Text.
	 * @param t: the text to be set.
	 */
	public void setText(String t){
		this.text = t;
	}
	
	/**
	 * This method returns the text the current Text returns.
	 */
	public String getText(){
		return this.text;
	}

	@Override
	public void paint(Graphics g) {
		super.drawCenteredText(g, this.getText());
		if(error) g.setColor(Color.red);
		else g.setColor(Color.black);
		if(border) g.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());

	}

	@Override
	public void handleSingleClick() {
		if(getCommunicationManager().getLockedElement() != (null) && !getCommunicationManager().getLockedElement().equals(this)){
			return;
		}
		for(Runnable r : this.singleClickListeners){
			r.run();
		}
	}

	@Override
	public void handleDoubleClick() {
		if(getCommunicationManager().getLockedElement() != (null) && !getCommunicationManager().getLockedElement().equals(this)){
			return;
		}
		
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if(getCommunicationManager().getLockedElement() != (null) && !getCommunicationManager().getLockedElement().equals(this)){
			return;
		}
		
		if (keyboardListeners.get(keyCode) == null)
			return;
		
		for (Runnable r : keyboardListeners.get(keyCode)) {
			r.run();
		}
	}

	

}
