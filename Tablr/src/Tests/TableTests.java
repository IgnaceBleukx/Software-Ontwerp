package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import domain.Cell;
import domain.Column;
import domain.IllegealDimensionException;
import domain.Row;
import domain.Table;
import domain.TableManager;
import domain.Type;

public class TableTests {
	
	public Table buildTable() {
		Table table = new Table("name");
		table.addColumn();
		table.addColumn();
		table.addColumn();
		Cell<String> c1 = new Cell<String>("Cell1");
		Cell<String> c2 = new Cell<String>("Cell2");
		Cell<String> c3 = new Cell<String>("Cell3");
		ArrayList<Cell<?>> cells = new ArrayList<Cell<?>>();
		cells.add(c1);
		cells.add(c2);
		cells.add(c3);
		Row row  = new Row(cells);
		try {
			table.addRow(row);
		} catch (IllegealDimensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
	}

	@Test
	public void addColumn() {
		Table table = new Table("name");
		table.addColumn();
		assertTrue(table.getColumns().size() == 1);
		assertTrue(table.getColumnNames().contains("Column0"));
		assertEquals(table.getColumns().get(0).getTable(),table);
	}
	
	@Test
	public void addRow() throws IllegealDimensionException{
		Table table = new Table("name");
		table.addColumn();
		table.addColumn();
		table.addColumn();
		Cell<String> c1 = new Cell<String>("Cell1");
		Cell<String> c2 = new Cell<String>("Cell2");
		Cell<String> c3 = new Cell<String>("Cell3");
		ArrayList<Cell<?>> cells = new ArrayList<Cell<?>>();
		cells.add(c1);
		cells.add(c2);
		cells.add(c3);
		Row row  = new Row(cells);
		table.addRow(row);
		assertTrue(table.getRows().contains(row));
		assertTrue(c1.getTable().equals(table));
	}
	
	@Test(expected = IllegealDimensionException.class)
	public void badRow() throws IllegealDimensionException{
		Table table = new Table("name");
		Cell<String> c = new Cell<String>("Test");
		ArrayList<Cell<?>> cells = new ArrayList<Cell<?>>();
		cells.add(c);
		table.addRow(new Row(cells));
	}
	
	@Test
	public void terminateRow() throws IllegealDimensionException{
		Table table = new Table("name");
		table.addColumn();
		ArrayList<Cell<?>> cells = new ArrayList<Cell<?>>();
		cells.add(new Cell<String>("Test"));
		Row row = new Row(cells);
		table.addRow(row);
		row.terminate();
		assertFalse(table.getRows().contains(row));
		assertNull(row.getTable());
		assertEquals(row.getCells().size(), 0);
	}
	
	@Test
	public void removeRow(){
		Table table = buildTable();
		table.removeRow(0);
		assertEquals(table.getRows().size(), 0);
		
	}
	
	@Test
	public void removeColumn(){
		Table table = buildTable();
		table.removeColumn(0);
		assertEquals(table.getColumns().size(), 2);
		assertEquals(table.getRows().get(0).getCells().size(), 2);
		
	}
	
	@Test
	public void removeCellFromRow(){
		Table table = buildTable();
		Row row = table.getRows().get(0);
		Cell<?> c = row.removeCell(0);
		assertEquals(row.getCells().size(), 2);
		assertEquals(c.getTable(), table);
		assertNull(c.getRow());
		assertNotNull(c.getColumn());
	}
	
	@Test
	public void removeCellFromColumn(){
		Table table = buildTable();
		Column col = table.getColumns().get(0);
		Cell<?> c = col.removeCell(0);
		for(Cell<?> cell : col.getCells()){
			assertNotEquals(c,cell);
		}
		assertEquals(table,c.getTable());
		assertNotNull(c.getRow());
		
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
	public void changeColumnDefaults(){
		Table table = buildTable();
		Column col = table.getColumns().get(0);
		col.setDefault(Type.STRING,"NewDefault");
		assertEquals("NewDefault", col.getDefault());
		col.setDefault(Type.BOOLEAN,"WrongValue");
	}
	
	
	@Test
	public void addAndRemoveTableToTableManager(){
		Table table = buildTable();
		TableManager man = new TableManager();
		man.addTable(table);
		assertEquals(1, man.getTables().size());
		man.addEmptyTable();
		assertEquals(2, man.getTables().size());
		man.removeTable(table);
		assertEquals(1, man.getTables().size());
	}
	
	@Test
	public void getTable(){
		Table table = buildTable();
		TableManager man = new TableManager();
		man.addTable(table);
		table.setName("Table1");
		assertEquals(table, man.getTable("Table1"));
		assertNull(man.getTable("TableNameDoesNotExist"));
	}
	
	@Test
	public void terminateCell(){
		Table table = new Table("name");
		Cell<String> c1 = new Cell<String>("Cell1");
		Cell<String> c2 = new Cell<String>("Cell2");
		table.addColumn();
		table.addColumn();
		ArrayList<Cell<?>> l = new ArrayList<Cell<?>>(){{ add(c1);add(c2);}};
		Row r = new Row(l);
		try {
			table.addRow(r);
		} catch (IllegealDimensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c1.terminate();
		assertNull(c1.getTable());
		assertNull(c1.getColumn());
		assertNull(c1.getRow());
		for (Cell<?> c : r.getCells()){
			assertNotEquals(c, c1);
		}
		assertEquals(1, r.getCells().size());
	}
	

	

}
