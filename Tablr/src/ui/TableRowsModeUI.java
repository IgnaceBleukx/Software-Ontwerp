package ui;

import java.util.ArrayList;

import uielements.Checkbox;
import uielements.Text;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import domain.Column;
import domain.Type;
import facades.Tablr;

public class TableRowsModeUI extends UI {
	
	public TableRowsModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
	}
	
	public void loadUI(){
		rows.clear();
		int rows = c.getRows(tab).size();
		int y = super.getY()+cellHeigth;
		for(int i=0;i<rows;i++){
			int x = super.getX()+20;
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			for(Column col : c.getColumns(tab)){
				String val = c.getValueString(col,i);
				if(c.getColumnType(col).equals(Type.BOOLEAN)){
					Boolean value = (Boolean) Column.parseValue(Type.BOOLEAN,val);
					Checkbox check = new Checkbox(x + (int)(cellWidth/2) - 10,y+(int)(cellHeigth/2)-10,20,20,value == null ? false : value);
					if (value == null) check.greyOut();
					Text dummy = new Text(x,y,cellWidth,cellHeigth,"");
					dummy.setBorder(true);
					emts.add(check);
					emts.add(dummy);
					int index = i;
					check.addSingleClickListener(() ->{
						c.toggleCellValueBoolean(col, index);
						loadTable(tab,cellWidth, cellHeigth);
					});
				}
				else{				
					TextField field =  new TextField(x,y,cellWidth, cellHeigth,val);
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
			UIRow uiRow = new UIRow(super.getX(),y,super.getWidth(),cellHeigth,emts);
			uiRow.setTablr(getTablr());
			addRow(uiRow);
			y += cellHeigth;
			
			//Adding listeners:
			uiRow.addSingleClickListener(()->{
				this.selected = uiRow; 
			});
			
			uiRow.addKeyboardListener(127, () -> {
				if(uiRow.equals(this.selected)){
					int index = this.rows.indexOf(uiRow);
					c.removeRow(tab,index);
					this.rows.remove(uiRow);
					this.selected = null;
					System.out.println("Amount of rows in table: " + tab.getRows().size());					
					loadTable(tab, cellWidth, cellHeigth);
				}
			});
		}
	}
	
}
