package uielements;

public class BottomLeftUICorner extends UIEdge {

	public BottomLeftUICorner(int x, int y, int w, int h) {
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
	}
	
	@Override
	public void resizeB(int delta){
		this.move(0,delta);
	}
	

	@Override
	void swell(int i) {
		if (isSwollen && i > 0) return;
		this.setWidth(getWidth()+i);
		this.setHeight(getHeight()+i);
		this.setX(getX()-i/2);
		this.setY(getY()-i/2);
		isSwollen = !isSwollen;

	}

}
