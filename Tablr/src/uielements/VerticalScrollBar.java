package uielements;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import Utils.Rounder;

/**
 * The Vertical scrollbar must always be placed on the right hand side of the items. 
 * @author r0674255
 *
 */
public class VerticalScrollBar extends ScrollBar{

	public VerticalScrollBar(int x, int y, int w, int h) {
		super(x, y, w, h);
		margin1 = new Button(getX(), getY(), getWidth(),0,"");
		margin2 = new Button(getX(), getY()+getHeight(),getWidth(), 0,"");
		
		//Adding listeners
		margin1.addSingleClickListener(() -> {
			int delta = -20;
			if (!isValidDelta(delta))
				delta = -margin1.getHeight();
			this.scroll(delta);
			});
		margin2.addSingleClickListener(() -> {
			int delta = 20;
			if (!isValidDelta(delta))
				delta = margin2.getHeight();
			this.scroll(delta);
		});
	}

	public void update(int elementsHeight, int windowHeight) {
		this.setHeight(windowHeight);
		if (elementsHeight <= windowHeight) { 
			this.disable();
			scrollBar.setHeight(windowHeight);
			margin1.setY(getY());
			margin1.setHeight(0);
			margin2.setY(getEndY());
			margin2.setHeight(0);
		}
		else {
			this.enable();
			int newSize = elementsHeight > 0 ? windowHeight * windowHeight / elementsHeight : windowHeight;
			int distance = scrollBar.getHeight() - newSize;
			scrollBar.setHeight(newSize);
			margin1.setHeight(0);
			margin2.setY(scrollBar.getEndY());
			margin2.setHeight(getEndY()-margin2.getY());
		}
	}
	
	@Override
	public boolean isValidDelta(int delta) {
		return scrollBar.getY()+delta >= getY() && scrollBar.getEndY()+delta <= getEndY();
	}

	@Override
	public void scroll(int delta) {
		if (!isValidDelta (delta))
			return;
		margin1.resizeB(delta);
		margin2.resizeT(delta);
		scrollBar.move(0, delta);
		scrollListeners.stream().forEach(l -> l.accept(rounder.round((double )delta*this.getHeight()/scrollBar.getHeight())));
	}
	
	@Override
	public void resizeL(int deltaX){	
	}
	@Override
	public void resizeR(int deltaX){
		this.move(deltaX,0);
	}
	@Override
	public void resizeT(int deltaY){
		this.move(0, deltaY);
		this.setHeight(getHeight()-deltaY);
	}
	@Override
	public void resizeB(int deltaY){
		this.setHeight(getHeight()+deltaY);
	}

	@Override
	public VerticalScrollBar clone(){
		return new VerticalScrollBar(getX(),getY(),getWidth(),getHeight());
	}
}