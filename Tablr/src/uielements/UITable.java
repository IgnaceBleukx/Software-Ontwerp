package uielements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

import Utils.GeometricUtils;
import Utils.Rounder;
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
		this.setLegend(legend);
		this.addAllRows(rows);
		scrollBarH.setUI(getUI());
		scrollBarV.setUI(getUI());
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
	
	private static int scrollBarW = 10;
	
	VerticalScrollBar scrollBarV = new VerticalScrollBar(getEndX()-scrollBarW,getY(),scrollBarW,getHeight()-scrollBarW);
	HorizontalScrollBar scrollBarH = new HorizontalScrollBar(getX(),getEndY()-scrollBarW,getWidth()-scrollBarW,scrollBarW);
	
	private void updateScrollBars(){
		scrollBarV.update(getRows().stream().mapToInt(r -> r.getHeight()).sum(),getHeight()-scrollBarW);
		scrollBarH.update(getRows().stream().mapToInt(r -> r.getWidth()).max().orElse(getWidth()), getWidth()-scrollBarW);
	}
	
	/**
	 * This method sets the legend for the UITable.
	 * @param newLegend
	 */
	public void setLegend(UIRow newLegend){
		newLegend.setUI(getUI());
		this.legend = newLegend;
	}
	
	
	/**
	 * adds a row to the table
	 * @param row: the row to be added
	 */
	public void addRow(UIRow row){
		this.rows.add(row);
		row.setUI(getUI());
		updateScrollBars();
	}

	/**
	 * This method adds an arraylist of rows to the current UITable.
	 * @param rows 	The arraylist containing the rows to be added.
	 */
	public void addAllRows(ArrayList<UIRow> rows){
		rows.stream().forEach(r -> this.addRow(r));
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		Shape oldClip = g.getClip();
		int[] i = GeometricUtils.intersection(getX(), getY(), getWidth(), getHeight(), oldClip.getBounds().x, 
												oldClip.getBounds().y, oldClip.getBounds().width, oldClip.getBounds().height);
		g.setClip(new Rectangle(i[0],i[1],i[2],i[3]));
		legend.paint(g);
		rows.stream().forEach(r -> r.paint(g));
		g.setClip(oldClip);
		if (getSelected() != null) {
			UIElement s = this.getSelected();
			g.fillOval(s.getX()+s.getWidth()+10, s.getY()+s.getHeight()/2, 8, 8);
		}
		scrollBarH.paint(g);
		scrollBarV.paint(g);
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

		System.out.println("[UITable.java:91]: Rows in UITable: ");
		rows.stream().forEach(e -> System.out.println(e));
		for (UIRow e : rows) { //Look in rows
			found = e.locatedAt(x,y);
			if (found != null)
				return found;
		}
		return this; //If no elements in the table match, return this.
	}

	@Override
	public void handleSingleClick() {
		new ArrayList<>(singleClickListeners).forEach(l -> l.run());
	}

	@Override
	public void handleDoubleClick() {
		new ArrayList<>(doubleClickListeners).forEach(l -> l.run());
	}
	
	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
		for(int i=0;i<rows.size();i++){
			rows.get(i).handleKeyboardEvent(keyCode, keyChar);
		}
		if (new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode) == null)
			return;
		new HashMap<Integer, ArrayList<Runnable>>(keyboardListeners).get(keyCode).stream().forEach(l -> l.run());
	}
	
	@Override
	public void handleDrag(int x, int y) {
		new ArrayList<BiConsumer<Integer,Integer>>(dragListeners).stream().forEach(r -> r.accept(x, y));
	}
	
	@Override
	public void selectElement(UIElement e) {
		if (e==this) 
			select();
		else
			deselect();
		
		for (UIElement el : rows) {
			el.selectElement(e);
		}
	}
	
	@Override
	public void setUI(UI ui) {
		this.ui = ui;
		rows.stream().forEach(r -> r.setUI(ui));
		legend.setUI(ui);
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
		updateScrollBars();
	}
	
	@Override
	public void move(int deltaX, int deltaY) {
		setX(getX() + deltaX);
		setY(getY() + deltaY);
		legend.move(deltaX, deltaY);
		rows.stream().forEach(e -> e.move(deltaX, deltaY));
		scrollBarH.move(deltaX, deltaY);
		scrollBarV.move(deltaX, deltaY);
	}
	
	@Override
	public void resizeL(int deltaX){
		this.setWidth(getWidth()-deltaX);
		this.setX(getX()+deltaX);
		legend.move(deltaX,0);
		rows.stream().forEach(r -> r.move(deltaX,0));
		scrollBarV.resizeL(deltaX);
		scrollBarH.resizeL(deltaX);
		updateScrollBars();
	}
	@Override
	public void resizeR(int deltaX){
		this.setWidth(getWidth()+deltaX);
		scrollBarV.resizeR(deltaX);
		scrollBarH.resizeR(deltaX);
		updateScrollBars();
	}
	@Override
	public void resizeB(int deltaY){
		this.setHeight(getHeight()+deltaY);
		legend.resizeB(deltaY);
		scrollBarV.resizeB(deltaY);
		scrollBarH.resizeB(deltaY);
		updateScrollBars();
	}
	@Override
	public void resizeT(int deltaY){
		this.setHeight(getHeight()-deltaY);
		this.setY(getY()+deltaY);
		legend.move(0,deltaY);
		rows.stream().forEach(r -> r.move(0, deltaY));
		scrollBarV.resizeT(deltaY);
		scrollBarH.resizeT(deltaY);
	}
	
	@Override
	public UITable clone(){
		ArrayList<UIRow> clonedRows = new ArrayList<UIRow>();
		rows.stream().forEach(e -> clonedRows.add(e.clone()));
		UITable clone = new UITable(getX(),getY(),getWidth(),getHeight(),legend.clone(),clonedRows);
		clone.scrollBarH = scrollBarH;
		clone.scrollBarV = scrollBarV;
		return clone;
	}

}
