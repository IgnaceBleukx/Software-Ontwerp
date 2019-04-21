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
		namePosX = getX() + margin + edgeW;
		nameSizeX = getWidth()*5/15;
		typePosX = getX() + margin + edgeW + nameSizeX;
		typeSizeX = getWidth()*3/15;
		blankPosX = getX() + margin + edgeW + nameSizeX + typeSizeX;
		blankSizeX = getWidth()*2/15;
		defPosX = getX() + margin + edgeW + nameSizeX + typeSizeX + blankSizeX;
		defSizeX = getWidth() *4/15 - edgeW;
	}
	
	
	
	public void loadUI(Table table){
		setActive();
		this.clear();
		loadUIAttributes();
		int currentHeight = getY()+titleHeight+edgeW;
		
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
			if (nameDragger.getX()+delta>=namePosX+10){
				nameDragger.move(delta, 0);
				nameSizeX+=delta;
				typeDragger.move(delta, 0);
				typePosX+=delta;
				blankDragger.move(delta, 0);
				blankPosX+=delta;
				defDragger.move(delta, 0);
				defPosX+=delta;
				refresh(table, titleHeight);
			}
		});
		
		typeDragger.addDragListener((newX,newY) -> { 
			int delta = newX - typeDragger.getGrabPointX();
			if (typeDragger.getX()+delta>=typePosX+10){
				typeDragger.move(delta, 0);
				typeSizeX+=delta;
				blankDragger.move(delta, 0);
				blankPosX+=delta;
				defDragger.move(delta, 0);
				defPosX+=delta;
				refresh(table, titleHeight);
			}
		});
		
		blankDragger.addDragListener((newX,newY) -> { 
			int delta = newX - blankDragger.getGrabPointX();
			if (blankDragger.getX()+delta>=blankPosX+10){
				blankDragger.move(delta, 0);
				blankSizeX+=delta;
				defDragger.move(delta, 0);
				defPosX+=delta;
				refresh(table, titleHeight);
			}
		});
		
		defDragger.addDragListener((newX,newY) -> { 
			int delta = newX - defDragger.getGrabPointX();
			if (defDragger.getX()+delta>=defPosX+10){
				defDragger.move(delta, 0);
				defSizeX+=delta;
				refresh(table, titleHeight);
			}
		});
		
		ListView listview = loadColumnAttributes(table);
		addUIElement(listview);
				
		//Reload listview when domain changed
		tablr.addDomainChangedListener(() -> {
			updateListView(table, titleHeight);
			titleBar.setText("Table Design Mode: " + table.getName());
		});
		
		titleBar.addKeyboardListener(17, () -> {
			tablr.loadTableRowsModeUI(table);
		});
		

	}	
	
	private void updateListView(Table table, int titleHeight){
		Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof ListView).findFirst();
		this.getElements().remove(ll.orElseThrow(() -> new RuntimeException("No listview to refresh")));
		
		//Refresh listview
		addUIElement(loadColumnAttributes(table));
	}	
	
	private void updateHeaders(int currentHeight){
		ArrayList<UIElement> toDelete = new ArrayList<UIElement>();
		for(UIElement e : getElements()){
			if(e instanceof Text){
				toDelete.add(e);
			}
		}
		
		for(UIElement e : toDelete){
			getElements().remove(e);
		}
		
		Text name = new Text(namePosX, currentHeight, nameSizeX, 15,"Name");
		Text type = new Text(typePosX, currentHeight, typeSizeX, 15,"Type");
		Text blank = new Text(blankPosX, currentHeight, blankSizeX, 15,"Blank");
		Text def = new Text(defPosX, currentHeight, defSizeX, 15,"Default");
		
		name.setBorder(true);
		type.setBorder(true);
		blank.setBorder(true);
		def.setBorder(true);
		
		this.addAllUIElements(new ArrayList<UIElement>()
				{{
					add(name);
					add(type);
					add(blank);
					add(def);
				}}
			);
	}
	
	private void refresh(Table table, int titleHeight){
		updateListView(table, titleHeight);
		updateHeaders(titleHeight + getY() + edgeW);
	}
	
	private ListView loadColumnAttributes(Table table) {
		
		int currentHeight = getY() + 30;
		
		ListView listview = new ListView(getX()+edgeW, 
				getY()+edgeW+currentHeight, 
				getWidth() - 2*edgeW, 
				getHeight()-currentHeight -2*edgeW, 
				new ArrayList<UIElement>());
		
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
			
			UIRow uiRow = new UIRow(getX() + edgeW ,getY()+currentHeight, getWidth() - 2*edgeW - 10, 30,elmts);
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
	
	/**
	 * This method resized the UI and all its element from the right.
	 * @param deltaW 	The amount of pixels the UI must be made larger with from the right.
	 */
	@Override
	public void resizeR(int deltaW) {
		int delta = getWidth() + deltaW < minWidth ? getWidth()-minWidth : deltaW;
		setWidth(getWidth() + delta);
		getElements().stream().filter(e -> !(e instanceof Text || e instanceof Dragger)).forEach(e -> e.resizeR(delta));
	}

	/**
	 * This method resizes the UI and all its elements from the left.
	 * @param deltaW 	The amount of pixels the UI must be made smaller with from the left.
	 */
	@Override
	public void resizeL(int deltaW) {
		int delta = getWidth() - deltaW < minWidth ? getWidth()-minWidth : deltaW;
		setX(getX()+delta);
		setWidth(getWidth() - delta);
		getElements().stream().filter(e -> !(e instanceof Text || e instanceof Dragger)).forEach(e -> e.resizeL(delta));
		getElements().stream().filter(e -> (e instanceof Text || e instanceof Dragger)).forEach(e -> e.move(delta,0));

	}
	
	/**
	 * This method resized the UI and all its elements from the top.
	 * @param deltaH 	The amount of pixels the UI must be made smaller with from the top.
	 */
	@Override
	public void resizeT(int deltaH) {
		int delta = getWidth() - deltaH < minHeight ? getWidth()-minHeight : deltaH;
		setHeight(getHeight() - delta);
		setY(getY()+delta);
		getElements().stream().filter(e -> !(e instanceof Text || e instanceof Dragger)).forEach(e -> e.resizeT(delta));
		getElements().stream().filter(e ->  (e instanceof Text || e instanceof Dragger)).forEach(e -> e.move(0,delta));
	}
	
	/**
	 * This method resizes the UI and all its elements from the bottom.
	 * @param deltaH 	The amount of pixels the UI should be made larger with from the bottom.
	 */
	@Override
	public void resizeB(int deltaH) {
		int delta = getWidth() + deltaH < minHeight ? getWidth()-minHeight : deltaH;
		setHeight(getHeight()+delta);
		getElements().stream().filter(e -> !(e instanceof Text || e instanceof Dragger)).forEach(e -> e.resizeB(delta));
	}
	
}
