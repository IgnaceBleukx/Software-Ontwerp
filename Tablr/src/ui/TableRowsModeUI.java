package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Utils.BooleanCaster;
import uielements.Checkbox;
import uielements.Dragger;
import uielements.Text;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import uielements.UITable;
import uielements.VoidElement;
import domain.Column;
import domain.StoredTable;
import domain.Table;
import domain.Type;
import facades.Tablr;

public class TableRowsModeUI extends UI {
	
	ArrayList<Integer> cellWidth = new ArrayList<>();
	private static int standardCellWidth = 100;
	
	/**
	 * Creates a new TableRowsModeUI
	 * @param x		X position of this UI
	 * @param y		Y position of this UI
	 * @param w		Width of this UI
	 * @param h		Height of this UI
	 * @param t		Reference to Tablr, to modify the domain
	 */
	public TableRowsModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
		
		this.columnResizeListeners.add((delta,index) -> {
			getUITable().getLegend().resizeElementR(delta, index*2);
			getUITable().getRows().stream().forEach(r -> r.resizeElementR(delta,index));
		});
		
	}
	
	/**
	 * Loads all elements into the UI and activates it.
	 * This involves loading all cells from a given table.
	 * Also initializes all actions happening in response to mouse clicks, drags, ...
	 * @param table		Table from which to load cells
	 */
	public void loadUI(Table table){
		setActive();
		UIRow legend = null;
		if(getUITable() != null)
			legend = getUITable().getLegend();
		this.clear();
		titleBar.setText("Table Rows mode: "+table.getName());
		loadUIAttributes();
		
		if (cellWidth.size() == 0) {
			for (String s : table.getColumnNames()) {
				cellWidth.add(standardCellWidth);
			}
		}
		
		
		if (legend == null)
			legend = loadLegend(table,cellWidth);
		
		ArrayList<UIElement> legendElementsCopy = legend.getElements();
		legendElementsCopy.sort((UIElement e1, UIElement e2) -> e1.getX()-e2.getX());
		for(Dragger drag: legendElementsCopy.stream().filter(e -> e instanceof Dragger).map(e -> (Dragger) e).collect(Collectors.toList())) {
			Text el = (Text) legendElementsCopy.get(legendElementsCopy.indexOf(drag)-1);
			drag.addDragListener((newX,newY) ->{
				int delta = newX - drag.getGrabPointX();
				int deltaFinal = delta;
				if (el.getWidth() + delta < minimumColumnWidth)
					deltaFinal = minimumColumnWidth - el.getWidth();
				UITable uitable = null;
				for (UIElement e : elements){
					if (e instanceof UITable) uitable = (UITable)e;				
				}
				UIRow uiLegend = uitable.getLegend();
				uiLegend.getElements().sort((UIElement e1,UIElement e2) -> e1.getX() - e2.getX());
				ArrayList<String> legendNames = new ArrayList<String>(uiLegend.getElements().stream().filter(e -> !(e instanceof Dragger)).map(e -> ((Text) e).getText()).collect(Collectors.toList()));
				int indexCurrent = legendNames.indexOf(el.getText());
				getWindowManager().notifyTableRowsModeUIsColResized(deltaFinal, indexCurrent, table);
			});
		}
		UITable uiTable = loadTable(table,legend);
		this.addUIElement(uiTable);
		

		
		//Adding domainchangedListener
		UIRow finalLegend = legend;
		tablr.addDomainChangedListener(() ->{
			//Remove the old uiTable
			Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof UITable).findFirst();
			this.getElements().remove(ll.orElseThrow(() -> new RuntimeException("No UITable to bind listener to.")));
			
			//Updating legend:
			ArrayList<String> columnNames = getTablr().getColumnNames(table);
			//Column added
			
			if (finalLegend.getElements().stream().filter(e -> !(e instanceof Dragger)).count() < columnNames.size()){
				Text text = new Text(finalLegend.getEndX()-2,finalLegend.getY(),standardCellWidth,20,columnNames.get(columnNames.size()-1));
				Dragger drag = new Dragger(text.getEndX()-2,finalLegend.getY(),4,20);
				finalLegend.addElement(text);
				finalLegend.addElement(drag);
				drag.addDragListener((newX, newY) ->{
					int delta = newX - drag.getGrabPointX();
					int deltaFinal = delta;
					if (text.getWidth()+delta < minimumColumnWidth)
						deltaFinal = minimumColumnWidth - text.getWidth();
					List<UIElement> legendPure = finalLegend.getElements().stream().filter(e -> !(e instanceof Dragger)).collect(Collectors.toList());
					getWindowManager().notifyTableRowsModeUIsColResized(deltaFinal,legendPure.indexOf(text), table);
				});
			}
			//Column deleted
			else if (finalLegend.getElements().stream().filter(e -> !(e instanceof Dragger)).count() > columnNames.size()){
				ArrayList<String> legendNames = new ArrayList<String>(finalLegend.getElements().stream().filter(e -> !(e instanceof Dragger)).map(e -> ((Text) e).getText()).collect(Collectors.toList()));
				legendNames.removeAll(columnNames);
				String removed = legendNames.get(0);
				finalLegend.getElements().sort((UIElement e1,UIElement e2) -> e1.getX() - e2.getX());
				for (UIElement e : finalLegend.getElements()){
					if (e instanceof Text && ((Text) e).getText().equals(removed)){
						int index = finalLegend.getElements().indexOf(e);
						finalLegend.removeElementAt(index);
						finalLegend.removeElementAt(index);
					}
				}
				System.out.println("[TableRowsMode.java:106]: " + finalLegend.getElements());
			}
			//Update columnnames
			else {
				for (int i=0;i<columnNames.size();i++) {
					((Text) finalLegend.getElements().get(2*i)).setText(columnNames.get(i));
				}
			}
			finalLegend.setWidth(finalLegend.getElements().stream().filter(e -> !(e instanceof Dragger)).mapToInt(e -> e.getWidth()).sum()+2);

			addUIElement(loadTable(table, finalLegend));
			titleBar.setText("Table Rows Mode: " + table.getName());
		});
		
		titleBar.addKeyboardListener(10, () -> { //Ctrl+Enter, create new Table Design subwindow.
			if (this.getWindowManager().recentCtrl()) {
				//
			}
		});
		
		
	}
	
	private UIRow loadLegend(Table table,ArrayList<Integer> cellWidth){
		UIRow legend = new UIRow(getX()+edgeW+margin,titleBar.getEndY(),0, 20, new ArrayList<UIElement>());		
		int a = 0;
		for(String name: getTablr().getColumnNames(table)) {
			
			int textX = 0;
			for (int count = 0; count < a; count++ ) {
				textX+=cellWidth.get(count);
			}
			
			Text el = new Text(legend.getX()+textX,titleBar.getEndY(), cellWidth.get(a), 20, name);
			Dragger drag = new Dragger(el.getEndX()-2,el.getY(),4,20);
			legend.addElement(el);
			legend.addElement(drag);
			a++;
		}
		legend.setWidth(legend.getElements().stream().filter(e -> !(e instanceof Dragger)).mapToInt(e -> e.getWidth()).sum()+2);
		return legend;
	}
	
	private static int margin = 20;
	
	private UITable getUITable(){
		Optional<UIElement> t = elements.stream().filter(e -> e instanceof UITable).findFirst();
		return (UITable) t.orElse(null);
	}
	
	/**
	 * Returns a UITable containing UICells for each cell of a given domain Table, and a legend containing the column names.
	 * @param tab			Table
	 * @param titleHeight	Height of the text in the legend
	 * @param cellHeight	Height of the UICells
	 * @param cellWidth		Width of the UICells
	 */
	private UITable loadTable(Table tab, UIRow legend){
		int cellHeight = 35;
			
		UITable uiTable = new UITable(getX()+edgeW, titleBar.getEndY(),getWidth()-2*edgeW, getHeight()-2*edgeW-titleBar.getHeight(), legend, new ArrayList<UIRow>());
		
		//Extracting data from the table
		int numberOfRows = getTablr().getRows(tab);
		int y = legend.getEndY();
		int [] widths = legend.getElements().stream().mapToInt(e -> e.getWidth()).toArray();
		for(int i=0;i<numberOfRows;i++){
			int x = uiTable.getX()+margin;
			int a = 0; 
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			for(Column col : getTablr().getColumns(tab)){
				String val = getTablr().getValueString(col,i);
				if(getTablr().getColumnType(col).equals(Type.BOOLEAN)){
					Checkbox booleanValue;
					booleanValue = new Checkbox(x + widths[a]/2 - 10,y+cellHeight/2-10,20,20, BooleanCaster.cast(tablr.getValueString(col,i)));

					emts.add(new VoidElement(x,y,widths[a], cellHeight, Color.white));
					emts.add(booleanValue);
					
					int index = i;
					booleanValue.addSingleClickListener(() ->
						getTablr().toggleCellValueBoolean(col, index)
					);
				}
				else{				
					TextField field =  new TextField(x,y,widths[a], cellHeight,val);
					emts.add(field);
					int index = i;
					field.addKeyboardListener(-1, () -> {
						try{
							getTablr().changeCellValue(col,index,field.getText());
							if(field.getError()) 
								field.isNotError();
						}catch(ClassCastException e){
							field.isError();
						}
					});
					
					field.addKeyboardListener(10,() ->{
						if (!field.getError() && field.isSelected())
							getTablr().domainChanged();
					});

					field.addDeselectionListener(() -> {
						tablr.domainChanged();
					});
				}
				x += widths[a];
				a += 2;
			}
			UIRow uiRow = new UIRow(uiTable.getX(),y,emts.stream().mapToInt(e -> e.getWidth()).sum()+margin,cellHeight,emts);
			uiTable.addRow(uiRow);
			y += cellHeight;
			
			//Adding listeners:
			uiRow.addSingleClickListener(()->{
				for (UIRow r : uiTable.getRows()) {
					if (r.getError() || (r.isSelected() && !r.equals(uiRow))) return;
				}
				if(uiRow.isSelected()) uiRow.deselect();
				else uiRow.select();
			});
			
			uiRow.addKeyboardListener(127, () -> {
				if(uiRow.isSelected() && tab.isStoredTable()){
					int index = uiTable.getRows().indexOf(uiRow);
					getTablr().removeRow((StoredTable)tab,index);
				}
			});
		}		
		uiTable.addKeyboardListener(17, () -> {
			if (tab.isStoredTable())
				getTablr().loadTableDesignModeUI((StoredTable)tab);
		});
		
		uiTable.addDoubleClickListener(() -> {
			if (tab.isStoredTable())
				getTablr().addRow((StoredTable)tab);
		});
		
		return uiTable;
	}
	
	@Override
	public TableRowsModeUI clone(){
		TableRowsModeUI clone = new TableRowsModeUI(getX(),getY(),getWidth(),getHeight(),getTablr());
		ArrayList<UIElement> clonedElements = new ArrayList<>(elements.stream().map(e -> e.clone()).collect(Collectors.toList()));
		clone.elements = clonedElements;
		return clone;
	}
	
}
