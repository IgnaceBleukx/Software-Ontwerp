package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import canvaswindow.CanvasWindow;
import canvaswindow.MyCanvasWindow;
import facades.CommunicationManager;
import ui.Loadable_Interfaces;
import uielements.UI;

public class TablesModeTests {
	
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * use case 4.1: Create a table
	 */
	@Test
	public void useCase1() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		CommunicationManager coMan = myCW.getCommunicationManager();
		// There are no tables yet
		assertTrue(coMan.getTables().size() == 0);
		// Step 2: The user double-clicks below the list of tables
		myCW.handleMouseEvent(0, 40, 530 , 2);
		// Step 3: Thereris a table added to the tables list
		assertTrue(coMan.getTables().size() == 1);
	}
	
	
	/**
	 * use case 4.2: Edit table name
	 */
	@Test
	public void useCase2() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		CommunicationManager coMan = myCW.getCommunicationManager();
		// Create an empty table with a simulated double click
		myCW.handleMouseEvent(0, 40, 530, 2);
		// Check the name of the added table
		System.out.println(coMan.getTables().get(0).getName());
		assertEquals("Table0",coMan.getTables().get(0).getName());
		// Step 2: The user clicks a table name
		myCW.handleMouseEvent(0, 51, 13, 1);
		// Step 3: Remove the last character of the highlighted table name
		// (8 is backspace)
		myCW.handleKeyEvent(1, 8, ' ');
		assertEquals("Table", coMan.getTables().get(0).getName());
	}
	
}
