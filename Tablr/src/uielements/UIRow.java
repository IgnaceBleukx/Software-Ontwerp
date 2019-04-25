package uielements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Utils.Rounder;
import ui.UI;

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
		this.elements.sort((UIElement e1, UIElement e2) -> e1.getX() - e2.getX());
	}
	
	/**
	 * the elements of this row
	 */
	private ArrayList<UIElement> elements;

	/**
	 * draws the Row in the UI
	 */
	public void paint(Graphics g){
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawRect(getX(),getY() ,getWidth(),getHeight());
		for (UIElement e : this.elements){
			e.paint(g);
		}
		if(isSelected())
			g.drawRect(getX()+1,getY()+1,getWidth()-2,getHeight()-2);
	}
	
	/**
	 * Adds a UIElement to this row.
	 * @param e		The element to add
	 */
	public void addElement(UIElement e) {
		this.elements.add(e);
		e.setUI(getUI());
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
		new ArrayList<>(singleClickListeners).forEach(l -> l.run());
	}

	@Override
	public void handleDoubleClick() {
		new ArrayList<>(doubleClickListeners).forEach(l -> l.run());
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		for(UIElement i: elements){
			i.handleKeyboardEvent(keyCode, keyChar);
		}
		if (new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode) == null)
			return;
		
		new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode).stream().forEach(l -> l.run());
	}
	
	@Override
	public void handleDrag(int x, int y) {
		new ArrayList<BiConsumer<Integer,Integer>>(dragListeners).stream().forEach(r -> r.accept(x, y));
	}
	
	@Override
	public void selectElement(UIElement e) {
		if (e==this) 
			select();
		else
			deselect();
		
		for (UIElement el : elements) {
			el.selectElement(e);
		}
	}
	
	@Override
	public void setUI(UI ui) {
		this.ui = ui;
		elements.stream().forEach(e -> e.setUI(ui));
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

	private boolean selected;
	
	public void select(){
		this.selected = true;
	}
	
	public void deselect(){
		this.selected = false;
	}
	
	public boolean isSelected(){
		return this.selected;
	}
	
	@Override
	public void move(int deltaX, int deltaY) {
		setX(getX() + deltaX);
		setY(getY() + deltaY);
		elements.stream().forEach(e -> e.move(deltaX, deltaY));
	}
	
	@Override
	public void resizeL(int deltaW) {
		this.move(deltaW,0);
	}
	
	
	@Override
	public void resizeR(int deltaW){
		//
	}

	public void resizeElementL(int deltaW, int index){
		elements.sort((UIElement e1,UIElement e2) -> e1.getX() - e2.getX());
		List<UIElement> toMove = elements.subList(0, index-1);
		toMove.stream().forEach(e -> e.move(deltaW, 0));
		elements.get(index).resizeL(deltaW);
	}
	
	public void resizeElementR(int deltaW,int index){
		elements.sort((UIElement e1, UIElement e2) -> e1.getX() - e2.getX());
		List<UIElement> toMove = elements.subList(index +1, elements.size());
		toMove.stream().forEach(e -> e.move(deltaW, 0));
		UIElement resize = elements.get(index);
		if (resize instanceof Checkbox)
			resize.resizeR(checkBoxResizer.round((double) deltaW/2));
		else
			resize.resizeR(deltaW);
		this.setWidth(getWidth()+deltaW);
	}
	
	Rounder checkBoxResizer = new Rounder();
	
	public void removeElementAt(int index){
		resizeElementR(-elements.get(index).getWidth(),index);
		elements.remove(index);
	}
	
	@Override
	public UIRow clone(){
		ArrayList<UIElement> clonedElements = new ArrayList<UIElement>();
		elements.stream().forEach(e -> clonedElements.add(e.clone()));
		return new UIRow(getX(),getY(),getWidth(),getHeight(),clonedElements);
	}
	
}
