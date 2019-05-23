package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import domain.Cell;
import domain.Column;
import domain.StoredTable;
import domain.Table;
import domain.Type;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;
import exceptions.InvalidTypeException;
import facades.Tablr;
import Utils.DebugPrinter;
import domain.ComputedTable;

public class DomainTests {
	
	/**
	 * This method creates a table with two rows and tree columns
	 * @return 	The created table.
	 */
	public Table buildTable() {
		StoredTable table = new StoredTable("name");
		table.addRow();
		table.addRow();
		table.addEmptyColumn(Type.STRING, "");
		table.addEmptyColumn(Type.STRING, "");
		table.addEmptyColumn(Type.STRING, "");
		return table;
	}
	
	/**
	 * This method adds a column to an empty table, 
	 * and verifies its existence in the table and vice versa
	 */
	@Test
	public void testCellBasic() throws InvalidNameException {
		//Creating cells works as intended
		Cell<Boolean> c = new Cell<Boolean>(true);
		assertEquals(c.getValue(), true);
		//Changing cell value
		c.setValue(false);
		assertEquals(c.getValue(), false);
		
		ArrayList<Cell<Boolean>> cells = new ArrayList<Cell<Boolean>>();
		cells.add(c);
		//add the cell to a column
		Column col = new Column("Test", null,Type.BOOLEAN,true);
		col.addCell(c);
		//the cell is in the column
		assertTrue(col.getCells().contains(c));

	}
	
	@Test (expected = ClassCastException.class)
	public void testColumnBasic() throws Exception {
		//Check if column constructor works as intended
		Column col1 = new Column("Col1",null,Type.BOOLEAN,true);
		assertEquals(col1.getColumnType(),Type.BOOLEAN);
		assertEquals(col1.getDefault(),true);
		assertEquals(col1.getName(),"Col1");
		
		Column col2 = new Column("Col2",null);
		
		//test methods setName and isValidName
		col2.setName("NewCol2");
		assertEquals(col2.getName(), "NewCol2");
		
		col2.isValidName("NewCol2");
		
		//test blanking policy
		assertTrue(col1.getBlankingPolicy());
		col1.toggleBlanks();
		assertFalse(col1.getBlankingPolicy());
		
		//test editing default value
		col1.setDefaultValue(null);
		assertNull(col1.getDefault());
		
		col1.setDefaultValue(false);
		assertEquals(col1.getDefault(),false);		
	}
	
	@Test  (expected = ClassCastException.class)
	public void changeColumnDefaultsError() throws InvalidTypeException, ClassCastException{
		Table table = buildTable();
		Column col = table.getColumns().get(0);
		//switch to Type.EMAIL
		try {
			col.setNextType();
		} catch (InvalidTypeException e1) {}
		
		//catch a ClassCastException for trying to set invalid default value in a column of type Type.EMAIL
		col.setDefaultValue("blub");
	}
	
	@Test
	public void testColumnInTable() throws InvalidNameException {
		StoredTable t = new StoredTable("Table 1");
		
		t.addEmptyColumn(Type.STRING, "Default");
		t.addRow();
		
		Column c = t.getColumns().get(0);
		assertTrue(c.getBlankingPolicy());
		//The cell should not be null: it should have the default value "Default" (string)
		assertNotNull(c.getCell(0));
		
		//Column Name should not be empty
		assertFalse(c.isValidName(""));
		
		//Column can be created with random name "j": will not throw exception
		try {
		Column c2 = new Column("j",null);
		} catch (InvalidNameException e){
			assert(false);
		}
		
		//Try to set invalid column name: catch, and set name as "CorrectName"
		try {
			c.setName("");
		} catch (InvalidNameException e) {
			c.setName("CorrectName");
		}
		
		assertEquals(c.getName(), "CorrectName");
	}
	
	@Test
	public void testRowBasic() {
		StoredTable t = new StoredTable("Table1");
		t.addEmptyColumn(Type.STRING, "");
		t.addRow();
		assertEquals(1, t.getRows());
		
	}
	
