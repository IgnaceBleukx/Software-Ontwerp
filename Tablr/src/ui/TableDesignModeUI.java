package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import uielements.Button;
import uielements.Checkbox;
import uielements.CloseButton;
import uielements.Dragger;
import uielements.ListView;
import uielements.ScrollBar;
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
	
	int margin = getWidth() / 15;
	
	private int namePosX;
	private int nameSizeX;
	private int typePosX;
	private int typeSizeX;
	private int blankPosX;
	private int blankSizeX;
	private int defPosX;
	private int defSizeX;
	
	public TableDesignModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
		namePosX = getX() + margin;
		nameSizeX = getWidth()*5/15;
		typePosX = getX() + margin + nameSizeX;
		typeSizeX = getWidth()*3/15;
		blankPosX = getX() + margin + nameSizeX + typeSizeX;
		blankSizeX = getWidth()*2/15;
		defPosX = getX() + margin + nameSizeX + typeSizeX + blankSizeX;
		defSizeX =  getWidth() *4/15 - 10; //10 van scrollbar
	}
	
	
	
	public void loadUI(Table table){
		setActive();
		this.clear();
		loadUIAttributes();
		//int titleHeight = 15;
		int margin = getWidth() / 15;
		int currentHeight = getY()+titleHeight;
		
		Text name = new Text(namePosX, currentHeight, nameSizeX, 15,"Name");
		Dragger nameDragger = new Dragger(namePosX+nameSizeX - 2, currentHeight, 4, 15);
		Text type = new Text(typePosX, currentHeight, typeSizeX, 15,"Type");
		Dragger typeDragger = new Dragger(typePosX+typeSizeX - 2, currentHeight, 4, 15);
		Text blank = new Text(blankPosX, currentHeight, blankSizeX, 15,"Blank");
		Dragger blankDragger = new Dragger(blankPosX+blankSizeX - 2, currentHeight, 4, 15);
		Text def = new Text(defPosX, currentHeight, defSizeX, 15,"Default");
		Dragger defDragger = new Dragger(defPosX+defSizeX - 2, currentHeight, 4, 15);
		
		name.setBorder(true);
		type.setBorder(true);
		blank.setBorder(true);
		def.setBorder(true);
		
		
		this.addAllUIElements(new ArrayList<UIElement>()
			{{
				add(nameDragger);
				add(typeDragger);
				add(blankDragger);
				add(defDragger);
				add(name);
				add(type);
				add(blank);
				add(def);
			}}
		);		

		nameDragger.addDragListener((newX,newY) -> { 
			int delta = newX - nameDragger.getGrabPointX();
			if (nameDragger.getX()+delta>=namePosX){
				nameSizeX+=delta;
				typePosX+=delta;
				blankPosX+=delta;
				defPosX+=delta;
				refresh(table, titleHeight);
				System.out.println("TDMUI: 118: DRAGGING");
			}
		});
		
		typeDragger.addDragListener((newX,newY) -> { 
			int delta = newX - typeDragger.getGrabPointX();
			if (typeDragger.getX()+delta>=typePosX){
				typeSizeX+=delta;
				blankPosX+=delta;
				defPosX+=delta;
				refresh(table, titleHeight);
			}
		});
		
		blankDragger.addDragListener((newX,newY) -> { 
			int delta = newX - blankDragger.getGrabPointX();
			if (blankDragger.getX()+delta>=blankPosX){
				blankSizeX+=delta;
				defPosX+=delta;
				refresh(table, titleHeight);
			}
		});
		
		defDragger.addDragListener((newX,newY) -> { 
			int delta = newX - defDragger.getGrabPointX();
			if (defDragger.getX()+delta>=defPosX){
				defSizeX+=delta;
				refresh(table, titleHeight);
			}
		});
		
		ListView listview = loadColumnAttributes(table, titleHeight);
		addUIElement(listview);
				
		//Reload listview when domain changed
		tablr.addDomainChangedListener(() -> {
			
			updateListView(table, titleHeight);
			
			titleBar.setText("Table Design Mode: " + table.getName());
		});

	}	
	
	private void updateListView(Table table, int titleHeight){
		Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof ListView).findFirst();
		this.getElements().remove(ll.orElseThrow(() -> new RuntimeException("No listview to refresh")));
		
		//Refresh listview
		addUIElement(loadColumnAttributes(table, titleHeight));
	}
	
	private void updateHeaders(int currentHeight){
		Stream<UIElement> ll = getElements().stream().filter(e -> e instanceof Text || e instanceof Dragger);
		this.getElements().remove(ll);
		
		Text name = new Text(namePosX, currentHeight, nameSizeX, 15,"Name");
		Dragger nameDragger = new Dragger(namePosX+nameSizeX - 2, currentHeight, 4, 15);
		Text type = new Text(typePosX, currentHeight, typeSizeX, 15,"Type");
		Dragger typeDragger = new Dragger(typePosX+typeSizeX - 2, currentHeight, 4, 15);
		Text blank = new Text(blankPosX, currentHeight, blankSizeX, 15,"Blank");
		Dragger blankDragger = new Dragger(blankPosX+blankSizeX - 2, currentHeight, 4, 15);
		Text def = new Text(defPosX, currentHeight, defSizeX, 15,"Default");
		Dragger defDragger = new Dragger(defPosX+defSizeX - 2, currentHeight, 4, 15);
		
		this.addAllUIElements(new ArrayList<UIElement>()
				{{
					add(nameDragger);
					add(typeDragger);
					add(blankDragger);
					add(defDragger);
					add(name);
					add(type);
					add(blank);
					add(def);
				}}
			);
	}
	
	private void refresh(Table table, int titleHeight){
		updateListView(table, titleHeight);
		updateHeaders(titleHeight + getY());
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
					c.removeColumn(table, listview.getElements().indexOf(uiRow) - 2); //2 scrollbars waar we geen rekening mee moeten houden
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
