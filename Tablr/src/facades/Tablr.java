package facades;

import java.awt.Graphics;
import java.util.ArrayList;

import domain.Column;
import domain.Row;
import domain.Table;
import domain.Type;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;
import ui.TableDesignModeUI;
import ui.TableRowsModeUI;
import ui.TablesModeUI;
import ui.UI;
import uielements.UIElement;

/**
 * Class holding all methods that are used by elements in the UI
 * to modify the domain, and by domain elements to modify the UI.
 * The CommunicationManager delegates its calls to one of its 
 * facades (domainFacade or UIFacade) to reduce coupling between
 * UI and Domain, because objects only need a reference to the
 * CommunicationManager.
 *
 */
public class Tablr {
	
	DomainFacade domainFacade;
	WindowManager windowManager;
	
	public Tablr() {
		domainFacade = new DomainFacade();
		windowManager = new WindowManager(this);
	}

	public Table getActiveTable() {
		return domainFacade.getActiveTable();
	}

	public void setActiveTable(Table t) {
		domainFacade.setActiveTable(t);
	}

	public boolean isEmptyTable(Table t) {
		return (domainFacade.getColumns(t).size() == 0);
	}
	
	public void addTablesModeUI(TablesModeUI ui) {
		windowManager.addTablesModeUI(ui);
	}
	
	public void loadTableModeUI(TablesModeUI ui){
		windowManager.loadTablesModeUI(ui);
	}
	
	public void loadTableRowsModeUI(Table table){
		windowManager.loadTableRowsModeUI(table);
	}
	
	public void loadTableDesignModeUI(Table table) {
		windowManager.loadTableDesignModeUI(table);
	}
	
	public void clearUIAt(int x, int y) {
		windowManager.clearUIAt(x,y);
	}
	
	public Table addEmptyTable() {
		Table t = domainFacade.addEmptyTable();
		windowManager.addTableDesignModeUI(t, new TableDesignModeUI(0,0,300,300,this));
		windowManager.addTableRowsModeUI(t, new TableRowsModeUI(0,0,300,300,this));
		domainChanged();
		return t;
	}
	
	public ArrayList<Table> getTables() {
		return new ArrayList<Table>(domainFacade.getTables());
	}
	
	public UI getUIAt(int x,int y) {
		return windowManager.getUIAt(x,y);
	}
	
	public void removeTable(Table t) {
		domainFacade.removeTable(t);
		domainChanged();
	}
	
	public ArrayList<Table> getTablesByName(String name) {
		ArrayList<Table> tlist = new ArrayList<Table>();
		ArrayList<Table> tables = domainFacade.getTables();
		for (Table t : tables) {
			if (t.getName().equals(name)) {
				tlist.add(t);
			}
		}
		return new ArrayList<Table>(tlist);
	}
	
	public void renameTable(Table t, String name) {
		domainFacade.renameTable(t, name);
//		domainChanged();
	}
	
	public void toggleBlanks(Column col) throws Exception{
		domainFacade.toggleBlanks(col);
		domainChanged();
	}
	
	public Table getTable(String name){
		return domainFacade.getTable(name);
	}

	public void addEmptyColumn(Table table, Type type, Object defaultValue) {
		domainFacade.addEmptyColumn(table,type, defaultValue);
		domainChanged();
	}

	public void setColumnName(Column col, String text) throws InvalidNameException {
		domainFacade.setColumnName(col,text);
		domainChanged();
	}

	public void addRow(Table tab) {
		domainFacade.addRow(tab);
		domainChanged();
	}

	public ArrayList<Column> getColumns(Table tab) {
		return domainFacade.getColumns(tab);
	}

	public String getValueString(Column col, int i) {
		return domainFacade.getValueString(col,i);
	}

	public ArrayList<String> getColumnNames(Table table) {
		return domainFacade.getColumnNames(table);
	}

	public ArrayList<Row> getRows(Table tab) {
		return domainFacade.getRows(tab);
	}

	public void removeRow(Table tab, int index) {
		domainFacade.removeRow(tab,index);
		domainChanged();
	}

	public void removeColumn(Table table, int index) {
		domainFacade.removeColumn(table, index);
		domainChanged();
	}
	
	public void notifyNewSelected(UIElement e) {
		windowManager.newSelected(e);
	}

	public void changeCellValue(Column col, int i, String string) throws ClassCastException {
		domainChanged();
		domainFacade.changeCellValue(col,i,string);
	}

	public void toggleColumnType(Column col) throws InvalidTypeException {
		domainChanged();
		domainFacade.toggleColumnType(col);
	}
	
	public void setDefault(Column col, String def) throws ClassCastException {
		domainChanged();
		domainFacade.setDefault(col,def);
	}

	public Type getColumnType(Column col) {
		return domainFacade.getColumnType(col);
	}

	public String getColumnName(Column col) {
		return domainFacade.getColumnName(col);
	}

	public boolean getBlankingPolicy(Column col) {
		return domainFacade.getBlankingPolicy(col);
	}

	public String getDefaultString(Column col) {
		return domainFacade.getDefaultString(col);
	}
	
	public Object getDefaultValue(Column col) {
		return domainFacade.getDefaultValue(col);
	}
	
	public void getSelectionLock(UIElement e) {
		windowManager.getSelectionLock(e);
	}
	
	public void releaseSelectionLock(UIElement e) {
		windowManager.releaseSelectionLock(e);
	}

	public void toggleDefault(Column col) {
		domainChanged();
		domainFacade.toggleDefault(col);
	}

	public void getLock(UIElement e) {
		windowManager.getLock(e);
	}

	public void releaseLock(UIElement e) {
		windowManager.releaseLock(e);
		
	}


	public void setColumnType(Column col, Type type) throws InvalidTypeException {
		domainChanged();
		domainFacade.setColumnType(col,type);
	}
	
	private ArrayList<Runnable> titleChangeRunnables = new ArrayList<>();
	
	public void addTitleChangeRunnable(Runnable r) {
		titleChangeRunnables.add(r);
	}
	
	private String newTitle;
	private ArrayList<Runnable> DomainChangedListeners = new ArrayList<Runnable>();
	
	public String getNewTitle() {
		return newTitle;
	}
	
	public void changeTitle(String title) {
		this.newTitle = title;
		for (Runnable r : titleChangeRunnables) {
			r.run();
		}
	}
	
	public ArrayList<UIElement> getElementsUIAt(int x, int y) {
		return windowManager.getElementsUIAt(x,y);
	}

	public void toggleCellValueBoolean(Column col, int i) {
		domainChanged();
		domainFacade.toggleCellValueBoolean(col,i);
	}
	
	public Object getValue(Column col, int index){
		return domainFacade.getValue(col,index);
	}

	private void domainChanged(){
		DomainChangedListeners.stream().forEach(l -> l.run());
		
	}
	
	public void addDomainChangedListener(Runnable r){
		this.DomainChangedListeners.add(r);
	}

	public void paint(Graphics g) {
		windowManager.paint(g);
	}
	
	public UI getSelectedUI() {
		return windowManager.getSelectedUI();
	}
	
	public void selectUI(UI e) {
		windowManager.selectUI(e);
	}
	
	public UIElement getLockedElement() {
		return windowManager.getLockedElement();
	}
}
