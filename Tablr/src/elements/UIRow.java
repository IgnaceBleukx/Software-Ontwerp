package elements;

import java.awt.Graphics;
import java.util.ArrayList;

public class UIRow extends UIElement {

	public UIRow(int x, int y,int w, int h,  ArrayList<UIElement> elements) {
		super(x, y, w, h);
		this.elements = elements;
	}
	
	private ArrayList<UIElement> elements;

	public void paint(Graphics g){
		for (UIElement e : this.elements){
			e.paint(g);
		}
	}
	

}
