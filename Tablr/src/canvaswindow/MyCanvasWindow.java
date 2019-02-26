package canvaswindow;

import java.awt.Graphics;
import canvaswindow.UI;
import elements.*;
import java.util.ArrayList;

public class MyCanvasWindow extends CanvasWindow {

	/**
	 * The currently active UI that is painted
	 */
	private UI activeUI;

	protected MyCanvasWindow(String title) {
		super(title);

		UI interface1 = new UI();

		TextField b = new TextField(100,50,400,50, "TextField 1");
		Button b1 = new Button(175, 125, 100, 50, "Button 1");
		Button b2 = new Button(325, 125, 100, 50, "Button 2");
		Checkbox c1 = new Checkbox(525, 63, 25,25, true);

		interface1.addUIElement(b);
		interface1.addUIElement(b1);
		interface1.addUIElement(b2);
		interface1.addUIElement(c1);

		Button b3 = new Button(100, 200, 50, 50, "Button 3");
		Button b4 = new Button(150, 200, 50, 50, "Button 4");
		Button b5 = new Button(200, 200, 50, 50, "Button 5");
		Button b6 = new Button(250, 200, 50, 50, "Button 6");
		ArrayList<UIElement> buttons = new ArrayList<>();
		buttons.add(b3);
		buttons.add(b4);
		buttons.add(b5);
		buttons.add(b6);

		UIRow row1 = new UIRow(85,185,230,80,buttons);
		interface1.addUIElement(row1);

		setActiveUI(interface1);

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
		System.out.println(clicked);
	}

	@Override
	public void handleKeyEvent(int id, int keyCode, char keyChar){
		
	}
}
