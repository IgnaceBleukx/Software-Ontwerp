package uielements;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Class containing the 'Close' Button attached to every subwindow.
 */
public class CloseButton extends Button {
	
	/**
	 * Creates a new CloseButton
	 * @param crossSize		The length of every part of the 'X' drawn in this CloseButton.
	 */
	public CloseButton(int x, int y, int w, int h, int crossSize) {
		super(x, y, w, h,"");
		this.crossSize = crossSize;
	}
	
	/**
	 * The length of every part of the 'X' drawn in this CloseButton.
	 */
	private int crossSize;
	
	/**
	 * Paints the CloseButton to the Canvas.
	 */
	@Override
	public void paint(Graphics g){
		int arcWidth = 8;
		int arcHeight = 8;
		int middleX = getX() + getWidth()/2;
		int middleY = getY() + getHeight()/2;
		g.setColor(getColor());
		g.fillRoundRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), arcWidth, arcHeight);
		g.setColor(Color.BLACK);
		g.drawRoundRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), arcWidth, arcHeight);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.drawLine(middleX - crossSize, middleY - crossSize, middleX + crossSize, middleY+crossSize);
		g.drawLine(middleX-crossSize,middleY+crossSize,middleX+crossSize,middleY-crossSize);
		((Graphics2D) g).setStroke(new BasicStroke(1));
	}
	
	
	@Override
	/**
	 * This method resizes the closebutton from left to right.
	 * Assuming the closebutton is always placed in the top righthand corner of the screen, the closebutton is not changed.
	 */
	public void resizeL(int deltaW) {
		
	}
	
	/**
	 * This method handles resizing the subwindow to the right by moving.
	 */
	@Override
	public void resizeR(int deltaW) {
		move(deltaW,0);
	}
	
	/**
	 * This method handles resizing the subwindow upwards by moving.
	 */
	@Override
	public void resizeT(int deltaH){
		move(0,deltaH);
	}
	
	/**
	 * Resize upwards.
	 * This method has no effect because the CloseButton is situated on the top of a subwindow.
	 */
	@Override
	public void resizeB(int deltaH){
		
	}
}
