package uielements;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import facades.Tablr;
import ui.UI;


public abstract class UIElement {
	
	@Override
	public String toString() {
		return this.getClass()+" - X="+getX()+" Y="+getY()+" W="+getWidth()+" H="+getHeight();
	}
	
	protected UI ui;
	
	public UI getUI() {
		return ui;
	}
	
	public void setUI(UI ui) {
		this.ui = ui;
	}
	
	/**
	 * Indicates whether UIElement is faulty.
	 */
	private boolean error = false;

	/**
	 * Indicates whether the UIElement is locked (thus needs to be unlocked before other actions can occur)
	 */
	private boolean lock;
	
	/**
	 * sets this element as an error element: it's value is incorrect and should be resolved
	 */
	public void isError() {
		System.out.println("[UIElement.java:33] Acquired selection lock on "+this);
		getUI().getTablr().getSelectionLock(this);
		this.error = true;
	}
	
	/**
	 * relieves the error state of this element
	 */
	public void isNotError() {
		this.error = false;
		System.out.println("[UIElement.java:40] Released selection lock on "+this);
		getUI().getTablr().releaseSelectionLock(this);
	}
	
	/**
	 * @return the error state of this element
	 */
	public boolean getError() {
		return this.error;
	}
	
	/**
	 * locks this element up: no other actions can be performed in the UI until the lock is resolved
	 */
	public void lock(){
		this.lock = true;
		System.out.println("[UIElement.java:49 Aquired hard lock on " + this);
		getUI().getTablr().getLock(this);
	}
	
	/**
	 * relieves the lock state and unlocks the UI
	 */
	public void unlock(){
		this.lock = false;
		System.out.println("[UIElement.java:54 Released hard lock on " + this);
		getUI().getTablr().releaseLock(this);
	}
	
	/**
	 * @return the lock state of this element
	 */
	public boolean getLocked(){
		return this.lock;
	}
	
	
	/**
	 * All objects that get notified when this UIElement is clicked.
	 */
	protected ArrayList<Runnable> singleClickListeners = new ArrayList();
	
	/**
	 * All objects that get notified when this UIElement is doubleclicked.
	 */
	protected ArrayList<Runnable> doubleClickListeners = new ArrayList();
	
	/**
	 * All objects that get notified when this UIElement is pressed.
	 */
	protected ArrayList<Consumer<UIElement>> pressListeners = new ArrayList();
	
	/**
	 * All objects that get notified when this UIElement is released.
	 */
	protected ArrayList<Runnable> releaseListeners = new ArrayList();
	
	/**
	 * HashMap that maps keycodes to a list of runnables that are to be executed
	 */
	protected HashMap<Integer, ArrayList<Runnable>> keyboardListeners = new HashMap<Integer, ArrayList<Runnable>>();

	protected ArrayList<BiConsumer<Integer,Integer>> dragListeners = new ArrayList<>();
	
	/**
	 * Attaches a function to a keyCode; the function will be executed when the key is pressed
	 * @param keyCode	Key code
	 * @param f			Function
	 */
	public void addKeyboardListener(int keyCode, Runnable f) {
		ArrayList<Runnable> r = keyboardListeners.get(keyCode);
		if (r == null) { //No Runnables for this keycode, create new ArrayList
			ArrayList<Runnable> singleton = new ArrayList<Runnable>();
			singleton.add(f);
			keyboardListeners.put(keyCode, singleton);
		}
		else { 			 //Already some Runnables, add to existing ArrayList
			keyboardListeners.get(keyCode).add(f);
		}	
	}
	
	/**
	 * adds a listener for a singleClick action
	 * @param f: the listener to be added
	 */
	public void addSingleClickListener(Runnable f) {
		singleClickListeners.add(f);
	}

	/**
	 * adds a listener for a doubleClick action
	 * @param f: the listener to be added
	 */
	public void addDoubleClickListener(Runnable f) {
		doubleClickListeners.add(f);
	}
	
	public void addDragListener(BiConsumer<Integer,Integer> f) {
		dragListeners.add(f);
	}

	public void addPressListener(Consumer<UIElement> f) {
		pressListeners.add(f);
	}
	
	public void addReleaseListener(Runnable f) {
		releaseListeners.add(f);
	}

	public void handlePressed(int x,int y){
		this.beginDrag();
		this.setGrabPointX(x);
		this.setGrabPointY(y);
		new ArrayList<>(pressListeners).stream().forEach(l -> l.accept(this));
	}
	
	public void handleReleased() {
		this.endDrag();
	}
	
	public abstract void handleDrag(int x, int y);
	
	public abstract void handleSingleClick();
	
	public abstract void handleDoubleClick();
	
	public abstract void handleKeyboardEvent(int keyCode, char keyChar);
	
	
	
