package uielements;

import java.awt.Color;
import java.awt.Graphics;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;

import domain.Column;
import domain.Table;
import domain.Type;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;
import facades.Tablr;
import ui.UI;

public class ListView extends UIElement {

	/**
	 * Constructor of the ListView.
	 * @param x: The x position of the left top corner of the ListView.
	 * @param y: The y position of the left top corner of the ListView.
	 * @param elements: The ArrayList of UIElements in the ListView.
	 */
	public ListView(int x, int y,int w, int h, ArrayList<UIElement> elements) {
		super(x, y, w, h);
		this.elements = elements;
		updateScrollBar();
	}
	
	private ScrollBar scrollBarV = new ScrollBar(getX() + getWidth() -10, getY(),10,getHeight()-10, ScrollDirection.VERTICAL);
	private ScrollBar scrollBarH = new ScrollBar(getX(), getY() + getHeight() - 10, getWidth()-10, 10, ScrollDirection.HORIZONTAL);
		
	private void updateScrollBar() {
		scrollBarV.update(elements.stream().mapToInt(e -> e.getHeight()).sum(), this.getHeight());
		scrollBarH.update(elements.stream().mapToInt(e -> e.getWidth()).sum(), this.getWidth());
	}
	
	
	/**
	 * This method adds an element to the current ListView.
	 * @param e: The element to be added to the ListView.
	 */
	public void addElement(UIElement e){
		this.elements.add(e);
		e.setUI(getUI());
		updateScrollBar();
	}
	
	/**
	 * This method removes an element of the ListVieuw.
	 * @param e: the UIElement to be removed. 
	 */
	public void removeElement(UIElement e){
		this.elements.remove(e);
		updateScrollBar();
	}
	
	/**
	 * The elements of the ListView (the rows)
	 */
	private ArrayList<UIElement> elements;
	
	public ArrayList<UIElement> getElements() {
		return new ArrayList<UIElement>(elements);
	}

	/**
	 * deselect the currently selected element
	 */
	@Override
	public void setNotSelected() {
		this.selectedElement = null;
	}
	
	public boolean isSelected() {
		if (this.getSelectedElement() != null) return true;
		return false;
	}

	/**
	 * the currently selected element of the ListView (optional, sometimes null)
	 */
	private UIElement selectedElement;
	
	/**
	 * Set element e as the currently selected element of the list
	 * @param e: the element to be selected
	 */
	public void setSelectedElement(UIElement e) {
		this.selectedElement = e;
	}
	
	/**
	 * returns the currently selected element
	 * @return the selected element
	 */
	public UIElement getSelectedElement() {
		return this.selectedElement;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(getX(),getY(),getWidth()-10,getHeight());
		g.setClip(getX(), getY(),getWidth(), getHeight());
		elements.stream().forEach(e -> e.paint(g));
		g.setClip(null);
		scrollBarV.paint(g);
		scrollBarH.paint(g);
	}
	
	@Override
	public UIElement locatedAt(int x, int y) {
		if (!containsPoint(x,y)) return null;
		
		UIElement found = null;
		if (scrollBarV.containsPoint(x, y) && scrollBarV.isActive()) return scrollBarV;
		if (scrollBarH.containsPoint(x, y) && scrollBarH.isActive()) return scrollBarH;
		for (UIElement e : elements) {
			found = e.locatedAt(x,y);
			if (found != null)
				return found;
		}
		return this;		
	}
		
	public void loadColumnAttributes(Table table){
		
		
	}

	@Override
	public boolean hasSelectedElement() {
		if (this.isSelected()) return true;
		for (UIElement e : getElements()){
			if (e.hasSelectedElement()) return true;
		}
		return false;
	}

	@Override
	public void handleSingleClick() {
		getUI().getTablr().notifyNewSelected((UIElement) this);
		singleClickListeners.stream().forEach(l -> l.run());
	}

	@Override
	public void handleDoubleClick() {
		doubleClickListeners.stream().forEach(l -> l.run());
	}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		for (int i=0;i<elements.size();i++) {
			elements.get(i).handleKeyboardEvent(keyCode, keyChar);
		}
		
		if (keyboardListeners.get(keyCode) == null)
			return;
		
		keyboardListeners.get(keyCode).stream().forEach(l -> l.run());
		
	}
	
	@Override
	public void handleDrag(int x, int y) {
		dragListeners.stream().forEach(r -> r.accept(x, y));
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
	public void setUI(UI ui) {
		this.ui = ui;
		scrollBarV.setUI(ui);
		elements.stream().forEach(e -> e.setUI(ui));
	}
	
	/**
	 * Overrides the method in UIElement:
	 * A listview is in the Error state if one of its elements are
	 * in the Error state
	 */
	@Override
	public boolean getError() {
		for (UIElement e : elements) {
			if (e.getError())
				return true;
		}
		return false;
	}
	
	@Override
	public void move(int deltaX, int deltaY) {
		setX(getX() + deltaX);
		setY(getY() + deltaY);
		scrollBarH.move(deltaX, deltaY);
		scrollBarV.move(deltaX, deltaY);
		elements.stream().forEach(e -> e.move(deltaX, deltaY));
	}
	
	
}
