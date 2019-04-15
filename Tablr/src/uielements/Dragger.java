package uielements;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Dragger extends UIElement{

	private boolean isSwollen = false;
	
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
		this.swell(10);
	}
	
	
	private void swell(int i) {
		System.out.println("[Dragger.java:30]: swelling");
		if (isSwollen && i > 0) return;
		this.setWidth(getWidth()+i);
		this.move(-i/2,0);
		isSwollen = true;
	}
	
	@Override
	public void handleReleased(){
		this.endDrag();
		if (isSwollen){
			System.out.println("[Dragger.java:41]: deswelling");
			swell(-10);
			isSwollen = false;
		}
	}
	
	@Override
	public void handleSingleClick() {}

	@Override
	public void handleDoubleClick() {}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {}

	@Override
	public void paint(Graphics g) {}
}
