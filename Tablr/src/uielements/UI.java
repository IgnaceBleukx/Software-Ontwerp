package uielements;

import java.awt.Graphics;
import java.util.ArrayList;

import domain.Column;
import domain.Table;
import domain.TableManager;
import domain.Type;
import ui.Loadable_Interfaces;


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
			case TABLES: loadTableDesignInterface(); break;
			case TABLE_DESIGN: loadTableDesignInterface(); break;
			case TABLE_ROWS: loadTableRowsInterface(); break;
			case TEST: loadTestInterface(); break;
		}
	}

	
	private void loadTestInterface() {
		tableManager = new TableManager();
		
		
		Text text1 = new Text(40,40,100,100,"Interface: test");
		ListView l = new ListView(10, 10, 100, 500, new ArrayList<UIElement>());
		Button createTableButton = new Button(10,520,580,70, "Create table");
		
		this.addUIElement(text1);
		this.addUIElement(l);
		this.addUIElement(createTableButton);
		createTableButton.addSingleClickListener(() -> {
			tableManager.addEmptyTable();
			l.loadFromTables(tableManager.getTables());
		});
		

		TextField field = new TextField(200,200,200,50,"textfield");
		this.addUIElement(field);

		
	}

	private void loadTableRowsInterface() {
		Text text1 = new Text(40,40,100,100,"Interface: rows mode");
		this.addUIElement(text1);		
	}

	
	private void loadTableDesignInterface() {
		// Creating title
		ListView l = new ListView(10, 30, 560, 500, new ArrayList<UIElement>());
		Text name = new Text(10,10,200, 20,"Name");
		Text type = new Text(210,10,150, 20,"Type");
		Text blanks_al = new Text(360,10,50, 20,"Blanks_al");
		Text def = new Text(410,10,200, 20,"Default");
		
		this.addUIElement(l);
		this.addUIElement(name);
		this.addUIElement(type);
		this.addUIElement(blanks_al);
		this.addUIElement(def);

		currentTable.addColumn();
		currentTable.addColumn();
		currentTable.addColumn();
		
		currentTable.getColumns().get(1).setColumnType(Type.BOOLEAN);
		currentTable.getColumns().get(2).setColumnType(Type.INTEGER);
		
		l.loadColumnAttributes(currentTable);
				
	}

	Table currentTable = new Table("name");
	
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
//	 * This method removes a UIElement from the current canvaswindow.UI. If the given UIElement is not part of the canvaswindow.UI, nothing happens.
	 */
	public void removeUIElement(UIElement e){
		this.elements.remove(e);
	}
	
	/**
	 * This method paints the current canvaswindow.UI.
	 */
	public void paint(Graphics g){
		for (UIElement e : getElements()){
			System.out.println(e);
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


