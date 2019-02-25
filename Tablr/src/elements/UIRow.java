package elements;

import java.util.ArrayList;

public class UIRow extends UIElement {

	public UIRow(double x, double y, ArrayList<UIElement> elements) {
		super(x, y);
		this.elements = elements;
	}
	
	private ArrayList<UIElement> elements;

	

}