	@Test
	public void terminateColumn(){
		StoredTable table = new StoredTable("name");
		Column col = table.addEmptyColumn(Type.STRING, "");
		table.addRow();
		col.terminate();
		//test if terminating a column deletes the column from it's table
		assertEquals(0,table.getColumns().size());
	}
	
	@Test (expected=InvalidTypeException.class)
	public void testInvalidTypeColumn() throws InvalidTypeException {
		StoredTable table = new StoredTable("name");
		Column col = table.addEmptyColumn(Type.EMAIL, "a@b.com");
		col.setNextType();
	}
	
	@Test
	public void removeRowTable() {
		StoredTable table = new StoredTable("name");
		Column col = table.addEmptyColumn(Type.STRING, "");
		Column col2 = table.addEmptyColumn(Type.STRING, "");
		Column col3 = table.addEmptyColumn(Type.STRING, "");
		table.addRow();
		table.removeRow(0);
		assertEquals(0,table.getRows());
		Type t = Type.BOOLEAN;
		t = Type.INTEGER;
		t = Type.EMAIL;
		t = Type.STRING;
	}
	
	@Test (expected = InvalidTypeException.class)
	public void setInvalidColumnType() throws InvalidTypeException {
		StoredTable table = new StoredTable("name");
		Column col = table.addEmptyColumn(Type.STRING, "");
		table.addRow();
		col.changeCellValue(0, "abc");
		col.setColumnType(Type.INTEGER);
	}
	
	@Test
	public void addAllCells() throws Exception {
		StoredTable table = new StoredTable("name");
		Column col = table.addEmptyColumn(Type.STRING, "");
		Cell<String> c1 = new Cell<>("A");
		Cell<String> c2 = new Cell<>("A");
		Cell<String> c3 = new Cell<>("A");
		
		ArrayList<Cell<?>> cells = new ArrayList<>();
		cells.add(c1);
		cells.add(c2);
		cells.add(c3);
		col.addAllcells(cells);
	}
	
	@Test
	public void addBlankCellEmail() {
		StoredTable table = new StoredTable("name");
		Column col = table.addEmptyColumn(Type.EMAIL, "");
		col.addBlankCell();
	}
	
	@Test
	public void changeCellValues() {
		StoredTable table = new StoredTable("name");
		Column col1 = table.addEmptyColumn(Type.STRING, "");
		Column col2 = table.addEmptyColumn(Type.EMAIL, "");
		Column col3 = table.addEmptyColumn(Type.INTEGER, "");
		Column col4 = table.addEmptyColumn(Type.BOOLEAN, "");
		table.addRow();
		col1.changeCellValue(0, "String");
		col2.changeCellValue(0, "a@b.com");
		col3.changeCellValue(0, "1234");
		col4.changeCellValue(0, "True");

	}
	
	@Test
	public void testIsValidValues() {
		assertTrue(Type.BOOLEAN.isValidValue(""));
		assertTrue(Type.BOOLEAN.isValidValue(null));
		assertTrue(Type.BOOLEAN.isValidValue("False"));
		assertTrue(Type.BOOLEAN.isValidValue("false"));
		assertTrue(Type.BOOLEAN.isValidValue("True"));
		assertTrue(Type.BOOLEAN.isValidValue("true"));
		assertFalse(Type.INTEGER.isValidValue("01"));
	}
	
	@Test
	public void testToggleDefaultBoolean() {
		StoredTable table = new StoredTable("name");
		Column colBadType = table.addEmptyColumn(Type.INTEGER, "143");
		Column col1 = table.addEmptyColumn(Type.BOOLEAN, "");
		colBadType.toggleDefaultBoolean();
		col1.toggleDefaultBoolean();
	}
	
	@Test
	public void testNextType() {
		assertEquals(Type.STRING,Column.getNextType(Type.INTEGER));	
	}
	
