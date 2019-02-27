package uielements;

import java.awt.Graphics;
import java.util.ArrayList;

public class UITable extends UIElement {

	/*
	 * Constructor of the Table.
	 * @param x: The x position of the left top corner of the Table.
	 * @param y: The y position of the left top corner of the Table.
	 * @param legend: The legendary of the table.
	 * @param rows: The rows of the table.
	 */
	public UITable(int x, int y, int w, int h,UIRow legend, ArrayList<UIRow> rows) {
		super(x, y,w,h);
		this.legend = legend;
		this.rows = rows;
	}

	ArrayList<UIRow> rows;
	UIRow legend;
	
	@Override
	public void paint(Graphics g) {
		legend.paint(g);
		for(UIRow r : rows){
			r.paint(g);
		}
	}

	/**
	 * Returns the most specific UIElement located at (x,y) by searching in its rows and legend
	 * @param x		X Coordinate
	 * @param y		Y Coordinate
	 * @return		UIElement a at position (x,y)
	 * @note		If possible and correct, UIElements inside containers will be returned
	 */
	@Override
	public UIElement locatedAt(int x, int y) {
		UIElement found = null;

		found = legend.locatedAt(x,y); //Look in legend
		if (found != null) return found;

		for (UIRow e : rows) { //Look in rows
			found = e.locatedAt(x,y);
			if (found != null)
				return found;
		}
		return this; //If no elements in the table match, return this.
	}

}
