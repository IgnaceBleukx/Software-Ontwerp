package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import domain.Cell;
import domain.Column;
import domain.IllegealDimensionException;
import domain.Row;
import domain.Table;

public class TableTests {
	
	public Table buildTable() {
		Table table = new Table();
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
		Table table = new Table();
		table.addColumn();
		assertTrue(table.getColumns().size() == 1);
		assertTrue(table.getColumnNames().contains("Column0"));
		assertEquals(table.getColumns().get(0).getTable(),table);
	}
	
	@Test
	public void addRow() throws IllegealDimensionException{
		Table table = new Table();
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
		Table table = new Table();
		Cell<String> c = new Cell<String>("Test");
		ArrayList<Cell<?>> cells = new ArrayList<Cell<?>>();
		cells.add(c);
		table.addRow(new Row(cells));
	}
	
	@Test
	public void terminateRow() throws IllegealDimensionException{
		Table table = new Table();
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
		
	}

}
