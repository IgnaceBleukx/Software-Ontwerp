package uielements;

public class TopLeftUICorner extends UIEdge {

	public TopLeftUICorner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	@Override
	public void resizeL(int delta){
		this.move(delta,0);
	}
	
	@Override 
	public void resizeR(int delta){
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
	public TopLeftUICorner clone(){
		return new TopLeftUICorner(getX(),getY(),getWidth(),getHeight());
	}
	
	
	
}
