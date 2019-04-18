package uielements;

public class HorizontalScrollBar extends ScrollBar {

	public HorizontalScrollBar(int x, int y, int w, int h) {
		super(x, y, w, h);
		margin1 = new Button(getX(), getY(), 0,getHeight(),"");
		margin2 = new Button(getX()+getWidth(),getY(),0,getHeight(),"");
		margin1.setUI(getUI());
		margin2.setUI(getUI());
		scrollBar.setUI(getUI());
	}

	@Override
	public void update(int elementsWidth, int windowWidth) {
		this.setWidth(windowWidth);
		if (elementsWidth <= windowWidth) { 
			super.disable();
			scrollBar.setWidth(windowWidth);
			margin1.setX(getX());
			margin1.setWidth(0);
			margin2.setX(getEndX());
			margin2.setWidth(0);
		}
		else {
			this.enable();
			int newSize = elementsWidth > 0 ? windowWidth * windowWidth / elementsWidth : windowWidth;
			int distance = scrollBar.getWidth() - newSize;
			margin1.resizeR(distance);
			margin2.resizeL(distance);
			scrollBar.setWidth(newSize);
		}
	}

	@Override
	public void scroll(int delta) {
		margin1.resizeR(delta);
		margin2.resizeL(delta);
		scrollBar.move(delta, 0);
	}

	@Override
	public boolean isValidDelta(int delta) {
		return scrollBar.getX()+delta >= getX() && scrollBar.getEndX()+delta <= getEndX();
	}

	@Override
	public void resizeL(int deltaX){
		this.move(deltaX,0);
		this.setWidth(getWidth()-deltaX);
	}
	@Override
	public void resizeR(int deltaX){
		this.setWidth(getWidth()+deltaX);
	}
	@Override
	public void resizeT(int deltaY){
	}
	@Override
	public void resizeB(int deltaY){
		this.move(0, deltaY);
	}
}
