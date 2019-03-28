package uielements;


import java.awt.Graphics;

public class CloseButton extends Button {
	
	public CloseButton(int x, int y, int w, int h) {
		super(x, y, w, h,"");
	}

	@Override
	public void paint(Graphics g){
		int arcWidth = 8;
		int arcHeight = 8;
		g.drawRoundRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), arcWidth, arcHeight);
		g.drawLine(getX(), getY(), getX()+getWidth(),getY()+getHeight());
		g.drawLine(getX(),getY()+getHeight(),getX()+getWidth(),getY());
				
	}

}
