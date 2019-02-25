package canvaswindow;

import java.awt.Graphics;

public class MyCanvasWindow extends CanvasWindow {

	protected MyCanvasWindow(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paint(Graphics g){
		g.draw3DRect(100, 100, 50, 100, false);
	}
	
	@Override
	public void handleMouseEvent(int id, int x, int y, int clickCount){
		
	}

	@Override
	public void handleKeyEvent(int id, int keyCode, char keyChar){
		
	}
}