	@Test
	public void testToggleCellValueBoolean() {
		StoredTable table = new StoredTable("name");
		Column colBadType = table.addEmptyColumn(Type.INTEGER, "143");
		Column col1 = table.addEmptyColumn(Type.BOOLEAN, "");
		table.addRow();
		
		colBadType.toggleCellValueBoolean(0);
		col1.toggleCellValueBoolean(0);
		
		col1.changeCellValue(0, "true");
		col1.toggleCellValueBoolean(0);
	}
	
	@Test
	public void textNextValueBoolean() {
		assertEquals(true, Column.nextValueBoolean(null, true));
		assertEquals(false, Column.nextValueBoolean(true, true));
		assertEquals(null, Column.nextValueBoolean(false, true));
		assertEquals(true, Column.nextValueBoolean(false, false));
	}

	@Test (expected = InvalidNameException.class)
	public void ChangeColumnNameTableWithReferences(){
		Tablr tablr = new Tablr();
		tablr.addEmptyTable();
		StoredTable table = (StoredTable) tablr.getTables().get(0);
		ArrayList<Cell<?>> c0Values = new ArrayList<Cell<?>>();
		c0Values.add(new Cell<Integer>(1));
		c0Values.add(new Cell<Integer>(2));
		c0Values.add(new Cell<Integer>(3));
		c0Values.add(new Cell<Integer>(4));
		table.addColumn(new Column("Column0",c0Values,Type.INTEGER,null));
		Column c = table.getColumns().get(0);
		Table secondTable = tablr.addEmptyTable();
		try {
			tablr.replaceTableFromQuery("SELECT t.Column0 AS c0 FROM Table0 AS t WHERE TRUE", secondTable);
		} catch (InvalidNameException e) {
			e.printStackTrace();
		} catch (InvalidQueryException e) {
			e.printStackTrace();
		}
		DebugPrinter.print("Changing Column name");
		tablr.setColumnName(c, "InvalidName");
	}
	
	@Test
	public void removeTableWithReferences(){
		Tablr tablr = new Tablr();
		tablr.addEmptyTable();
		StoredTable table = (StoredTable) tablr.getTables().get(0);
		ArrayList<Cell<?>> c0Values = new ArrayList<Cell<?>>();
		c0Values.add(new Cell<Integer>(1));
		c0Values.add(new Cell<Integer>(2));
		c0Values.add(new Cell<Integer>(3));
		c0Values.add(new Cell<Integer>(4));
		table.addColumn(new Column("Column0",c0Values,Type.INTEGER,null));
		
		StoredTable secondTable = tablr.addEmptyTable();
		try {
			tablr.replaceTableFromQuery("SELECT t.Column0 AS c0 FROM Table0 AS t WHERE TRUE", secondTable);
		} catch (InvalidNameException e) {
			e.printStackTrace();
		} catch (InvalidQueryException e) {
			e.printStackTrace();
		}
		ComputedTable cTable = (ComputedTable) tablr.getTables().get(1);
		tablr.removeTable(table);
		assertFalse(tablr.getTables().contains(cTable));
	}

	@Test
	public void removeColumnWithReferences() {
		Tablr tablr = new Tablr();
		tablr.addEmptyTable();
		StoredTable table = (StoredTable) tablr.getTables().get(0);
		ArrayList<Cell<?>> c0Values = new ArrayList<Cell<?>>();
		c0Values.add(new Cell<Integer>(1));
		c0Values.add(new Cell<Integer>(2));
		c0Values.add(new Cell<Integer>(3));
		c0Values.add(new Cell<Integer>(4));
		table.addColumn(new Column("Column0",c0Values,Type.INTEGER,null));
		
		StoredTable secondTable = tablr.addEmptyTable();
		try {
			tablr.replaceTableFromQuery("SELECT t.Column0 AS c0 FROM Table0 AS t WHERE TRUE" , secondTable);
		} catch (InvalidNameException e) {
			e.printStackTrace();
		} catch (InvalidQueryException e) {
			e.printStackTrace();
		}
		ComputedTable cTable = (ComputedTable) tablr.getTables().get(1);
		tablr.removeColumn(table, 0);
		assertEquals(1,tablr.getTables().size());
		assertFalse(tablr.getTables().contains(cTable));
	}
	
}


