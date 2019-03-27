package facades;

import java.util.ArrayList;

import domain.Column;
import domain.Row;
import domain.Table;
import domain.Type;
import exceptions.InvalidNameException;
import exceptions.InvalidTypeException;
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
	WindowManager UIFacade;
	
	public Tablr() {
		domainFacade = new DomainFacade();
		UIFacade = new WindowManager(this);
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
	
	public void loadTableModeUI(){
		UIFacade.loadTablesModeUI();
	}
	
	public void loadTableRowsModeUI(Table table){
		UIFacade.loadTableRowsModeUI(table);
	}
	
	public void loadTableDesignModeUI(Table table) {
		UIFacade.loadTableDesignModeUI(table);
	}
	
	public void clearUI() {
		UIFacade.clearUI();
	}
	
	public Table addEmptyTable() {
		domainChanged();
		return domainFacade.addEmptyTable();
	}
	
	public ArrayList<Table> getTables() {
		return new ArrayList<Table>(domainFacade.getTables());
	}
	
	public UI getActiveUI() {
		return UIFacade.getActiveUI();
	}
	
	public void removeTable(Table t) {
		domainChanged();
		domainFacade.removeTable(t);
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
		domainChanged();
		domainFacade.renameTable(t, name);
	}
	
	public void toggleBlanks(Column col) throws Exception{
		domainChanged();
		domainFacade.toggleBlanks(col);
	}
	
	public Table getTable(String name){
		return domainFacade.getTable(name);
	}

	public void addEmptyColumn(Table table, Type type, Object defaultValue) {
		domainChanged();
		domainFacade.addEmptyColumn(table,type, defaultValue);
		
	}

	public void setColumnName(Column col, String text) throws InvalidNameException {
		domainChanged();
		domainFacade.setColumnName(col,text);
		
	}

	public void addRow(Table tab) {
		domainChanged();
		domainFacade.addRow(tab);		
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
		domainChanged();
		domainFacade.removeRow(tab,index);
		
	}

	public void removeColumn(Table table, int index) {
		domainChanged();
		domainFacade.removeColumn(table, index);
	}
	
	public void notifyNewSelected(UIElement e) {
		UIFacade.newSelected(e);
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
		UIFacade.getSelectionLock(e);
	}
	
	public void releaseSelectionLock(UIElement e) {
		UIFacade.releaseSelectionLock(e);
	}

	public void toggleDefault(Column col) {
		domainChanged();
		domainFacade.toggleDefault(col);
	}

	public void getLock(UIElement e) {
		UIFacade.getLock(e);
	}

	public void releaseLock(UIElement e) {
		UIFacade.releaseLock(e);
		
	}

	public UIElement getLockedElement() {
		return UIFacade.getLockedElement();
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
	private ArrayList<Runnable> DomainChangedListeners;
	
	public String getNewTitle() {
		return newTitle;
	}
	
	public void changeTitle(String title) {
		this.newTitle = title;
		for (Runnable r : titleChangeRunnables) {
			r.run();
		}
	}
	
	public ArrayList<UIElement> getActiveUIElements() {
		return UIFacade.getActiveUIElements();
	}

	public void toggleCellValueBoolean(Column col, int i) {
		domainChanged();
		domainFacade.toggleCellValueBoolean(col,i);
	}
	
	public Object getValue(Column col, int index){
		return domainFacade.getValue(col,index);
	}

	private void domainChanged(){
		for(Runnable r : DomainChangedListeners){
			r.run();
		}
	}
	
	public void addDomainChangedListener(Runnable r){
		this.DomainChangedListeners.add(r);
	}
}
