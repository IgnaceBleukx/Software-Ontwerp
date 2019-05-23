package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import facades.Tablr;
import facades.WindowManager;
import uielements.BottomLeftUICorner;
import uielements.BottomRightUICorner;
import uielements.BottomUIEdge;
import uielements.CloseButton;
import uielements.LeftUIEdge;
import uielements.RightUIEdge;
import uielements.Titlebar;
import uielements.TopLeftUICorner;
import uielements.TopRightUICorner;
import uielements.TopUIEdge;
import uielements.UIElement;
import uielements.UIRow;
import uielements.VoidElement;


public class UI {
	
	/**
	 * Creates a new UI
	 * @param x			X coordinate
	 * @param y			Y coordinate
	 * @param width		Width
	 * @param height	Height
	 */
	UI(int x, int y, int width, int height) {
		if (width < 0 || height < 0) 
			throw new IllegalArgumentException("Illegal parameters in UI constructor");
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		titleBar = new Titlebar(getX()+edgeW,getY()+edgeW,getWidth()-30,titleHeight,"Unknown");
		close = new CloseButton(titleBar.getEndX(),getY()+edgeW,30-edgeW,titleHeight,4);
		
		leftResize = new LeftUIEdge(	getX(),						getY()+edgeW,				edgeW,				getHeight()-2*edgeW);;
		rightResize = new RightUIEdge(	getX()+getWidth()-edgeW,	getY()+edgeW,				edgeW,				getHeight()-2*edgeW);
		topResize = new TopUIEdge(		getX()+edgeW,				getY(),						getWidth()-2*edgeW,	edgeW);;
		bottomResize = new BottomUIEdge(getX()+edgeW,				getY()+getHeight()-edgeW,	getWidth()-2*edgeW,	edgeW);
		
		topLeft = new TopLeftUICorner(getX(),getY(),edgeW,edgeW);
		topRight = new TopRightUICorner(getX()+getWidth()-edgeW,getY(),edgeW,edgeW);
		bottomLeft = new BottomLeftUICorner(getX(),getY()+getHeight()-edgeW,edgeW,edgeW);
		bottomRight = new BottomRightUICorner(getX()+getWidth()-edgeW,getY()+getHeight()-edgeW,edgeW,edgeW);
		
		//Adding listeners to UI attributes
		leftResize.addDragListener((newX,newY) ->{
			int delta = newX - leftResize.getGrabPointX();
			if (this.getWidth() - delta < 100){
				int deltaFinal = -(100 - this.getWidth());
				this.resizeL(deltaFinal);
			}
			else{
				int deltaFinal = delta;
				this.resizeL(deltaFinal);
			}
		});
		rightResize.addDragListener((newX,newY) ->{
			int delta = newX - rightResize.getGrabPointX();
			if (this.getWidth() + delta < 100){
				int deltaFinal = 100 - this.getWidth();
				this.resizeR(deltaFinal);
			}
			else{
				int deltaFinal = delta;
				this.resizeR(deltaFinal);
			}
		});
		bottomResize.addDragListener((newX,newY) -> {
			int delta = newY - bottomResize.getGrabPointY();
			if (this.getHeight() + delta < 100){
				int deltaFinal = 100 - this.getHeight();
				this.resizeB(deltaFinal);
			}
			else{
				int deltaFinal = delta;
				this.resizeB(deltaFinal);
			}
		});
		
		topResize.addDragListener((newX,newY) -> {
			int delta = newY - topResize.getGrabPointY();
			if (this.getHeight() - delta < 100){
				int deltaFinal = -(100 - this.getHeight());
				this.resizeT(deltaFinal);
			}
			else{
				int deltaFinal = delta;
				this.resizeT(deltaFinal);
			}
		});
		
		
		topLeft.addDragListener((newX,newY) ->{
			int deltaX = newX - topLeft.getGrabPointX();
			int deltaY = newY - topLeft.getGrabPointY();
			if (this.getWidth() - deltaX < 100){
				int deltaFinalX = -(100 - this.getWidth());
				this.resizeL(deltaFinalX);
			}
			else{
				int deltaFinalX = deltaX;
				this.resizeL(deltaFinalX);
			}
			if (this.getHeight() - deltaY < 100){
				int deltaFinalY = -(100 - this.getHeight());
				this.resizeT(deltaFinalY);
			}
			else{
				int deltaFinalY = deltaY;
				this.resizeT(deltaFinalY);
			}
		});
		topRight.addDragListener((newX,newY) ->{
			int deltaX = newX - topRight.getGrabPointX();
			int deltaY = newY - topRight.getGrabPointY();
			if (this.getWidth() + deltaX < 100){
				int deltaFinalX = 100 - this.getWidth();
				this.resizeR(deltaFinalX);
			}
			else{
				int deltaFinalX = deltaX;
				this.resizeR(deltaFinalX);
			}
			if (this.getHeight() - deltaY < 100){
				int deltaFinalY = -(100 - this.getHeight());
				this.resizeT(deltaFinalY);
			}
			else{
				int deltaFinalY = deltaY;
				this.resizeT(deltaFinalY);
			}
		});
		bottomLeft.addDragListener((newX,newY) ->{
			int deltaX = newX - bottomLeft.getGrabPointX();
			int deltaY = newY - bottomLeft.getGrabPointX();
			if (this.getWidth() - deltaX < 100){
				int deltaFinalX = -(100 - this.getWidth());
				this.resizeL(deltaFinalX);
			}
			else{
				int deltaFinalX = deltaX;
				this.resizeL(deltaFinalX);
			}
			if (this.getHeight() + deltaY < 100){
				int deltaFinalY = 100 - this.getHeight();
				this.resizeB(deltaFinalY);
			}
			else{
				int deltaFinalY = deltaY;
				this.resizeB(deltaFinalY);
			}
		});
		bottomRight.addDragListener((newX,newY)->{
			int deltaX = newX - bottomRight.getGrabPointX();
			int deltaY = newY - bottomRight.getGrabPointY();
			if (this.getWidth() + deltaX < 100){
				int deltaFinalX = 100 - this.getWidth();
				this.resizeR(deltaFinalX);
			}
			else{
				int deltaFinalX = deltaX;
				this.resizeR(deltaFinalX);
			}
			if (this.getHeight() + deltaY < 100){
				int deltaFinalY = 100 - this.getHeight();
				this.resizeB(deltaFinalY);
			}
			else{
				int deltaFinalY = deltaY;
				this.resizeB(deltaFinalY);
			}
		});
		titleBar.addDragListener((newX,newY) -> { 
			if (!titleBar.getDragging()) return;
			int deltaX = newX - titleBar.getGrabPointX();
			int deltaY = newY - titleBar.getGrabPointY();
			this.move(deltaX, deltaY);
		});
		close.addSingleClickListener(() -> {
			terminate();
			getWindowManager().selectNewUI();
		});		
		
		
	}
	
