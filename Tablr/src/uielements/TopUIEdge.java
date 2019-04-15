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

	@Override
	void swell(int i) {
		if (isSwollen && i > 0) return;
		setHeight(getHeight()+i);
		this.move(0,-i);
		isSwollen = true;
	}
}
