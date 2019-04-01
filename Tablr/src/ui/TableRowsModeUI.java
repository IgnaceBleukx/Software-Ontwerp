package ui;

import java.util.ArrayList;

import uielements.Button;
import uielements.Checkbox;
import uielements.CloseButton;
import uielements.Text;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import uielements.UITable;
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
		
		Tablr c = getTablr();
		int cellHeight = 15;
		int cellWidth = getWidth()/c.getColumnNames(tab).size();
		
		Button titleBar = new Button(getX(), getY(), getWidth() - 30, cellHeight, "Table Rows Mode");
		CloseButton close = new CloseButton(getX() + getWidth() - 30, getY(), 30, cellHeight, 4);
		this.addUIElement(close);
		this.addUIElement(titleBar);
		
		
		//Creating legend with all column names:
		UIRow legend = new UIRow(getX(), getY() + cellHeight, getWidth(), 30, new ArrayList<UIElement>());
		
		int a = 0;
		for(String name: c.getColumnNames(tab)) {
			legend.addElement(new TextField(getX()+a*getWidth()/cellWidth, getY(), cellWidth, 20, name));
		}
		
		
		UITable uiTable = new UITable(getX(), getY(), getWidth(), getHeight(), legend, new ArrayList<UIRow>());
		
		
		
		int rows = c.getRows(tab).size();
		int y = super.getY()+cellHeight;
		for(int i=0;i<rows;i++){
			int x = super.getX()+20;
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			for(Column col : c.getColumns(tab)){
				String val = c.getValueString(col,i);
				if(c.getColumnType(col).equals(Type.BOOLEAN)){
					Boolean value = (Boolean) Column.parseValue(Type.BOOLEAN,val);
					Checkbox check = new Checkbox(x + (int)(cellWidth/2) - 10,y+(int)(cellHeight/2)-10,20,20,value == null ? false : value);
					if (value == null) check.greyOut();
					Text dummy = new Text(x,y,cellWidth,cellHeight,"");
					dummy.setBorder(true);
					emts.add(check);
					emts.add(dummy);
					int index = i;
					check.addSingleClickListener(() ->{
						c.toggleCellValueBoolean(col, index);
						//loadTable(tab,cellWidth, cellHeight);
					});
				}
				else{				
					TextField field =  new TextField(x,y,cellWidth, cellHeight,val);
					emts.add(field);
					int index = i;
					field.addKeyboardListener(-1, () -> {
						try{
							if (field.getText().length() == 0)	c.changeCellValue(col, index, "");
							else c.changeCellValue(col,index,field.getText());
							if(field.getError()) field.isNotError();
						}catch(ClassCastException e){
							field.isError();
						}
					});
				}
				x += cellWidth;
				
				
			}
			UIRow uiRow = new UIRow(super.getX(),y,super.getWidth(),cellHeight,emts);
			uiRow.setTablr(getTablr());
			uiTable.addRow(uiRow);
			y += cellHeight;
			
			//Adding listeners:
			uiRow.addSingleClickListener(()->{
				uiTable.selectElement(uiRow); 
			});
			
			uiRow.addKeyboardListener(127, () -> {
				if(uiRow.equals(uiTable.getSelected())){
					int index = uiTable.getRows().indexOf(uiRow);
					c.removeRow(tab,index);
					uiTable.removeRow(uiRow);
					uiTable.selectElement(null);
					System.out.println("Amount of rows in table: " + tab.getRows().size());					
					//loadTable(tab, cellWidth, cellHeight);
				}
			});
		}
		
		//Adding listeners:
		titleBar.addDragListener((x,yy) -> { 
			this.setX(x);
			this.setY(yy);
		});
		close.addSingleClickListener(() -> {
			this.setInactive();
		});		
			
	}
	
}
