package uielements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

abstract class UIEdge extends UIElement {

	UIEdge(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void handlePressed(int x, int y){
		this.beginDrag();
		this.setGrabPointX(x);
		this.setGrabPointY(y);
		new ArrayList<>(pressListeners).stream().forEach(l -> l.accept(this));
	}
	
	@Override
	public void handleReleased(){
		this.endDrag();
	}
	
	@Override
	public void handleDrag(int x, int y) {
		new ArrayList<BiConsumer<Integer,Integer>>(dragListeners).stream().forEach(e -> e.accept(x,y));

	}

	@Override
	public void handleSingleClick() {
		new ArrayList<>(singleClickListeners).stream().forEach(e -> e.run());

	}

	@Override
	public void handleDoubleClick() {
		new ArrayList<>(doubleClickListeners).stream().forEach(e -> e.run());

	}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if (new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode) == null)
			return;
		new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode).stream().forEach(l -> l.run());
	}

	@Override
	public void paint(Graphics g) {
		Color transparant = new Color(0,0,0);
		g.setColor(transparant);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public abstract UIEdge clone();
}
