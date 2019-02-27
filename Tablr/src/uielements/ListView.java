package uielements;

import java.awt.Graphics;
import java.util.ArrayList;

public class ListView extends UIElement {

	/*
	 * Constructor of the ListView.
	 * @param x: The x position of the left top corner of the ListView.
	 * @param y: The y position of the left top corner of the ListView.
	 * @param elements: The ArrayList of UIElements in the ListView.
	 */
	public ListView(int x, int y,int w, int h, ArrayList<UIElement> elements) {
		super(x, y, w, h);	
		this.elements = elements;
	}

	/*
	 * This method adds an element to the current ListView.
	 * @param e: The element to be added to the ListVieuw.
	 */
	public void addElement(UIElement e){
		this.elements.add(e);
	}
	
	/*
	 * This method removes an element of the ListVieuw.
	 * @param e: the UIElement to be removed. 
	 */
	public void removeElement(UIElement e){
		this.elements.remove(e);
	}
	
	ArrayList<UIElement> elements;
	
	
	@Override
	public void paint(Graphics g) {
		g.drawRect(getX(),getY(),getWidth(),getHeight());

	}

	/**
	 * Returns the most specific UIElement located at (x,y) by searching in elements
	 * @param x		X Coordinate
	 * @param y		Y Coordinate
	 * @return		UIElement a at position (x,y)
	 * @note		If possible and correct, UIElements inside containers will be returned
	 */
	@Override
	public UIElement locatedAt(int x, int y) {
		UIElement found = null;
		for (UIElement e : elements) {
			found = e.locatedAt(x,y);
			if (found != null)
				return found;
		}
		return this; //If no elements inside the list match, return this.
	}


}
