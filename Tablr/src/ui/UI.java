package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import domain.Column;
import domain.Table;
import domain.Type;
import facades.Tablr;
import facades.WindowManager;
import uielements.ListView;
import uielements.Text;
import uielements.UIElement;
import uielements.UIRow;
import uielements.UITable;


public class UI {
	
	public UI(int x, int y, int width, int height) {
		if (x<0 || y < 0 || width < 0 || height < 0) 
			throw new IllegalArgumentException("Illegal parameters in UI constructor");
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	private int x;
	private int y;
	private int height;
	private int width;
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public int getHeight(){
		return height;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void paintUI(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(getX(),getY(), getWidth(), getHeight());
		for(UIElement e : getElements()) {
			e.paint(g);
		}
		
	}
	
	/**
//	 * Loads the Tables interface: the interface in which the user can add or delete tables
//	 */
//	public void loadTablesInterface() {
//		setMode(Loadable_Interfaces.TABLES);
//		this.clear();
//		ListView l = new ListView(10, 10, 560, 570, new ArrayList<UIElement>());
//		this.addUIElement(l);
//
//		//Button createTableButton = new Button(10,520,580,70, "Create table");		
//		l.addSingleClickListener(() -> {
//			for (UIElement e : getElements()){
//				if (e.isSelected()) e.setNotSelected();
//			}
//		});		
//		
//		
//		l.addDoubleClickListener(() -> {
//			for (UIElement e : getElements()){
//				if (e.getError()) return;
//			}
//			communicationManager.addEmptyTable();
//			l.loadFromTables(communicationManager.getTables());
//		});
//
//		l.loadFromTables(communicationManager.getTables());
//
//	}

//	/**
//	 * Loads the TableRows interface: the interface in which the user can edit fields of the rows of a table
//	 * (a standard view of a table)
//	 */
//	public void loadTableRowsInterface() {
//		setMode(Loadable_Interfaces.TABLE_ROWS);
//		this.clear();
//
//		//Temporary table
////		Table table = communicationManager.addEmptyTable();
////		communicationManager.addEmptyColumn(table,Type.BOOLEAN,true);
////		communicationManager.addEmptyColumn(table,Type.INTEGER,0);
////		communicationManager.addEmptyColumn(table,Type.STRING,"");
////		communicationManager.addEmptyColumn(table,Type.EMAIL,"");
////		communicationManager.addRow(table);
////		communicationManager.addRow(table);
////		communicationManager.addRow(table);
//
//		Table table = communicationManager.getActiveTable();
//
//		
//		//Properties of the UITable
//		int x = 10;
//		int y = 30;
//		int width = 560;
//		int height = 520;
//		int cellHeight = 30;
//		
//		//Creating the legend of the UITable
//		ArrayList<UIElement> legend = new ArrayList<UIElement>();
//		ArrayList<String> names = communicationManager.getColumnNames(table);
//		int legendX = x+20;
//		int cellWidth = (width-20) / (names.size());
//		for(String name:names){
//			System.out.println(name);
//			legend.add(new Text(legendX,y,cellWidth, cellHeight,name));
//			legendX += cellWidth;
//		}
//		
//		UIRow legendRow = new UIRow(x+20,y,width-20,30,legend);
//	
//		//Creating the UITable
//		UITable t = new UITable(x, y, width, height,legendRow, new ArrayList<UIRow>());
//		this.addUIElement(t);
//		
//		//Filling the UITable with the cells of the table.
//		t.loadTable(table,cellWidth, cellHeight);
//		
//		t.addKeyboardListener(27,() ->{ //ESCAPE, go to TABLES interface
//			communicationManager.changeTitle("Tables Mode");
//			communicationManager.loadUI(Loadable_Interfaces.TABLES);
//		});
//		
//		t.addKeyboardListener(17, () -> {
//			communicationManager.loadUI(Loadable_Interfaces.TABLE_DESIGN);
//		});
//		
//		t.addDoubleClickListener(() -> {
//			System.out.println("adding row");
//			communicationManager.addRow(table);
//			t.loadTable(table, cellWidth, cellHeight);
//		});
//
//
//	}

//	/**
//	 * Loads the TableDesign interface: the interface in which the user can edit the variables of the columns of a table.
//	 */
//	public void loadTableDesignInterface() {
//		setMode(Loadable_Interfaces.TABLE_DESIGN);
//		this.clear();
//
//		// Creating title
//		int margin = 20;
//		ListView l = new ListView(10, 30, 560, 520, new ArrayList<UIElement>());
//		Text name = new Text(10+margin,10,200, 20,"Name");
//		Text type = new Text(210+margin,10,150, 20,"Type");
//		Text blanks_al = new Text(360+margin,10,50, 20,"Blanks_al");
//		Text def = new Text(410,10,200-margin, 20,"Default");
//		
//		this.addAllUIElements(new ArrayList<UIElement>()
//			{{
//				add(l);
//				add(name);
//				add(type);
//				add(blanks_al);
//				add(def);
//			}}
//		);
//
//		Table currentTable = communicationManager.getActiveTable();
//		
//		
//		l.loadColumnAttributes(currentTable);
//		
//		l.addSingleClickListener(() -> {
//			for (UIElement e : getElements()){
//				if (e.isSelected()) e.setNotSelected();
//			}
//		});		
//		
//		l.addDoubleClickListener(() -> {
//			for (UIElement e : getElements()){
//				if (e.getError()) return;
//			}
//			communicationManager.addEmptyColumn(currentTable,Type.STRING,"");
//			l.loadColumnAttributes(currentTable);
//		});
//		
//		l.addKeyboardListener(27,() ->{ //ESCAPE, go to TABLES interface
//			System.out.println("[UI.java:171]: " + l.hasElementInError());
//			System.out.println("[UI.java:172]: " + l.hasSelectedElement());
//			if (l.hasElementInError() || l.hasSelectedElement()){
//				System.out.println("[UI.java:174]: Element in error or element selected in listvieuw");
//				return;
//			}
//			else{
//				communicationManager.changeTitle("Tables Mode");
//				communicationManager.loadUI(Loadable_Interfaces.TABLES);
//			}
//		});
//		
//				
//	}

	
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
		e.setTablr(getTablr());
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
		//TODO: add titlebar, close button, ...
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
			else e.setNotSelected();
		}
		return null;
	}
	
