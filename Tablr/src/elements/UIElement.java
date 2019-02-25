package elements;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class UIElement {
	
	public UIElement(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	private int x;
	private int y;
	private int w;
	private int h;
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getWidth(){
		return this.w;
	}
	
	public int getHeight(){
		return this.h;
	}
	
	public void paint(Graphics g){
		
	}
	
	public void drawCenteredText(Graphics g, String text){
		Font font = g.getFont();
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = this.getX()+ (this.getWidth()- metrics.stringWidth(text)) / 2;
	    int y = this.getY() +  ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.drawString(text, this.x, this.y);
	}

}
