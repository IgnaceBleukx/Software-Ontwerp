package uielements;


import java.awt.BasicStroke;
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
		g.drawRoundRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), arcWidth, arcHeight);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.drawLine(middleX - crossSize, middleY - crossSize, middleX + crossSize, middleY+crossSize);
		g.drawLine(middleX-crossSize,middleY+crossSize,middleX+crossSize,middleY-crossSize);
		((Graphics2D) g).setStroke(new BasicStroke(1));
	}

}
