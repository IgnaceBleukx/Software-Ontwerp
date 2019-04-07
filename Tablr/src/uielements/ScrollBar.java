package uielements;

import java.awt.Color;

public class ScrollBar extends Button {

	public ScrollBar(int x, int y, int w, int h, ScrollDirection dir) {
		super(x, y, w, h, "-" + "\n" + "-");
		this.scrollDirection = dir;
		margin1.setColor(new Color(230,230,230));
		margin2.setColor(new Color(230,230,230));
		scrollBar.setColor(Color.LIGHT_GRAY);
	}
	
	
	private Button margin1 = new Button(getX(),getY(),0,0,"");
	private Button margin2 =  new Button(getX(),getY(),0,0,"");
	private Button scrollBar = new Button(getX(),getY(),getWidth(),getHeight(),"-" + "\n" + "-");
	
	public void update(int elementsSize, int windowSize) {
		if (elementsSize <= windowSize) 
			this.disable();
		else 
			this.enable();
		
		int newSize = elementsSize > 0 ? windowSize * windowSize / elementsSize : windowSize;
		if (scrollDirection == ScrollDirection.VERTICAL) {
			scrollBar.setHeight(newSize);
			margin1.setHeight(0);
			margin2.setHeight(windowSize - newSize);
		}
		else if (scrollDirection == ScrollDirection.HORIZONTAL) {
			scrollBar.setWidth(newSize);
			margin1.setWidth(0);
			margin2.setWidth(windowSize - newSize);
		}
	}
	
	public void scroll(int delta) {
		
	}
	
	private ScrollDirection scrollDirection;
	
	private boolean active;
	
	public boolean isActive() {
		return active;
	}
	
	private void enable() {
		setColor(Color.LIGHT_GRAY);
		active = true;
	}

	private void disable() {
		setColor(new Color(230,200,200));
		active = false;
	}
}
