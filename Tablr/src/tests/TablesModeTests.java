package tests;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import org.junit.Before;
import org.junit.Test;

import canvaswindow.CanvasWindow;
import canvaswindow.MyCanvasWindow;
import facades.Tablr;
import ui.UI;
import uielements.Button;
import uielements.ListView;
import uielements.TextField;
import uielements.UIRow;

public class TablesModeTests {
	
	@Before
	public void setUp() throws Exception {
	}
	
	
	/**
	 * use case 4.1: Create a table
	 */
	@Test
	public void useCase1() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// There are no tables yet
		assertEquals(tablr.getTableDesignUIs().size(), 0);

		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
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
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		// Double click on listview to create a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		
		// Check the name of the added table
		assertEquals("Table0",tablr.getTables().get(0).getName());
		
		// Step 1 & 2: The user clicks a table name and the textfield gets selected
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 30, 1);
		TextField t = (TextField) tablr.getUIAt(60, 30).locatedAt(60, 30);
		assertEquals(t.isSelected(), true);
		// Step 3: Remove the last character of the highlighted table name
		// (8 is backspace)
		myCW.handleKeyEvent(0, 8, ' ');
		assertEquals("Table", tablr.getTables().get(0).getName());
		
		// Step 4: Check to see if table name gets red when it is empty or equal to name of another table
		assertEquals(t.getError(), false);
		for(int i = 0; i<5; i++){
			myCW.handleKeyEvent(1, 8, ' ');
		}
		assertEquals("", tablr.getTables().get(0).getName());
		assertEquals(t.getError(), true);
		
		// Add a character to the highlighted table name
		myCW.handleKeyEvent(1, 65, 'a');
		assertEquals("a", tablr.getTables().get(0).getName());
		
		// Step 5: Press Enter to finish editing
		myCW.handleKeyEvent(1, 10, ' ');
		assertEquals(t.isSelected(), false);
		
		// Or click outside table name 
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 30, 1);
		assertEquals(true, t.isSelected());
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 300, 1);
		assertEquals(false, t.isSelected());
	}
	
	/**
	 * use case 4.3: Delete Table
	 * 
	 */
	@Test
	public void useCase3() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();

		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Double click on listview to create a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		
		//Tablr has one table
		assertEquals(tablr.getTables().size(), 1);
		
		ListView l = (ListView) tablr.getUIAt(200, 200).locatedAt(200, 200);
		UIRow r = (UIRow) l.getElements().get(2);
		
		// Step 1: The user clicks the margin to the left of a table name.
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 6, 21, 1);
		
		// Step 2: row is selected
		assertEquals(true, r.isSelected());

		// Step 3: User presses Delete key
		myCW.handleKeyEvent(0, 127, ' ');
		
		// Step 4: The system removes the table and shows the updated list of tables
		assertEquals(tablr.getTables().size(), 0);
	}
	
	/**
	 * use case 4.4: Open Table
	 */
	@Test
	public void useCase4() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
	
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Double click on listview to create a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		
		// Step 1: The user double-clicks a table name.
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 40, 2);
		
		// Step 2: The system creates a new Table Design subwindow for the doubleclicked table 
		// (if the table has no columns), or a new Table Rows subwindow for the double-clicked table (otherwise).
		assertEquals(1, tablr.getTableDesignUIs().size());
		
		// The user double-clicks below the list of tables to create a column
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 380, 100 , 2);
		
		// Table has a column
		assertEquals(1, tablr.getColumns(tablr.getTables().get(0)).size());
		
		// The user double-clicks the table name to open the table rows mode subwindow
		myCW.handleMouseEvent(0, 60, 40, 2);
		
		// A Table Rows mode subwindow is shown.
		assertEquals(1, tablr.getTableRowsUIs().size());
	}
	
	/**
	 * Deletes a table
	 */
	@Test
	public void dragTablesMode() {
		// Load the window
	MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
	Tablr tablr = myCW.getTablr();
	
	// Confirm the first position of the tables mode
//	assertEquals(tablr.getTablesModeUIs().get(0).getX(), 0);
//	assertEquals(tablr.getTablesModeUIs().get(0).getY(), 0);

	// Perform a ctrl+T
	MyCanvasWindow.replayRecording("./recordings/dragTablesMode/test", myCW);
	
	// Check the changed position of the subwindow
	assertEquals(tablr.getTablesModeUIs().get(0).getX(), 0);
	assertEquals(tablr.getTablesModeUIs().get(0).getY(), 19);
	}
	
	
}
