package canvaswindow;

import java.awt.Graphics;

import uielements.*;
import ui.Loadable_Interfaces;
import java.util.ArrayList;

public class MyCanvasWindow extends CanvasWindow {

	/**
	 * The currently active UI that is painted
	 */
	private UI activeUI;

	protected MyCanvasWindow(String title) {
		super(title);

		UI test = new UI(Loadable_Interfaces.TEST);
		UI tables = new UI(Loadable_Interfaces.TABLES);
		UI design = new UI(Loadable_Interfaces.TABLE_DESIGN);
		UI rows = new UI(Loadable_Interfaces.TABLE_ROWS);
			
		setActiveUI(design);

	}

	/**
	 * Returns the active UI
	 */
	public UI getActiveUI() {
		return this.activeUI;
	}

	/**
	 * Sets the active UI to a given UI
	 * @param ui	The UI
	 */
	public void setActiveUI(UI ui) {
		this.activeUI = ui;
	}

	@Override
	public void paint(Graphics g){
		getActiveUI().paint(g);
	}
	
	@Override
	public void handleMouseEvent(int id, int x, int y, int clickCount){
		UIElement clicked = getActiveUI().locatedAt(x, y);
		System.out.println("Clicked on "+clicked);
		
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
		for (UIElement e : getActiveUI().getElements())
			e.handleKeyboardEvent(keyCode, keyChar);
		
		repaint();
	}
}
