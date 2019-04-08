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
import uielements.Titlebar;
import uielements.UIElement;
import uielements.UIRow;
import uielements.VoidElement;
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
		this.clear();
		
		int titleHeight = 15;
		int currentHeight = getY()+13;
		int margin = getWidth() / 15;
		
		VoidElement background = new VoidElement(getX(), getY(), getWidth(), getHeight(), new Color(230,230,230,230));
		addUIElement(background);
		
		Titlebar titleBar = new Titlebar(getX(), getY(), getWidth() - 30, titleHeight, "Table Design Mode: " + table.getName());
		CloseButton close = new CloseButton(getX() + getWidth() - 30, getY(), 30, titleHeight, 4);
		this.addUIElement(close);
		this.addUIElement(titleBar);
		
		Text name = new Text(getX() + margin, currentHeight, getWidth()*6/15, 20,"Name");
		Text type = new Text(getX() + margin + getWidth()* 6 / 15, currentHeight, getWidth() *3/ 15, 20,"Type");
		Text blanks_al = new Text(getX() + margin + getWidth()* 9 / 15+10, currentHeight, 20, 20,"Blanks_al");
		Text def = new Text(getX() + margin + getWidth()* 11 / 15, currentHeight, getWidth() *4/15, 20,"Default");
		
		this.addAllUIElements(new ArrayList<UIElement>()
			{{
				add(name);
				add(type);
				add(blanks_al);
				add(def);
			}}
		);

		
		//Adding listeners:
		titleBar.addDragListener((x,y) -> { 
			this.setX(x);
			this.setY(y);
		});
		close.addSingleClickListener(() -> {
			this.setInactive();
			getWindowManager().selectUI(null);
		});		
		
		
		
		ListView listview = loadColumnAttributes(table, titleHeight);
		addUIElement(listview);
				
		//Reload listview when domain changed
		tablr.addDomainChangedListener(() -> {
			//Remove the old listview
			Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof ListView).findFirst();
			this.getElements().remove(ll.orElseThrow(() -> new RuntimeException("No listview to bind listener to.")));
			
			//Load new ListView from tables
			addUIElement(loadColumnAttributes(table, titleHeight));
			
			titleBar.setText("Table Design Mode: " + table.getName());
		});

	}	
	
	private ListView loadColumnAttributes(Table table,int titleHeight) {
		
		int margin = getWidth() / 15;
		int currentHeight = getY() + 30;
		
		ListView listview = new ListView(getX(), currentHeight, getWidth(), getHeight()-titleHeight-20, new ArrayList<UIElement>());
		Tablr c = getTablr();
		
		for(Column col : c.getColumns(table)){
			TextField colName = new TextField(getX() + margin, currentHeight, getWidth()*6/15, 30, c.getColumnName(col));
			Text colType = new Text(getX() + getWidth()*6/15 + margin, currentHeight, getWidth()*3/15, 30, c.getColumnType(col).toString()); 
			colType.setBorder(true);
			Checkbox colBlankPol = new Checkbox(getX() +getWidth()*9/15 + margin + 10, currentHeight + 5, 20, 20, c.getBlankingPolicy(col));
			String defaultValue = c.getDefaultString(col);

			ArrayList<UIElement> list = new ArrayList<UIElement>(){{ add(colName); add(colType); add(colBlankPol);}};
			if(c.getColumnType(col) == Type.BOOLEAN){				
				Checkbox defaultBoolean = new Checkbox(getX() + getWidth()*11/15 + margin + 10, currentHeight + 5,20,20,(Boolean) c.getDefaultValue(col));
				list.add(defaultBoolean);
				defaultBoolean.addSingleClickListener(() -> {
					c.toggleDefault(col);
				});
			}
			else{
				TextField colDefText = new TextField(getX() + getWidth()*11/15+margin,currentHeight,getWidth() *4/15 ,30, defaultValue);
				list.add(colDefText);
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
				colDefText.addKeyboardListener(10,() -> {
					getTablr().domainChanged();
				});
			}
			
			UIRow uiRow = new UIRow(getX(),currentHeight,getWidth(),30,list);
			currentHeight += 30;
			listview.addElement(uiRow);			
			
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
				}
			});
			
			colBlankPol.addSingleClickListener(() -> {
				if (!colBlankPol.getError()){
					try {
						c.toggleBlanks(col);
					} catch (Exception e) {
						colBlankPol.isError();
						colBlankPol.setValue(false);
						c.getLock(colBlankPol);
					}
				}else {
					colBlankPol.setValue(true);
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
					}catch(InvalidTypeException e){
						colType.setText(Column.getNextType(Type.valueOf(colType.getText())).toString());
						colType.isError();
						c.getLock(colType);
					}
				}
				
				else{
					try{
						c.toggleColumnType(col);
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
			colName.addKeyboardListener(10,() -> {
				getTablr().domainChanged();
			});
			
		}
		
		listview.addDoubleClickListener(() -> {
			for (UIElement e : getElements()){
				if (e.getError()) return;
			}
			tablr.addEmptyColumn(table, Type.STRING, "");
		});
		
		return listview;
	}
	
	

}
