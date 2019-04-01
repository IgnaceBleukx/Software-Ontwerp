package uielements;

import java.awt.Color;
import java.awt.Graphics;

public class ScrollBar extends UIElement {

	public ScrollBar(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	private Button bar = new Button(getX(), getY(), getWidth(), getHeight(), "");
	
	public void setBar(Button scrollBar) {
		this.bar = scrollBar;
	}
	
	private boolean enabeled = true;

	public void enable() {
		enabeled = true;
	}
	
	public void disable() {
		enabeled = false;
	}
	
	public boolean isEnabeled() {
		return enabeled;
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawRect(getX(),getY(), getWidth(), getHeight());
		if(enabeled) bar.setColor(Color.darkGray);
		else bar.setColor(Color.LIGHT_GRAY);
		bar.paint(g);
	}
	
	@Override
	public void handleDrag(int x, int y) {
		if (!enabeled) return;
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
	public UIElement locatedAt(int x, int y) {
		if (bar.containsPoint(x, y)) 			return bar;
		else if (this.containsPoint(x, y)) 	return this;
		else 									return null;
	}

}
