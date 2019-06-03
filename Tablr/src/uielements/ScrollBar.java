package uielements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import Utils.Rounder;
import ui.UI;

public abstract class ScrollBar extends UIElement{

	public ScrollBar(int x, int y, int w, int h) {
		super(x, y, w, h);
		scrollBar.setColor(off);
		scrollBar.addPressListener((f) -> new ArrayList<>(pressListeners).stream().forEach(l -> l.accept(this)));
	}
	
	protected Rounder rounder = new Rounder();
	
	private Color on = Color.LIGHT_GRAY;
	private Color off = new Color(230,230,230);
	
	protected Button scrollBar = new Button(getX(),getY(),getWidth(),getHeight(),"-" + "\n" + "-");
	protected Button margin1;
	protected Button margin2;
	
	/**
	 * This method is used to update the scrollbars size according to the elements it is used with.
	 * @param elementsSize 	The Size of the elements to scroll trough.
	 * @param windowSize	The Size of the viewable window.
	 */
	public abstract void update(int elementsStart, int elementsEnd,int windowStart, int windowEnd);
	
	public abstract void scroll(int delta);
	
	public abstract boolean isValidDelta(int delta);
	
	public void addScrollListener(Consumer<Integer> c) {
		scrollListeners.add(c);
	}
	
	protected ArrayList<Consumer<Integer>> scrollListeners = new ArrayList<Consumer<Integer>>();
	
	private boolean active;
	
	public boolean isActive() {
		return active;
	}
	
	protected void enable() {
		scrollBar.setColor(on);
		active = true;
	}

	protected void disable() {
		scrollBar.setColor(off);
		active = false;
	}
	
	public Button getScrollBar() {
		return this.scrollBar;
	}
	
	@Override
	public void setGrabPointX(int x){
		scrollBar.setGrabPointX(x);
	}
	
	@Override
	public void setGrabPointY(int y){
		scrollBar.setGrabPointY(y);
	}
	
	@Override
	public int getGrabPointX() {
		return scrollBar.getGrabPointX();
	}
	
	@Override
	public int getGrabPointY() {
		return scrollBar.getGrabPointY();
	}

	@Override
	public void handleDrag(int x, int y) {
		new ArrayList<BiConsumer<Integer,Integer>>(dragListeners).stream().forEach(l -> l.accept(x,y));
	}

	@Override
	public void handleSingleClick() {
		new ArrayList<>(singleClickListeners).stream().forEach(l -> l.run());
	}

	@Override
	public void handleDoubleClick() {
		new ArrayList<>(doubleClickListeners).stream().forEach(l -> l.run());
		
	}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if (new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode) == null)
			return;
		new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode).stream().forEach(l -> l.run());
	}
	
	@Override
	public void paint(Graphics g) {
		if (isActive()) {
			margin1.paint(g);
			margin2.paint(g);
		}
		scrollBar.paint(g);
	}
	
	@Override
	public UIElement locatedAt(int x, int y) {
		if (scrollBar.containsPoint(x, y)) 	return scrollBar;
		if (margin1.containsPoint(x, y)) 	return margin1;
		if (margin2.containsPoint(x, y)) 	return margin2;
		else return null;
	}
	
	@Override 
	public void setUI(UI ui) {
		margin1.setUI(ui);
		margin2.setUI(ui);
		scrollBar.setUI(ui);
	}
	
	@Override
	public void move(int deltaX, int deltaY) {
		setX(getX()+deltaX);
		setY(getY()+deltaY);
		margin1.move(deltaX, deltaY);
		margin2.move(deltaX, deltaY);
		scrollBar.move(deltaX, deltaY);
	}

	/**
	 * Clones this object
	 */
	@Override
	public abstract ScrollBar clone();
}
