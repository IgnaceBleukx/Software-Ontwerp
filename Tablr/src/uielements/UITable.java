package uielements;

import java.awt.Graphics;
import java.util.ArrayList;

import domain.Cell;
import domain.Column;
import domain.Table;
import domain.Type;
import facades.Tablr;
import facades.WindowManager;
import ui.UI;

public class UITable extends UIElement {

	/**
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

	/**
	 * the rows stored in this table
	 */
	ArrayList<UIRow> rows =  new ArrayList<UIRow>();
	
	public ArrayList<UIRow> getRows() {
		return rows;
	}

	/**
	 * the upper row of this table that contains the legend of all the columns
	 */
	UIRow legend;
	
	/**
	 * adds a row to the table
	 * @param row: the row to be added
	 */
	public void addRow(UIRow row){
		this.rows.add(row);
	}
	
	@Override
	public void paint(Graphics g) {
		legend.paint(g);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		for(UIRow r : rows){
			r.paint(g);
		}
		if (getSelected() != null) {
			UIElement s = this.getSelected();
			g.fillOval(s.getX()+s.getWidth()+10, s.getY()+s.getHeight()/2, 8, 8);
		}
	}

	/**
	 * The currently selected element in this table
	 */
	private UIElement selected;
	
	public UIElement getSelected() {
		return selected;
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
		singleClickListeners.forEach(l -> l.run());
	}

	@Override
	public void handleDoubleClick() {
		doubleClickListeners.forEach(l -> l.run());
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		for(int i=0;i<rows.size();i++){
			rows.get(i).handleKeyboardEvent(keyCode, keyChar);
		}
		if (keyboardListeners.get(keyCode) == null)
			return;
		keyboardListeners.get(keyCode).stream().forEach(l -> l.run());
	}
	
	@Override
	public void handleDrag(int x, int y) {
		dragListeners.stream().forEach(r -> r.accept(x, y));
	}
	
	@Override
	public void selectElement(UIElement e) {
		if (e==this) 
			setSelected();
		else
			setNotSelected();
		
		for (UIElement el : rows) {
			el.selectElement(e);
		}
	}
	
	@Override
	public void setUI(UI ui) {
		this.ui = ui;
		rows.stream().forEach(r -> r.setUI(ui));
		}
	
	@Override
	public boolean getError() {
		for (UIElement e : this.rows) {
			if (e.getError())
				return true;
		}
		return false;
	}
	
	@Override
	public boolean hasSelectedElement() {
		if (this.isSelected()) return true;
		for (UIElement e : getRows()){
			if (e.hasSelectedElement()) return true;
		}
		return false;
	}

	@Override
	public boolean hasElementInError() {
		if (this.isSelected()) return true;
		for (UIElement e : getRows()){
			if (e.hasElementInError()) return true;
		}
		return false;
	}

	public void removeRow(UIRow uiRow) {
		this.rows.remove(uiRow);
		
	}
	
	@Override
	public void move(int deltaX, int deltaY) {
		setX(getX() + deltaX);
		setY(getY() + deltaY);
		rows.stream().forEach(e -> e.move(deltaX, deltaY));
	}
}
