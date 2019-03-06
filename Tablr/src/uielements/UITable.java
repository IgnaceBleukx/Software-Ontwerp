package uielements;

import java.awt.Graphics;
import java.util.ArrayList;

import domain.Cell;
import domain.Column;
import domain.Table;

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
		this.rows.addAll(rows);
	}

	ArrayList<UIRow> rows =  new ArrayList<UIRow>();
	UIRow legend;
	
	public void addRow(UIRow row){
		this.rows.add(row);
	}
	
	@Override
	public void paint(Graphics g) {
		legend.paint(g);
		for(UIRow r : rows){
			r.paint(g);
		}
	}

	public void loadTable(Table tab,int cellWidth, int cellHeigth) {
		int rows = communicationManager.getRows(tab).size();
		int y = super.getY()+cellHeigth;
		for(int i=0;i<rows;i++){
			int x = super.getX();
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			for(Column col : communicationManager.getColumns(tab)){
				String val = communicationManager.getValue(col,i).toString();
				TextField field =  new TextField(x,y,cellWidth, cellHeigth,val);
				emts.add(field);
				x += cellWidth;
			}
			UIRow uiRow = new UIRow(super.getX(),y,super.getWidth(),super.getHeight(),emts);
			addRow(uiRow);
			y += cellHeigth;
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
		if (!containsPoint(x,y)) return null;

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

	@Override
	public void handleSingleClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDoubleClick() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		for(UIElement i: rows){
			i.handleKeyboardEvent(keyCode, keyChar);
		}
		if (keyboardListeners.get(keyCode) == null)
			return;
		
		for (Runnable r : keyboardListeners.get(keyCode)) {
			r.run();
		}
		
	}

}
