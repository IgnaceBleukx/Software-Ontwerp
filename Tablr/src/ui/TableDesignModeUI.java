package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import Utils.DebugPrinter;
import uielements.Checkbox;
import uielements.Dragger;
import uielements.ListView;
import uielements.Text;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import domain.Column;
import domain.StoredTable;
import domain.Type;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;
import facades.Tablr;
import domain.Table;

/**
 * Create a new TableDesignModeUI
 *
 */
public class TableDesignModeUI extends UI {
	
	private int margin = getWidth() / 15;

	public TableDesignModeUI(int x, int y, int w, int h,Tablr t) {
		super(x,y,w,h);
		this.setTablr(t);
		this.columnResizeListeners.add((delta,index) ->{
			getLegend().resizeElementR(delta, index*2);
			getListView().getElements().stream().filter(e -> e instanceof UIRow).forEach(r -> ((UIRow) r).resizeElementR(delta, index));
		});
		
	}
	
	public void loadUI(StoredTable table){
		setActive();
		UIRow legend = getLegend();
		if(getLegend() != null)
			legend = getLegend();
		this.clear();
		loadUIAttributes();
		
		titleBar.setText("Table design mode: "+table.getName());
		int currentHeight = getY()+titleHeight+edgeW;
		
		
		if(legend == null) {
			DebugPrinter.print("Legend is null");
			int namePosX = getX() + margin + edgeW;
			int nameSizeX = (getWidth()-16-2*edgeW)*5/15;
			int typePosX = namePosX + nameSizeX + 2;
			int typeSizeX = (getWidth()-16-2*edgeW)*3/15;
			int blankPosX = typePosX + typeSizeX +2;
			int blankSizeX = (getWidth()-16-2*edgeW)*2/15;
			int defPosX = blankPosX + blankSizeX + 2;
			int defSizeX = (getWidth()-16-2*edgeW)*4/15;
			
			Text name = new Text(namePosX, currentHeight, nameSizeX, 15,"Name");
			Dragger nameDragger = new Dragger(namePosX+nameSizeX, currentHeight, 4, 15);
			Text type = new Text(typePosX, currentHeight, typeSizeX, 15,"Type");
			Dragger typeDragger = new Dragger(typePosX+typeSizeX, currentHeight, 4, 15);
			Text blank = new Text(blankPosX, currentHeight, blankSizeX, 15,"Blank");
			Dragger blankDragger = new Dragger(blankPosX+blankSizeX, currentHeight, 4, 15);
			Text def = new Text(defPosX, currentHeight, defSizeX, 15,"Default");
			Dragger defDragger = new Dragger(def.getEndX(), currentHeight, 4, 15);
			
			name.setBorder(true);
			type.setBorder(true);
			blank.setBorder(true);
			def.setBorder(true);
					
			legend = new UIRow(namePosX,currentHeight,this.getWidth(),15,new ArrayList<UIElement>(
										Arrays.asList(nameDragger,typeDragger,blankDragger,defDragger,name,type,blank,def)));
			legend.setWidth(legend.getElements().stream().mapToInt(e ->e.getWidth()).sum()-8);
		}
		
		this.addUIElement(legend);		
		ArrayList<UIElement> legendElementsCopy = legend.getElements();
		legendElementsCopy.sort((UIElement e1,UIElement e2) -> e1.getX() - e2.getX());
		Text name = (Text) legendElementsCopy.get(0);
		Dragger nameDragger = (Dragger) legendElementsCopy.get(1);
		Text type = (Text) legendElementsCopy.get(2);
		Dragger typeDragger = (Dragger) legendElementsCopy.get(3);
		Text blank = (Text) legendElementsCopy.get(4);
		Dragger blankDragger = (Dragger) legendElementsCopy.get(5);
		Text def = (Text) legendElementsCopy.get(6);
		Dragger defDragger = (Dragger) legendElementsCopy.get(7);
	
		nameDragger.addDragListener((newX,newY) -> { 
			int delta = newX - nameDragger.getGrabPointX();
			int deltaFinal = delta;
			if (name.getWidth() + delta < minimumColumnWidth){
				deltaFinal = minimumColumnWidth - name.getWidth();
			}
			getWindowManager().notifyTableDesignModeUIsColResized(deltaFinal, 0, table);
		});
		
		typeDragger.addDragListener((newX,newY) -> { 
			int delta = newX - typeDragger.getGrabPointX();
			int deltaFinal = delta;
			if (type.getWidth() + delta < minimumColumnWidth)
				deltaFinal = minimumColumnWidth - type.getWidth();
			getWindowManager().notifyTableDesignModeUIsColResized(deltaFinal, 1, table);
		});
		
		blankDragger.addDragListener((newX,newY) -> { 
			int delta = newX - blankDragger.getGrabPointX();
			int deltaFinal = delta;
			if (blank.getWidth() + delta < minimumColumnWidth)
				deltaFinal = minimumColumnWidth - blank.getWidth();
			getWindowManager().notifyTableDesignModeUIsColResized(deltaFinal, 2, table);
		});
		
		defDragger.addDragListener((newX,newY) -> { 
			int delta = newX - defDragger.getGrabPointX();
			int deltaFinal = delta;
			if (def.getWidth() + delta < minimumColumnWidth)
				deltaFinal = minimumColumnWidth - def.getWidth();
			getWindowManager().notifyTableDesignModeUIsColResized(deltaFinal, 3, table);
		});
		
		ListView list = loadColumnAttributes(table);
		this.addUIElement(list);
	
		//Reload listview when domain changed
		getTablr().addDomainChangedListener((Table t) -> {
			if (t != null && !t.equals(table)) return;
			Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof ListView).findFirst();
			getElements().remove(ll.orElseThrow(() -> new RuntimeException("No Listview in UI")));
			this.addUIElement(loadColumnAttributes(table));
			titleBar.setText("Table Design Mode: " + table.getName());
		});
		
