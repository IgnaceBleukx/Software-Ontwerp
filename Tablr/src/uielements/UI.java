package uielements;

import java.awt.Graphics;
import java.util.ArrayList;

import domain.Column;
import domain.Table;
import domain.Type;
import facades.CommunicationManager;
import facades.UIFacade;
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
			case TABLES: loadTablesInterface(); break;
			case TABLE_DESIGN: loadTableDesignInterface(); break;
			case TABLE_ROWS: loadTableRowsInterface(); break;
			case TEST: loadTestInterface(); break;
		}
		
	}

	
	public void loadTestInterface() {				
	
	}
	
	/**
	 * Loads the Tables interface: the interface in which the user can add or delete tables
	 */
	public void loadTablesInterface() {
		this.clear();
		ListView l = new ListView(10, 10, 560, 570, new ArrayList<UIElement>());
		this.addUIElement(l);

		//Button createTableButton = new Button(10,520,580,70, "Create table");

		l.addDoubleClickListener(() -> {
			communicationManager.addEmptyTable();
			l.loadFromTables(communicationManager.getTables());
		});

		l.loadFromTables(communicationManager.getTables());

	}

	/**
	 * Loads the TableRows interface: the interface in which the user can edit fields of the rows of a table
	 * (a standard view of a table)
	 */
	public void loadTableRowsInterface() {
		this.clear();

		//Temporary table
//		Table table = communicationManager.addEmptyTable();
//		communicationManager.addEmptyColumn(table,Type.BOOLEAN,true);
//		communicationManager.addEmptyColumn(table,Type.INTEGER,0);
//		communicationManager.addEmptyColumn(table,Type.STRING,"");
//		communicationManager.addEmptyColumn(table,Type.EMAIL,"");
//		communicationManager.addRow(table);
//		communicationManager.addRow(table);
//		communicationManager.addRow(table);

		Table table = communicationManager.getActiveTable();

		
		//Properties of the UITable
		int x = 10;
		int y = 30;
		int width = 560;
		int height = 520;
		int cellHeight = 30;
		
		//Creating the legend of the UITable
		ArrayList<UIElement> legend = new ArrayList<UIElement>();
		ArrayList<String> names = communicationManager.getColumnNames(table);
		int legendX = x+20;
		int cellWidth = (width-20) / (names.size());
		for(String name:names){
			System.out.println(name);
			legend.add(new Text(legendX,y,cellWidth, cellHeight,name));
			legendX += cellWidth;
		}
		
		UIRow legendRow = new UIRow(x+20,y,width-20,30,legend);
	
		//Creating the UITable
		UITable t = new UITable(x, y, width, height,legendRow, new ArrayList<UIRow>());
		this.addUIElement(t);
		
		//Filling the UITable with the cells of the table.
		t.loadTable(table,cellWidth, cellHeight);
		
		t.addKeyboardListener(27,() ->{ //ESCAPE, go to TABLES interface
			communicationManager.changeTitle("Tables Mode");
			communicationManager.loadUI(Loadable_Interfaces.TABLES);
		});
		
		t.addDoubleClickListener(() -> {
			System.out.println("adding row");
			communicationManager.addRow(table);
			t.loadTable(table, cellWidth, cellHeight);
		});


	}

	/**
	 * Loads the TableDesign interface: the interface in which the user can edit the variables of the columns of a table.
	 */
	public void loadTableDesignInterface() {
		this.clear();

		// Creating title
		int margin = 20;
		ListView l = new ListView(10, 30, 560, 520, new ArrayList<UIElement>());
		Text name = new Text(10+margin,10,200, 20,"Name");
		Text type = new Text(210+margin,10,150, 20,"Type");
		Text blanks_al = new Text(360+margin,10,50, 20,"Blanks_al");
		Text def = new Text(410,10,200-margin, 20,"Default");
		
		this.addAllUIElements(new ArrayList<UIElement>(){{add(l);add(name);add(type);add(blanks_al);add(def);}});
	
		
		//Creating temporary test table
//		communicationManager.addEmptyTable();
//		Table currentTable = communicationManager.getTables().get(0);
//		communicationManager.addEmptyColumn(currentTable,Type.STRING,"");
//		communicationManager.addEmptyColumn(currentTable,Type.BOOLEAN,false);
//		communicationManager.addEmptyColumn(currentTable,Type.INTEGER,0);

		Table currentTable = communicationManager.getActiveTable();
		
		
		l.loadColumnAttributes(currentTable);
		
		l.addDoubleClickListener(() -> {
			communicationManager.addEmptyColumn(currentTable,Type.STRING,"");
			l.loadColumnAttributes(currentTable);
		});
		
		l.addKeyboardListener(27,() ->{ //ESCAPE, go to TABLES interface
			communicationManager.changeTitle("Tables Mode");
			communicationManager.loadUI(Loadable_Interfaces.TABLES);
		});
		
				
	}

	/**
	 * add the elements of a given list to the current UIElements-list in this UI
	 * @param list: the UIElements to be added
	 */
	private void addAllUIElements(ArrayList<UIElement> list) {
		for(UIElement e : list){
			this.addUIElement(e);
		}
		
	}

	
	/**
	 * All of the UIElements that make up this UI
	 */
	private ArrayList<UIElement> elements = new ArrayList<UIElement>();
	
	/**
	 * This method returns the elements of the current canvaswindow.UI.
	 */
	public ArrayList<UIElement> getElements(){
		return this.elements;
	}
	
	/**
	 * This method adds an element to the current canvaswindow.UI.
	 * @param e: The UIElement to be added to the current canvaswindow.UI.
	 */
	public void addUIElement(UIElement e){
		this.elements.add(e);
		e.setCommunicationManager(getCommunicationManager());
	}
	
	
	/**
	 * @param e: The UIElement to be removed from the current canvaswindow.UI.
//	 * This method removes a UIElement from the current canvaswindow.UI. If the given UIElement is not part of the canvaswindow.UI, nothing happens.
	 */
	public void removeUIElement(UIElement e){
		this.elements.remove(e);
		e.setCommunicationManager(null);
	}
	
	/**
	 * remove all UIElements from this UI
	 */
	public void clear() {
		this.elements.clear();
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
	
	/**
	 * the CommunicationManager that handles interactions with the Domain
	 */
	private CommunicationManager communicationManager;
	
	/**
	 * @return the communicationManager
	 */
	public CommunicationManager getCommunicationManager() {
		return this.communicationManager;
	}
	
	/**
	 * set c as the new CommunicationManager, and do so for all UIElements as well
	 * @param c
	 */
	public void setCommunicationManager(CommunicationManager c) {
		this.communicationManager = c;
		for (UIElement e : getElements() ) {
			e.setCommunicationManager(c);
		}
	}
	
	/**
	 * an element that currently locks the UI from selecting other elements
	 */
	public UIElement lockedSelectedElement = null;
	public UIElement hardLockedElement = null;
	
	/**
	 * Select element newElement. 
	 * Behaviour varies depending on whether or not an element is blocking the UI from selecting different elements.
	 * @param newElement: the element that wants to be selected
	 */
	public void selectElement(UIElement newElement) {
		//An element has placed a lock on selecting other elements
		if (lockedSelectedElement != null) { 
			System.out.println("[UI.java:243] cannot select because locked.");
			return;
		}
		
		for (UIElement e : getElements()) {
			//Notify all elements that a new element has been selected.
			e.selectElement(newElement);
		}
	}
}


