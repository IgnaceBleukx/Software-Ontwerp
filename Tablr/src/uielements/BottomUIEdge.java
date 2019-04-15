package uielements;

public class BottomUIEdge extends UIEdge {

	public BottomUIEdge(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	@Override
	public void resizeT(int deltaH){
	}
	
	@Override
	public void resizeB(int deltaH){
		this.move(0, deltaH);
	}

	@Override
	void swell(int i) {
		if (isSwollen && i > 0) return;
		this.setHeight(getHeight()+i);
		this.move(0, -i/2);
		isSwollen = true;
	}

}
