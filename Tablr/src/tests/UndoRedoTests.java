package tests;

import static org.junit.Assert.*;

import org.junit.*;

import Utils.DebugPrinter;
import domain.*;
import facades.Tablr;

public class UndoRedoTests {

	
	@Test
	public void undoRedoAddTable() {
		Tablr t =  new Tablr();
		//Add some tables
		Table table0 = t.addEmptyTable();
		Table table1 = t.addEmptyTable();
		Table table2 = t.addEmptyTable();
		
		//Undo the adding of table2
		t.undo();
		assertEquals(2,t.getTables().size());
		assertTrue(t.getTables().contains(table0));
		assertTrue(t.getTables().contains(table1));
		assertFalse(t.getTables().contains(table2));
		
		//Redo the adding of table2
		t.redo();
		assertEquals(3,t.getTables().size());
		assertTrue(t.getTables().contains(table2));
	}
	
	@Test
	public void undoRedoRemoveTable() {
		Tablr t = new Tablr();
		//Add some tables
		StoredTable table0 = t.addEmptyTable();
		StoredTable table1 = t.addEmptyTable();
		StoredTable table2 = t.addEmptyTable();
		//Remove table 1
		t.removeTable(table1);
		assertEquals(2,t.getTables().size());
		
		//Undo the removing of table1
		t.undo();
		assertEquals(3,t.getTables().size());
		assertTrue(t.getTables().contains(table0));
		assertTrue(t.getTables().contains(table1));
		assertTrue(t.getTables().contains(table2));
		assertEquals(1,t.getTables().indexOf(table1));
		
		//Redo the removing of table1
		t.redo();
		assertEquals(2,t.getTables().size());
		assertFalse(t.getTables().contains(table1));
	}
	
	@Test
	public void undoRedoChangeTableName() {
		Tablr t = new Tablr();
		//Add a table
		Table table0 = t.addEmptyTable();
		String tableName = table0.getName();
		//Rename the table name
		t.renameTable(table0, "newTableName");
		
		//Undo the renaming of the tablename
		t.undo();
		assertEquals(tableName,table0.getName());
		
		//Redo the renaming of the table
		t.redo();
		assertEquals("newTableName", table0.getName());
	}
	
	@Test
	public void undoRedoAddColumn() {
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add a column to the table
		t.addEmptyColumn(table0, Type.STRING, "default0");
		t.addEmptyColumn(table0, Type.STRING, "default1");
		t.addEmptyColumn(table0, Type.STRING, "default2");
		Column column0 = t.getColumns(table0).get(0);
		Column column1 = t.getColumns(table0).get(1);
		Column column2 = t.getColumns(table0).get(2);	
		//Remove column1
		assertEquals(3,t.getColumns(table0).size());
		
		//Undo the adding of column2
		t.undo();	
		assertEquals(2,t.getColumns(table0).size());
		assertTrue(t.getColumns(table0).contains(column0));
		assertTrue(t.getColumns(table0).contains(column1));
		assertTrue(!t.getColumns(table0).contains(column2));
		
		//Redo the adding of column2
		t.redo();
		DebugPrinter.print(t.getColumns(table0));
		assertEquals(3,t.getColumns(table0).size());
		assertFalse(t.getColumns(table0).contains(column2));
	}

	@Test
	public void undoRedoRemoveColumn() {
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add some column to the table
		t.addEmptyColumn(table0, Type.STRING, "default0");
		t.addEmptyColumn(table0, Type.STRING, "default1");
		t.addEmptyColumn(table0, Type.STRING, "default2");
		Column column0 = t.getColumns(table0).get(0);
		Column column1 = t.getColumns(table0).get(1);
		Column column2 = t.getColumns(table0).get(2);	
		//Remove column1
		t.removeColumn(table0, 1);
		
		//Undo the removing of column1
		t.undo();
		assertEquals(3,t.getColumns(table0).size());
		assertTrue(t.getColumns(table0).contains(column0));
		assertTrue(t.getColumns(table0).contains(column1));
		assertTrue(t.getColumns(table0).contains(column2));
		assertEquals(1,t.getColumns(table0).indexOf(column1));
		
		//Redo the removing of column1
		t.redo();
		DebugPrinter.print(t.getColumns(table0));
		assertEquals(2,t.getColumns(table0).size());
		assertFalse(t.getColumns(table0).contains(column1));
	}
	
	@Test
	public void undoRedoChangeColumnName() {
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add a column to the table
		t.addEmptyColumn(table0, Type.STRING, "default0");
		Column column0 = t.getColumns(table0).get(0);
		
		assertEquals("Column0", column0.getName());
		
		// Change name of column
		t.setColumnName(column0, "Column");
		assertEquals("Column", column0.getName());
		
		// undo renaming of column0
		t.undo();
		assertEquals("Column0", column0.getName());
		
		// redo renaming of column0
		t.redo();
		assertEquals("Column", column0.getName());
		
		
	}

