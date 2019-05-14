package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import Utils.BooleanCaster;
import Utils.DebugPrinter;
import domain.Column;
import domain.StoredTable;
import domain.Table;
import domain.Type;
import facades.Tablr;
import uielements.Button;
import uielements.Checkbox;
import uielements.Dragger;
import uielements.ListView;
import uielements.Text;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import uielements.VoidElement;

public class FormsModeUI extends UI {

	public FormsModeUI(int x, int y, int width, int height, Tablr tablr) {
		super(x, y, width, height);
		this.setTablr(tablr);
		
		this.columnResizeListeners.add((delta,index) -> {
			getLegend().resizeElementR(delta, index*2);
			getListView().getElements().stream().filter(e -> e instanceof UIRow).forEach(r -> ((UIRow) r).resizeElementR(delta, index));
		});
	}
		
	public void loadUI(Table table) {
		setActive();
		UIRow legend = getLegend();
		this.clear();
		loadUIAttributes();
		
		titleBar.setText("Forms Mode: " + table.getName() + " - Row " + rowNumber);
		
		if (legend == null) {
			//Adding legend to UI:
			legend = new UIRow(getX()+edgeW,titleBar.getEndY(),getWidth()-2*edgeW-10,15,new ArrayList<UIElement>());
			Text colNameText = new Text(legend.getX(),legend.getY(),78,15,"ColName");
			Text valueText = new Text(colNameText.getEndX()+4,legend.getY(),legend.getWidth()-colNameText.getWidth()-8,15,"Value of row");
					
			Dragger nameDragger = new Dragger(colNameText.getEndX(),colNameText.getY(),4,15);
			Dragger valueDragger = new Dragger(valueText.getEndX(),valueText.getY(),4,15);
			
			legend.addElement(nameDragger);
			legend.addElement(valueDragger);
			legend.addElement(colNameText);
			legend.addElement(valueText);
		}
		
		this.addUIElement(legend);
		//Adding listeners to legend
		ArrayList<UIElement> legendElementsCopy = legend.getElements();
		legendElementsCopy.sort((UIElement e1,UIElement e2) -> e1.getX() - e2.getX());
		DebugPrinter.print(legendElementsCopy);
		Dragger nameDragger = (Dragger) legendElementsCopy.get(1);
		Text colNameText = (Text) legendElementsCopy.get(0);
		nameDragger.addDragListener((newX,newY) -> {
			int delta = newX - nameDragger.getGrabPointX();
			int deltaFinal = delta;
			if (colNameText.getWidth() + delta < minimumColumnWidth)
				deltaFinal = minimumColumnWidth - colNameText.getWidth();
			getWindowManager().notifyFormsModeUIsColResized(deltaFinal,0,table);
		});
		Dragger valueDragger = (Dragger) legendElementsCopy.get(3);
		Text valueText = (Text) legendElementsCopy.get(2);
		valueDragger.addDragListener((newX,newY) -> {
			int delta = newX - valueDragger.getGrabPointX();
			int deltaFinal = delta;
			if (valueText.getWidth() + delta < minimumColumnWidth)
				deltaFinal = minimumColumnWidth - valueText.getWidth();
			getWindowManager().notifyFormsModeUIsColResized(deltaFinal,1,table);
		});
		
		legend.addKeyboardListener(33, () ->{
			rowNumber++;
			titleBar.setText("Forms Mode: " + table.getName() + " - Row " + rowNumber);
			this.reloadListView(table);
		});
		legend.addKeyboardListener(34, ()  -> {
			if (rowNumber > 0)
				rowNumber--;
			titleBar.setText("Forms Mode: " + table.getName() + " - Row " + rowNumber);
			this.reloadListView(table);
		});
		legend.addKeyboardListener(78, () ->{
			if (!getWindowManager().recentCtrl())
				return;
			if (table.isStoredTable()) { //Stored table
				StoredTable t = (StoredTable) table;
				tablr.addRow(t);
			}
				
		});
		legend.addKeyboardListener(68, () ->{
			if (!getWindowManager().recentCtrl())
				return;
			try {
				if (table.isStoredTable()) { //Stored table
					StoredTable t = (StoredTable) table;
					tablr.removeRow(t,rowNumber);
				}
			} catch(IndexOutOfBoundsException e) {
				//The row does not exist, do nothing
			}
		});
		
		ListView list = getForm(table);
		this.addUIElement(list);
		legend.setWidth(list.getWidth()-list.getScrollBarWidth());
		
		//Reload listview when domain is changed
		tablr.addDomainChangedListener(() -> {
			//Remove the old listview
			Optional<UIElement> l = getElements().stream().filter(e -> e instanceof ListView).findFirst();
			this.getElements().remove(l.orElseThrow(() -> new RuntimeException("No listview to bind listener to.")));
			
			//Load new listview from table
			addUIElement(getForm(table));
		});
		
	}
	
