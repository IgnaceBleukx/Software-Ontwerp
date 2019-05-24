package uielements;

public class TopRightUICorner extends UIEdge {

	public TopRightUICorner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	@Override
	public void resizeL(int delta){
	}
	
	@Override 
	public void resizeR(int delta){
		this.move(delta,0);
	}
	
	@Override
	public void resizeT(int delta){
		this.move(0, delta);
	}
	
	@Override
	public void resizeB(int delta){
	}

	/**
	 * Clones this object
	 */
	@Override
	public TopRightUICorner clone(){
		return new TopRightUICorner(getX(),getY(),getWidth(),getHeight());
	}
	
}
