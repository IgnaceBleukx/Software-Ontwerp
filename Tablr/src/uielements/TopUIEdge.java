package uielements;

public class TopUIEdge extends UIEdge {

	public TopUIEdge(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	@Override
	public void resizeT(int deltaH){
		move(0,deltaH);
	}
	
	@Override
	public void resizeB(int deltaH){
	}

	/**
	 * Clones this object
	 */
	@Override
	public TopUIEdge clone(){
		return new TopUIEdge(getX(),getY(),getWidth(),getHeight());
	}
}
