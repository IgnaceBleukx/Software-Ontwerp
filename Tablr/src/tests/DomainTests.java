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
import exceptions.InvalidTypeException;

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
		assertEquals(c.getColumn(),col);
		
		//Switch the cell to another column
		Column col2 = new Column("Test", null,Type.BOOLEAN,true);
		c.setColumn(col2);
		col2.addCell(c);
		assertEquals(c.getColumn(),col2);
		
		//Terminating a cell deletes it in it's column
		c.terminate();
		assertTrue(!col2.getCells().contains(c));
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
		Table t = new Table("Table 1");
		
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
		Table t = new Table("Table1");
		t.addRow();
		
		Row r = t.getRows().get(0);
		//test the basic functionality of rows.
		r.setTable(t);
		assertEquals(r.getTable(),t);
		r.terminate();
		assertEquals(r.getTable(),null);
	}
	
	@Test
	public void terminateColumn(){
		Table table = new Table("name");
		Column col = table.addEmptyColumn(Type.STRING, "");
		table.addRow();
		col.terminate();
		//test if terminating a column deletes the column from it's table
		assertEquals(0,table.getColumns().size());
	}}
