package uielements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Scrollbar;
import java.awt.Shape;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

import Utils.Rounder;
import domain.Column;
import domain.Table;
import domain.Type;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;
import facades.Tablr;
import ui.UI;

/**
 * A Listview is a UIElement that groups multiple UIElements together.
 * At all times, only one element in the ListView can be selected.
 *
 */
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
		elements.add(scrollBarV);
		elements.add(scrollBarH);
		updateScrollBar();
		
		//add listeners
		scrollBarV.addPressListener((e) -> {
			new ArrayList<>(pressListeners).stream().forEach(l -> l.accept(scrollBarV));
		});
		
		scrollBarV.addScrollListener((delta) ->{
			elements.stream().filter(e -> !(e instanceof ScrollBar)).forEach(e -> e.move(0, -delta));
		});
		
		scrollBarV.addDragListener((newX,newY) -> {
			int delta = newY - scrollBarV.getGrabPointY();
			scrollBarV.scroll(delta);
		});
		
		
		scrollBarH.addPressListener((e) -> {
			new ArrayList<>(pressListeners).stream().forEach(l -> l.accept(scrollBarH));
		});
		
		scrollBarH.addScrollListener((delta) ->{
			elements.stream().filter(e -> !(e instanceof ScrollBar)).forEach(e -> e.move(-delta, 0));
		});
		
		scrollBarH.addDragListener((newX,newY) -> {
			int delta = newY - scrollBarH.getGrabPointY();
			scrollBarH.scroll(delta);
		});
		
		
	}
	
	/**
	 * Width of the scrollbar
	 */
	private static int scrollbarW = 10;
	
	/**
	 * The vertical scrollbar in this ListView
	 */
	private VerticalScrollBar scrollBarV = new VerticalScrollBar(getEndX()-scrollbarW, getY(),scrollbarW,getHeight()-scrollbarW);
	
	/**
	 * The horizontal scrollbar in this ListView
	 */
	private HorizontalScrollBar scrollBarH = new HorizontalScrollBar(getX(),getEndY()-scrollbarW,getWidth(), scrollbarW);
		
	public int getScrollBarWidth(){
		return scrollbarW;
	}

	/**
	 * Updates the horizontal and vertical scrollbars.
	 */
	public void updateScrollBar() {
		scrollBarV.update(elements.stream().filter(e -> !(e instanceof ScrollBar)).mapToInt(e -> e.getHeight()).sum(), this.getHeight()-scrollbarW);
		scrollBarH.update(elements.stream().filter(e -> !(e instanceof ScrollBar)).mapToInt(e -> e.getWidth()).max().orElse(getWidth()-scrollbarW), this.getWidth()-scrollbarW);
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
	 * Paints the ListView to the Canvas.
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(getX(),getY(),getWidth()-10,getHeight()-10);
		Shape oldClip = g.getClip();
		g.setClip(getX(), getY(),getWidth()-scrollbarW, getHeight()-scrollbarW);
		elements.stream().forEach(e -> e.paint(g));
		g.setClip(oldClip);
		scrollBarV.paint(g);
		scrollBarH.paint(g);
	}
	
	/**
	 * Finds the UIElement in this ListView at coordinates (x,y).
	 * @param x 	The X Coordinate
	 * @param y		The Y Coordinate
	 * @return		A UIElement at coordinates (x,y), null if no such UIElement exists.
	 * @return 		null if (x,y) is located outside the ListView.
	 */
	@Override
	public UIElement locatedAt(int x, int y) {
		if (!containsPoint(x,y)) return null;
		
		UIElement found = null;
//		if (scrollBarV.containsPoint(x, y)) return scrollBarV.locatedAt(x, y);
//		if (scrollBarH.containsPoint(x, y)) return scrollBarH.locatedAt(x, y);
		for (UIElement e : elements) {
			System.out.println(e);
			found = e.locatedAt(x,y);
			if (found != null)
				return found;
		}
		return this;		
	}

	/**
	 * Checks if this ListView has a element that is currently selected.
	 */
	@Override
	public boolean hasSelectedElement() {
		if (this.isSelected()) return true;
		for (UIElement e : getElements()){
			if (e.hasSelectedElement()) return true;
		}
		return false;
	}
	
	/**
	 * Handles a single click by running all actions associated with it.
	 */
	@Override
	public void handleSingleClick() {
		getUI().getTablr().notifyNewSelected((UIElement) this);
		new ArrayList<>(singleClickListeners).stream().forEach(l -> l.run());
	}

	/**
	 * Handles a double click by running all actions associated with it.
	 */
	@Override
	public void handleDoubleClick() {
		new ArrayList<>(doubleClickListeners).stream().forEach(l -> l.run());
	}

	/**
	 * Handles a keyboard event by running all actions associated with the pressed key.
	 */
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		for (int i=0;i<elements.size();i++) {
			elements.get(i).handleKeyboardEvent(keyCode, keyChar);
		}
		
		if (new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode) == null)
			return;
		
		new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode).stream().forEach(l -> l.run());
		
	}
	
	/**
	 * Handles dragging by running all actions associated with dragging this element.
	 */
	@Override
	public void handleDrag(int x, int y) {
		new ArrayList<BiConsumer<Integer,Integer>>(dragListeners).stream().forEach(r -> r.accept(x, y));
	}
	
	/**
	 * Handles selection of a new UIElement, passing the message to all its elements.
	 */
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
	
	/**
	 * Sets the UI this ListView belongs to.
	 * @param ui	The UI
	 */
	@Override
	public void setUI(UI ui) {
		this.ui = ui;
//		scrollBarV.setUI(ui);
//		scrollBarH.setUI(ui);
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
	
	/**
	 * Moves the ListView to (x+deltaX, y+deltaY). Also moves all of its elements.
	 * @param deltaX	X difference
	 * @param deltaY 	Y difference
	 */
	@Override
	public void move(int deltaX, int deltaY) {
		setX(getX() + deltaX);
		setY(getY() + deltaY);
		elements.stream().forEach(e -> e.move(deltaX, deltaY));
	}
	
	
	/**
	 * This method resizes the listview from the left. Its elements are not resized.
	 * @param deltaW 	The amount of pixels the listview should be made smaller with from the left. 
	 */
	@Override
	public void resizeL(int deltaW){
		setWidth(getWidth()- deltaW);
		setX(getX()+deltaW);
		getElements().stream().forEach(e -> e.resizeL(deltaW));
		updateScrollBar();
	}
	
	/**
	 * This method resizes the listview from the right. Its elements are not resized.
	 * @param deltaW 	The amount of pixels the listview should be made larger with from the right.
	 */
	@Override
	public void resizeR(int deltaW){
		setWidth(getWidth() + deltaW);
		getElements().stream().forEach(e -> e.resizeR(deltaW));
		updateScrollBar();
	}
	
	/**
	 * Resize the ListView from the top. Updates scrollbars.
	 * @param deltaH	Amount of pixels the ListView is resized from the top.
	 */
	@Override
	public void resizeT(int deltaH){
		this.setHeight(getHeight() - deltaH);
		this.setY(getY()+deltaH);
		getElements().stream().filter(e -> !(e instanceof ScrollBar)).forEach(e -> e.move(0, deltaH));
		scrollBarH.resizeT(deltaH);
		scrollBarV.resizeT(deltaH);
		updateScrollBar();
	}
	
	/**
	 * Resize the ListView from the bottom. Also updates scrollbars.
	 * @param deltaH	Amount of pixels the ListView is resized from the top.

	 */
	@Override
	public void resizeB(int deltaH){
		this.setHeight(getHeight() + deltaH);
		getElements().stream().filter(e -> e instanceof ScrollBar).forEach(e -> e.resizeB(deltaH));
		updateScrollBar();
	}
	
	@Override
	public ListView clone(){
		ArrayList<UIElement> clonedElements = new ArrayList<UIElement>();
		elements.stream().forEach(e -> clonedElements.add(e.clone()));
		ListView clone = new ListView(getX(), getY(), getWidth(),getHeight(),clonedElements);
		clone.scrollBarH = scrollBarH;
		clone.scrollBarV = scrollBarV;
		return clone;
	}
	
}
