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
	 * Clones this object
	 */
	@Override
	public BottomRightUICorner clone(){
		return new BottomRightUICorner(getX(), getY(), getWidth(), getHeight());
	}
	
}
