package uielements;

import Utils.Rounder;

/**
 * Class containing a Horizontal Scrollbar
 */
public class HorizontalScrollBar extends ScrollBar {
	
	/**
	 * Creates a new Horizontal ScrollBar
	 * @param rounder	Rounder object used to round scrolling coordinates in a balanced way.
	 */
	public HorizontalScrollBar(int x, int y, int w, int h) {
		super(x, y, w, h);
		margin1 = new Button(getX(), getY(), 0,getHeight(),"");
		margin2 = new Button(getX()+getWidth(),getY(),0,getHeight(),"");
		margin1.setUI(getUI());
		margin2.setUI(getUI());
		scrollBar.setUI(getUI());
	}
	
	/**
	 * Changes the size of the scrollbar to match the subwindow's contents.
	 */
	@Override
	public void update(int elementsWidth, int windowWidth) {
		this.setWidth(windowWidth);
		if (elementsWidth <= windowWidth) { 
			super.disable();
			scrollBar.setWidth(windowWidth);
			margin1.setX(getX());
			margin1.setWidth(0);
			margin2.setX(getEndX());
			margin2.setWidth(0);
		}
		else {
			this.enable();
			int newSize = elementsWidth > 0 ? windowWidth * windowWidth / elementsWidth : windowWidth;
			int distance = scrollBar.getWidth() - newSize;
			margin1.resizeR(distance);
			margin2.resizeL(distance);
			scrollBar.setWidth(newSize);
		}
	}
	
	/**
	 * Scrolls horizontally.
	 */
	@Override
	public void scroll(int delta) {
		margin1.resizeR(delta);
		margin2.resizeL(delta);
		scrollBar.move(delta, 0);
	}
	
	/**
	 * @param delta		X scroll distance		
	 * @return 			Whether scrolling by delta pixels is allowed.
	 */
	@Override
	public boolean isValidDelta(int delta) {
		return scrollBar.getX()+delta >= getX() && scrollBar.getEndX()+delta <= getEndX();
	}
	
	/**
	 * Handles resizing to the left.
	 */
	@Override
	public void resizeL(int deltaX){
		this.move(deltaX,0);
		this.setWidth(getWidth()-deltaX);
	}
	
	/**
	 * Handles resizing to the right.
	 */
	@Override
	public void resizeR(int deltaX){
		this.setWidth(getWidth()+deltaX);
	}
	
	/**
	 * Handles resizing to the top.
	 */
	@Override
	public void resizeT(int deltaY){
	}
	
	/**
	 * Handles resizing to the bottom.
	 */
	@Override
	public void resizeB(int deltaY){
		this.move(0, deltaY);
	}

	public HorizontalScrollBar clone(){
		return new HorizontalScrollBar(getX(),getY(),getWidth(),getHeight());
	}
}
