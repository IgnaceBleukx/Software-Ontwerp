package uielements;

/**
 * 	The Bottom left corner of a subwindow. 
 *  Used to drag horizontally and vertically at the same time from this corner.
 */
public class BottomLeftUICorner extends UIEdge {
	
	/**
	 * Creates a new BottomLeftUICorner
	 */
	public BottomLeftUICorner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	/**
	 * Handles resizing the left side by moving.
	 */
	@Override
	public void resizeL(int delta){
		this.move(delta,0);
	}
	
	/**
	 * Resize to the right.
	 * This method has no effect because BottomLeftUICorner is situated on the left side of a subwindow.
	 */
	@Override 
	public void resizeR(int delta){
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

	@Override
	public BottomLeftUICorner clone(){
		return new BottomLeftUICorner(getX(),getY(),getWidth(),getHeight());
	}
	
}
