package tests;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import org.junit.Test;

import uielements.Button;
import uielements.ListView;
import uielements.TextField;
import canvaswindow.MyCanvasWindow;
import facades.Tablr;

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
	
		// Drag the tables mode subwindow to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 140, 2, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 140, -18, 1);
		
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
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add 7 tables
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
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 293, 132, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 293, 170, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 293, 170, 1);
		
		//Check the name of the first shown table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 75, 55, 1);
		t = (TextField) tablr.getUIAt(75, 55).locatedAt(75, 55);
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
		
		// Add 7 tables
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
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 75, 55, 1);
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
		
		// Perform a ctrl+T to add tables mode subwindow
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');
		
		// Add a tables
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 285, 2);
		
		// Click the table name
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add a column to the table and open rows mode
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 455, 168, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 55, 50, 2);
		
		// Add 8 rows to the table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 535, 580, 2);
		
		// Change text of first cell
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 345, 365, 1);
		myCW.handleKeyEvent(0, 0, 'a');
		
		// Drag the horizontal scrollbar to the right
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 593, 400, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 593, 446, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 593, 446, 1);
		
		TextField t = (TextField) tablr.getUIAt(345, 365).locatedAt(345, 365);
		assertEquals("", t.getText());
	}
}
