package uielements;

import java.awt.Graphics;
import java.util.ArrayList;

public class UIRow extends UIElement {

	public UIRow(int x, int y,int w, int h,  ArrayList<UIElement> elements) {
		super(x, y, w, h);
		this.elements = elements;
	}
	
	
	private ArrayList<UIElement> elements;

	public void paint(Graphics g){
	    g.drawRect(getX(),getY(),getWidth(),getHeight());

		for (UIElement e : this.elements){
			e.paint(g);
		}
	}

	/**
	 * Returns the most specific UIElement located at (x,y) by searching in its elements
	 * @param x		X Coordinate
	 * @param y		Y Coordinate
	 * @return		UIElement a at position (x,y)
	 * @note		If possible and correct, UIElements inside containers will be returned
	 */
	@Override
	public UIElement locatedAt(int x, int y) {
		if (!containsPoint(x,y)) return null;

		UIElement found = null;
		for (UIElement e : elements) {
			found = e.locatedAt(x,y);
			if (found != null)
				return found;
		}
		return this; //If no elements of this row match, return this
	}

}
