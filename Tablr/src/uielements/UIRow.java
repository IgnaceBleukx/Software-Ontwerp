package uielements;

import java.awt.Graphics;
import java.util.ArrayList;

import facades.Tablr;

public class UIRow extends UIElement {

	/**
	 * constructor
	 * @param x: The x position of the left top corner of the Table.
	 * @param y: The y position of the left top corner of the Table.
	 * @param w: width of the row.
	 * @param h: height of the row.
	 * @param elements: the elements of this row
	 */
	public UIRow(int x, int y,int w, int h,  ArrayList<UIElement> elements) {
		super(x, y, w, h);
		this.elements = elements;
	}
	
	/**
	 * the elements of this row
	 */
	private ArrayList<UIElement> elements;

	/**
	 * draws the Row in the UI
	 */
	public void paint(Graphics g){
		g.drawRect(getX(),getY(),getWidth(),getHeight());
		for (UIElement e : this.elements){
			e.paint(g);
		}
	}
	
	/**
	 * Adds a UIElement to this row.
	 * @param e		The element to add
	 */
	public void addElement(UIElement e) {
		this.elements.add(e);
		e.setCommunicationManager(getCommunicationManager());
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

	@Override
	public void handleSingleClick() {
		if(getCommunicationManager().getLockedElement() != (null) && !getCommunicationManager().getLockedElement().equals(this)){
			return;
		}
		for(Runnable r : singleClickListeners){
			r.run();
		}
		
	}

	@Override
	public void handleDoubleClick() {
		if(getCommunicationManager().getLockedElement() != (null) && !getCommunicationManager().getLockedElement().equals(this)){
			return;
		}
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if(getCommunicationManager().getLockedElement() != (null) && !getCommunicationManager().getLockedElement().equals(this)){
			return;
		}
		for(UIElement i: elements){
			i.handleKeyboardEvent(keyCode, keyChar);
		}
		if (keyboardListeners.get(keyCode) == null)
			return;
		
		for (Runnable r : keyboardListeners.get(keyCode)) {
			r.run();
		}
		
	}
	
	@Override
	public void selectElement(UIElement e) {
		if (e==this) 
			setSelected();
		else
			setNotSelected();
		
		for (UIElement el : elements) {
			el.selectElement(e);
		}
	}
	
	@Override
	public void setCommunicationManager(Tablr c) {
		this.c = c;
		for (UIElement e : elements) {
			e.setCommunicationManager(c);
		}
	}
	
	@Override
	public boolean getError() {
		for (UIElement e : this.elements) {
			if (e.getError())
				return true;
		}
		return false;
	}

	
	public ArrayList<UIElement> getElements() {
		return new ArrayList<UIElement>(elements);
	}

	@Override
	public boolean hasSelectedElement() {
		if (this.isSelected()) return true;
		for (UIElement e : elements){
			if (e.hasSelectedElement()) return true;
		}
		return false;
	}

	@Override
	public boolean hasElementInError() {
		if (this.getError()) return true;
		for (UIElement e : elements){
			if (e.hasElementInError()) return true;
		}
		return false;
	}

	

}