	/**
	 * constructor: x and y being the coordinates of the new element, w and h the dimensions
	 * @param x: The x position of the left top corner of the Table.
	 * @param y: The y position of the left top corner of the Table.
	 * @param w: width of the table.
	 * @param h: height of the table.
	 */
	public UIElement(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/**
	 * coordinates and dimensions of the element
	 */
	private int x;
	private int y;
	private int w;
	private int h;
	
	
	/**
	 * This method returns the x-position of the current UIElement.
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * This method returns the y-position of the current UIElement.
	 */
	public int getY(){
		return this.y;
	}
	
	/**
	 * Sets a new X coordinate for this UIElement
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Sets a new Y coordinate for this UIElement
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * This method returns the width of the current UIElement.
	 */
	public int getWidth(){
		return this.w;
	}
	
	public int getEndX() {
		return x + w;
	}
	
	public int getEndY() {
		return y + h;
	}
	
	/**
	 * This method returns the height of the current UIElement.
	 */
	public int getHeight(){
		return this.h;
	}
	
	public void setWidth(int width) {
		this.w = width;
	}
	
	public void setHeight(int height) {
		this.h = height;
	}
	
	/**
	 * This method paints the UIElement on the canvas window specified by the Graphics element.
	 * This method should be overwritten by every subclass of UIElement.
	 * @param g: The graphics engine on which the UIElement is painted.
	 */
	public abstract void paint(Graphics g);

	public void paintVerticalOffset(Graphics g, int offset) {
	}
	
	/**
	 * Draws text centered in the UIElement.
	 * @param g		Graphics object
	 * @param text	String to draw
	 */
	public void drawCenteredText(Graphics g, String text){
		Font font = g.getFont();
	    FontMetrics metrics = g.getFontMetrics(font);
	    int centeredX = this.getX()+ (this.getWidth()- metrics.stringWidth(text)) / 2;
	    int centeredY = this.getY() +  ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.drawString(text, centeredX,centeredY);
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
	 * @return		UIElement at position (x,y)
	 * @note		If possible and correct, UIElements inside containers will be returned
	 */
	public UIElement locatedAt(int x, int y) {
		if (containsPoint(x,y))
			return this;
		else
			return null;
	}
	
	/**
	 * Is this element selected? (Only one UIElement can be selected per UI)
	 */
	private boolean isSelected;
	
	/**
	 * @return if the element is selected or not
	 */
	public boolean isSelected() {
		return this.isSelected;
	}
	
	/**
	 * set the element as selected
	 */
	public void select() {
		this.isSelected = true;
	}
	
	/**
	 * set the element as not selected
	 */
	public void deselect() {
		this.isSelected = false;
	}

	/**
	 * sets this element as selected if the given argument IS this element
	 * @param newElement: the element to be compared with this one
	 */
	public void selectElement(UIElement newElement) {
		if (newElement.equals(this)) {
			select();
		}
		else {
			deselect();
		}
	}
	
	public boolean hasSelectedElement(){
		return this.isSelected();
	}
	
	public boolean hasElementInError(){
		return this.getError();
	}

	private int grabPointX;
	private int grabPointY;
	
	public int getGrabPointX(){
		return grabPointX;
	}

	public int getGrabPointY(){
		return grabPointY;
	}
	
	public void setGrabPointX(int x){
		grabPointX = x;
	}
	
	public void setGrabPointY(int y){
		grabPointY = y;
	}

	private Color color = Color.white;

	
	public Color getColor(){
		return this.color;
	}
	
	public void setColor(Color color){
		this.color =  color;
	}
	
	/**
	 * This method moves the ui element.
	 * @param deltaX 	The amount of pixels the UIElement must be shifted to the right.
	 * @param deltaY	The amount of pixels the UIElement must be shifted to the bottom.
	 */
	public void move(int deltaX, int deltaY){
		x += deltaX;
		y += deltaY;
	}
	
	/**
	 * This method resizes the UIElement when resized from the left.
	 * @param deltaWidth 	The amount of pixels the UIElement must be made smaller from the left.
	 */
	public void resizeL(int deltaWidth) {
		w -= deltaWidth;
		this.move(deltaWidth,0);
	}
	
	/**
	 * This method resizes the UIElement when resized from the right
	 * @param deltaWidth 	The amount of pixels the UIElement must be made larger from the right.
	 */
	public void resizeR(int deltaWidth) {
		w += deltaWidth;
	}
	
	/**
	 * This method resizes the UIElement when resized from the top.
	 * @param deltaHeight 	The amount of pixels the UIElement must be made smaller with from the top.
	 */
	public void resizeT(int deltaHeight) {
		h -= deltaHeight;
		this.move(0,deltaHeight);
	}
	
	/**
	 * This method resized the UIElement when resized from the bottom.
	 * @param deltaHeight 	The amount of pixels the UIElement must be made larger with from the bottom.
	 */
	public void resizeB(int deltaHeight) {
		h += deltaHeight;
	}
	
	private boolean dragging;

	public void beginDrag() {
		this.dragging = true;
		getUI().setDragging(this);
	}
	
	public void endDrag() {
		getUI().setDragging(null);
		this.dragging = false;
	}
	
	public boolean getDragging() {
		return dragging;
	}

	@Override
	public abstract UIElement clone();

	protected Rectangle2D toShape(){
		return new Rectangle(getX(),getY(),getWidth(),getHeight());
	}

}