	/**
	 * the Tablr object that handles interactions with the Domain
	 */
	protected Tablr tablr;
	
	/**
	 * @return the Tablr object
	 */
	public Tablr getTablr() {
		return this.tablr;
	}
	
	/**
	 * set c as the new Tablr, and do so for all UIElements as well
	 * @param c
	 */
	public void setTablr(Tablr c) {
		this.tablr = c;
	}
	
	/**
	 * the WindowManager used to interact between UI's
	 */
	private WindowManager windowManager;
	
	/**
	 * @return the WindowManager object
	 */
	public WindowManager getWindowManager() {
		return this.windowManager;
	}
	
	/**
	 * set c as the new WindowManager
	 * @param c
	 */
	public void setWindowManager(Tablr c) {
		this.tablr = c;
	}
	
	
	/**
	 * Whether this UI is active. Only active UIs are drawn on the canvas
	 */
	private boolean active;


	/**
	 * Activates the UI, meaning it will be drawn on the canvas
	 */
	public void setActive() {
		active = true;
	}
	
	/**
	 * Deactivates this UI, meaning it will no longer be drawn on the canvas, but its contents will be preserved.
	 */
	public void setInactive() {
		active = false;
	}
	
	/**
	 * Returns if this is active (and thus should be drawn).
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	public boolean containsPoint(int x, int y) {
		return (x >= getX() &&
				y >= getY() &&
				x <= getX()+getWidth() &&
				y <= getY()+getHeight());
	}
}


