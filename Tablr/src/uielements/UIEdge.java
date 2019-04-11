package uielements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class UIEdge extends UIElement {

	public UIEdge(int x, int y, int w, int h) {
		super(x, y, w, h);
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

}