	@Test
	public void undoRedoToggleBlanks() throws Exception {
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add a column to the table
		t.addEmptyColumn(table0, Type.STRING, "default0");
		Column column0 = t.getColumns(table0).get(0);
		
		assertTrue(column0.getBlankingPolicy());
		
		//Toggle blanks of Column0
		t.toggleBlanks(column0);
		assertFalse(column0.getBlankingPolicy());
		
		//Undo the toggeling of blanks of column0
		t.undo();
		assertTrue(column0.getBlankingPolicy());
		
		//Redo the removing of blanks of column0
		t.redo();
		assertFalse(column0.getBlankingPolicy());
		
	}

	@Test
	public void undoRedoAddRow(){
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add a column
		t.addEmptyColumn(table0, Type.STRING,"default");
		//Add some rows
		t.addRow(table0);
		t.addRow(table0);
		t.addRow(table0);
		
		//Undo the adding of the last row
		t.undo();
		assertEquals(2,table0.getRows());
		
		//Redo the adding of the last row
		t.redo();
		assertEquals(3,table0.getRows());
	}
	
	@Test
	public void undoRedoRemoveRow(){
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add a column
		t.addEmptyColumn(table0, Type.STRING, "default");
		//Add some rows
		t.addRow(table0);
		t.addRow(table0);
		t.addRow(table0);
		//Add some values to the rows
		t.changeCellValue(t.getColumns(table0).get(0),0, "a");
		t.changeCellValue(t.getColumns(table0).get(0),1, "b");
		t.changeCellValue(t.getColumns(table0).get(0),2, "c");
		//Remove the second row
		t.removeRow(table0, 1);
		
		//Undo the removing of the second row
		t.undo();
		assertEquals(3,t.getRows(table0));
		assertEquals("a",t.getValueString(t.getColumns(table0).get(0), 0));
		assertEquals("b",t.getValueString(t.getColumns(table0).get(0), 1));
		assertEquals("c",t.getValueString(t.getColumns(table0).get(0), 2));

		//Redo the removing of the second row
		t.redo();
		assertEquals(2,t.getRows(table0));
	}
	
	@Test
	public void undoRedoChangeCellValue() {
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add a column
		t.addEmptyColumn(table0, Type.STRING, "default");
		Column column0 = t.getColumns(table0).get(0);
		//Add a row
		t.addRow(table0);
		assertEquals("default",t.getValueString(column0, 0));
		//Change the value of the cell
		t.changeCellValue(column0, 0, "newValue");
		assertEquals("newValue",t.getValueString(column0, 0));

		//Undo the value change
		t.undo();
		assertEquals("default",t.getValueString(column0, 0));
		
		//Redo the value change
		t.redo();

		assertEquals("Column0", column0.getName());
		assertEquals("newValue",t.getValueString(column0, 0));
	}
	
	@Test
	public void undoRedoToggleColumnType() {
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add a column to the table
		t.addEmptyColumn(table0, Type.STRING, "default0");
		Column col = t.getColumns(table0).get(0);
		
		assertEquals(Type.STRING, col.getColumnType());
		t.setDefault(col, "@");
		
		// Toggle Column type
		t.toggleColumnType(col);
		assertEquals(Type.EMAIL, col.getColumnType());
		
		// undo toggling
		t.undo();
		assertEquals(Type.STRING, col.getColumnType());
		
		// Redo toggling
		t.redo();
		assertEquals(Type.EMAIL, col.getColumnType());
	}

	@Test
	public void undoRedoChangeDefault() {
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add a column to the table
		t.addEmptyColumn(table0, Type.STRING, "default0");
		Column col = t.getColumns(table0).get(0);
		
		// Change default value of col to "def"
		t.setDefault(col, "def");
		assertEquals("def", col.getDefault());
		
		// Undo change name of column
		t.undo();
		assertEquals("default0", col.getDefault());
		
		// Redo change name of column
		t.redo();
		assertEquals("def", col.getDefault());
	}
	
	@Test
	public void undoRedoToggleDefault() {
		Tablr t = new Tablr();
		//Add a table
		StoredTable table0 = t.addEmptyTable();
		//Add a column to the table
		t.addEmptyColumn(table0, Type.BOOLEAN, null);
		Column col = t.getColumns(table0).get(0);
		
		// Change default value of col to allow
		t.toggleDefault(col);
		assertEquals(true, col.getDefault());
		
		// Undo change name of column
		t.undo();
		assertEquals(null, col.getDefault());
		
		// Redo change name of column
		t.redo();
		assertEquals(true, col.getDefault());
	}
}

