package uielements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.function.Consumer;

import ui.UI;

public abstract class ScrollBar extends UIElement{

	public ScrollBar(int x, int y, int w, int h) {
		super(x, y, w, h);
		scrollBar.setColor(off);
	}
	
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
	public abstract void update(int elementsSize, int windowSize);
	
	public abstract void scroll(int delta);
	
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
	
	protected ArrayList<Consumer<Integer>> scrollListeners = new ArrayList<Consumer<Integer>>();
	
	public void addScrollListener(Consumer<Integer> l) {
		scrollListeners.add(l);
	}

	@Override
	public void handleDrag(int x, int y) {
		dragListeners.stream().forEach(l -> l.accept(x,y));
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
	public void paint(Graphics g) {
		margin1.paint(g);
		margin2.paint(g);
		scrollBar.paint(g);
	}
	
	@Override
	public UIElement locatedAt(int x, int y) {
		if (margin1.containsPoint(x, y)) 	return margin1;
		if (margin2.containsPoint(x, y)) 	return margin2;
		if (scrollBar.containsPoint(x, y)) 	return scrollBar;
		else return null;
	}
	
	@Override 
	public void setUI(UI ui) {
		margin1.setUI(ui);
		margin2.setUI(ui);
		scrollBar.setUI(ui);
	}
}
