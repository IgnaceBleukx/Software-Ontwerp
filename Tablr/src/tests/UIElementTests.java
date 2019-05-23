package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import Utils.DebugPrinter;
import uielements.Button;
import uielements.Checkbox;
import uielements.Text;
import uielements.TextField;
import uielements.UITable;
import canvaswindow.CanvasWindow;
import canvaswindow.MyCanvasWindow;
import domain.Table;
import facades.Tablr;
import ui.FormsModeUI;
import ui.TableDesignModeUI;
import ui.TableRowsModeUI;
import ui.TablesModeUI;

public class UIElementTests {

	/**
	 * Drag the tables mode subwindow 10 pixels to the right
	 */
	@Test
	public void dragTablesMode() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Confirm the first position of the tables mode
		assertEquals(0, tablr.getTablesModeUIs().get(0).getX());
		assertEquals(0, tablr.getTablesModeUIs().get(0).getY());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 0, 0, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 0, 10, 1);
		
		// Check the changed position of the subwindow
		assertEquals(0, tablr.getTablesModeUIs().get(0).getX());
		assertEquals(10, tablr.getTablesModeUIs().get(0).getY());
	}	
	
	/**
	 * Resize the Tables Mode Subwindow
	 */
	@Test
	public void resizeRight() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Confirm the first position of the tables mode
		assertEquals(300, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(300, tablr.getTablesModeUIs().get(0).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 300, 113, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 320, 113, 1);
		
		// Check the changed position of the subwindow
		assertEquals(320, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(300, tablr.getTablesModeUIs().get(0).getHeight());
	}

	@Test
	public void resizeLeft() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Confirm the first position of the tables mode
		assertEquals(300, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(300, tablr.getTablesModeUIs().get(0).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 3, 85, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, -17, 85, 1);
		
		// Check the changed position of the subwindow
		assertEquals(320, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(300, tablr.getTablesModeUIs().get(0).getHeight());
	}
	
	@Test
	public void resizeBottom() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Confirm the first position of the tables mode
		assertEquals(300, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(300, tablr.getTablesModeUIs().get(0).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 130, 299, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 130, 399, 1);
		
		// Check the changed position of the subwindow
		assertEquals(300, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(400, tablr.getTablesModeUIs().get(0).getHeight());
	}	
	
	@Test
	public void resizeTop() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Confirm the first position of the tables mode
		assertEquals(300, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(300, tablr.getTablesModeUIs().get(0).getHeight());
	
		// Drag the tables mode subwindow to the the bottom
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 140, 10, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 140, 95, 1);
		// Resize the top
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 140, 87, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 140, 67, 1);
		
		
		// Check the changed position of the subwindow
		assertEquals(300, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(320, tablr.getTablesModeUIs().get(0).getHeight());
	}
	
	@Test
	public void resizeCorner() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Confirm the first position of the tables mode
		assertEquals(300, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(300, tablr.getTablesModeUIs().get(0).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 300, 300, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 305, 305, 1);
		
		// Check the changed width of the subwindow
		assertEquals(305, tablr.getTablesModeUIs().get(0).getWidth());
		assertEquals(305, tablr.getTablesModeUIs().get(0).getHeight());
	}
	
	@Test 
	public void verticalScrollBar() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
//		// Perform a ctrl+T to add tables mode subwindow
//		myCW.handleKeyEvent(1, 17, ' ');
//		myCW.handleKeyEvent(1, 84, ' ');
//		
//		// Add 8 tables
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		
//		// Check the name of the first shown table
//		TextField t = (TextField) tablr.getUIAt(75, 55).locatedAt(75, 55);
//		assertEquals("Table0", t.getText());
//	
//		// Drag the vertical scrollbar to the bottom
//		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 293, 105, 1);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 293, 145, 1);
		
		CanvasWindow.replayRecording("recordings/VerticalScrollBar/test.test", myCW);
		
		//Check the name of the first shown table
		TextField t = (TextField) tablr.getUIAt(75, 55).locatedAt(75, 55);
		assertEquals("Table1", t.getText());
	}
	
	/**
	 *  Click at free space instead of dragging the scrollbar
	 */
	@Test 
	public void verticalScrollBarClick() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add 8 tables
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// Check the name of the first shown table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 75, 55, 1);
		TextField t = (TextField) tablr.getUIAt(75, 55).locatedAt(75, 55);
		assertEquals("Table0", t.getText());
		
		// Drag the vertical scrollbar to the bottom
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 293, 280, 1);
		
		//Check the name of the first shown table
		t = (TextField) tablr.getUIAt(75, 55).locatedAt(75, 55);
		assertEquals("Table1", t.getText());
	
		// Drag the vertical scrollbar to the top again
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 293, 41, 1);
		
		//Check the name of the first shown table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 75, 55, 1);
		t = (TextField) tablr.getUIAt(75, 55).locatedAt(75, 55);
		assertEquals("Table0", t.getText());
	}
	
	@Test
	public void HorizontalScrollBar() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a tables
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// The clicked element is a button
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 23, 49, 1);
		Button b = (Button) tablr.getUIAt(23,49).locatedAt(23, 49);
		
		// Resize the subwindow, so it is possible to use the horizontalScrollbar
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 299, 150, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 213, 150, 1);
		
		// Drag the horizontal scrollbar to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 49, 295, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 76, 295, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 76, 295, 1);
		
		//The clicked element is now a textfield
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 23, 49, 1);
		TextField t = (TextField) tablr.getUIAt(23, 49).locatedAt(23, 49);
	}
	
	/**
	 *  Click at free space instead of dragging the scrollbar
	 */
	@Test
	public void HorizontalScrollBarClick() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a tables
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// The clicked element is a button
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 23, 49, 1);
		Button b = (Button) tablr.getUIAt(23,49).locatedAt(23, 49);
		
		// Resize the subwindow, so it is possible to use the horizontalScrollbar
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 299, 150, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 213, 150, 1);
		
		// Drag the horizontal scrollbar to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 171, 293, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 171, 293, 1);
		
		//The clicked element is now a textfield
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 23, 49, 1);
		TextField t = (TextField) tablr.getUIAt(23, 49).locatedAt(23, 49);
	}
	
	@Test
	public void dragTablesModeSubwindow() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a tables
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// Check the name of the first table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 50, 1);
		TextField t = (TextField) tablr.getUIAt(60, 50).locatedAt(60, 50);
		assertEquals("Table0", t.getText());
		
		// Drag the horizontal scrollbar to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 141, 12, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 141, 312, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 141, 312, 1);
		
		//Check if the table name is dragged
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 355, 1);
		t = (TextField) tablr.getUIAt(60, 355).locatedAt(60, 355);
		assertEquals("Table0", t.getText());		
	}

	@Test
	public void VerticalScrollListenersUITable() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
