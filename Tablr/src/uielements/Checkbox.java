package uielements;

import java.awt.Color;
import java.awt.Graphics;

public class Checkbox extends UIElement {

	/**
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
	 * Sets the value of greyedout to true (Button is now grey)
	 */
	public void greyOut(){
		this.greyedOut = true;
	}

	/**
	 * Whether this checkbox is checked or not
	 */
	private boolean checked = true;
	
	/**
	 * Whether this checkbox is greyed out or not
	 */
	private boolean greyedOut = false;
	
	public boolean getGreyedOut() {
		return greyedOut;
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.black);
		if (greyedOut) {
			g.setColor(Color.gray);
			g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());	
		}
		else if(checked){
			g.setColor(Color.black);
			int x1 = super.getX();
			int y1 = super.getY();
			int x2 = super.getX()+super.getWidth();
			int y2 = super.getY()+super.getHeight();
			g.drawLine(x1, y1, x2, y2);
			g.drawLine(x1, y2, x2, y1);
		}
		else if (getError()) g.setColor(Color.red);
		g.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
	}

	@Override
	public void handleSingleClick() {
		toggle();
		singleClickListeners.forEach(l -> l.run());
	}

	@Override
	public void handleDoubleClick() {
		doubleClickListeners.forEach(l -> l.run());
	}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if (keyboardListeners.get(keyCode) == null)
			return;
		
		keyboardListeners.get(keyCode).forEach(l -> l.run());

	}
	
	@Override
	public void handleDrag(int x, int y) {
		dragListeners.stream().forEachOrdered(r -> r.accept(x, y));
	}

	public boolean isChecked() {
		return checked;
	}
	
	
}
