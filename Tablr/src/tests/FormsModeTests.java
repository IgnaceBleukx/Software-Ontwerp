package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import canvaswindow.MyCanvasWindow;
import facades.Tablr;
import ui.FormsModeUI;

public class FormsModeTests {

	/** 
	* Use case 9: Add a row in forms mode
	 * 
	 */
	@Test
	public void useCase9(){
		MyCanvasWindow myCW = new MyCanvasWindow("TestWindow");
		Tablr tablr = myCW.getTablr();
	
		//The user opens a tables mode subwindow
		myCW.handleKeyEvent(KeyEvent.KEY_PRESSED, 17,Character.MIN_VALUE);
		myCW.handleKeyEvent(KeyEvent.KEY_PRESSED, 84,Character.MIN_VALUE);
		//The user adds a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 100, 200, 2);
		//The user opens the table design mode ui
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 50, 2);
		//The user adds 2 columns
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 200, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 200, 2);
		//The user selects a table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 15, 50, 1);
		//The user presses CTRL+F
		myCW.handleKeyEvent(KeyEvent.KEY_PRESSED, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(0, 70, Character.MIN_VALUE);
		//The forms mode is opened
		assertTrue(tablr.getUIAt(100, 500) instanceof FormsModeUI);
		//The user presses CTRL+N to add a new row
		myCW.handleKeyEvent(KeyEvent.KEY_PRESSED, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(0, 78, Character.MIN_VALUE);
		assertEquals(1,tablr.getRows(tablr.getTables().get(0)));
	}
	
	
	/** 
	* Use case 10: Remove a row in forms mode
	 * 
	 */
	@Test
	public void useCase10(){
		MyCanvasWindow myCW = new MyCanvasWindow("TestWindow");
		Tablr tablr = myCW.getTablr();
	
		//The user opens a tables mode subwindow
		myCW.handleKeyEvent(KeyEvent.KEY_PRESSED, 17,Character.MIN_VALUE);
		myCW.handleKeyEvent(KeyEvent.KEY_PRESSED, 84,Character.MIN_VALUE);
		//The user adds a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 100, 200, 2);
		//The user opens the table design mode ui
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 50, 2);
		//The user adds 2 columns
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 200, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 400, 200, 2);
		//The user selects a table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 15, 50, 1);
		//The user presses CTRL+F
		myCW.handleKeyEvent(KeyEvent.KEY_PRESSED, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(0, 70, Character.MIN_VALUE);
		//The forms mode is opened
		assertTrue(tablr.getUIAt(100, 500) instanceof FormsModeUI);
		//The user presses CTRL+N to add a new row
		myCW.handleKeyEvent(KeyEvent.KEY_PRESSED, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(0, 78, Character.MIN_VALUE);
		assertEquals(1,tablr.getRows(tablr.getTables().get(0)));
		
		//The user presses CTRL+D to delete the current row
		myCW.handleKeyEvent(KeyEvent.KEY_PRESSED, 17, Character.MIN_VALUE);
		myCW.handleKeyEvent(0, 68, Character.MIN_VALUE);
		assertEquals(0,tablr.getRows(tablr.getTables().get(0)));
	}

}
