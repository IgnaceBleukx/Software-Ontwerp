package elements;

import java.util.ArrayList;

public class UITable extends UIElement {

	/*
	 * Constructor of the Table.
	 * @param x: The x position of the left top corner of the Table.
	 * @param y: The y position of the left top corner of the Table.
	 * @param legend: The legendary of the table.
	 * @param rows: The rows of the table.
	 */
	public UITable(double x, double y,UIRow legend, ArrayList<UIElement> rows) {
		super(x, y);
		this.legend = legend;
		this.rows = new ListView(x,y,rows);
	}

	ListView rows;
	UIRow legend;
	
	@Override
	public void paint() {
		// TODO Auto-generated method stub

	}

}