	private UIRow getLegend() {
		return (UIRow) getElements().stream().filter(e -> e instanceof UIRow).findFirst().orElse(null);
	}
	
	private void reloadListView(Table table) {
		Optional<UIElement> list = this.getElements().stream().filter(e -> e instanceof ListView).findFirst();
		this.elements.remove(list.orElseThrow(() -> new RuntimeException()));
		this.addUIElement(getForm(table));
	}
	
	private ListView getListView() {
		return (ListView) elements.stream().filter(e -> e instanceof ListView).findFirst().orElse(null);
	}
	private int rowNumber=0;
	private static final int cellHeight = 35;
	
	private ListView getForm(Table table) {
		ListView list = new ListView(getX()+edgeW,getLegend().getEndY(), getWidth()-2*edgeW,getEndY()-edgeW-getLegend().getEndY(),new ArrayList<UIElement>());
			
		int currentHeight = list.getY();
		if (tablr.getColumns(table).isEmpty()) {
			list.addElement(new Text(list.getX(),list.getY(),list.getWidth(),list.getHeight(),"Index out of bounds"));
			return list;
		}
		ArrayList<UIElement> legendElements = new ArrayList<>(getLegend().getElements().stream().filter(e -> !(e instanceof Dragger)).collect(Collectors.toList()));
		for(Column col : getTablr().getColumns(table)){
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			String val;
			try{
				val = getTablr().getValueString(col,rowNumber);
			} catch (IndexOutOfBoundsException e) {
				list.addElement(new Text(list.getX(),list.getY(),list.getWidth()-list.getScrollBarWidth(),list.getHeight()-list.getScrollBarWidth(),"Index out of bounds"));
				return list;
			}
			//Creating columnLabel
			Text colLabel = new Text(legendElements.get(0).getX(),currentHeight,legendElements.get(0).getWidth()+2,cellHeight,tablr.getColumnName(col));			
			emts.add(colLabel);
			
			if(getTablr().getColumnType(col).equals(Type.BOOLEAN)){
				
				VoidElement v = new VoidElement(colLabel.getEndX(),currentHeight,legendElements.get(1).getWidth()+6, cellHeight, Color.white);
				Checkbox booleanValue = new Checkbox((v.getX()+v.getWidth())/2-10,currentHeight+cellHeight/2-10,20,20, BooleanCaster.cast(tablr.getValueString(col,rowNumber)));
				
				emts.add(v);
				emts.add(booleanValue);
				
				int index = rowNumber;
				booleanValue.addSingleClickListener(() ->
					getTablr().toggleCellValueBoolean(col, index)
				);
			}
			else{				
				TextField field =  new TextField(colLabel.getEndX(),currentHeight,legendElements.get(1).getWidth()+6, cellHeight,val);
				emts.add(field);
				int index = rowNumber;
				field.addKeyboardListener(-1, () -> {
					try{
						getTablr().changeCellValue(col,index,field.getText());
						if(field.getError()) 
							field.isNotError();
					}catch(ClassCastException e){
						field.isError();
					}
				});
				
				field.addKeyboardListener(10,() ->{
					if (!field.getError() && field.isSelected())
						getTablr().domainChanged();
				});

				field.addDeselectionListener(() -> {
					tablr.domainChanged();
				});
			}
			
			list.addElement(new UIRow(list.getX(),currentHeight,getLegend().getWidth(),cellHeight,emts));
			currentHeight += cellHeight;
		}		
		return list;
	}
	
	@Override
	public FormsModeUI clone() {
		FormsModeUI clone = new FormsModeUI(getX(),getY(),getWidth(),getHeight(),getTablr());
		ArrayList<UIElement> clonedElements = new ArrayList<>(elements.stream().map(e -> e.clone()).collect(Collectors.toList()));
		clone.elements = clonedElements;
		return clone;
	}
	
	@Override
	public String toString() {
		return "FormsUI : X="+getX() + " Y="+getY() + " W=" +getWidth() + " H="+getHeight();
	}

}
