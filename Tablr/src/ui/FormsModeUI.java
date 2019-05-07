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
import uielements.Checkbox;
import uielements.ListView;
import uielements.Text;
import uielements.TextField;
import uielements.UIElement;
import uielements.UIRow;
import uielements.VoidElement;

public class FormsModeUI extends UI {

	FormsModeUI(int x, int y, int width, int height, Tablr tablr) {
		super(x, y, width, height);
		this.setTablr(tablr);
		
	}
		
	public void loadUI(Table table) {
		setActive();
		this.clear();
		loadUIAttributes();
		
		titleBar.setText("Table design mode: "+table.getName());
		int currentHeight = getY()+titleHeight+edgeW;
	
	}
	
	private UIRow getLegend() {
		return null;
	}
	
	private void reloadListView(Table table,int rowNumber) {
		Optional<UIElement> list = this.getElements().stream().filter(e -> e instanceof ListView).findFirst();
		this.elements.remove(list.orElseThrow(() -> new RuntimeException()));
		this.addUIElement(getForm(table,rowNumber));
	}
	
	private int cellHeigth = 20;
	
	private ListView getForm(Table table, int rowNumber) {
		ListView list = new ListView(getX()+edgeW,getLegend().getEndY(), getWidth() - 2*edgeW,	getHeight()-2*edgeW-titleHeight-15,new ArrayList<UIElement>());
		
		int currentHeigth = list.getY();
		for(Column col : getTablr().getColumns(table)){
			ArrayList<UIElement> emts = new ArrayList<UIElement>();
			String val = getTablr().getValueString(col,rowNumber);
			//Creating columnLabel
			Text colLabel = new Text(getX()+edgeW,currentHeigth,80,cellHeigth,tablr.getColumnName(col));			
			emts.add(colLabel);
			
			if(getTablr().getColumnType(col).equals(Type.BOOLEAN)){
				Checkbox booleanValue;
				booleanValue = new Checkbox(colLabel.getEndX()+50-10,currentHeigth+cellHeigth/2-10,20,20, BooleanCaster.cast(tablr.getValueString(col,rowNumber)));

				emts.add(new VoidElement(colLabel.getEndX(),currentHeigth,100, cellHeigth, Color.white));
				emts.add(booleanValue);
				
				int index = rowNumber;
				booleanValue.addSingleClickListener(() ->
					getTablr().toggleCellValueBoolean(col, index)
				);
			}
			else{				
				TextField field =  new TextField(colLabel.getEndX(),currentHeigth,100, cellHeigth,val);
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
			
			colLabel.addKeyboardListener(33, () ->{
				this.reloadListView(table,rowNumber+1);
			});
			colLabel.addKeyboardListener(34, ()  -> {
				this.reloadListView(table,rowNumber-1);
			});
			
		}		
		return list;
	}
	
	@Override
	public FormsModeUI clone() {
		FormsModeUI clone = new FormsModeUI(getX(),getY(),getWidth(),getHeight(),getTablr());
		return clone;
	}

}
