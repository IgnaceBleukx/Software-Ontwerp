package uielements;

import java.awt.Graphics;
import ui.Loadable_Interfaces;
import java.util.ArrayList;

import domain.TableManager;
import uielements.*;


public class UI {
	
	/**
	 * Creates an empty UI
	 */
	public UI() {}
	
	/**
	 * Creates a UI and loads a preprogrammed interface in it.
	 */
	public UI(Loadable_Interfaces i) {
		switch (i) {
			case TABLES: loadTableDesignInterface();
			case TABLE_DESIGN: loadTableDesignInterface();
			case TABLE_ROWS: loadTableRowsInterface();
			case TEST: loadTestInterface();
		}
	}

	
	private void loadTestInterface() {
		tableManager = new TableManager();
		Text text1 = new Text(40,40,100,100,"Interface: test");
		ListView l = new ListView(10, 10, 580, 500, null);
		Button createTableButton = new Button(10,520,580,70, "Create table");
		
		this.addUIElement(text1);
		this.addUIElement(l);
		this.addUIElement(createTableButton);
		createTableButton.addSingleClickListener(() -> {tableManager.addEmptyTable();});
		
		
		
	}

	private void loadTableRowsInterface() {
		Text text1 = new Text(40,40,100,100,"Interface: rows mode");
		this.addUIElement(text1);		
	}

	private void loadTableDesignInterface() {
		Text text1 = new Text(40,40,100,100,"Interface: table design");
		this.addUIElement(text1);		
	}

	private void loadTablesInterface() {
		Text text1 = new Text(40,40,100,100,"Interface: tables");
		this.addUIElement(text1);
	}
	
	private TableManager tableManager;

	private ArrayList<UIElement> elements = new ArrayList<UIElement>();
	
	/**
	 * This method returns the elements of the current canvaswindow.UI.
	 */
	public ArrayList<UIElement> getElements(){
		return this.elements;
	}
	
	/**
	 * @param e: The UIElement to be added to the current canvaswindow.UI.
	 * This method adds an element to the current canvaswindow.UI.
	 */
	public void addUIElement(UIElement e){
		this.elements.add(e);
	}
	
	
	/**
	 * @param e: The UIElement to be removed from the current canvaswindow.UI.
	 * This method removes a UIElement from the current canvaswindow.UI. If the given UIElement is not part of the canvaswindow.UI, nothing happens.
	 */
	public void removeUIElement(UIElement e){
		this.elements.remove(e);
	}
	
	/**
	 * This method paints the current canvaswindow.UI.
	 */
	public void paint(Graphics g){
		for (UIElement e : getElements()){
			e.paint(g);
		}
	}
	
	

	/**
	 * Returns the most specific UIElement that is an element of this canvaswindow.UI at position (x,y)
	 * @param x		X Coordinate
	 * @param y		Y Coordinate
	 * @return		UIElement e
	 * 					| e.getX() = x
	 * 					| e.getY() = y
	 * 					| getElements().contains(e)
	 */
	public UIElement locatedAt(int x, int y) {
		UIElement found = null;
		for (UIElement e : getElements()) {
			found = e.locatedAt(x,y);
			if (found != null) return found;
		}
		return null;
	}
}
