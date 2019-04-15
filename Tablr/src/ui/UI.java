package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Optional;

import facades.Tablr;
import facades.WindowManager;
import uielements.CloseButton;
import uielements.Titlebar;
import uielements.UIElement;


public class UI {
	
	public UI(int x, int y, int width, int height) {
		if (x<0 || y < 0 || width < 0 || height < 0) 
			throw new IllegalArgumentException("Illegal parameters in UI constructor");
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	private int x;
	private int y;
	private int height;
	private int width;
	static int edgeW = 5;
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public int getHeight(){
		return height;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void paintUI(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(getX(),getY(), getWidth(), getHeight());
		for(UIElement e : getElements()) {
			e.paint(g);
		}
		
	}
		
	/**
	 * All of the UIElements that make up this UI
	 */
	private ArrayList<UIElement> elements = new ArrayList<UIElement>();
	
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
		e.setUI(this);
	}
	
	/**
	 * add the elements of a given list to the current UIElements-list in this UI
	 * @param list: the UIElements to be added
	 */
	public void addAllUIElements(ArrayList<UIElement> list) {
		list.stream().forEach(e -> addUIElement(e));
		
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
		getElements().stream().forEach(e -> e.paint(g));
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
			else e.setNotSelected();
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
	 * Deactivates this UI, meaning it will no longer be drawn on the canvas, but its contents will be preserved.
	 */
	public void setInactive() {
		active = false;
	}
	
	/**
	 * Returns if this is active (and thus should be drawn).
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	public boolean containsPoint(int x, int y) {
		return (x >= getX() &&
				y >= getY() &&
				x <= getX()+getWidth() &&
				y <= getY()+getHeight());
	}
	
	private boolean isSelected;
	
	public void select(){
		isSelected = true;
		for (UIElement e : elements){
			if (e instanceof Titlebar|| e instanceof CloseButton) e.setColor(new Color(200,200,200));
		}
	}
	
	public void deselect(){
		isSelected = false;
		for (UIElement e : elements){
			if (e instanceof Titlebar || e instanceof CloseButton) e.setColor(Color.WHITE);
		}
	}
	
	public boolean isSelected(){
		return isSelected;
	}
	
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
		setX(getX()+deltaW);
		setWidth(getWidth() - deltaW);
		elements.stream().forEach(e -> e.resizeL(deltaW));
	}
	
	/**
	 * This method resized the UI and all its element from the right.
	 * @param deltaW 	The amount of pixels the UI must be made larger with from the right.
	 */
	public void resizeR(int deltaW) {
		setWidth(getWidth() + deltaW);
		elements.stream().forEach(e -> e.resizeR(deltaW));
	}
	
	/**
	 * This method resized the UI and all its elements from the top.
	 * @param deltaH 	The amount of pixels the UI must be made smaller with from the top.
	 */
	public void resizeT(int deltaH) {
		setHeight(getHeight() - deltaH);
		setY(getY()+deltaH);
		elements.stream().forEach(e -> e.resizeT(deltaH));
	}
	
	/**
	 * This method resizes the UI and all its elements from the bottom.
	 * @param deltaH 	The amount of pixels the UI should be made larger with from the bottom.
	 */
	public void resizeB(int deltaH) {
		setHeight(getHeight()+deltaH);
		elements.stream().forEach(e -> e.resizeB(deltaH));
	}
	
	@Override
	public String toString() {
		return "UI : X="+getX() + " Y="+getY() + " W=" +getWidth() + " H="+getHeight();
	}
}


