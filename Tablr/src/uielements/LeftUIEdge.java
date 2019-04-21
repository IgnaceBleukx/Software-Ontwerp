package uielements;

/**
 * The left edge of a subwindow.
 */
public class LeftUIEdge extends UIEdge {
	
	/**
	 * Creates a new LeftUIEdge
	 */
	public LeftUIEdge(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	/**
	 * This method handles the resizing for left uiedges. The uiedge is not resized, only moved.
	 * @param deltaW 	The amount of pixels the uiedge must be shifted to the right.
	 */
	@Override
	public void resizeL(int deltaW){
		this.move(deltaW, 0);
	}
	/**
	 * This method handles the resizing for left UIedges. The uiedge is not resized nor moved.
	 */
	@Override
	public void resizeR(int deltaW){
		
	}
	
	/**
	 * Increases the clickbox for this UIElement to facilitate dragging.
	 */
	@Override
	void swell(int i) {
		if (isSwollen && i > 0) return;
		this.setWidth(getWidth()+i);
		this.move(-i,0);
		isSwollen = true;
	}

	@Override
	public LeftUIEdge clone(){
		return new LeftUIEdge(getX(),getY(),getWidth(),getHeight());
	}
	
}
