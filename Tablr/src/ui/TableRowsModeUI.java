package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;

import uielements.Checkbox;
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
	}
	
	/**
	 * Loads all elements into the UI and activates it.
	 * This involves loading all cells from a given table.
	 * Also initializes all actions happening in response to mouse clicks, drags, ...
	 * @param tab		Table from which to load cells
	 */
	public void loadUI(Table tab){
		setActive();
		this.clear();
		loadUIAttributes();
		
		Tablr c = getTablr();
		int cellHeight = 30;
		
		int cellWidth = c.getColumnNames(tab).size() != 0 ? (getWidth()-2*edgeW+20)/c.getColumnNames(tab).size() : 0;
		UITable uiTable = loadTable(tab, titleBar.getHeight(), cellHeight, cellWidth);
		this.addUIElement(uiTable);
		
		//Adding domainchangedListener
		tablr.addDomainChangedListener(() ->{
			//Remove the old uiTable
			Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof UITable).findFirst();
			this.getElements().remove(ll.orElseThrow(() -> new RuntimeException("No UITable to bind listener to.")));
			
			//Load new ListView from tables
			addUIElement(loadTable(tab, titleBar.getHeight(), cellHeight, cellWidth));

			titleBar.setText("Table Design Mode: " + tab.getName());
		});
		
		
	}
	
	/**
	 * Returns a UITable containing UICells for each cell of a given domain Table, and a legend containing the column names.
	 * @param tab			Table
	 * @param titleHeight	Height of the text in the legend
	 * @param cellHeight	Height of the UICells
	 * @param cellWidth		Width of the UICells
	 */
	private UITable loadTable(Table tab, int titleHeight, int cellHeight, int cellWidth){
		//Creating legend with all column names:
		UIRow legend = new UIRow(getX()+edgeW,titleBar.getEndY(), getWidth()-2*edgeW, 30, new ArrayList<UIElement>());
		int margin = 20;
		
		int a = 0;
		for(String name: getTablr().getColumnNames(tab)) {
			legend.addElement(new Text(getX() + margin+a*cellWidth,legend.getY(), cellWidth, 20, name));
			a++;
		}
		UITable uiTable = new UITable(getX()+edgeW, titleBar.getEndY(), getWidth()-2*edgeW, getHeight()-2*edgeW-titleBar.getHeight(), legend, new ArrayList<UIRow>());
		
		//Extracting data from the table
		int numberOfRows = getTablr().getRows(tab);
		int y = legend.getEndY();
		for(int i=0;i<numberOfRows;i++){
			int x = getX()+margin;
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			for(Column col : getTablr().getColumns(tab)){
				String val = getTablr().getValueString(col,i);
				if(getTablr().getColumnType(col).equals(Type.BOOLEAN)){
					Checkbox booleanValue = new Checkbox(x + (int)(cellWidth/2) - 10,y+(int)(cellHeight/2)-10,20,20, (Boolean)getTablr().getValue(col,i));
					emts.add(new VoidElement(x,y,cellWidth, cellHeight, Color.white));
					emts.add(booleanValue);
					
					int index = i;
					booleanValue.addSingleClickListener(() ->
						getTablr().toggleCellValueBoolean(col, index)
					);
				}
				else{				
					TextField field =  new TextField(x,y,cellWidth, cellHeight,val);
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
				x += cellWidth;
			}
			UIRow uiRow = new UIRow(uiTable.getX(),y,uiTable.getWidth(),cellHeight,emts);
			System.out.println("[TableRowsModeUI.java:1]: Adding uirow: " + uiRow);
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
	
}
