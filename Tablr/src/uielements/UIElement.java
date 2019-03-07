package uielements;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import facades.CommunicationManager;


public abstract class UIElement {
	
	/**
	 * Reference to the communicationsmanager that is used to talk to the UI.
	 */
	protected CommunicationManager communicationManager;

	public void setCommunicationManager(CommunicationManager c) {
		this.communicationManager = c;
	}
	
	public CommunicationManager getCommunicationManager() {
		return this.communicationManager;
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
	 * HashMap that maps keycodes to a list of runnables that are to be executed
	 */
	protected HashMap<Integer, ArrayList<Runnable>> keyboardListeners = new HashMap<Integer, ArrayList<Runnable>>();
	
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
	
	public void addSingleClickListener(Runnable f) {
		singleClickListeners.add(f);
	}
	
	public void addDoubleClickListener(Runnable f) {
		doubleClickListeners.add(f);
	}
	
	
	public abstract void handleSingleClick();
	
	public abstract void handleDoubleClick();
	
	public abstract void handleKeyboardEvent(int keyCode, char keyChar);
	
	
	
	public UIElement(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

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
	
	/**
	 * This method returns the height of the current UIElement.
	 */
	public int getHeight(){
		return this.h;
	}
	
	/**
	 * This method paints the UIElement on the canvas window specified by the Graphics element.
	 * This method should be overwritten by every subclass of UIElement.
	 * @param g: The graphics engine on which the UIElement is painted.
	 */
	public void paint(Graphics g){
		
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
	protected boolean isSelected;
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void setSelected() {
		this.isSelected = true;
	}
	
	public void setNotSelected() {
		this.isSelected = false;
	}

	protected void selectElement(UIElement newElement) {
		if (newElement == this) {
			setSelected();
		}
		else {
			setNotSelected();
		}
	}

}