//		
//		// Perform a ctrl+T to add tables mode subwindow
//		myCW.handleKeyEvent(1, 17, ' ');
//		myCW.handleKeyEvent(1, 84, ' ');
//		
//		// Add a tables
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		
//		// Click the table name
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
//		
//		// Add a column to the table and open rows mode
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
//		
//		// Add 8 rows to the table
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
//		
//		// Change text of first cell
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 345, 365, 1);
//		myCW.handleKeyEvent(0, 0, 'a');
//		myCW.handleKeyEvent(0, 10, ' ');
//		
//		// Drag the vertical scrollbar to the bottom
//		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 593, 400, 1);
//		snapShot(myCW,"pic1.png");
//		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 593, 455, 1);
//		snapShot(myCW,"pic2.png");
//		myCW.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 593, 455, 1);
//		snapShot(myCW,"pic3.png");
		
		CanvasWindow.replayRecording("recordings/VerticalScrollBarUITable/test.test",myCW);
		
		
		TextField t = (TextField) tablr.getUIAt(345, 356).locatedAt(345, 356);
		snapShot(myCW,"pic.png");
		assertEquals("", t.getText());
	}
	
	@Test
	public void HorizontalScrollListenersUITable() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
//		
//		// Perform a ctrl+T to add tables mode subwindow
//		myCW.handleKeyEvent(1, 17, ' ');
//		myCW.handleKeyEvent(1, 84, ' ');
//		
//		// Add a tables
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
//		
//		// Click the table name
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
//		
//		// Add four columns to the table and open rows mode
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
//		
//		// Add a row to the table
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 390, 2);
//		
//		// Change text of first cell
//		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 345, 365, 1);
//		myCW.handleKeyEvent(0, 0, 'a');
//		myCW.handleKeyEvent(KeyEvent.KEY_TYPED, 10, ' '); 
//		
//		// Drag the horizontal scrollbar to the right
//		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 373, 590, 1);
//		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 488, 590, 1);
		
		CanvasWindow.replayRecording("recordings/HorizontalScrollBarUITable/test.test", myCW);
		
		TextField t = (TextField) tablr.getUIAt(345, 365).locatedAt(345, 365);
		assertEquals("", t.getText());
	}
	
	@Test
	public void UITableVaria() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a tables
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// Click the table name
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add four columns to the table and open rows mode
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add a row to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 390, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 390, 2);

		UITable uit = (UITable) tablr.getUIAt(435, 480).locatedAt(435, 480);
		assertFalse(uit.getError());

		assertEquals(2, tablr.getRows(tablr.getTables().get(0)));
		
		// Click the second row and delete it
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 311, 390, 1);
		myCW.handleKeyEvent(1, 127, ' ');
		
		assertEquals(1, tablr.getRows(tablr.getTables().get(0)));
	}
	
	@Test
	public void resizeLeftRowsMode() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// Click the table name
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add four columns to the table and open rows mode
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add a row to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 390, 2);
		
		Table table = tablr.getTables().get(0);
		DebugPrinter.print(tablr.getTableRowsUIs(table).get(0));
		// Confirm the first width and height of the tables mode
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getHeight());
	
		// Drag the tables mode subwindow to the left
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 302, 400, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 297, 400, 1);
		
		// Check the changed width and height of the subwindow
		assertEquals(305, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getHeight());
	
	}
	
	@Test
	public void resizeRightRowsMode() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// Click the table name
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add four columns to the table and open rows mode
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add a row to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 390, 2);
		
		Table table = tablr.getTables().get(0);
		DebugPrinter.print(tablr.getTableRowsUIs(table).get(0));
		// Confirm the first width and height of the tables mode
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 599, 451, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 604, 451, 1);
		
		// Check the changed width and height of the subwindow
		assertEquals(305, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getHeight());
	}
	
	@Test
	public void resizeTopRowsMode() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// Click the table name
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add four columns to the table and open rows mode
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add a row to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 390, 2);
		
		Table table = tablr.getTables().get(0);
		DebugPrinter.print(tablr.getTableRowsUIs(table).get(0));
		// Confirm the first width and height of the tables mode
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 444, 300, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 444, 250, 1);
		
		// Check the changed width and height of the subwindow
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getWidth());
		assertEquals(350, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getHeight());
	}
	
	@Test
	public void resizeBottomRowsMode() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// Click the table name
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add one column to the table and open rows mode
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add a row to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 390, 2);
		
		Table table = tablr.getTables().get(0);
		DebugPrinter.print(tablr.getTableRowsUIs(table).get(0));
		// Confirm the first width and height of the tables mode
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 442, 597, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 442, 602, 1);
		
		// Check the changed width and height of the subwindow
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getWidth());
		assertEquals(305, ((TableRowsModeUI) tablr.getTableRowsUIs(table).get(0)).getHeight());
	}
	
	@Test
	public void resizeLegendTablesMode() {
		// Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a table to the list
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED,100,150,2);
		
		Text tableName = (Text) tablr.getUIAt(100,100).locatedAt(45,20);
		Text query = (Text) tablr.getUIAt(100, 100).locatedAt(150, 20);
		TextField tableNameInput = (TextField) tablr.getUIAt(100,100).locatedAt(45, 40);
		TextField queryInput = (TextField) tablr.getUIAt(100, 100).locatedAt(150, 40);
		int tableNameWidth = tableName.getWidth();
		int queryX = query.getX();
		int tableNameInputWidth = tableNameInput.getWidth();
		int queryInputX = queryInput.getX();
		
		//Drag the table name dragger 30px to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 135, 20, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 165, 20, 1);
		
		assertEquals(30,tableName.getWidth()-tableNameWidth);
		assertEquals(-30,queryX-query.getX());
		assertEquals(30,tableNameInput.getWidth()-tableNameInputWidth);
		assertEquals(-30,queryInputX-queryInput.getX());
	}
	
	@Test
	public void resizeLegendDesignMode() {
		//Load window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a table to the list
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED,100,150,2);
		//Open table design mode for table0
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 45, 40,2);
		//Add a column to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 150, 2);
		
		Text name = (Text) tablr.getUIAt(370, 25).locatedAt(370,25);
		Text type = (Text) tablr.getUIAt(370, 25).locatedAt(450,25);
		Text blank = (Text) tablr.getUIAt(370, 25).locatedAt(490,25);
		Text def = (Text) tablr.getUIAt(370, 25).locatedAt(550,25);
		TextField nameInput = (TextField) tablr.getUIAt(370, 25).locatedAt(370,45);
		Text typeInput = (Text) tablr.getUIAt(370, 25).locatedAt(450,45);
		Checkbox blankInput = (Checkbox) tablr.getUIAt(370, 25).locatedAt(490,45);
		TextField defInput = (TextField) tablr.getUIAt(370, 25).locatedAt(550,45);

		int nameWidth = name.getWidth();
		int typeWidth = type.getWidth();
		int blankX = blank.getX();
		int defX = def.getX();
		int nameInputWidth = nameInput.getWidth();
		int typeInputWidth = typeInput.getWidth();
		int blankInputX = blankInput.getX();
		int defInputX = defInput.getX();
		
		//Drag typedragger 30px to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 475,25, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 505,25, 1);

		assertEquals(name.getWidth(),nameWidth);
		assertEquals(30,type.getWidth()-typeWidth);
		assertEquals(30,blank.getX()-blankX);
		assertEquals(30,def.getX()-defX);
		assertEquals(nameInput.getWidth(),nameInputWidth);
		assertEquals(30,typeInput.getWidth()-typeInputWidth);
		assertEquals(30,blankInput.getX()-blankInputX);
		assertEquals(30,defInput.getX()-defInputX);
		
	}

	@Test
	public void resizeLegendRowsMode() {
		//Load window
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design Mode");
		Tablr tablr = myCW.getTablr();
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a table to the list
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED,100,150,2);
		//Open table design mode for table0
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 45, 40,2);
		//Add some columns to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 150, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 150, 2);
		//Open table rows mode
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 80, 50, 2);
		//Add row
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 500, 2);
		
		
		Text col0Label = (Text) tablr.getUIAt(370, 330).locatedAt(370, 330);
		Text col1Label = (Text) tablr.getUIAt(370, 330).locatedAt(470, 330);
		TextField col0Input = (TextField) tablr.getUIAt(370, 330).locatedAt(370, 360);
		TextField col1Input = (TextField) tablr.getUIAt(370, 330).locatedAt(470, 360);

		int col0LabelWidth = col0Label.getWidth();
		int col1LabelX = col1Label.getX();
		int col0InputWidth = col0Input.getWidth();
		int col1InputX = col1Input.getX();
		
		//Drag the column0 dragger 30px to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 425, 325, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 455, 325, 1);

		snapShot(myCW,"rowsMode.png");
		
		assertEquals(30, col0Label.getWidth()-col0LabelWidth);
		assertEquals(30, col1Label.getX()-col1LabelX);
		assertEquals(30, col0Input.getWidth()-col0InputWidth);
		assertEquals(30, col1Input.getX()-col1InputX);
		
	}
	
	@Test
	public void cloneTablesModeUI() {
		//Load window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		//Create a new tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		//Resize the tables subwindow to a width of 400px
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED,299,150,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 399, 150, 1);
				
		TablesModeUI tablesMode1 = (TablesModeUI) tablr.getUIAt(100, 100);
		//Create another tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		assertEquals(2,tablr.getTablesModeUIs().size());
		assertNotEquals(tablesMode1,tablr.getUIAt(100, 100));
		assertEquals(400, tablr.getUIAt(100, 100).getWidth());
	}
	
	@Test
	public void cloneTableDesignModeUI() {
		//Load window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		//Create a new tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		//Add a table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 100, 150,2);
		//Open a first tables design mode window
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 70, 50, 2);
		//Close the tables mode window
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 287, 11, 1);
		
		//Resize the tables design subwindow
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED,302,150,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 202, 150, 1);
		TableDesignModeUI designMode = (TableDesignModeUI) tablr.getUIAt(400, 150);
		
		//Open another tables subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		//Open antoher tables design subwindow
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 70, 50, 2);

		assertEquals(2,tablr.getTableDesignUIs(tablr.getTables().get(0)).size());
		assertNotEquals(designMode,tablr.getUIAt(400, 150));
		assertEquals(400,tablr.getUIAt(400, 150).getWidth());
	}

	@Test
	public void cloneTableRowsModeUI(){
		//Load window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		//Create a new tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		//Add a table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 100, 150,2);
		//Open a first tables design mode window
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 70, 50, 2);
		//Add a column to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED,400,250,2);
		//Open a first table rows mode for the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 70, 50, 2);
		
		//Resize the table rows subwindow
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 301, 400, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 201, 400, 1);
		TableRowsModeUI rowsMode = (TableRowsModeUI) tablr.getUIAt(400, 400);
		//Open a second rowsMode for the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 70, 50, 2);

		assertEquals(2,tablr.getTableRowsUIs(tablr.getTables().get(0)).size());
		assertNotEquals(rowsMode, tablr.getUIAt(400, 500));
		assertEquals(400,tablr.getUIAt(400, 500).getWidth());
	}
	
	@Test
	public void cloneFormsModeUI() {
		//Load window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		Tablr tablr = myCW.getTablr();
		
		//Create a new tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		//Add a table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 100, 150,2);
		//Open a first forms mode ui for the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 25, 55, 1);
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 70, ' ');
		

		//Resize the forms mode to a width of 400px
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED,299,450,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 399,450, 1);
		FormsModeUI formsMode = (FormsModeUI) tablr.getUIAt(150, 500);
		
		//Open a second forms mode for the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 25, 55, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 25, 55, 1);
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 70, ' ');
		
		assertEquals(2,tablr.getFormsModeUIs(tablr.getTables().get(0)).size());
		assertNotEquals(formsMode, tablr.getUIAt(150, 500));
		assertEquals(400, tablr.getUIAt(150, 500).getWidth());
		
	}
	
	private void snapShot(MyCanvasWindow myCW,String out) {
		File outputFile = new File(out);
		try {
			ImageIO.write(myCW.captureImage(), "png", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