		titleBar.addKeyboardListener(10, () -> { //Ctrl+Enter, create new Table Rows subwindow.
			for (UIElement e : getElements()){
				if (e.getError()) return;
			}
			
			if (this.getWindowManager().recentCtrl()) {
				tablr.loadTableRowsModeUI(table);
			}
		});
	}
			
	public UIRow getLegend(){
		Optional<UIElement> r = getElements().stream().filter(e -> e instanceof UIRow).findFirst();
		UIRow row = (UIRow) r.orElse(null);
		return row;
	}
	
	private ListView getListView(){
		Optional<UIElement> ll = getElements().stream().filter(e -> e instanceof ListView).findFirst();
		ListView list = (ListView) ll.orElseThrow(() -> new RuntimeException("No listvieuw in UI"));
		return list;
	}

	/**
	 * Loads the columns from a given table
	 * @return 		A Listview containing all information.
	 */
	private ListView loadColumnAttributes(StoredTable table) {
		int currentHeight = getY() + edgeW+titleHeight+15;
		
		int namePosX = getLegend().getElements().get(0).getX();
		int nameSizeX = getLegend().getElements().get(0).getWidth();
		int typePosX = getLegend().getElements().get(2).getX();
		int typeSizeX = getLegend().getElements().get(2).getWidth();
		int blankPosX = getLegend().getElements().get(4).getX();
		int blankSizeX = getLegend().getElements().get(4).getWidth();
		int defPosX = getLegend().getElements().get(6).getX();
		int defSizeX = getLegend().getElements().get(6).getWidth();
				
		ListView list = new ListView(getX()+edgeW, currentHeight, getWidth() - 2*edgeW,	getHeight()-2*edgeW-titleHeight-15,new ArrayList<UIElement>());
		
		int index = 0; 
		for(Column col : tablr.getColumns(table)){
			TextField colName = new TextField(namePosX, currentHeight, nameSizeX+2, 30, tablr.getColumnName(col));
			Text colType = new Text(typePosX, currentHeight, typeSizeX+2, 30, tablr.getColumnType(col).toString()); 
			colType.setBorder(true);
			Checkbox colBlankPol = new Checkbox(blankPosX + blankSizeX/2 - 10, currentHeight + 5, 20, 20, tablr.getBlankingPolicy(col));
			String defaultValue = tablr.getDefaultString(col);	
			
			ArrayList<UIElement> elmts = new ArrayList<UIElement>(){
				{ add(colName); add(colType); add(colBlankPol);}};
			if(tablr.getColumnType(col) == Type.BOOLEAN){				
				Checkbox defaultBoolean = new Checkbox(defPosX + defSizeX/2 - 10, currentHeight + 5,20,20,(Boolean) tablr.getDefaultValue(col));
				elmts.add(defaultBoolean);
				defaultBoolean.addSingleClickListener(() -> {
					tablr.toggleDefault(col);
				});
			}
			else{
				TextField colDefText = new TextField(defPosX,currentHeight, defSizeX ,30, defaultValue);
				elmts.add(colDefText);
				colDefText.addKeyboardListener(-1,()-> {
					try {
						if (colDefText.getText().isEmpty())
							tablr.setDefault(col,"");
						else
							tablr.setDefault(col,colDefText.getText());
						if (colDefText.getError()) {
							colDefText.isNotError();
						}
					}catch(ClassCastException e){
						colDefText.isError();
					}
				});
				colDefText.addKeyboardListener(10,() -> {
					tablr.domainChanged(table);
				});
				colDefText.addDeselectionListener(() -> {
					tablr.domainChanged(table);
				});
			}
			
			UIRow uiRow = new UIRow(list.getX() ,currentHeight, list.getWidth()-list.getScrollBarWidth(), 30,elmts);
			currentHeight += 30;
			list.addElement(uiRow);			
			
			//Adding listeners
			uiRow.addSingleClickListener(() -> {
				for (UIElement e : list.getElements()){
					if (e.getError() || (e.isSelected() && !e.equals(uiRow))) 
						return;
				}
				if (uiRow.isSelected())
					uiRow.deselect();
				else
					uiRow.select();
			});
			
			int i = index;
			uiRow.addKeyboardListener(127,() -> {
				
				//TODO: remove computedtables with 
				//queries that refer to this column!
				if(uiRow.isSelected()){
					try {
						tablr.removeColumn(table, i);
					} catch (InvalidNameException e) {
						throw new RuntimeException("InvalidNameException while removing column");
					} //2 scrollbars waar we geen rekening mee moeten houden
				}
			});
			colName.addSingleClickListener(() -> colName.select());
			
			colName.addKeyboardListener(-1,() -> {	
				//TODO: if a column name is referred to in 
				//a query, only the original name is valid!
				if (colName.getText().length() == 0) {
					colName.isError(); 
					return;
				}
				try{
					if (colName.isSelected()){
						tablr.setColumnName(col, colName.getText());
						if (colName.getError()) colName.isNotError();
					}
				}catch (InvalidNameException e){
					colName.isError();
				}
			});
			
			colName.addKeyboardListener(10,() -> {
				tablr.domainChanged(table);
			});

			colName.addDeselectionListener(() -> {
				tablr.domainChanged(table);
			});
			
			colBlankPol.addSingleClickListener(() -> {
				if (!colBlankPol.getError()){
					try {
						tablr.toggleBlanks(col);
					} catch (Exception e) {
						colBlankPol.isError();
						colBlankPol.setValue(false);
						tablr.getLock(colBlankPol);
					}
				}else {
					colBlankPol.setValue(true);
					colBlankPol.isNotError();
					tablr.releaseLock(colBlankPol);
				}
			});
			
			colType.addSingleClickListener(() -> {
				if (colType.getError()){
					DebugPrinter.print("colType is in error");
					try{
						tablr.setColumnType(col, Column.getNextType(Type.valueOf(colType.getText())));
						colType.isNotError();
						tablr.releaseLock(colType);
					}catch(InvalidTypeException e){
						colType.setText(Column.getNextType(Type.valueOf(colType.getText())).toString());
						colType.isError();
						tablr.getLock(colType);
					}
				}
				
				else{
					try{
						tablr.toggleColumnType(col);
					}catch (InvalidTypeException e){
						colType.setText(Column.getNextType(tablr.getColumnType(col)).toString());
						colType.isError();
						tablr.getLock(colType);
					}
				}
			});
			
			
			index++;
		}
		
		list.addDoubleClickListener(() -> {
			for (UIElement e : getElements()){
				if (e.getError()) return;
			}
			tablr.addEmptyColumn(table, Type.STRING, "");
		});
		
		list.addHorizontalScrollListener((delta) ->{
			getLegend().move(-delta,0);
		});
		
		return list;
	}
	
	@Override
	public TableDesignModeUI clone(){
		TableDesignModeUI clone = new TableDesignModeUI(getX(),getY(),getWidth(),getHeight(),getTablr());
		ArrayList<UIElement> clonedElements = new ArrayList<>(elements.stream().map(e -> e.clone()).collect(Collectors.toList()));
		clone.elements = clonedElements;
		DebugPrinter.print(clone.elements);
		return clone;
	}
	
	@Override
	public String toString() {
		return "DesignUI : X="+getX() + " Y="+getY() + " W=" +getWidth() + " H="+getHeight();
	}
}
