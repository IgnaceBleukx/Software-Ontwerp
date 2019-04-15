package uielements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Dragger extends UIElement{

	
	public Dragger(int x, int y, int w, int h){
		super(x,y,w,h);
	}
	
	@Override
	public void handleDrag(int x, int y) {
		new ArrayList<BiConsumer<Integer,Integer>>(dragListeners).stream().forEach(r -> r.accept(x, y));
	}

	@Override
	public void handlePressed(int x, int y){
		this.beginDrag();
		this.setGrabPointX(x);
		this.setGrabPointY(y);
		new ArrayList<>(pressListeners).stream().forEach(l -> l.run());
	}
	
	@Override
	public void handleReleased(){
	}
	
	@Override
	public void handleSingleClick() {}

	@Override
	public void handleDoubleClick() {}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {}

	
	@Override
	public void paint(Graphics g) {
		Color transparant = new Color(0,0,0);
		g.setColor(transparant);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}
}
