package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import uielements.Button;
import uielements.ListView;
import uielements.Text;
import uielements.TextField;
import uielements.UIElement;
import uielements.UITable;
import canvaswindow.CanvasWindow;
import canvaswindow.MyCanvasWindow;
import facades.Tablr;
import ui.TableRowsModeUI;

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
		
		CanvasWindow.replayRecording("./recordings/VerticalScrollBarTest/test.test", myCW);
		
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
		
		CanvasWindow.replayRecording("./recordings/VerticalScrollListenersUITableTest/test.test",myCW);
		
		
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
		
		CanvasWindow.replayRecording("./recordings/HorizontalScrollListenersUITableTest/test.test", myCW);
		
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
		
		System.out.println(tablr.getTableRowsUIs().entrySet().iterator().next().getValue());
		// Confirm the first width and height of the tables mode
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getHeight());
	
		// Drag the tables mode subwindow to the left
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 302, 400, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 297, 400, 1);
		
		// Check the changed width and height of the subwindow
		assertEquals(305, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getHeight());
	
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
		
		System.out.println(tablr.getTableRowsUIs().entrySet().iterator().next().getValue());
		// Confirm the first width and height of the tables mode
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 599, 451, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 604, 451, 1);
		
		// Check the changed width and height of the subwindow
		assertEquals(305, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getHeight());
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
		
		System.out.println(tablr.getTableRowsUIs().entrySet().iterator().next().getValue());
		// Confirm the first width and height of the tables mode
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 444, 300, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 444, 250, 1);
		
		// Check the changed width and height of the subwindow
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getWidth());
		assertEquals(350, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getHeight());
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
		
		System.out.println(tablr.getTableRowsUIs().entrySet().iterator().next().getValue());
		// Confirm the first width and height of the tables mode
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getWidth());
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getHeight());
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 442, 597, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 442, 602, 1);
		
		// Check the changed width and height of the subwindow
		assertEquals(300, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getWidth());
		assertEquals(305, ((TableRowsModeUI) tablr.getTableRowsUIs().entrySet().iterator().next().getValue().get(0)).getHeight());
	}
	
	/**
	 * Not yet tested
	 */
	@Test
	public void resizeLegend() {
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
		
		// Add a column to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);

		TextField t = (TextField) tablr.getUIAt(394, 50).locatedAt(394, 50);
		
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 425, 30, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 370, 30, 1);

		Text te = (Text) tablr.getUIAt(394, 50).locatedAt(394, 50);
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
