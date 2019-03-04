package uielements;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class TextField extends UIElement {

	/*
	 * Constructor of the TextField.
	 * @param x: The x position of the left top corner of the TextField.
	 * @param y: The y position of the left top corner of the TextField.
	 * @param checked: Whether the TextField is checked or not.
	 */
	public TextField(int x, int y,int w, int h, String t) {
		super(x, y, w, h);
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
	public void paint(Graphics g) {
		FontMetrics metrics =  g.getFontMetrics(g.getFont());
		g.setColor(Color.white);
		g.fillRect(super.getX(),super.getY(), super.getWidth(), super.getHeight());
		g.setColor(Color.black);
		g.drawRect(super.getX(),super.getY(), super.getWidth(), super.getHeight());
		int y = this.getY() +  ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
		
		if (editing == false)
			g.drawString(this.getText(), super.getX()+10, y);
		else
			g.drawString(this.getText() + "<", super.getX()+10, y);

	}
	
	/**
	 * Variable that indicates whether this TextField is currently accepting keyboard input
	 */
	private boolean editing = false;

	public void startEditing() {
		this.editing = true;
	}
	
	public void endEditing() {
		this.editing = false;
	}
	
	
	@Override
	public void handleSingleClick() {
		if (!editing) {
			startEditing();
			System.out.println("Edit mode...");
		}
		
		
	}

	@Override
	public void handleDoubleClick() {
		
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if (keyCode == 10 && editing == true) { //Enter, end editing
			endEditing();
		}
				
		if (keyCode == 8 && editing == true) { //Backspace remove character
			if (getText().length() > 0) {
				String newText = getText().substring(0, getText().length() - 1);
				setText(newText);
			}
				
			
		}
		if (Character.isLetterOrDigit(keyChar) && editing == true) {
			setText(getText()+keyChar);
		}
		
		
		if (keyboardListeners.get(keyCode) == null)
			return;
		
		for (Runnable r : keyboardListeners.get(keyCode)) {
			r.run();
		}

	}

}
