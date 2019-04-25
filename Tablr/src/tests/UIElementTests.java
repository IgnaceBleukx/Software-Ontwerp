package tests;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import org.junit.Test;

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
}
