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
	 * Clones this object
	 */
	@Override
	public BottomLeftUICorner clone(){
		return new BottomLeftUICorner(getX(),getY(),getWidth(),getHeight());
	}
	
}
