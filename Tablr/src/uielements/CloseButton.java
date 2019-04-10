package uielements;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CloseButton extends Button {
	
	public CloseButton(int x, int y, int w, int h, int crossSize) {
		super(x, y, w, h,"");
		this.crossSize = crossSize;
	}

	private int crossSize;
	
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
	
	@Override
	/**
	 * 
	 */
	public void resizeR(int deltaW) {
		move(deltaW,0);
	}
	
	@Override
	public void resizeT(int deltaH) {
		move(0,deltaH);
	}
	
	@Override
	public void resizeB(int deltaH) {
		
	}
	
	

}
