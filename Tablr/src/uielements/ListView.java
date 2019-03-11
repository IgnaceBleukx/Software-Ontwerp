package uielements;

import java.awt.Graphics;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;

import domain.Column;
import domain.InvalidNameException;
import domain.Table;
import domain.Type;
import facades.CommunicationManager;

public class ListView extends UIElement {

	/**
	 * Constructor of the ListView.
	 * @param x: The x position of the left top corner of the ListView.
	 * @param y: The y position of the left top corner of the ListView.
	 * @param elements: The ArrayList of UIElements in the ListView.
	 */
	public ListView(int x, int y,int w, int h, ArrayList<UIElement> elements) {
		super(x, y, w, h);	
		this.elements = elements;
	}

	/**
	 * This method adds an element to the current ListView.
	 * @param e: The element to be added to the ListView.
	 */
	public void addElement(UIElement e){
		this.elements.add(e);
		e.setCommunicationManager(getCommunicationManager());
	}
	
	/**
	 * This method removes an element of the ListVieuw.
	 * @param e: the UIElement to be removed. 
	 */
	public void removeElement(UIElement e){
		this.elements.remove(e);
	}
	
	/**
	 * The elements of the ListView (the rows)
	 */
	ArrayList<UIElement> elements;
	
	/**
	 * the currently selected element of the ListView (optional, sometimes null)
	 */
	private UIElement selectedElement;
	
	/**
	 * Set element e as the currently selected element of the list
	 * @param e: the element to be selected
	 */
	public void setSelectedElement(UIElement e) {
		this.selectedElement = e;
	}
	
	/**
	 * returns the currently selected element
	 * @return the selected element
	 */
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
	
	/**
	 * 
	 * @param tables
	 */
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
				if (getSelectedElement() == currRow && this.getError() == false) {
					removeElement((UIElement) currRow); //Remove row from ListView
					c.removeTable(curr); //Remove table from list of tables
					setSelectedElement(null);
					loadFromTables(c.getTables());
				}
			});
			
			currRow.addElement(deleteButton);
			
			TextField tableNameLabel = new TextField(getX()+40, getY()+2+i*40, 520, 38, curr.getName());
			tableNameLabel.addKeyboardListener(-1, () -> {
				c.renameTable(curr, tableNameLabel.getText());
				ArrayList<Table> tablesSameName = c.getTablesByName(curr.getName());
			
				if ((tablesSameName.size() > 1 && tableNameLabel.isSelected) | tableNameLabel.getText().length() == 0) {
					tableNameLabel.isError();
				}
				else if (tableNameLabel.getError() == true){
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
		for(Column col : c.getColumns(table)){
			TextField colName = new TextField(10+margin,y,200,50,  c.getColumnName(col));
			Text colType = new Text(210+margin,y,150,50, c.getColumnType(col).toString()); colType.setBorder(true);
			Checkbox colBlankPol = new Checkbox(375+margin,y+15,20,20, c.getBlankingPolicy(col));
						
			//TextField colDefText = null;
			ArrayList<UIElement> list;
			if(c.getColumnType(col) == Type.BOOLEAN){
				Object defaultValue = c.getDefault(col);
				Checkbox colDefCheck;
				if (defaultValue == null) {
					colDefCheck = new Checkbox(480, y+15,20,20, false);
					colDefCheck.greyOut();
				}
				else colDefCheck = new Checkbox(480, y+15,20,20,(boolean) c.getDefault(col));
				list = new ArrayList<UIElement>(){{ add(colName); add(colType); add(colBlankPol); add(colDefCheck);}};
				colDefCheck.addSingleClickListener(() -> {
					c.toggleDefault(col);
					loadColumnAttributes(table);
				});
			}
			else{
				TextField colDefText = new TextField(410+margin,y,160-margin,50, c.getDefault(col).toString());
				list = new ArrayList<UIElement>(){{ add(colName); add(colType); add(colBlankPol); add(colDefText);}};
			}
			
			UIRow uiRow = new UIRow(10,y,560,50,list);
			this.addElement(uiRow);
			y += 50;
			
			//Adding listeners
			uiRow.addSingleClickListener(() -> {
				setSelectedElement(uiRow);
			});
			uiRow.addKeyboardListener(127,() -> {
				if(uiRow.equals(getSelectedElement())){
					c.removeColumn(table, elements.indexOf(uiRow));
					setSelectedElement(null);
					loadColumnAttributes(table);
				}
			});
			
			colBlankPol.addSingleClickListener(() -> {
				if (!colBlankPol.getError()){
					try {
						c.toggleBlanks(col);
					} catch (Exception e) {
						colBlankPol.isError();
					}
				}else colBlankPol.isNotError();
			});
			
			colType.addSingleClickListener(() -> {
				try {
					c.toggleColumnType(col);
					colType.isNotError();
				} catch (Exception e) {
					colType.isError();
				}
				loadColumnAttributes(table);				
			});
			
			colName.addKeyboardListener(-1,() -> {
				try{
					if (colName.isSelected){
						c.setColumnName(col, colName.getText());
						if (colName.getError()) colName.isNotError();
					}
				}catch (InvalidNameException e){
					colName.isError();
				}
			});
			
		}
	}
	
	/**
	 * add all of the elements in the given list to this ListView
	 * @param list: the elements to be added
	 */
	private void addAllElements(ArrayList<UIElement> list) {
		for(UIElement e: list){
			this.addElement(e);
		}
		
	}

	@Override
	public void handleSingleClick() {
		c.notifyNewSelected((UIElement) this);
		
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
		if (e==this) 
			setSelected();
		else
			setNotSelected();
		
		for (UIElement el : elements) {
			el.selectElement(e);
		}
	}

	@Override
	public void setCommunicationManager(CommunicationManager c) {
		this.c = c;
		for (UIElement e : elements) {
			e.setCommunicationManager(c);
		}
	}
	
	/**
	 * Overrides the method in UIElement:
	 * A listview is in the Error state if one of its elements are
	 * in the Error state
	 */
	@Override
	public boolean getError() {
		for (UIElement e : elements) {
			if (e.getError())
				return true;
		}
		return false;
	}
	

}
