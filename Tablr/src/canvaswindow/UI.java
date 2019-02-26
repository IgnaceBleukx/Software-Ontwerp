package canvaswindow;

import java.awt.Graphics;
import java.util.ArrayList;

import elements.UIElement;


public class UI {

	
	private ArrayList<UIElement> elements = new ArrayList<UIElement>();
	
	/*
	 * This method returns the elements of the current canvaswindow.UI.
	 */
	public ArrayList<UIElement> getElements(){
		return this.elements;
	}
	
	/*
	 * @param e: The UIElement to be added to the current canvaswindow.UI.
	 * This method adds an element to the current canvaswindow.UI.
	 */
	public void addUIElement(UIElement e){
		this.elements.add(e);
	}
	
	/*
	 * @param e: The UIElement to be removed from the current canvaswindow.UI.
	 * This method removes a UIElement from the current canvaswindow.UI. If the given UIElement is not part of the canvaswindow.UI, nothing happens.
	 */
	public void removeUIElement(UIElement e){
		this.elements.remove(e);
	}
	
	/*
	 * This method paints the current canvaswindow.UI.
	 */
	public void paint(Graphics g){
		for (UIElement e : getElements()){
			e.paint(g);
		}
	}

	/**
	 * Returns the most specific UIElement that is an element of this canvaswindow.UI at position (x,y)
	 * @param x		X Coordinate
	 * @param y		Y Coordinate
	 * @return		UIElement e
	 * 					| e.getX() = x
	 * 					| e.getY() = y
	 * 					| getElements().contains(e)
	 */
	public UIElement locatedAt(int x, int y) {
		UIElement found = null;
		for (UIElement e : getElements()) {
			found = e.locatedAt(x,y);
			if (found != null) return found;
		}
		return null;
	}
}
