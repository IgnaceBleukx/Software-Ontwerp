package uielements;

import java.awt.Graphics;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;

import domain.Column;
import domain.Table;
import facades.CommunicationManager;

public class ListView extends UIElement {

	/*
	 * Constructor of the ListView.
	 * @param x: The x position of the left top corner of the ListView.
	 * @param y: The y position of the left top corner of the ListView.
	 * @param elements: The ArrayList of UIElements in the ListView.
	 */
	public ListView(int x, int y,int w, int h, ArrayList<UIElement> elements) {
		super(x, y, w, h);	
		this.elements = elements;
	}

	/*
	 * This method adds an element to the current ListView.
	 * @param e: The element to be added to the ListVieuw.
	 */
	public void addElement(UIElement e){
		this.elements.add(e);
		e.setCommunicationManager(getCommunicationManager());
	}
	
	/*
	 * This method removes an element of the ListVieuw.
	 * @param e: the UIElement to be removed. 
	 */
	public void removeElement(UIElement e){
		this.elements.remove(e);
	}
	
	
	ArrayList<UIElement> elements;
	
	private UIElement selectedElement;
	
	public void setSelectedElement(UIElement e) {
		this.selectedElement = e;
	}
	
	public UIElement getSelectedElement() {
		return this.selectedElement;
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawRect(getX(),getY(),getWidth(),getHeight());
		
		if (elements != null) {
			for (UIElement e : elements) {
				e.paint(g);
			}
		}
		
		if (selectedElement != null) {
			UIElement s = this.selectedElement;
			g.fillOval(s.getX()+s.getWidth()+10, s.getY()+s.getHeight()/2, 8, 8);
		}
	}
	

	/**
	 * Returns the most specific UIElement located at (x,y) by searching in elements
	 * @param x		X Coordinate
	 * @param y		Y Coordinate
	 * @return		UIElement a at position (x,y)
	 * @note		If possible and correct, UIElements inside containers will be returned
	 */
	@Override
	public UIElement locatedAt(int x, int y) {
		if (!containsPoint(x,y)) return null;
		
		UIElement found = null;
		
		if (elements != null) {
			for (UIElement e : elements) {
				found = e.locatedAt(x,y);
				if (found != null)
					return found;
			}
		}
		
		return this;
		 
		//If no elements inside the list match, return this.
		
	}
	
	public void loadFromTables(ArrayList<Table> tables) {
		
		elements.clear();
		for (int i=0;i<tables.size();i++) { 
			Table curr = tables.get(i);
			UIRow currRow = new UIRow(getX()+1, getY()+1+i*40, getWidth()-2, 40, new ArrayList<UIElement>());
			
			Button deleteButton = new Button(getX()+2, getY()+2+i*40,38,38,"");
			deleteButton.addSingleClickListener(() -> {
				this.setSelectedElement(currRow);
			});
			
			deleteButton.addKeyboardListener(127, () -> {
				if (getSelectedElement() == currRow) {
					removeElement((UIElement) currRow); //Remove row from ListView
					communicationManager.removeTable(curr); //Remove table from list of tables
					loadFromTables(communicationManager.getTables());
				}
			});
			
			currRow.addElement(deleteButton);
			
			TextField tableNameLabel = new TextField(getX()+40, getY()+2+i*40, 520, 38, curr.getName());
			tableNameLabel.addKeyboardListener(-1, () -> {
				communicationManager.renameTable(curr, tableNameLabel.getText());
				ArrayList<Table> tablesSameName = communicationManager.getTablesByName(curr.getName());
			
				if (tablesSameName.size() > 1 && tableNameLabel.isSelected) {
					tableNameLabel.isError();
				}
				else {
					tableNameLabel.isNotError();
				}
			});
			currRow.addElement(tableNameLabel);
			
			addElement(currRow);
		}	
	}
	
	
	public void loadColumnAttributes(Table table){
		int margin = 20;
		int y = 30;
		elements.clear();
		for(Column col : communicationManager.getColumns(table)){
			TextField colName = new TextField(10+margin,y,200,50,  communicationManager.getColumnName(col));
			Text colType = new Text(210+margin,y,150,50, communicationManager.getColumnType(col).toString()); colType.setBorder(true);
			Checkbox colBlankPol = new Checkbox(375+margin,y+15,20,20, communicationManager.getBlankingPolicy(col));
			TextField colDef = new TextField(410+margin,y,160-margin,50, communicationManager.getDefault(col).toString());
						
			ArrayList<UIElement> list = new ArrayList<UIElement>(){{ add(colName); add(colType); add(colBlankPol); add(colDef);}};		

			UIRow uiRow = new UIRow(10,y,560,50,list);
			this.addElement(uiRow);
			y += 50;
			
			//Adding listeners
			uiRow.addSingleClickListener(() -> {
				setSelectedElement(uiRow);
			});
			colBlankPol.addSingleClickListener(() -> {
				communicationManager.toggleBlanks(col);
			});
			colType.addSingleClickListener(() -> {
				communicationManager.toggleColumnType(col);
				loadColumnAttributes(table);
			});
			colDef.addKeyboardListener(-1, () -> {
				System.out.println("Keyboard event for " + colDef.toString());
				if(!Column.isValidValue(communicationManager.getColumnType(col), colDef.getText()) && colDef.isSelected){
					System.out.println("Non valid value for type");
					colDef.isError();
				}else{
					System.out.println("Valid value");
					colDef.isNotError();
				}
			});
			
		}
	}
	
	private void addAllElements(ArrayList<UIElement> list) {
		for(UIElement e: list){
			this.addElement(e);
		}
		
	}

	@Override
	public void handleSingleClick() {
		for (Runnable r: singleClickListeners){
			r.run();
		}
	}

	@Override
	public void handleDoubleClick() {
		for (Runnable r: doubleClickListeners){
			r.run();
		}
	}

	@Override
	public void handleKeyboardEvent(int keyCode, char keyChar) {
//		for(UIElement i: elements){
//			i.handleKeyboardEvent(keyCode, keyChar);
//		}
		
		for (int i=0;i<elements.size();i++) {
			elements.get(i).handleKeyboardEvent(keyCode, keyChar);
		}
		
		if (keyboardListeners.get(keyCode) == null)
			return;
		
		for (Runnable r : keyboardListeners.get(keyCode)) {
			r.run();
		}
		
	}
	
	
	@Override
	public void selectElement(UIElement e) {
		if (e == this) {
			isSelected = true;
		}
		else {
			for (UIElement el : elements) {
				el.selectElement(e);
			}
		}
	}

	@Override
	public void setCommunicationManager(CommunicationManager c) {
		this.communicationManager = c;
		for (UIElement e : elements) {
			e.setCommunicationManager(c);
		}
	}
	

}
