package uielements;

/**
 * 	The Bottom right corner of a subwindow. 
 *  Used to drag horizontally and vertically at the same time from this corner.
 */
public class BottomRightUICorner extends UIEdge {
	
	/**
	 * Creates a new BottomRightUICorner
	 */
	public BottomRightUICorner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	/**
	 * Resize to the left.
	 * This method has no effect because BottomLeftUICorner is situated on the right side of a subwindow.
	 */
	@Override
	public void resizeL(int delta){
	}
	
	/**
	 * Resize to the right by moving to the right.
	 */
	@Override 
	public void resizeR(int delta){
		this.move(delta,0);
	}
	
	/**
	 * Resize upwards.
	 * This method has no effect because BottomLeftUICorner is situated on the bottom side of a subwindow.
	 */
	@Override
	public void resizeT(int delta){
	}
	
	/**
	 * Resize downwards by moving down.
	 */
	@Override
	public void resizeB(int delta){
		this.move(0,delta);
	}
	
	/**
	 * Increases the clickbox for this UIElement to facilitate dragging.
	 */
	@Override
	void swell(int i) {
		if (isSwollen && i > 0) return;
		this.setWidth(getWidth()+i);
		this.setHeight(getHeight()+i);
		this.setX(getX()-i/2);
		this.setY(getY()-i/2);
		isSwollen = !isSwollen;
	}

}
