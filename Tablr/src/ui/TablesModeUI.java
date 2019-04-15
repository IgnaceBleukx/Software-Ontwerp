package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;

import uielements.BottomUIEdge;
import uielements.Button;
import uielements.CloseButton;
import uielements.RightUIEdge;
import uielements.TopUIEdge;
import uielements.ListView;
import uielements.TextField;
import uielements.Titlebar;
import uielements.UIElement;
import uielements.UIRow;
import uielements.LeftUIEdge;
import uielements.VoidElement;
import domain.Table;
import facades.Tablr;

public class TablesModeUI extends UI {
	
	static int titleHeight = 15;
	static int tableRowHeight = 35;
	static int scrollBarWidth = 10;
	
	public TablesModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
	}
	
	public void loadUI(){
		setActive();
		this.clear();
		
		//Creating background:
		addUIElement(new VoidElement(getX(), getY(), getWidth(), getHeight(), new Color(230,230,230,230)));
		
		//Creating window layout
		Titlebar titleBar = new Titlebar(getX(),getY()+edgeW,getWidth()-30,titleHeight,"Tables Mode");
		CloseButton close = new CloseButton(titleBar.getEndX(),getY()+edgeW,30,titleHeight,4);
		this.addUIElement(close);
		this.addUIElement(titleBar);
		
		LeftUIEdge leftResize = new LeftUIEdge(getX(),getY(),edgeW,getHeight());
		this.addUIElement(leftResize);
		leftResize.addDragListener((newX,newY) ->{
			int delta = newX - leftResize.getGrabPointX();
			this.resizeL(delta);
		});
		RightUIEdge rightResize = new RightUIEdge(getX()+getWidth()-edgeW,getY(),edgeW,getHeight());
		this.addUIElement(rightResize);
		rightResize.addDragListener((newX,newY) ->{
			int delta = newX - rightResize.getGrabPointX();
			this.resizeR(delta);
		});
		BottomUIEdge bottomResize = new BottomUIEdge(getX(),getY()+getHeight()-edgeW,getWidth(),edgeW);
		this.addUIElement(bottomResize);
		bottomResize.addDragListener((newX,newY) -> {
			int delta = newY - bottomResize.getGrabPointY();
			this.resizeB(delta);
		});
		TopUIEdge topResize =  new TopUIEdge(getX(),getY(),getWidth(),edgeW);
		this.addUIElement(topResize);
		topResize.addDragListener((newX,newY) -> {
			int delta = newY - topResize.getGrabPointY();
			this.resizeT(delta);
		});
		
		//Adding listeners:
		titleBar.addDragListener((newX,newY) -> { 
			if (!titleBar.getDragging()) return;
			int deltaX = newX - titleBar.getGrabPointX();
			int deltaY = newY - titleBar.getGrabPointY();
			this.move(deltaX, deltaY);
			getWindowManager().selectUI(this);
		});
		close.addSingleClickListener(() -> {
			setInactive();
			//getWindowManager().selectUI(null);
			getWindowManager().selectNewUI();
		});		
		
		ListView list = loadFromTables();
		addUIElement(list);
		
		//Reload listview when domain changed
		tablr.addDomainChangedListener(() -> {
			//Remove the old listview
			Optional<UIElement> l = getElements().stream().filter(e -> e instanceof ListView).findFirst();
			this.getElements().remove(l.orElseThrow(() -> new RuntimeException("No listview to bind listener to.")));
			
			//Load new ListView from tables
			addUIElement(loadFromTables());
		});
	}
	
	private ListView loadFromTables() {
		ArrayList<UIElement> rows = new ArrayList<>();
		System.out.println("[TableModeUI.java:78] : Dimensions of UI: X=" + getX() + " Y= " + getY() + " W=" + getWidth() + " H=" + getHeight());
		ListView list = new ListView(getX()+edgeW, getY()+edgeW+titleHeight, getWidth()-2*edgeW, getHeight()-2*edgeW-titleHeight, rows);

		ArrayList<Table> tables = this.getTablr().getTables();
		int y = list.getY();
		for (int i=0;i<tables.size();i++) { 
			Table curr = tables.get(i);
			UIRow currRow = new UIRow(list.getX(), y, getWidth()-scrollBarWidth,tableRowHeight, new ArrayList<UIElement>());
			list.addElement(currRow);

			int buttonSize = currRow.getHeight();
			Button deleteButton = new Button(list.getX(), y,buttonSize,buttonSize,"");
			currRow.addElement(deleteButton);
			
			//Listener to select rows
			deleteButton.addSingleClickListener(() -> {
				for (UIElement e : getElements())
					if (e.getError()) return;
				if(currRow.isSelected()) currRow.deselect();
				else currRow.select();
			});
			
			//Listener to remove row
			deleteButton.addKeyboardListener(127, () -> {
				if (currRow.isSelected() && list.getError() == false) {
					list.removeElement((UIElement) currRow); //Remove row from ListView
					tablr.removeTable(curr); //Remove table from list of tables
					list.setSelectedElement(null);
				}
			});
			
			
			TextField tableNameLabel = new TextField(deleteButton.getEndX(), y, getWidth()-buttonSize-scrollBarWidth, tableRowHeight, curr.getName());
			currRow.addElement(tableNameLabel);
			//Table name textfields listen to alphanumeric keyboard input
			tableNameLabel.addKeyboardListener(-1, () -> {
				tablr.renameTable(curr, tableNameLabel.getText());
				ArrayList<Table> tablesSameName = tablr.getTablesByName(curr.getName());
			
				if ((tablesSameName.size() > 1 && tableNameLabel.isSelected()) || tableNameLabel.getText().length() == 0) {
					tableNameLabel.isError();
					//c.getSelectionLock(tableNameLabel);
				}
				else if (tableNameLabel.getError() == true){
					tableNameLabel.isNotError();
					//c.releaseSelectionLock(tableNameLabel);
				}
			});
			
			tableNameLabel.addKeyboardListener(10,() -> {
				if (list.getError()) return;
				tablr.domainChanged();
			});

			//Table name textfields listen to double click events to switch modes
			tableNameLabel.addDoubleClickListener(() -> {
				for (UIElement e : getElements()){
					if (e.getError()) return;
				}
				if (tablr.isEmptyTable(curr)) {
					System.out.println("[TablesModeUI.java:125]: Opening an empty table -> DesignMode");
				//	tablr.changeTitle("Table Design Mode: "+curr.getName());
					this.getWindowManager().loadTableDesignModeUI(curr);
				}
				else {
					System.out.println("[TablesModeUI.java:130]: Opening a table rows mode");
					this.getWindowManager().loadTableRowsModeUI(curr);
					tablr.changeTitle("Table Rows Mode: "+curr.getName());

				}
			});
			
			y = currRow.getEndY();
		}	
		
		//Selects a row in the listview
		list.addSingleClickListener(() -> {
			for (UIElement e : list.getElements()){
				if (e.isSelected()) e.setNotSelected();
			}
		});		
		
		//Adding a new table
		list.addDoubleClickListener(() -> {
			for (UIElement e : getElements()){
				if (e.getError()) return;
			}
			tablr.addEmptyTable();
		});
		
		return list;
	}
	
	

}
