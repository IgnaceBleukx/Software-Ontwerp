package elements;

import java.util.ArrayList;

public class ListView extends UIElement {

	/*
	 * Constructor of the ListView.
	 * @param x: The x position of the left top corner of the ListView.
	 * @param y: The y position of the left top corner of the ListView.
	 * @param elements: The ArrayList of UIElements in the ListView.
	 */
	public ListView(double x, double y, ArrayList<UIElement> elements) {
		super(x, y);	
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
	public void paint() {
		// TODO Auto-generated method stub

	}

}
