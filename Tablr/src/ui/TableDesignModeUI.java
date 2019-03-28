package ui;

import java.util.ArrayList;

import uielements.Checkbox;
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
	
	public void loadUI(Table table){
		
		int margin = 20;
		int y = 30;
		
		ListView listview = new ListView(getX() + margin, y, getHeight() - 100, getWidth() - margin - 20, new ArrayList<UIElement>());
		
		
		Tablr c = getTablr();
		for(Column col : c.getColumns(table)){
			TextField colName = new TextField(10+margin,y,200,50,  c.getColumnName(col));
			Text colType = new Text(210+margin,y,150,50, c.getColumnType(col).toString()); colType.setBorder(true);
			Checkbox colBlankPol = new Checkbox(375+margin,y+15,20,20, c.getBlankingPolicy(col));
			String defaultValue = c.getDefaultString(col);

			ArrayList<UIElement> list;
			if(c.getColumnType(col) == Type.BOOLEAN){
				Checkbox colDefCheck;
				if (defaultValue == "") {
					colDefCheck = new Checkbox(480, y+15,20,20, false);
					colDefCheck.greyOut();
				}
				else colDefCheck = new Checkbox(480, y+15,20,20,Boolean.parseBoolean(defaultValue));
				
				list = new ArrayList<UIElement>(){{ add(colName); add(colType); add(colBlankPol); add(colDefCheck);}};
				
				colDefCheck.addSingleClickListener(() -> {
					c.toggleDefault(col);
					//loadColumnAttributes(table);
				});
			}
			else{
				TextField colDefText = new TextField(410+margin,y,160-margin,50, defaultValue);
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
			
			UIRow uiRow = new UIRow(10,y,560,50,list);
			this.addUIElement(uiRow);
			y += 50;
			
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
