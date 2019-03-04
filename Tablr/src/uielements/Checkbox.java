package uielements;

import java.awt.Color;
import java.awt.Graphics;

public class Checkbox extends UIElement {

	/*
	 * Constructor of the checkbox.
	 * @param x: The x position of the left top corner of the checkbox.
	 * @param y: The y position of the left top corner of the checkbox.
	 * @param checked: Whether the checkbox is checked or not.
	 */
	public Checkbox(int x, int y,int w, int h, boolean checked){
		super(x, y,w,h);
		this.checked = checked;
	}

	/**
	 * 	Toggles the state of this checkbox ON->OFF or OFF->ON
	 */
	public void toggle(){
		checked = !checked;
	}

	/**
	 * Whether this checkbox is checked or not
	 */
	private boolean checked = true;
	
	@Override
	public void paint(Graphics g){
		if (checked){
			g.setColor(Color.black);
			g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
		}
		else{
			g.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
		}
		
	}

	@Override
	public void handleSingleClick() {
		toggle();
		
	}

	@Override
	public void handleDoubleClick() {
		return;
	}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if (keyboardListeners.get(keyCode) == null)
			return;
		
		for (Runnable r : keyboardListeners.get(keyCode)) {
			r.run();
		}
	}


}
