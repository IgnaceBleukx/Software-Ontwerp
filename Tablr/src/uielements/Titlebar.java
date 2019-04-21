package uielements;


public class Titlebar extends Button {

	public Titlebar(int x, int y, int w, int h, String text) {
		super(x, y, w, h, text);
	}
	
	@Override
	public void resizeT(int deltaH){
		move(0,deltaH);
	}
	
	@Override
	public void resizeB(int deltaH){
		
	}

	@Override
	public Titlebar clone(){
		return new Titlebar(getX(),getY(),getWidth(),getHeight(),getText());
	}
	
}
