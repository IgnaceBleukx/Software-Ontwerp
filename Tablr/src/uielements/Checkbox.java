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
	public Checkbox(int x, int y,int w, int h, Boolean value){
		super(x, y,w,h);
		this.value = value;
	}
	
	/**
	 * Whether this checkbox is checked or not
	 */
	private Boolean value = true;
	
	public Boolean getValue(){
		return value;
	}
	
	public void setValue(Boolean value){
		this.value = value;
	}
	
	@Override
	public void paint(Graphics g){
		//Drawing inside:
		g.setColor(getColor());
		if (value == null){
			g.setColor(Color.gray);
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}
		else if (value ==  true){
			g.fillRect(getX(), getY(), getWidth(), getHeight());
			g.setColor(Color.black);
			int x1 = getX();
			int y1 = getY();
			int x2 = getX()+ getWidth();
			int y2 = getY()+ getHeight();
			g.drawLine(x1, y1, x2, y2);
			g.drawLine(x1, y2, x2, y1);
		}
		else
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		//Drawing border:
		if (getError())
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void handleSingleClick() {
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
		dragListeners.stream().forEach(r -> r.accept(x, y));
	}

	public boolean isChecked() {
		return value;
	}
	
	
}
