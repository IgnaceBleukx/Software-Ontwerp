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
	private boolean border = false;
	
	public void setBorder(boolean b){
		border = b;
	}

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
		if(border) g.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());

	}

	@Override
	public void handleSingleClick() {
		for(Runnable r : this.singleClickListeners){
			r.run();
		}
	}

	@Override
	public void handleDoubleClick() {
		
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
