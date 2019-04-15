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
		if (elementsWidth <= windowWidth) { 
			super.disable();
			return;
		}
		else 
			this.enable();
		
		int newSize = elementsWidth > 0 ? windowWidth * windowWidth / elementsWidth : windowWidth;
		int distance = scrollBar.getWidth() - newSize;
		margin1.resizeR(distance);
		margin2.resizeL(distance);
		scrollBar.setHeight(newSize);

	}

	@Override
	public void scroll(int delta) {
		margin1.resizeL(delta);
		margin2.resizeR(-delta);
		scrollBar.move(delta, 0);
	}

	@Override
	public boolean isValidDelta(int delta) {
		return scrollBar.getX()+delta >= getX() && scrollBar.getX()+scrollBar.getWidth() <= getX()+getWidth();
	}
	
	
	@Override
	public void resizeL(int deltaW){
		setX(getX() + deltaW);
		setWidth(getWidth() - deltaW);
		margin1.resizeL(deltaW);
		margin2.resizeL(deltaW);
		scrollBar.resizeL(deltaW);
	}
}
