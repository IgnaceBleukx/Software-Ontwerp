package uielements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

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
	 * turns the border on or off (true or false)
	 * @param b: the value of the border
	 */
	public void setBorder(boolean b){
		border = b;
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
		Shape oldClip = g.getClip();
		g.setClip(getX(),getY(),getWidth(),getHeight());
		super.drawCenteredText(g, this.getText());
		g.setClip(oldClip);
		if(getError()) g.setColor(Color.red);
		else g.setColor(Color.black);
		if(border) g.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());

	}

	@Override
	public void handleSingleClick() {
		new ArrayList<>(singleClickListeners).forEach(l -> l.run());
	}

	@Override
	public void handleDoubleClick() {
		new ArrayList<>(doubleClickListeners).forEach(l -> l.run());
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if (new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode) == null)
			return;
		
		new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode).stream().forEach(l -> l.run());
	}

	@Override
	public void handleDrag(int x, int y) {
		new ArrayList<BiConsumer<Integer,Integer>>(dragListeners).stream().forEach(r -> r.accept(x, y));
	}

	@Override
	public Text clone(){
		Text clone = new Text(getX(),getY(),getWidth(),getHeight(),getText());
		clone.border = border;
		return clone;
	}
	
}
