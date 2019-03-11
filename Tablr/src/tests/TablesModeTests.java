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
	

	private CommunicationManager coMan;

	@Before
	public void setUp() throws Exception {
		coMan = new CommunicationManager();
		coMan.loadUI(Loadable_Interfaces.TABLES);
	}

	@Test
	public void test() {
		assertTrue(true);
	}

	/**
	 * use case 4.1: Create a table
	 */
	@Test
	public void useCase1() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		// There are no tables yet
		assertTrue(coMan.getTables().size() == 0);
		// Step 2: The user double-clicks below the list of tables
		myCW.handleMouseEvent(0, 40, 530 , 2);
		// Step 3: Therer is a table added to the tables list
		CommunicationManager coMan = myCW.getCommunicationManager();
		assertTrue(coMan.getTables().size() == 1);
	}
	
	
	/**
	 * use case 4.2: Edit table name
	 */
	@Test
	public void useCase2() {
		
	}
	
}
