package canvaswindow;

import java.awt.Graphics;

import uielements.*;

import java.util.ArrayList;
import java.util.Date;

import facades.Tablr;
import ui.TablesModeUI;

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
		tablr.loadTableModeUI(new TablesModeUI(0,0,300,300,tablr));
		
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
			clicked = tablr.getUIAt(x, y).locatedAt(x, y);
		} catch (NullPointerException e) {
			System.out.println("No UI at these coordinates");
			return;
		}
		System.out.println("[MyCanvaswindow.java:64] : Id = " +id);
		System.out.println("[MyCanvaswindow.java:65] : X-coordinate = " + x);
		System.out.println("[MyCanvaswindow.java:66] : Y-coordinate = " + y);
		System.out.println("[MyCanvasWindow.java:67]: Clicked on: " + clicked);
		
		if (clicked == null)
			return;
		
//		if (id % 3 != 0) 
//			return;
		
		
		if (id % 3 == 1) return;
		else if(id % 3 == 2) {
			clicked.handleDrag(x,y);
		}
		else if (id %  3 == 0) {
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
	private int milliSecondsWaiting = 3000;
	

	private void checkCtrlT(int keyCode) {
		// 17 is Ctrl
		if (keyCode == 17) {
			ctrlTimestamp = new Date();
		}
		
		// 84 is 'T'
		// If Ctrl is pressed within 'milliSecondsWaiting' then the user pressed Ctrl+T 
		if (keyCode == 84 && ctrlTimestamp != null && (new Date().getTime() - ctrlTimestamp.getTime() < milliSecondsWaiting)) {
			System.out.println("[MyCanvasWindow.java: 113] Create a new tables subwindow");
			tablr.addTablesModeUI(new TablesModeUI(200,0,300,300,tablr));
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
		
		for (int i=0;i<tablr.getSelectedUI().getElements().size();i++) {
			UIElement e = tablr.getSelectedUI().getElements().get(i);
			e.handleKeyboardEvent(keyCode, keyChar);

			if (Character.isLetterOrDigit(keyChar) || keyCode == 8 || keyChar == '@' || keyChar == '.') {
				e.handleKeyboardEvent(-1, Character.MIN_VALUE);
			}
		}
		
		repaint();
	}

	
}
