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

public class DomainTests {
	
	/**
	 * This method creates a table with two rows and tree columns
	 * @return 	The created table.
	 */
	public Table buildTable() {
		Table table = new Table("name");
		Column col1 = new Column("col1",  new ArrayList<Cell<?>>(){{	add(new Cell<String>("Cell1.1")); 
																	    add(new Cell<String>("Cell2.1"));}});
		Column col2 = new Column("col2",  new ArrayList<Cell<?>>(){{	add(new Cell<String>("Cell1.2")); 
																		add(new Cell<String>("Cell2.2"));}});
		Column col3 = new Column("col1",  new ArrayList<Cell<?>>(){{	add(new Cell<String>("Cell1.3")); 
	      																add(new Cell<String>("Cell2.3"));}});
		table.addColumn(col1);
		table.addColumn(col2);
		table.addColumn(col3);
		return table;
	}
	
	/**
	 * This method adds a column to an empty table, 
	 * and verifies its existence in the table and vice versa
	 */
	@Test
	public void addColumn() {
		Table table = new Table("name");
		table.addEmptyColumn();
		assertTrue(table.getColumns().size() == 1);
		assertTrue(table.getColumnNames().contains("Column0"));
		assertEquals(table.getColumns().get(0).getTable(),table);
	}
	
	/**
	 * This method adds a new row to a table with 3 columns and
	 * verifies that afterwards the table has exactly one row
	 */
	@Test
	public void addRow() {
		Table table = new Table("name");
		table.addEmptyColumn();
		table.addEmptyColumn();
		table.addEmptyColumn();
		table.addRow();
		assertEquals(1,table.getRows().size());
	}
	

	@Test
	public void removeRow(){
		Table table = buildTable();
		table.removeRow(0);
		assertEquals(table.getRows().size(), 1);
		
	}
	
	@Test
	public void removeColumn(){
		Table table = buildTable();
		table.removeColumn(0);
		assertEquals(table.getColumns().size(), 2);
		
	}
	
	@Test
	public void removeCellFromColumn(){
		Table table = buildTable();
		Column col = table.getColumns().get(0);
		Cell<?> c = col.removeCell(0);
		for(Cell<?> cell : col.getCells()){
			assertNotEquals(c,cell);
		}		
	}
	
	@Test
	public void toggleColumnTypes(){
		Table table = buildTable();
		Column col = table.getColumns().get(0);
		assertEquals(Type.STRING, col.getColumnType());
		col.setNextType();
		assertEquals(Type.EMAIL, col.getColumnType());
		col.setNextType();
		assertEquals(Type.BOOLEAN, col.getColumnType());
		col.setNextType();
		assertEquals(Type.INTEGER, col.getColumnType());
		col.setNextType();
		assertEquals(Type.STRING, col.getColumnType());
		
	}

	@Test (expected = ClassCastException.class)
	public void changeColumnDefaultsError(){
		Table table = buildTable();
		Column col = table.getColumns().get(0);
		col.setDefault(Type.STRING,false);		
	}
	
	@Test
	public void changeColumnDefaults(){
		Table table = buildTable();
		Column col = table.getColumns().get(0);
		
		col.setDefault(Type.STRING, "string");
		col.setDefault(Type.BOOLEAN, false);
		col.setDefault(Type.EMAIL, "sampleEmail@tests.com");
		col.setDefault(Type.INTEGER, 100);
		
		assertEquals("string", col.getDefault());
		col.setColumnType(Type.BOOLEAN);
		assertEquals(false, col.getDefault());
		col.setColumnType(Type.EMAIL);
		assertEquals("sampleEmail@tests.com", col.getDefault());
		col.setColumnType(Type.INTEGER);
		assertEquals(100, col.getDefault());		
	}
	
	
//	@Test
//	public void addAndRemoveTableToTableManager(){
//		Table table = buildTable();
//		TableManager man = new TableManager();
//		man.addTable(table);
//		assertEquals(1, man.getTables().size());
//		man.ad
}dEmptyTable();
//		assertEquals(2, man.getTables().size());
//		man.removeTable(table);
//		assertEquals(1, man.getTables().size());
//	}
//	
//	@Test
//	public void getTable(){
//		Table table = buildTable();
//		TableManager man = new TableManager();
//		man.addTable(table);
//		table.setName("Table1");
//		assertEquals(table, man.getTable("Table1"));
//		assertNull(man.getTable("TableNameDoesNotExist"));
//	}
	
	@Test
	public void addBlankCells(){
		Column c = new Column("Column1", null);
		c.addBlankCell();
		assertEquals("",c.removeCell(0).getValue());
		c.setColumnType(Type.BOOLEAN);
		c.addBlankCell();
		assertEquals(false,c.removeCell(0).getValue());
		c.setColumnType(Type.EMAIL);
		c.addBlankCell();
		assertEquals("",c.removeCell(0).getValue());
		c.setColumnType(Type.INTEGER);
		c.addBlankCell();
		assertEquals(0,c.removeCell(0).getValue());
	}
	
	@Test
	public void terminateColumn(){
		Table table = new Table("name");
		Column col = table.addEmptyColumn();
		table.addRow();
		col.terminate();
		assertEquals(0,table.getColumns().size());
	}
	
	@Test
	public void terminateRow(){
		Table table = buildTable();
		Row row = table.getRows().get(0);
		row.terminate();
		for (Column col: table.getColumns()){
			assertEquals(1, col.getCells().size());
		}
		assertNull(row.getTable());
	}
	
	@Test
	public void terminateTable(){
		Table table = buildTable();
		table.terminate();
		assertEquals(0, table.getRows().size());
		assertEquals(0, table.getColumns().size());
	}
	

}
