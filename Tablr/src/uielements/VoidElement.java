package uielements;

import java.awt.Color;
import java.awt.Graphics;

public class VoidElement extends UIElement{

	public VoidElement(int x, int y, int w, int h, Color c) {
		super(x, y, w, h);
		color = c;
	}

	private Color color;

	
	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(getX(),getY(),getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(getX(),getY(),getWidth(), getHeight());
	}
	
	@Override
	public boolean containsPoint(int x,int y) {
		return false;
	}
	
	@Override
	public void handleDrag(int x, int y) {
				
	}

	@Override
	public void handleSingleClick() {
	
	}

	@Override
	public void handleDoubleClick() {

	}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {

	}

	@Override
	public VoidElement clone(){
		return new VoidElement(getX(),getY(),getWidth(),getHeight(),color);
	}
	
}
