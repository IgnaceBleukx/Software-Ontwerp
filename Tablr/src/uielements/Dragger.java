package uielements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 * Class containing a Dragger. A dragger is a simple invisible object that 
 * can be dragged.
 * @author quinten
 *
 */
public class Dragger extends UIElement{

	/**
	 * Creates a new Dragger
	 * @param x		x coordinate
	 * @param y		y coordinate	
	 * @param w		width
	 * @param h		height
	 */
	public Dragger(int x, int y, int w, int h){
		super(x,y,w,h);
	}
	
	/**
	 * Handles dragging this object
	 */
	@Override
	public void handleDrag(int x, int y) {
		new ArrayList<BiConsumer<Integer,Integer>>(dragListeners).stream().forEach(r -> r.accept(x, y));
	}

	/**
	 * Start dragging
	 */
	@Override
	public void handlePressed(int x, int y){
		this.beginDrag();
		this.setGrabPointX(x);
		this.setGrabPointY(y);
		new ArrayList<>(pressListeners).stream().forEach(l -> l.accept(this));
	}
	
	/**
	 * Stop dragging
	 */
	@Override
	public void handleReleased(){
	}
	
	@Override
	public void handleSingleClick() {}

	@Override
	public void handleDoubleClick() {}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {}

	@Override
	public void paint(Graphics g) {
		Color transparant = new Color(0,0,0);
		g.setColor(transparant);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void resizeR(int deltaW){
		this.move(deltaW,0);
	}
	@Override
	public void resizeL(int deltaW){
		this.move(deltaW,0);
	}
	
	/**
	 * Clones this object
	 */
	@Override
	public Dragger clone(){
		return new Dragger(getX(),getY(),getWidth(),getHeight());
	}
	
}
