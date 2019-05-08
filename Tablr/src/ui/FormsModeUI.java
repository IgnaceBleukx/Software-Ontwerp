package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

import Utils.BooleanCaster;
import domain.Column;
import domain.Table;
import domain.Type;
import facades.Tablr;
import uielements.Button;
import uielements.Checkbox;
import uielements.ListView;
import uielements.Text;
import uielements.TextBox;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import uielements.VoidElement;

public class FormsModeUI extends UI {

	public FormsModeUI(int x, int y, int width, int height, Tablr tablr) {
		super(x, y, width, height);
		this.setTablr(tablr);
		
	}
		
	public void loadUI(Table table) {
		setActive();
		this.clear();
		loadUIAttributes();
		
		titleBar.setText("Forms mode: "+table.getName());
		int currentHeight = getY()+titleHeight+edgeW;
		
		//Adding legend to UI:
		UIRow legend = new UIRow(getX()+edgeW,titleBar.getEndY(),180,15,new ArrayList<UIElement>());
		Text colNameText = new Text(legend.getX(),legend.getY(),80,15,"ColName");
		Text valueText = new Text(legend.getX()+80,legend.getY(),100,15,"Value of row "+rowNumber);
		legend.addElement(colNameText);
		legend.addElement(valueText);
		
		this.addUIElement(legend);
		
		legend.addKeyboardListener(33, () ->{
			increaseRowN();
			this.reloadListView(table);
		});
		legend.addKeyboardListener(34, ()  -> {
			decreaseRowN();
			this.reloadListView(table);
		});
		
		ListView list = getForm(table);
		this.addUIElement(list);
	
		TextBox queryInput = new TextBox(list.getEndX()+10,legend.getY(),getEndX()-edgeW-10-list.getEndX()-5,list.getHeight()-30,"");
		Button submit = new Button(queryInput.getEndX()-60,queryInput.getEndY()+5,60,20,"Submit");
		this.addUIElement(queryInput);
		this.addUIElement(submit);
		
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
		((Text) getLegend().getElements().get(1)).setText("Value of row "+rowNumber);
	}
	
	private void increaseRowN() {
		rowNumber++;
		((Text) getLegend().getElements().get(1)).setText("Value of row "+rowNumber);
	}
	private void decreaseRowN() {
		if (rowNumber == 0) 
			return;
		rowNumber--;
		((Text) getLegend().getElements().get(1)).setText("Value of row "+rowNumber);

	}
	
	private int rowNumber;
	private int cellHeight = 35;
	
	private ListView getForm(Table table) {
		ListView list = new ListView(getX()+edgeW,getLegend().getEndY(), 180,getEndY()-edgeW-getLegend().getEndY(),new ArrayList<UIElement>());
			
		int currentHeight = list.getY();
		if (tablr.getColumns(table).isEmpty()) {
			list.addElement(new Text(list.getX(),list.getY(),list.getWidth(),list.getHeight(),"Index out of bounds"));
			return list;
		}
		for(Column col : getTablr().getColumns(table)){
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			String val;
			try{
				val = getTablr().getValueString(col,rowNumber);
			} catch (IndexOutOfBoundsException e) {
				list.addElement(new Text(list.getX(),list.getY(),list.getWidth(),list.getHeight(),"Index out of bounds"));
				return list;
			}
			//Creating columnLabel
			Text colLabel = new Text(getX()+edgeW,currentHeight,80,cellHeight,tablr.getColumnName(col));			
			emts.add(colLabel);
			
			if(getTablr().getColumnType(col).equals(Type.BOOLEAN)){
				Checkbox booleanValue;
				booleanValue = new Checkbox(colLabel.getEndX()+50-10,currentHeight+cellHeight/2-10,20,20, BooleanCaster.cast(tablr.getValueString(col,rowNumber)));

				emts.add(new VoidElement(colLabel.getEndX(),currentHeight,100, cellHeight, Color.white));
				emts.add(booleanValue);
				
				int index = rowNumber;
				booleanValue.addSingleClickListener(() ->
					getTablr().toggleCellValueBoolean(col, index)
				);
			}
			else{				
				TextField field =  new TextField(colLabel.getEndX(),currentHeight,100, cellHeight,val);
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
			
			list.addElement(new UIRow(list.getX(),currentHeight,list.getWidth()-list.getScrollBarWidth(),cellHeight,emts));
			currentHeight += cellHeight;
		}		
		return list;
	}
	
	@Override
	public FormsModeUI clone() {
		FormsModeUI clone = new FormsModeUI(getX(),getY(),getWidth(),getHeight(),getTablr());
		return clone;
	}

}
