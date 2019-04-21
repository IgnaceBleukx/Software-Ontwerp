package ui;

import java.util.ArrayList;
import java.util.Optional;

import uielements.Checkbox;
import uielements.Dragger;
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
	
	int margin = getWidth() / 15;
	
	public TableDesignModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
	}
	
	public void loadUI(Table table){
		setActive();
		this.clear();
		loadUIAttributes();
		int currentHeight = getY()+titleHeight+edgeW;
				
		int namePosX = getX() + margin + edgeW;
		int nameSizeX = getWidth()*5/15;
		int typePosX = getX() + margin + edgeW + nameSizeX;
		int typeSizeX = getWidth()*3/15;
		int blankPosX = getX() + margin + edgeW + nameSizeX + typeSizeX;
		int blankSizeX = getWidth()*2/15;
		int defPosX = getX() + margin + edgeW + nameSizeX + typeSizeX + blankSizeX;
		int defSizeX = getWidth() *4/15 - edgeW;
		
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
				
		UIRow legend = new UIRow(namePosX,currentHeight,this.getWidth()-2*edgeW-10,15,(new ArrayList<UIElement>()
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
		));		
		this.addUIElement(legend);
		
		ListView list = loadColumnAttributes(table);
		this.addUIElement(list);
		
		nameDragger.addDragListener((newX,newY) -> { 
			int delta = newX - nameDragger.getGrabPointX();
			legend.resizeElementR(delta, 0);
			getListView().getElements().stream().filter(e -> e instanceof UIRow).forEach(r -> ((UIRow) r).resizeElementR(delta, 0));
		});
		
		typeDragger.addDragListener((newX,newY) -> { 
			int delta = newX - typeDragger.getGrabPointX();
			legend.resizeElementR(delta,2);
			getListView().getElements().stream().filter(e -> e instanceof UIRow).forEach(r -> ((UIRow) r).resizeElementR(delta, 1));
		});
		
		blankDragger.addDragListener((newX,newY) -> { 
			int delta = newX - blankDragger.getGrabPointX();
			legend.resizeElementR(delta, 4);
			getListView().getElements().stream().filter(e -> e instanceof UIRow).forEach(r -> ((UIRow) r).resizeElementR(delta, 2));
		});
		
		defDragger.addDragListener((newX,newY) -> { 
			int delta = newX - defDragger.getGrabPointX();
			legend.resizeElementR(delta, 6);
			getListView().getElements().stream().filter(e -> e instanceof UIRow).forEach(r -> ((UIRow) r).resizeElementR(delta, 3));
		});
		
	
		//Reload listview when domain changed
		tablr.addDomainChangedListener(() -> {
			Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof ListView).findFirst();
			getElements().remove(ll.orElseThrow(() -> new RuntimeException("No Listview in UI")));
			this.addUIElement(loadColumnAttributes(table));
			titleBar.setText("Table Design Mode: " + table.getName());
		});
		
		titleBar.addKeyboardListener(17, () -> {
			tablr.loadTableRowsModeUI(table);
		});
		

	}	
	
	private UIRow getLegend(){
		Optional<UIElement> r = getElements().stream().filter(e -> e instanceof UIRow).findFirst();
		UIRow row = (UIRow) r.orElseThrow(() -> new RuntimeException("No UIRow in UI"));
		return row;
	}
	
	private ListView getListView(){
		Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof ListView).findFirst();
		ListView list = (ListView) ll.orElseThrow(() -> new RuntimeException("No listvieuw in UI"));
		return list;
	}
	
	private ListView loadColumnAttributes(Table table) {
		
		int currentHeight = getY() + edgeW+titleHeight+15;
		
		int namePosX = getLegend().getElements().get(0).getX();
		int nameSizeX = getLegend().getElements().get(0).getWidth();
		int typePosX = getLegend().getElements().get(2).getX();
		int typeSizeX = getLegend().getElements().get(2).getWidth();
		int blankPosX = getLegend().getElements().get(4).getX();
		int blankSizeX = getLegend().getElements().get(4).getWidth();
		int defPosX = getLegend().getElements().get(6).getX();
		int defSizeX = getLegend().getElements().get(6).getWidth();
		
		System.out.println(getLegend().getElements());
		
		ListView listview = new ListView(getX()+edgeW, currentHeight, getWidth() - 2*edgeW,	getHeight()-2*edgeW-titleHeight-15,new ArrayList<UIElement>());
		
		Tablr c = getTablr();
		int index = 0; 
			for(Column col : c.getColumns(table)){
			TextField colName = new TextField(namePosX, currentHeight, nameSizeX, 30, c.getColumnName(col));
			Text colType = new Text(typePosX, currentHeight, typeSizeX, 30, c.getColumnType(col).toString()); 
			colType.setBorder(true);
			Checkbox colBlankPol = new Checkbox(blankPosX + blankSizeX/2 - 10, currentHeight + 5, 20, 20, c.getBlankingPolicy(col));
			String defaultValue = c.getDefaultString(col);
			
			ArrayList<UIElement> elmts = new ArrayList<UIElement>(){{ add(colName); add(colType); add(colBlankPol);}};
			if(c.getColumnType(col) == Type.BOOLEAN){				
				Checkbox defaultBoolean = new Checkbox(defPosX + defSizeX/2 - 10, currentHeight + 5,20,20,(Boolean) c.getDefaultValue(col));
				elmts.add(defaultBoolean);
				defaultBoolean.addSingleClickListener(() -> {
					c.toggleDefault(col);
				});
			}
			else{
				TextField colDefText = new TextField(defPosX,currentHeight, defSizeX ,30, defaultValue);
				elmts.add(colDefText);
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
			
			UIRow uiRow = new UIRow(getX() + edgeW ,currentHeight, getWidth() - 2*edgeW - 10, 30,elmts);
			currentHeight += 30;
			listview.addElement(uiRow);			
			
			//Adding listeners
			uiRow.addSingleClickListener(() -> {
				for (UIElement e : getElements()){
					if (e.getError()) return;
				}
				uiRow.select();
			});
			
			int i = index;
			uiRow.addKeyboardListener(127,() -> {
				if(uiRow.isSelected()){
					c.removeColumn(table, i); //2 scrollbars waar we geen rekening mee moeten houden
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
			
			index++;
			
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
