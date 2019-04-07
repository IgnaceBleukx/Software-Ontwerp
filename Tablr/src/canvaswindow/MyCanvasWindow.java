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
		tablr.addTitleChangeRunnable(() -> {
			//this.setTitle(communicationManager.getNewTitle());
		});

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
	 * 
	 * In normal circumstances, mouse events occur in pairs of three:
	 * PRESSED, DRAGGED, RELEASED. id % 3 allows us to find out which
	 * type occurred.
	 * @param id 			Click event id
	 * @param x				X Coordinate
	 * @param y				Y Coordinate
	 * @param clickCount	Number of times clicked
	 * 
	 */
	@Override
	public void handleMouseEvent(int id, int x, int y, int clickCount){
		UIElement clicked;
		try {
			System.out.println("[MyCanvasWindow.java:65]: tablr = " + tablr);
			System.out.println("[MyCanvasWindow.java:66]: ui = " + tablr.getUIAt(x,y));
			clicked = tablr.getUIAt(x, y).locatedAt(x, y);
		} catch (NullPointerException e) {
			System.out.println("[MyCanvasWindow.java:67]: No UI at these coordinates");
			return;
		}
		getTablr().selectUI(clicked.getUI());
//		System.out.println("[MyCanvasWindow.java:71]: Clicked on: " + clicked);
//		System.out.println("[MyCanvaswindow.java:72] : Id = " +id);
//		System.out.println("[MyCanvaswindow.java:73] : X-coordinate = " + x);
//		System.out.println("[MyCanvaswindow.java:74] : Y-coordinate = " + y);
				
		if (clicked == null) return;
		
		//Mouse pressed
		if (id  == MouseEvent.MOUSE_PRESSED) {
			System.out.println("[MyCanvasWindow.java:83]: Mouse Pressed!");
			clicked.handlePressed(x, y);
		}
		//Mouse dragged
		else if (id == MouseEvent.MOUSE_DRAGGED) {
			System.out.println("[MyCanvasWindow.java:88]: Mouse Dragged!");
			dragCounter++;
			clicked.handleDrag(x,y);
			clicked.handlePressed(x, y);
		}
		//Mouse released
		else if (id == MouseEvent.MOUSE_RELEASED) {	
			System.out.println("[MyCanvasWindow.java:94]: Mouse Released!");
			clicked.handleReleased();
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
		repaint();
	}

	// Date that keeps track of the latest time a 'Ctrl' button is clicked
	private Date ctrlTimestamp = null;
	private int milliSecondsWaiting = 1000;
	private int dragCounter = 0;

	private void checkCtrlT(int keyCode) {
		// 17 is Ctrl
		if (keyCode == 17) {
			ctrlTimestamp = new Date();
		}
		
		// 84 is 'T'
		// If Ctrl is pressed within 'milliSecondsWaiting' then the user pressed Ctrl+T 
		if (keyCode == 84 && ctrlTimestamp != null && (new Date().getTime() - ctrlTimestamp.getTime() < milliSecondsWaiting)) {
			System.out.println("[MyCanvasWindow.java: 113] Create a new tables subwindow");
			
			tablr.addTablesModeUI(new TablesModeUI(0,0,300,300,tablr));
			
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
	 * To allow flexible keyboard event handling, negative keycodes are used.
	 * @param id		ID of the event
	 * @param keyCode	Java keyCode of the key that was pressed
	 * @param keyChar	Java c2har of the key that was pressed (if applicable)
	 */
	@Override
	public void handleKeyEvent(int id, int keyCode, char keyChar){
		System.out.println("Keycode: "+keyCode);
		checkCtrlT(keyCode);

		UI ui = tablr.getSelectedUI();
		if(ui == null) return;
		for (int i=0;i<ui.getElements().size();i++) {
			UIElement e = tablr.getSelectedUI().getElements().get(i);
			e.handleKeyboardEvent(keyCode, keyChar);

			if (Character.isLetterOrDigit(keyChar) || keyCode == 8 || keyChar == '@' || keyChar == '.') {
				e.handleKeyboardEvent(-1, Character.MIN_VALUE);
			}
		}
		
		repaint();
	}

	
}
