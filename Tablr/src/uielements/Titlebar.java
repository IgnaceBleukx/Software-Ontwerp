package uielements;

/**
 * Class containing a Titlebar.
 *
 */
public class Titlebar extends Button {

	/**
	 * Creates a new Titlebar
	 * @param x		x coordinate
	 * @param y		y coordinate
	 * @param w		width
	 * @param h		height
	 * @param text	title
	 */
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

	/**
	 * Clones this object
	 */
	@Override
	public Titlebar clone(){
		return new Titlebar(getX(),getY(),getWidth(),getHeight(),getText());
	}
	
}