	/**
	 * Adds all 'default' elements of a UI: titlebar, close button, elements for resizing.
	 */
	protected void loadUIAttributes(){
		//Creating background:
		addUIElement(new VoidElement(getX(), getY(), getWidth(), getHeight(), new Color(230,230,230,230)));
		
		//Creating window layout
		this.addUIElement(close);
		this.addUIElement(titleBar);
		this.addUIElement(leftResize);
		this.addUIElement(rightResize);
		this.addUIElement(bottomResize);
		this.addUIElement(topResize);
		
		this.addUIElement(topLeft);
		this.addUIElement(topRight);
		this.addUIElement(bottomLeft);
		this.addUIElement(bottomRight);
	}

	protected static int minimumColumnWidth = 30;
	
	/**
	 * X Coordinate
	 */
	private int x;
	
	/**
	 * Y Coordinate
	 */
	private int y;
	
	/**
	 * Height of the UI
	 */
	private int height;
	
	/**
	 * Width of the UI
	 */
	private int width;
	
	/**
	 * Width of the area used to resize the UI
	 */
	static int edgeW = 3;
	
	/**
	 * Which element is currently dragging.
	 */
	private UIElement dragging = null;
	
	/**
	 * Returns the element that is currently dragging.
	 */
	public UIElement getDragging(){
		return dragging;
	}
	
	/**
	 * Sets the element that is currently dragging.
	 * @param e
	 */
	public void setDragging(UIElement e){
		dragging = e;
	}
	
	/**
	 * Height of the titlebar
	 */
	static int titleHeight = 15;
	
	/**
	 * This UI's titlebar
	 */
	protected Titlebar titleBar;
	
