package canvaswindow;

import java.awt.Graphics;

import uielements.*;
import java.util.ArrayList;

import facades.Tablr;

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
		tablr.loadTableModeUI();
		
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
		UIElement clicked = tablr.getUIAt(x, y).locatedAt(x, y);
		System.out.println("[MyCanvaswindow.java:64] : X-coordinate = " + x);
		System.out.println("[MyCanvaswindow.java:65] : Y-coordinate = " + y);
		System.out.println("[MyCanvasWindow.java:66]: Clicked on: " + clicked);
		
		if (clicked == null)
			return;
		
		if (id % 3 != 0) 
			return;
		
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
		repaint();
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
