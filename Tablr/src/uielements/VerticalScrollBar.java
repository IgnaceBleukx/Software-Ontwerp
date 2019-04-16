package uielements;

import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 * The Vertical scrollbar must always be placed on the right hand side of the items. 
 * @author r0674255
 *
 */
public class VerticalScrollBar extends ScrollBar{

	public VerticalScrollBar(int x, int y, int w, int h) {
		super(x, y, w, h);
		margin1 = new Button(getX(), getY(), getWidth(),0,"/\\");
		margin2 = new Button(getX(), getY()+getHeight(),getWidth(), 0,"\\/");
		
		//Adding listeners
		margin1.addSingleClickListener(() -> this.scroll(-20));
		margin2.addSingleClickListener(() -> this.scroll(20));
	}

	public void update(int elementsHeight, int windowHeight) {
		this.setHeight(windowHeight);
		if (elementsHeight <= windowHeight) { 
			this.disable();
			scrollBar.setHeight(windowHeight);
			margin1.setY(getY());
			margin1.setHeight(0);
			margin2.setY(getY() + getHeight());
			margin2.setHeight(0);
		}
		else {
			this.enable();
			int newSize = elementsHeight > 0 ? windowHeight * windowHeight / elementsHeight : windowHeight;
			int distance = scrollBar.getHeight() - newSize;
			//margin1.resize(0,distance);
			margin2.resizeT(distance);
			scrollBar.setHeight(newSize);
		}
	}
	
	@Override
	public boolean isValidDelta(int delta) {
		System.out.println("[VerticalScrollBar.java:33] : endPoint of scrollBar: " + scrollBar.getEndY());
		System.out.println("[VerticalScrollBar.java:34] : endPoint of scrollBarElement: " + getEndY());
		System.out.println("[VerticalScrollBar.java:35] : delta: " +delta);
		return scrollBar.getY()+delta >= getY() && scrollBar.getEndY()+delta <= getEndY();
	}

	@Override
	public void scroll(int delta) {
		if (!isValidDelta (delta)) {
			return;}
		margin1.resizeB(delta);
		margin2.resizeT(-delta);
		scrollBar.move(0, delta);
		scrollListeners.stream().forEach(l -> l.accept(delta*this.getHeight()/scrollBar.getHeight()));
	}
}