package uielements;

/**
 * The bottom edge of a subwindow
 */
public class BottomUIEdge extends UIEdge {
	
	/**
	 * Creates a new BottomUIEdge.
	 */
	public BottomUIEdge(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	/**
	 * Resize upwards.
	 * This method has no effect because BottomUIEdge is situated on the bottom of a subwindow.
	 */
	@Override
	public void resizeT(int deltaH){
	}
	
	/**
	 * Resize downwards by moving down.
	 */
	@Override
	public void resizeB(int deltaH){
		this.move(0, deltaH);
	}

	/**
	 * Clones this object
	 */
	@Override
	public BottomUIEdge clone(){
		return new BottomUIEdge(getX(), getY(),getWidth(),getHeight());
	}
	
}
