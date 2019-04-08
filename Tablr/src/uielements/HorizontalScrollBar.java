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
	//	margin1.resize(distance,0);
		margin2.resize(distance,0);
		margin2.move(-distance,0);
		scrollBar.setHeight(newSize);

	}

	@Override
	public void scroll(int delta) {
		margin1.resize(delta,0);
		margin2.resize(-delta,0);
		margin2.move(-delta,0);
		scrollBar.move(delta, 0);
	}

	@Override
	public boolean isValidDelta(int delta) {
		return scrollBar.getX()+delta >= getX() && scrollBar.getX()+scrollBar.getWidth() <= getX()+getWidth();
	}

}
