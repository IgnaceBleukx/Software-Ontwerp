package canvaswindow;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import uielements.*;

import java.util.ArrayList;
import java.util.Date;

import facades.Tablr;
import ui.TablesModeUI;
import ui.UI;

public class MyCanvasWindow extends CanvasWindow {
	
	private Tablr tablr;

	/**
	 * Initiates a Canvas with a new Tablr and loads a UI into the Canvas.
	 * Loading a UI passes a reference of the Tablr to all elements in the UI.
	 * @param title		Window title
	 * 		
	 */
	public MyCanvasWindow(String title) {
		super(title);
		tablr = new Tablr();		
	}

	/**
	 * Returns the communicationManager of this Canvas
	 * @return 	CommunicationManager c
	 */
	public Tablr getTablr() {
		return tablr;
	}

	
	/**
	 * Paints the active UI on the Canvas
	 */
	public void paint(Graphics g){
		tablr.paint(g);
	}
	
	/**
	 * Delegates a mouse click event to the clicked UIElement.
	 * In normal circumstances, mouse events occur in pairs of three:
	 * PRESSED, DRAGGED, RELEASED. id % 3 allows us to find out which
	 * type occurred.
	 * @param id 			Click event id
	 * @param x				X Coordinate
	 * @param y				Y Coordinate
	 * @param clickCount	Number of times clicked
	 */
	@Override
	public void handleMouseEvent(int id, int x, int y, int clickCount){
		UIElement clicked = null;

//		System.out.println(x);
//		System.out.println(y);
		
		if (id == MouseEvent.MOUSE_PRESSED || id == MouseEvent.MOUSE_CLICKED){ //Only search for a potential new UI with THESE actions
			try {
//				System.out.println("[MyCanvasWindow.java:66]: ui = " + tablr.getUIAt(x,y));
				clicked = tablr.getUIAt(x, y).locatedAt(x, y);
			} catch (NullPointerException e) {
				System.out.println("[MyCanvasWindow.java:67]: No UI at these coordinates");
				return;
			}
			
			try{
				getTablr().selectUI(clicked.getUI());
			}catch (NullPointerException e){
				getTablr().selectUI(null);
			}
			if (clicked == null) return;
			
			System.out.println("[MyCanvasWindow.java:83]: Clicked on : " + clicked);
			
			//Mouse pressed
			if (id  == MouseEvent.MOUSE_PRESSED) {
				System.out.println("[MyCanvasWindow.java:83]: Mouse Pressed!");
				clicked.handlePressed(x, y);
			}

			//Mouse clicked
			else if (id == MouseEvent.MOUSE_CLICKED) {
				System.out.println("[MyCanvasWindow.java:99]: Mouse Clicked!");
				/**
				 * Some element has a hard lock, ignore this input if the
				 * element getting the input is not the element that has the hard lock
				 */
				if(getTablr().getLockedElement() != (null) && !getTablr().getLockedElement().equals(clicked)){
					return;
				}
				

				if (clickCount == 2) {
					clicked.handleDoubleClick();
				}
				else if (clickCount == 1) {
					clicked.handleSingleClick();
				}
			}
			
		}

		
		//Mouse dragged
		else if (id == MouseEvent.MOUSE_DRAGGED) {
			System.out.println("[MyCanvasWindow.java:88]: Mouse Dragged!");
			try{
				System.out.println("[MyCanvasWindow.java:119]: Dragging : " + getTablr().getSelectedUI().getDragging());
				UIElement draggingItem = getTablr().getSelectedUI().getDragging();
				draggingItem.handleDrag(x, y);
				draggingItem.handlePressed(x, y);
			}catch (NullPointerException e){}
		}
		
		//Mouse released
		else if (id == MouseEvent.MOUSE_RELEASED) {	
			System.out.println("[MyCanvasWindow.java:94]: Mouse Released!");
			try{
				getTablr().getSelectedUI().getDragging().handleReleased();
				getTablr().getSelectedUI().setDragging(null);
			}catch (NullPointerException e){}
		}
		
		repaint();
	}

	// Date that keeps track of the last time a 'Ctrl' button is clicked
	private Date ctrlTimestamp = null;
	private int milliSecondsWaiting = 1000;

	private void checkCtrlT(int keyCode) {
		// 17 is Ctrl
		if (keyCode == 17) {
			ctrlTimestamp = new Date();
		}
		
		// 84 is 'T'
		// If Ctrl is pressed within 'milliSecondsWaiting' then the user pressed Ctrl+T 
		if (keyCode == 84 && ctrlTimestamp != null && (new Date().getTime() - ctrlTimestamp.getTime() < milliSecondsWaiting)) {
			System.out.println("[MyCanvasWindow.java: 113] Create a new tables subwindow");
			
			tablr.addTablesModeUI();
			
			// Following lines are for testing purposes of Rows Mode:
			// Uncomment deze lijnen voor die modes te testen!!!
			// Add a table to the tablr
			this.handleMouseEvent(0, 50, 150, 2);
			
			//Click on the table to open design mode
			this.handleMouseEvent(0, 70, 30, 2);
			
		}
	}
	
	/**
	 * Delegates a key event to all elements in the <b>selected</b> UI.
	 * Negative keycodes are used to listen to more complex events, such as
	 * alphanumerics.
	 * @param id		ID of the event
	 * @param keyCode	Java keyCode of the key that was pressed
	 * @param keyChar	Java char of the key that was pressed (if applicable)
	 */
	@Override
	public void handleKeyEvent(int id, int keyCode, char keyChar){
		System.out.println("Keycode: "+keyCode);
		checkCtrlT(keyCode);

		UI ui = tablr.getSelectedUI();
		if(ui == null) return;
		for (int i=0;i<ui.getElements().size();i++) {
			UIElement e;
			try {
				e = tablr.getSelectedUI().getElements().get(i);
			} catch (IndexOutOfBoundsException e1) {
				return;
			}
			e.handleKeyboardEvent(keyCode, keyChar);
			
			if (keyCode == 17)
				tablr.controlPressed();
			if (Character.isLetterOrDigit(keyChar) || keyCode == 8 || keyChar == '@' || keyChar == '.') {
				e.handleKeyboardEvent(-1, Character.MIN_VALUE);
			}
		}
		
		repaint();
	}

	
}
