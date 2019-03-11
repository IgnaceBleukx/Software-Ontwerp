package canvaswindow;

import java.awt.Graphics;

import uielements.*;
import ui.Loadable_Interfaces;
import java.util.ArrayList;

import facades.CommunicationManager;

public class MyCanvasWindow extends CanvasWindow {
	
	private CommunicationManager communicationManager;
	
//	/**
//	 * The currently active UI that is painted
//	 */
//	private UI activeUI;

	
	public MyCanvasWindow(String title) {
		super(title);
		
		communicationManager = new CommunicationManager();
		communicationManager.loadUI(Loadable_Interfaces.TABLE_DESIGN);

//		UI test = new UI(Loadable_Interfaces.TEST);
//		UI tables = new UI(Loadable_Interfaces.TABLES);
//		UI design = new UI(Loadable_Interfaces.TABLE_DESIGN);
//		UI rows = new UI(Loadable_Interfaces.TABLE_ROWS);
//			
//		setActiveUI(design);

	}

//	/**
//	 * Returns the active UI
//	 */
//	public UI getActiveUI() {
//		return this.activeUI;
//	}
//
//	/**
//	 * Sets the active UI to a given UI
//	 * @param ui	The UI
//	 */
//	public void setActiveUI(UI ui) {
//		this.activeUI = ui;
//	}
	
	public CommunicationManager getCommunicationManager() {
		return communicationManager;
	}

	
	/**
	 * Paints the active UI on the Canvas
	 */
	public void paint(Graphics g){
		communicationManager.getActiveUI().paint(g);
	}
	
	@Override
	public void handleMouseEvent(int id, int x, int y, int clickCount){
		UIElement clicked = communicationManager.getActiveUI().locatedAt(x, y);
		System.out.println("Clicked on "+clicked);
		//System.out.println(clicked.getX());
		//System.out.println(clicked.getY());
		//System.out.println(clicked.getWidth());
		//System.out.println(clicked.getHeight());
		
		if (clicked == null)
			return;
		
		if (id % 3 != 0) 
			return;
		
		if (clickCount == 1) {
			clicked.handleSingleClick();
		}
		else if (clickCount == 2) {
			clicked.handleDoubleClick();
		}
		repaint();
	}


	@Override
	public void handleKeyEvent(int id, int keyCode, char keyChar){
		System.out.println("Keycode: "+keyCode);
		for (UIElement e : communicationManager.getActiveUI().getElements()) {
			e.handleKeyboardEvent(keyCode, keyChar);
			if (Character.isLetterOrDigit(keyChar) || keyCode == 8) {
				e.handleKeyboardEvent(-1, Character.MIN_VALUE);
			}
		}

		repaint();
	}
	
}
