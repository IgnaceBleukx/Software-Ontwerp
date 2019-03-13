package canvaswindow;

import java.awt.Graphics;

import uielements.*;
import ui.Loadable_Interfaces;
import java.util.ArrayList;

import facades.CommunicationManager;

public class MyCanvasWindow extends CanvasWindow {
	
	private CommunicationManager communicationManager;

	/**
	 * Initiates a Canvas with a new CommunicationManager and loads a UI into the Canvas.
	 * Loading a UI passes a reference of the communicationManager to all elements in the UI.
	 * @param title		Window title
	 * 		
	 */
	public MyCanvasWindow(String title) {
		super(title);
		
		communicationManager = new CommunicationManager();
		communicationManager.loadUI(Loadable_Interfaces.TABLES);
		
		communicationManager.addTitleChangeRunnable(() -> {
			//this.setTitle(communicationManager.getNewTitle());
		});

	}

	/**
	 * Returns the communicationManager of this Canvas
	 * @return 	CommunicationManager c
	 */
	public CommunicationManager getCommunicationManager() {
		return communicationManager;
	}

	
	/**
	 * Paints the active UI on the Canvas
	 */
	public void paint(Graphics g){
		communicationManager.getActiveUI().paint(g);
	}
	
	/**
	 * Delegates a mouse click event to the clicked UIElement.
	 * 
	 * In normal circumstances, mouse events occur in pairs of three:
	 * PRESSED, DRAGGED, RELEASED. id % 3 allows us to find out which
	 * type occurred.
	 * @param id 			Click event id
	 * @param x				X Coordinate
	 * @param y				repaint();Y Coordinate
	 * @param clickCount	Number of times clicked
	 * 
	 */
	@Override
	public void handleMouseEvent(int id, int x, int y, int clickCount){
		UIElement clicked = communicationManager.getActiveUI().locatedAt(x, y);
		System.out.println(clicked.getX());
		System.out.println(clicked.getY());
		System.out.println("[MyCanvasWindow.java:62]: Clicked on: " + clicked);
		
		if (clicked == null)
			return;
		
		if (id % 3 != 0) 
			return;
		
		if (clickCount == 2) {
			clicked.handleDoubleClick();
		}
		else if (clickCount == 1) {
			clicked.handleSingleClick();
		}
		repaint();
	}

	/**
	 * Delegates a key event to ALL elements n the loaded UI.
	 * To allow flexible keyboard event handling, negative keycodes are used.
	 * Listening to 
	 * @param id		ID of the event
	 * @param keyCode	Java keyCode of the key that was pressed
	 * @param keyChar	Java char of the key that was pressed (if applicable)
	 * TODO: reduce coupling on line 99
	 */
	@Override
	public void handleKeyEvent(int id, int keyCode, char keyChar){
		System.out.println("Keycode: "+keyCode);
//		for (UIElement e : communicationManager.getActiveUI().getElements()) {
//			e.handleKeyboardEvent(keyCode, keyChar);
//			if (Character.isLetterOrDigit(keyChar) || keyCode == 8) {
//				e.handleKeyboardEvent(-1, Character.MIN_VALUE);
//			}
//		}
		
		for (int i=0;i<communicationManager.getActiveUIElements().size();i++) {
			UIElement e = communicationManager.getActiveUIElements().get(i);
			e.handleKeyboardEvent(keyCode, keyChar);

			if (Character.isLetterOrDigit(keyChar) || keyCode == 8 || keyChar == '@') {
				e.handleKeyboardEvent(-1, Character.MIN_VALUE);
			}
		}
		
		repaint();
	}
	
}