	/**
	 * This UIs close button
	 */
	private CloseButton close;
	
	/**
	 * The edges of this UI, used to resize the UI
	 */
	private LeftUIEdge leftResize;
	private RightUIEdge rightResize;
	private TopUIEdge topResize;
	private BottomUIEdge bottomResize;
	
	private TopLeftUICorner topLeft;
	private TopRightUICorner topRight;
	private BottomLeftUICorner bottomLeft;
	private BottomRightUICorner bottomRight;
	
	private static int minHeight = 100;
	private static int minWidth = 100;
	
	
	/**
	 * @return	The X Coordinate of this UI
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Sets the X Coordinate of this UI
	 * @param x		New X Coordinate
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * @return 	The x-coordinate of the end of the UI.
	 */
	public int getEndX() {
		return x + width;
	}
	
	/**
	 * @return	The Y coordinate of this UI
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * @return The y-coordinate of the end of the UI.
	 */
	public int getEndY() {
		return y + height;
	}
	
	/**
	 * Sets the Y Coordinate of this UI
	 * @param y		New Y Coordinate
	 */
	public void setY(int y){
		this.y = y;
	}

	/**
	 * @return	The height of this UI
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * Sets the height of this UI
	 * @param height	New height
	 */
	public void setHeight(int height){
		this.height = height;
	}
	
	/**
	 * @return	The width of this UI
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Sets the width of this UI
	 * @param width		New width
	 */
	public void setWidth(int width){
		this.width = width;
	}
	
		
	/**
	 * All of the UIElements that make up this UI
	 */
	protected ArrayList<UIElement> elements = new ArrayList<UIElement>();
	
	/**
	 * This method returns the elements of the current canvaswindow.UI.
	 */
	public ArrayList<UIElement> getElements(){
		return this.elements;
	}
	
	/**
	 * This method adds an element to the current canvaswindow.UI.
	 * @param e: The UIElement to be added to the current canvaswindow.UI.
	 */
	public void addUIElement(UIElement e){
		this.elements.add(e);
		
		
		e.addPressListener((dragElement) -> {
			setDragging(dragElement);
		});
		
		e.addReleaseListener(() -> {
			setDragging(null);
		});
		
		e.setUI(this);
	}
	
	/**
	 * remove all UIElements from this UI
	 */
	public void clear() {
		this.elements.clear();
	}
	
