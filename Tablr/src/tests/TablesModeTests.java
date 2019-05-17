package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import Utils.DebugPrinter;
import canvaswindow.MyCanvasWindow;
import domain.Table;
import facades.Tablr;
import uielements.ListView;
import uielements.TextField;
import uielements.UIRow;

public class TablesModeTests {	
	/**
	 * use case 4.1: Create a table
	 */
	@Test
	public void useCase1() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// There are no tables yet
		assertEquals(tablr.getUIs().size(), 0);

		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(1, 84, Character.MIN_VALUE);
		
		assertEquals(tablr.getTables().size(), 0);
		
		// Double click on listview to create a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		
		// There is a table added to the tables list
		assertEquals(tablr.getTables().size(), 1);
	}
	
	
	/**
	 * use case 4.2: Edit table name
	 * TODO: Laatste 4 lijnen werken nog niet
	 * 
	 */
	@Test
	public void useCase2() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T
		myCW.handleKeyEvent(1, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(1, 84, Character.MIN_VALUE);
		// Double click on listview to create a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		
		// Check the name of the added table
		assertEquals("Table0",tablr.getTables().get(0).getName());
		
		// Step 1 & 2: The user clicks a table name and the textfield gets selected
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 35, 1);

		TextField t = (TextField) tablr.getUIAt(60, 35).locatedAt(60, 35);
		assertEquals(t.isSelected(), true);
		// Step 3: Remove the last character of the highlighted table name
		// (8 is backspace)
		myCW.handleKeyEvent(0, 8, Character.MIN_VALUE);
		assertEquals("Table", tablr.getTables().get(0).getName());
		
		// Step 4: Check to see if table name gets red when it is empty or equal to name of another table
		assertEquals(t.getError(), false);
		for(int i = 0; i<8; i++){
			DebugPrinter.print(tablr.getTables().get(0).getName());
			myCW.handleKeyEvent(1, 8, Character.MIN_VALUE);
		}
		assertEquals("T", tablr.getTables().get(0).getName());
		assertEquals(t.getError(), true);
		
		// Add a character to the highlighted table name
		myCW.handleKeyEvent(1, 65, 'a');
		assertEquals("a", tablr.getTables().get(0).getName());
		
		// Step 5: Press Enter to finish editing
		myCW.handleKeyEvent(1, 10, Character.MIN_VALUE);
		assertEquals(t.isSelected(), false);
		
		// Or click outside table name 
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 30, 1);
		assertEquals(false, t.isSelected());
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 300, 1);
		assertEquals(false, t.isSelected());
	}
	
	/**
	 * use case 4.4: Delete Table
	 * 
	 */
	@Test
	public void useCase4() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();

		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(1, 84, Character.MIN_VALUE);
		
		// Double click on listview to create a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		
		//Tablr has one table
		assertEquals(tablr.getTables().size(), 1);
		
		ListView l = (ListView) tablr.getUIAt(200, 200).locatedAt(200, 200);
		UIRow r = (UIRow) l.getElements().get(2);
		
		// Step 1: The user clicks the margin to the left of a table name.
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 19, 49, 1);
		
		// Step 2: row is selected
		assertEquals(true, r.isSelected());

		// Step 3: User presses Delete key
		myCW.handleKeyEvent(0, 127, Character.MIN_VALUE);
		
		// Step 4: The system removes the table and shows the updated list of tables
		assertEquals(tablr.getTables().size(), 0);
	}
	
	/**
	 * use case 4.4: Open Table
	 */
	@Test
	public void useCase5() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
	
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(1, 84, Character.MIN_VALUE);
		
		// Double click on listview to create a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		
		// Step 1: The user double-clicks a table name.
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 40, 2);
		
		// Step 2: The system creates a new Table Design subwindow for the doubleclicked table 
		// (if the table has no columns), or a new Table Rows subwindow for the double-clicked table (otherwise).
		Table table = tablr.getTables().get(0);
		assertEquals(1, tablr.getTableDesignUIs(table).size());
		
		// The user double-clicks below the list of tables to create a column
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 380, 100 , 2);
		
		// Table has a column
		assertEquals(1, tablr.getColumns(tablr.getTables().get(0)).size());
		
		// The user double-clicks the table name to open the table rows mode subwindow
		myCW.handleMouseEvent(0, 60, 40, 2);
		
		// A Table Rows mode subwindow is shown.
		assertEquals(1, tablr.getTableRowsUIs(table).size());
	}
	
	@Test
	public void testRenameTable() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
	
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(1, 84, Character.MIN_VALUE);
		
		// Double click on listview to create a two new tables (Table1 and Table2)
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		
		//The user selects the second table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 90, 88, 1);
		TextField textField = (TextField) tablr.getUIAt(90, 88).locatedAt(90, 88);
		assertEquals("Table1",textField.getText());
		
		//User presses backspace to delete the '1' in Table1
		myCW.handleKeyEvent(KeyEvent.KEY_TYPED, 8, Character.MIN_VALUE);
		assertEquals("Table",textField.getText());
		
		//User presses 0 to create identical table names
		myCW.handleKeyEvent(KeyEvent.KEY_TYPED, 48, '0');
		assertEquals("Table0",textField.getText());
		
		//User presses backspace to delete the '0' 
		myCW.handleKeyEvent(KeyEvent.KEY_TYPED, 8, Character.MIN_VALUE);
		assertEquals("Table",textField.getText());
		
		//User presses Enter to confirm table name "Table"
		myCW.handleKeyEvent(KeyEvent.KEY_TYPED, 10, Character.MIN_VALUE);
		assertEquals("Table",textField.getText());
		
	}
	
	@Test
	public void removeTable() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
	
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(1, 84, Character.MIN_VALUE);
		
		// Double click on listview to create a two new tables (Table1 and Table2)
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		assertEquals(1, tablr.getTables().size());

		
		//User selects the first table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 20, 54, 1);
		
		//User presses delete
		myCW.handleKeyEvent(KeyEvent.KEY_TYPED, 127, Character.MIN_VALUE);
		
		assertEquals(0, tablr.getTables().size());
		
	}
	
}
