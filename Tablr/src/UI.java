import java.awt.Graphics;
import java.util.ArrayList;

import elements.UIElement;


public class UI {

	
	private ArrayList<UIElement> elements = new ArrayList<UIElement>();
	
	/*
	 * This method returns the elements of the current UI. 
	 */
	public ArrayList<UIElement> getElements(){
		return this.elements;
	}
	
	/*
	 * @param e: The UIElement to be added to the current UI.
	 * This method adds an element to the current UI.
	 */
	public void addUIElement(UIElement e){
		this.elements.add(e);
	}
	
	/*
	 * @param e: The UIElement to be removed from the current UI.
	 * This method removes a UIElement from the current UI. If the given UIElement is not part of the UI, nothing happens.
	 */
	public void removeUIElement(UIElement e){
		this.elements.remove(e);
	}
	
	/*
	 * This method paints the current UI.
	 */
	public void paint(Graphics g){
		for (UIElement e : getElements()){
			e.paint(g);
		}
	}
}
