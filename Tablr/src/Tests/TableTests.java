package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import domain.Cell;
import domain.Row;
import domain.Table;

public class TableTests {

	@Test
	public void addColumn() {
		Table table = new Table();
		table.addColumn();
		assertTrue(table.getColumns().size() == 1);
		assertTrue(table.getColumnNames().contains("Column0"));
		assertEquals(table.getColumns().get(0).getTable(),table);
	}
	
	@Test
	public void addRow(){
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

}
