package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import domain.Cell;
import domain.Column;
import domain.Row;
import domain.Table;
import domain.Type;
import exceptions.InvalidNameException;

public class DomainTests {
	
	/**
	 * This method creates a table with two rows and tree columns
	 * @return 	The created table.
	 */
	public Table buildTable() {
		Table table = new Table("name");
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
		Cell<Boolean> c = new Cell<Boolean>(true);
		assertEquals(c.getValue(), true);
		
		c.setValue(false);
		assertEquals(c.getValue(), false);
		
		ArrayList<Cell<Boolean>> cells = new ArrayList<Cell<Boolean>>();
		cells.add(c);
		
		Column col = new Column("Test", null,Type.BOOLEAN,true);
		col.addCell(c);
		
		assertEquals(c.getColumn(),col);
		
		Column col2 = new Column("Test", null,Type.BOOLEAN,true);
		c.setColumn(col2);
		col2.addCell(c);
		assertEquals(c.getColumn(),col2);
		
		c.terminate();
		assertTrue(!col2.getCells().contains(c));
	}
	
	@Test
	public void testColumnBasic() throws Exception {
		Column col1 = new Column("Col1",null,Type.BOOLEAN,true);
		assertEquals(col1.getColumnType(),Type.BOOLEAN);
		assertEquals(col1.getDefault(),true);
		assertEquals(col1.getName(),"Col1");
		
		Column col2 = new Column("Col2",null);
		
		col2.setName("NewCol2");
		assertEquals(col2.getName(), "NewCol2");
		
		col2.isValidName("NewCol2");
		
		assertTrue(col1.getBlankingPolicy());
		col1.toggleBlanks();
		assertEquals(false, col1.getBlankingPolicy());
		
		col1.setDefaultValue(null);
		assertEquals(col1.getDefault(),null);
		
		col1.setDefaultValue(false);
		assertEquals(col1.getDefault(),false);
		
		
	}
	
	@Test // (expected = ClassCastException.class)
	public void changeColumnDefaultsError() {
		Table table = buildTable();
		Column col = table.getColumns().get(0);
		try {
			col.setDefaultValue(false);}
		catch (ClassCastException c){
			assert(true);
			return;
		}
		assert(false);
	}
	
	@Test
	public void testColumnInTable() throws InvalidNameException {
		Table t = new Table("Table 1");	
		Cell<String> cell = new Cell<String>("1234");
		
		t.addEmptyColumn(Type.STRING, "Default");
		t.addRow();
		
		Column c = t.getColumns().get(0);
		assertTrue(!c.isValidName(""));
		assertTrue(!c.isValidName(c.getName()));
		Column c2 = new Column("j",null);
		assertTrue(c.isValidName("j"));
		
		try {
			c.setName("");
		} catch (Exception e) {
			c.setName("CorrectName");
		}
		
		assertTrue(c.getBlankingPolicy());
		
		assertNotNull(c.getCell(0));
		
	}
	
	@Test
	public void testRowBasic() {
		Table t = new Table("Table1");
		t.addRow();
		Row r = t.getRows().get(0);
		
		r.setTable(t);
		assertEquals(r.getTable(),t);
		r.terminate();
		assertEquals(r.getTable(),null);
	}
	
	@Test
	public void testDomainElementBasic() {
		//Because DomainElement is abstract, we test its methods
		//through a Row object (Row inherits from DomainElement).
		Row r = new Row();
		assertFalse(r.getError());
		r.isError();
		assertTrue(r.getError());
		r.isNotError();
		assertFalse(r.getError());
	}
		
	public void terminateColumn(){
		Table table = new Table("name");
		Column col = table.addEmptyColumn(Type.STRING, "");
		table.addRow();
		col.terminate();
		assertEquals(0,table.getColumns().size());
	}}
