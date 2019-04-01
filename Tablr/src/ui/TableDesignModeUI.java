package ui;

import java.util.ArrayList;

import uielements.Button;
import uielements.Checkbox;
import uielements.CloseButton;
import uielements.ListView;
import uielements.Text;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import domain.Column;
import domain.Table;
import domain.Type;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;
import facades.Tablr;

public class TableDesignModeUI extends UI {
	
	public TableDesignModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
	}
	
	public void loadUI(Table table){
		
		setActive();
		
		int titleHeight = 15;
		int margin = 20;
		
		Button titleBar = new Button(getX(), getY(), getWidth() - 30, titleHeight, "Table Design Mode");
		CloseButton close = new CloseButton(getX() + getWidth() - 30, getY(), 30, titleHeight, 4);
		this.addUIElement(close);
		this.addUIElement(titleBar);
		
		//Adding listeners:
		titleBar.addDragListener((x,y) -> { 
			this.setX(x);
			this.setY(y);
		});
		close.addSingleClickListener(() -> {
			this.setInactive();
		});		
		
		
		ListView listview = new ListView(getX() + margin, getY() + titleHeight, getHeight(), getWidth() - margin - 20, new ArrayList<UIElement>());
		
		
		Tablr c = getTablr();
		for(Column col : c.getColumns(table)){
			TextField colName = new TextField(getX() + margin, getY() + 30, getWidth() / 2, 50, c.getColumnName(col));
			Text colType = new Text(getX() + 190 +margin, getY() + 30, getWidth() / 4, 50, c.getColumnType(col).toString()); 
			colType.setBorder(true);
			Checkbox colBlankPol = new Checkbox(getX() + 350 + margin,getY() + 30+15,20,20, c.getBlankingPolicy(col));
			String defaultValue = c.getDefaultString(col);

			ArrayList<UIElement> list;
			if(c.getColumnType(col) == Type.BOOLEAN){
				Checkbox colDefCheck;
				if (defaultValue == "") {
					colDefCheck = new Checkbox(480, getY() + 30+15,20,20, false);
					colDefCheck.greyOut();
				}
				else colDefCheck = new Checkbox(480, getY() + 30+15,20,20,Boolean.parseBoolean(defaultValue));
				
				list = new ArrayList<UIElement>(){{ add(colName); add(colType); add(colBlankPol); add(colDefCheck);}};
				
				colDefCheck.addSingleClickListener(() -> {
					c.toggleDefault(col);
					//loadColumnAttributes(table);
				});
			}
			else{
				TextField colDefText = new TextField(410+margin,getY() + 30,getWidth() / 4 - 20,50, defaultValue);
				list = new ArrayList<UIElement>(){{ add(colName); add(colType); add(colBlankPol); add(colDefText);}};
				colDefText.addKeyboardListener(-1,()-> {
					try{
						if (colDefText.getText().length() == 0) {
							c.setDefault(col,"");
						}
						else{
							c.setDefault(col,colDefText.getText());
						}
						if (colDefText.getError()) colDefText.isNotError();
					}catch(ClassCastException e){
						colDefText.isError();
					}
				});
			}
			
			UIRow uiRow = new UIRow(10,getY() + 30,560,50,list);
			this.addUIElement(uiRow);
			
			//Adding listeners
			
			uiRow.addSingleClickListener(() -> {
				for (UIElement e : getElements()){
					if (e.getError()) return;
				}
				listview.setSelectedElement(uiRow);
			});
			
			uiRow.addKeyboardListener(127,() -> {
				if(uiRow.equals(listview.getSelectedElement())){
					c.removeColumn(table, listview.getElements().indexOf(uiRow));
					listview.setSelectedElement(null);
					listview.loadColumnAttributes(table);
				}
			});
			
			colBlankPol.addSingleClickListener(() -> {
				if (!colBlankPol.getError()){
					try {
						c.toggleBlanks(col);
					} catch (Exception e) {
						colBlankPol.isError();
						c.getLock(colBlankPol);
					}
				}else {
					colBlankPol.isNotError();
					c.releaseLock(colBlankPol);
				}
			});
			
			colType.addSingleClickListener(() -> {
				if (colType.getError()){
					System.out.println("[ListVieuw.java:250] : colType is in error");
					try{
						c.setColumnType(col, Column.getNextType(Type.valueOf(colType.getText())));
						colType.isNotError();
						c.releaseLock(colType);
						loadColumnAttributes(table);
					}catch(InvalidTypeException e){
						colType.setText(Column.getNextType(Type.valueOf(colType.getText())).toString());
						colType.isError();
						c.getLock(colType);
					}
				}
				
				else{
					try{
						c.toggleColumnType(col);
						loadColumnAttributes(table);
					}catch (InvalidTypeException e){
						colType.setText(Column.getNextType(c.getColumnType(col)).toString());
						colType.isError();
						c.getLock(colType);
					}
				}
			});
			
			colName.addKeyboardListener(-1,() -> {	
				if (colName.getText().length() == 0) {
					colName.isError(); 
					return;
				}
				try{
					if (colName.isSelected()){
						c.setColumnName(col, colName.getText());
						if (colName.getError()) colName.isNotError();
					}
				}catch (InvalidNameException e){
					colName.isError();
				}
			});
			
		}
	}
	
	private ListView loadColumnAttributes(Table table) {
		return null;
			
	}
	
	

}
