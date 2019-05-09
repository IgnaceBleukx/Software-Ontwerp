package uielements;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

import javax.naming.CommunicationException;

import Utils.GeometricUtils;

public class QueryTextField extends TextField {

	/**
	 * Constructor of the TextField.
	 * @param x: The x position of the left top corner of the TextField.
	 * @param y: The y position of the left top corner of the TextField.
	 * @param checked: Whether the TextField is checked or not.
	 */
	public QueryTextField(int x, int y,int w, int h, String t) {
		super(x, y, w, h, t);
		setText(t);
	}
	

	@Override
	public void paint(Graphics g) {
		FontMetrics metrics =  g.getFontMetrics(g.getFont());
		g.setColor(Color.white);
		g.fillRect(super.getX(),super.getY(), super.getWidth(), super.getHeight());
					
		
		if (getError()){
			g.setColor(Color.red);
			g.drawRect(super.getX()+1,super.getY()+1, super.getWidth()-2, super.getHeight()-2);
		}else
			g.setColor(Color.black);
		g.drawRect(super.getX(),super.getY(), super.getWidth(), super.getHeight());
		g.setColor(Color.black);
		int y = this.getY() +  ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
		
		Shape oldClip = g.getClip();
		int[] i = GeometricUtils.intersection(getX(), getY(), getWidth(), getHeight(), oldClip.getBounds().x, 
												oldClip.getBounds().y, oldClip.getBounds().width, oldClip.getBounds().height);
		g.setClip(new Rectangle(i[0],i[1],i[2],i[3]));
		if (!this.isSelected())
			g.drawString(this.getText(), super.getX()+10, y);
		else
			g.drawString(this.getText() + "<", super.getX()+10, y);
		g.setClip(oldClip);
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		if (!this.isSelected()) return;
		if (keyCode == 10 && isSelected() == true) { //Enter, end editing
			if (!getError())
				deselect();
		}

		if (keyCode == 27 &&  isSelected()) { //Esc, restore
			restoreText();
			deselect();
			if(getError()) isNotError();
		}
				
		if (keyCode == 8 && isSelected()) { //Backspace: remove character
			if (getText().length() > 0) {
				String newText = getText().substring(0, getText().length() - 1);
				setText(newText);
			}	
		}

		
		if ((Character.isLetterOrDigit(keyChar) || keyChar == '@' || keyChar == '.' || keyChar == ' ') && isSelected()) {
			setText(getText()+keyChar);
		}
		
		
		if (new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode) == null)
			return;
		
		new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode).stream().forEach(l -> l.run());
	}
	

	@Override
	public QueryTextField clone(){
		return new QueryTextField(getX(),getY(),getWidth(),getHeight(),getText());
	}
}
