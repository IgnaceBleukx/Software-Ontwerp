package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;

import uielements.Button;
import uielements.Checkbox;
import uielements.CloseButton;
import uielements.ListView;
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
	
	public TableRowsModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
	}
	
	public void loadUI(Table tab){
		setActive();
		this.clear();
		
		Tablr c = getTablr();
		int cellHeight = 30;
		int cellWidth = getWidth()/c.getColumnNames(tab).size();
		
		Button titleBar = new Button(getX(), getY(), getWidth() - 30, cellHeight, "Table Rows Mode");
		CloseButton close = new CloseButton(getX() + getWidth() - 30, getY(), 30, cellHeight, 4);
		this.addUIElement(close);
		this.addUIElement(titleBar);
			
		//Adding listeners:
		titleBar.addDragListener((x,yy) -> { 
			this.setX(x);
			this.setY(yy);
		});
		close.addSingleClickListener(() -> {
			this.setInactive();
		});		
					
		
		UITable uiTable = loadTable(tab, titleBar.getHeight(), cellHeight, cellWidth);
		this.addUIElement(uiTable);
		
		//Adding domainchangedListener
		tablr.addDomainChangedListener(() ->{
			//Remove the old uiTable
			Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof UITable).findFirst();
			this.getElements().remove(ll.orElseThrow(() -> new RuntimeException("No UITable to bind listener to.")));
			
			//Load new ListView from tables
			addUIElement(loadTable(tab, titleBar.getHeight(), cellHeight, cellWidth));
		});
		
		
	}
	public UITable loadTable(Table tab, int titleHeight, int cellHeight, int cellWidth){
		//Creating legend with all column names:
		UIRow legend = new UIRow(getX(), getY() + cellHeight, getWidth(), 30, new ArrayList<UIElement>());
		
		int a = 0;
		for(String name: getTablr().getColumnNames(tab)) {
			legend.addElement(new Text(getX() + a*cellWidth, getY() + cellHeight, cellWidth, 20, name));
			a++;
		}
		
		UITable uiTable = new UITable(getX(), getY(), getWidth(), getHeight(), legend, new ArrayList<UIRow>());
		
		int numberOfRows = getTablr().getRows(tab).size();
		System.out.println("nbrows: " + numberOfRows);
		int y = getY()+2*cellHeight;
		for(int i=0;i<numberOfRows;i++){
			int x = getX()+20;
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			for(Column col : getTablr().getColumns(tab)){
				String val = getTablr().getValueString(col,i);
				if(getTablr().getColumnType(col).equals(Type.BOOLEAN)){
					Checkbox booleanValue = new Checkbox(x + (int)(cellWidth/2) - 10,y+(int)(cellHeight/2)-10,20,20,(Boolean) getTablr().getValue(col,i));
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
							if (field.getText().length() == 0)	getTablr().changeCellValue(col, index, "");
							else getTablr().changeCellValue(col,index,field.getText());
							if(field.getError()) field.isNotError();
						}catch(ClassCastException e){
							field.isError();
						}
					});
				}
				x += cellWidth;
				
				
			}
			UIRow uiRow = new UIRow(getX(),y,getWidth(),cellHeight,emts);
			uiRow.setUI(this);
			uiTable.addRow(uiRow);
			y += cellHeight;
			
			//Adding listeners:
			uiRow.addSingleClickListener(()->{
				uiTable.selectElement(uiRow); 
			});
			
			uiRow.addKeyboardListener(127, () -> {
				if(uiRow.equals(uiTable.getSelected())){
					int index = uiTable.getRows().indexOf(uiRow);
					getTablr().removeRow(tab,index);
					uiTable.removeRow(uiRow);
					uiTable.selectElement(null);
					System.out.println("[TableRowsModeUI.java: 105] Amount of rows in table: " + tab.getRows().size());					
					//loadTable(tab, cellWidth, cellHeight);
				}
			});
		}
		
		addUIElement(uiTable);
		
		uiTable.addKeyboardListener(17, () -> {
			getTablr().loadTableDesignModeUI(tab);;
		});
		
		uiTable.addDoubleClickListener(() -> {
			getTablr().addRow(tab);
		});
		
		return uiTable;
	}
	
}
