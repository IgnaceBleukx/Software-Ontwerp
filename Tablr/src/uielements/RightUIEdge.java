package uielements;

public class RightUIEdge extends UIEdge {

	public RightUIEdge(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	/**
	 * This method handles resizing from the left for the UIedges. The UIEdge is not resized nor moved.
	 */
	@Override
	public void resizeL(int deltaW){
	}
	
	/**
	 * This method handles resizing from the right for the UIEdge. The UIEdge is not resized, only moved.
	 */
	@Override
	public void resizeR(int deltaW){
		this.move(deltaW,0);
	}

	
	
	@Override
	void swell(int i) {
		if (isSwollen && i > 0) return;
		this.setWidth(getWidth()+i);
		this.move(-i, 0);
		isSwollen = true;
	}

	

	@Override
	public RightUIEdge clone(){
		return new RightUIEdge(getX(),getY(),getWidth(),getHeight());
	}
	
}
