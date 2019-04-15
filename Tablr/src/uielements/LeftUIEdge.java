package uielements;

public class LeftUIEdge extends UIEdge {

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

	@Override
	void swell(int i) {
		if (isSwollen && i > 0) return;
		this.setWidth(getWidth()+i);
		this.move(-i/2,0);
		isSwollen = true;
	}

}
