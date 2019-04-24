package ui;

import java.awt.Color;
import java.util.ArrayList;
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
import domain.Table;
import domain.Type;
import facades.Tablr;

public class TableRowsModeUI extends UI {
	
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
		this.clear();
		titleBar.setText("Table Rows mode: "+table.getName());
		loadUIAttributes();
		
		int cellWidth = 100;
		
		
		UIRow legend = loadLegend(table,cellWidth);
		
		UITable uiTable = loadTable(table,legend);
		this.addUIElement(uiTable);
		

		
		//Adding domainchangedListener
		tablr.addDomainChangedListener(() ->{
			//Remove the old uiTable
			Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof UITable).findFirst();
			this.getElements().remove(ll.orElseThrow(() -> new RuntimeException("No UITable to bind listener to.")));
			
			//Updating legend:
			ArrayList<String> columnNames = getTablr().getColumnNames(table);
			//Column added
			if (legend.getElements().stream().filter(e -> !(e instanceof Dragger)).count() < columnNames.size()){
				Text text = new Text(legend.getEndX(),legend.getY(),cellWidth,20,columnNames.get(columnNames.size()-1));
				Dragger drag = new Dragger(text.getEndX()-2,legend.getY(),4,20);
				legend.addElement(text);
				legend.addElement(drag);
				drag.addDragListener((newX, newY) ->{
					int delta = newX - drag.getGrabPointX();
					getWindowManager().notifyTableRowsModeUIsColResized(delta, legend.getElements().indexOf(text), table);
				});
				
				legend.setWidth(legend.getElements().stream().mapToInt(e -> e.getWidth()).sum());
			}
			//Column deleted
			else if (legend.getElements().stream().filter(e -> !(e instanceof Dragger)).count() > columnNames.size()){
				ArrayList<String> legendNames = new ArrayList(legend.getElements().stream().filter(e -> !(e instanceof Dragger)).map(e -> ((Text) e).getText()).collect(Collectors.toList()));
				legendNames.removeAll(columnNames);
				String removed = legendNames.get(0);
				for (UIElement e : legend.getElements()){
					if (e instanceof Text && ((Text) e).getText().equals(removed)){
						int index = legend.getElements().indexOf(e);
						legend.removeElementAt(index);
						legend.removeElementAt(index);
					}
				}
			}
			//Update columnnames
			else {
				for (int i=0;i<columnNames.size();i++) {
					((Text) legend.getElements().get(2*i)).setText(columnNames.get(i));
				}
			}
			
			
			addUIElement(loadTable(table, legend));
			titleBar.setText("Table Rows Mode: " + table.getName());
		});
		
		titleBar.addKeyboardListener(10, () -> { //Ctrl+Enter, create new Table Design subwindow.
			if (this.getWindowManager().recentCtrl()) {
				//
			}
		});
		
		
	}
	
	private UIRow loadLegend(Table table,int cellWidth){
		UIRow legend = new UIRow(getX()+edgeW,titleBar.getEndY(),getWidth(), 20, new ArrayList<UIElement>());		
		int a = 0;
		int margin = 20;
		for(String name: getTablr().getColumnNames(table)) {
			Text el = new Text(getX() + margin+a*cellWidth,titleBar.getEndY(), cellWidth, 20, name);
			Dragger drag = new Dragger(el.getEndX()-2,el.getY(),4,20);
			int index = a;
			drag.addDragListener((newX,newY) ->{
				int delta = newX - drag.getGrabPointX();
				getWindowManager().notifyTableRowsModeUIsColResized(delta, index, table);
			});
			legend.addElement(el);
			legend.addElement(drag);
			a++;
		}
		legend.setWidth(legend.getElements().stream().filter(e -> !(e instanceof Dragger)).mapToInt(e -> e.getWidth()).sum());
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
		//Creating legend with all column names:
		int amountOfColumns = getTablr().getColumns(tab).size();
		int cellHeight = 35;
			
		UITable uiTable = new UITable(getX()+edgeW, titleBar.getEndY(),getWidth()-2*edgeW, getHeight()-2*edgeW-titleBar.getHeight(), legend, new ArrayList<UIRow>());
		
		//Extracting data from the table
		int numberOfRows = getTablr().getRows(tab);
		int y = legend.getEndY();
		int [] widths = legend.getElements().stream().mapToInt(e -> e.getWidth()).toArray();
		for(int i=0;i<numberOfRows;i++){
			int x = getX()+margin;
			int a = 0; 
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			for(Column col : getTablr().getColumns(tab)){
				String val = getTablr().getValueString(col,i);
				if(getTablr().getColumnType(col).equals(Type.BOOLEAN)){
					Checkbox booleanValue;
					booleanValue = new Checkbox(x + (int)(widths[a]/2) - 10,y+(int)(cellHeight/2)-10,20,20, BooleanCaster.cast(tablr.getValueString(col,i)));

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
				}
				x += widths[a];
				a += 2;
			}
			UIRow uiRow = new UIRow(uiTable.getX(),y,emts.stream().mapToInt(e -> e.getWidth()).sum(),cellHeight,emts);
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
				if(uiRow.isSelected()){
					int index = uiTable.getRows().indexOf(uiRow);
					getTablr().removeRow(tab,index);
				}
			});
		}		
		uiTable.addKeyboardListener(17, () -> {
			getTablr().loadTableDesignModeUI(tab);;
		});
		
		uiTable.addDoubleClickListener(() -> {
			getTablr().addRow(tab);
		});
		
		return uiTable;
	}
	
	@Override
	public TableRowsModeUI clone(){
		TableRowsModeUI clone = new TableRowsModeUI(getX(),getY(),getWidth(),getHeight(),getTablr());
		ArrayList<UIElement> clonedElements = new ArrayList<UIElement>();
		elements.stream().forEach(e -> clonedElements.add(e.clone()));
		clone.elements = clonedElements;
		return clone;
	}
	
}