	/**
	 * This method paints the current canvaswindow.UI.
	 */
	public void paint(Graphics g){
		g.setClip(getX(),getY(),getWidth(),getHeight());
		getElements().stream().forEach(e -> e.paint(g));
		g.setClip(null);
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
			if (found == null) e.deselect();
		}
		for (UIElement e : getElements()) {
			found = e.locatedAt(x,y);
			if (found != null) return found;
		}
		return null;
	}
	
	/**
	 * the Tablr object that handles interactions with the Domain
	 */
	protected Tablr tablr;
	
	/**
	 * @return the Tablr object
	 */
	public Tablr getTablr() {
		return this.tablr;
	}
	
	/**
	 * set c as the new Tablr, and do so for all UIElements as well
	 * @param c
	 */
	public void setTablr(Tablr c) {
		this.tablr = c;
	}
	
	/**
	 * the WindowManager used to interact between UI's
	 */
	private WindowManager windowManager;
	
	/**
	 * @return the WindowManager object
	 */
	public WindowManager getWindowManager() {
		return this.windowManager;
	}
	
	/**
	 * set c as the new WindowManager
	 * @param c
	 */
	public void setWindowManager(WindowManager w) {
		this.windowManager = w;
	}
	
	
	/**
	 * Whether this UI is active. Only active UIs are drawn on the canvas
	 */
	private boolean active;


	/**
	 * Activates the UI, meaning it will be drawn on the canvas
	 */
	public void setActive() {
		active = true;
	}
	
	/**
	 * Deactivates the UI, meaning it will not be drawn on the canvas (but still stored)
	 */
	public void deactivate() {
		active = false;
	}
	
	/**
	 * Deactivates this UI, meaning it will no longer be drawn on the canvas, but its contents will be preserved.
	 */
	public void terminate() {
		active = false;
		getWindowManager().deleteUI(this);
	}
	
	
	/**
	 * Returns if this is active (and thus should be drawn).
	 * @return
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Returns whether a given point lies within this UI
	 * @param x		X Coordinate
	 * @param y		Y Coordinate
	 */
	public boolean containsPoint(int x, int y) {
		return (x >= getX() &&
				y >= getY() &&
				x <= getX()+getWidth() &&
				y <= getY()+getHeight());
	}
	
	/**
	 * Whether this UI is selected. 
	 * Only the selected UI receives keyboard input and is visually distinct from other UIs.
	 */
	private boolean isSelected;
	
	/**
	 * Makes this UI the selected UI.
	 */
	public void select(){
		isSelected = true;
		close.setColor(new Color(200,200,200,200));
		titleBar.setColor(new Color(200,200,200,200));
	}
	
	/**
	 * Makes this UI not selected.
	 */
	public void deselect(){
		isSelected = false;
		close.setColor(Color.WHITE);
		titleBar.setColor(Color.WHITE);
	}
	
	/**
	 * @return	Whether this UI is currently selected
	 */
	public boolean isSelected(){
		return isSelected;
	}
	
	/**
	 * Moves this UI and all its elements by along vector (deltaX, deltaY)
	 * @param deltaX		horizontal displacement
	 * @param deltaY		vertical displacement
	 */
	public void move (int deltaX, int deltaY) {
		setX(getX() + deltaX);
		setY(getY() + deltaY);
		elements.stream().forEach(e -> e.move(deltaX, deltaY));
	}
	
	/**
	 * This method resizes the UI and all its elements from the left.
	 * @param deltaW 	The amount of pixels the UI must be made smaller with from the left.
	 */
	public void resizeL(int deltaW) {
		int delta = getWidth() - deltaW < minWidth ? getWidth()-minWidth : deltaW;
		setX(getX()+delta);
		setWidth(getWidth() - delta);
		elements.stream().forEach(e -> e.resizeL(delta));
	}
	
	/**
	 * This method resized the UI and all its element from the right.
	 * @param deltaW 	The amount of pixels the UI must be made larger with from the right.
	 */
	public void resizeR(int deltaW) {
		int delta = getWidth() + deltaW < minWidth ? getWidth()-minWidth : deltaW;
		setWidth(getWidth() + delta);
		elements.stream().forEach(e -> e.resizeR(delta));
	}
	
	/**
	 * This method resized the UI and all its elements from the top.
	 * @param deltaH 	The amount of pixels the UI must be made smaller with from the top.
	 */
	public void resizeT(int deltaH) {
		int delta = getWidth() - deltaH < minHeight ? getWidth()-minHeight : deltaH;
		int deltaFinal = getY() + delta < 0 ? getY() : delta;
		setHeight(getHeight() - deltaFinal);
		setY(getY()+deltaFinal);
		elements.stream().forEach(e -> e.resizeT(deltaFinal));
	}
	
	/**
	 * This method resizes the UI and all its elements from the bottom.
	 * @param deltaH 	The amount of pixels the UI should be made larger with from the bottom.
	 */
	public void resizeB(int deltaH) {
		int delta = getWidth() + deltaH < minHeight ? getWidth()-minHeight : deltaH;
		setHeight(getHeight()+delta);
		elements.stream().forEach(e -> e.resizeB(delta));
	}
	
	protected ArrayList<BiConsumer<Integer,Integer>> columnResizeListeners = new ArrayList<BiConsumer<Integer,Integer>>();
	
	public ArrayList<BiConsumer<Integer,Integer>> getColumnResizeListeners(){
		return new ArrayList<BiConsumer<Integer, Integer>>(columnResizeListeners);
	}
	
	public void columnChanged(int delta, int index) {
		columnResizeListeners.stream().forEach(l -> l.accept(delta, index));
	}
	
	@Override
	public String toString() {
		return "UI : X="+getX() + " Y="+getY() + " W=" +getWidth() + " H="+getHeight();
	}

	@Override
	public UI clone(){
		UI clone = new UI(getX(),getY(),getWidth(),getHeight());
		ArrayList<UIElement> clonedElements = new ArrayList<UIElement>();
		elements.stream().forEach(e -> clonedElements.add(e.clone()));
		clone.elements = clonedElements;
		return clone;
	}

	public boolean getError() {
		for (UIElement e : getElements()) {
			if (e.getError())
				return true;
		}
		return false;
	}
}


