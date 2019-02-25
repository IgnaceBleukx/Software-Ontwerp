package canvaswindow;

import java.awt.Graphics;

import elements.*;

public class MyCanvasWindow extends CanvasWindow {

	protected MyCanvasWindow(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paint(Graphics g){
		TextField b = new TextField(100,50,100,50, "Tralalalala");
		b.paint(g);
	}
	
	@Override
	public void handleMouseEvent(int id, int x, int y, int clickCount){
		
	}

	@Override
	public void handleKeyEvent(int id, int keyCode, char keyChar){
		
	}
}
