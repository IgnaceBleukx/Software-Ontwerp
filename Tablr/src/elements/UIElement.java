package elements;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class UIElement {
	
	public UIElement(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	private int x;
	private int y;
	private int w;
	private int h;
	
	
	/*
	 * This method returns the x-position of the current UIElement.
	 */
	public int getX(){
		return this.x;
	}
	
	/*
	 * This method returns the y-position of the current UIElement.
	 */
	public int getY(){
		return this.y;
	}
	
	/*
	 * This method returns the width of the current UIElement.
	 */
	public int getWidth(){
		return this.w;
	}
	
	/*
	 * This method returns the height of the current UIElement.
	 */
	public int getHeight(){
		return this.h;
	}
	
	/*
	 * This method paints the UIElement on the canvas window specified by the Graphics element.
	 * This method should be overwritten by every subclass of UIElement.
	 * @param g: The graphics engine on which the UIElement is painted.
	 */
	public void paint(Graphics g){
		
	}

	/**
	 * Draws text centered in the UIElement.
	 * @param g		Graphics object
	 * @param text	String to draw
	 */
	public void drawCenteredText(Graphics g, String text){
		Font font = g.getFont();
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = this.getX()+ (this.getWidth()- metrics.stringWidth(text)) / 2;
	    int y = this.getY() +  ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.drawString(text, this.x, this.y);
	}

	/**
	 *	Returns whether (x,y) is inside the bounds of this UIElement
	 * @param x 	X Coordinate
	 * @param y 	Y Coordinate
	 */
	protected boolean containsPoint(int x,int y) {
		return (x >= getX() &&
				y >= getY() &&
				x <= getX()+getWidth() &&
				y <= getY()+getHeight());
	}


	/**
	 * Returns the most specific UIElement located at (x,y)
	 * @param x		X Coordinate
	 * @param y		Y Coordinate
	 * @return		UIElement a at position (x,y)
	 * @note		If possible and correct, UIElements inside containers will be returned
	 */
	public UIElement locatedAt(int x, int y) {
		if (containsPoint(x,y))
			return this;
		else
			return null;
	}

}
