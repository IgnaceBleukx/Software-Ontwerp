package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import canvaswindow.MyCanvasWindow;
import facades.CommunicationManager;
import ui.Loadable_Interfaces;
import uielements.TextField;
import uielements.UIRow;

public class TableRowsTests {
	
	/**
	 * use case 4.10: Delete Row 
	 * TODO: Add row werkt hier niet
	 */
	@Test
	public void useCase10() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		CommunicationManager coMan = myCW.getCommunicationManager();
		coMan.clearUI();
		coMan.loadUI(Loadable_Interfaces.TABLES);
		
		// The user double-clicks below the list of tables to create a table
		myCW.handleMouseEvent(0, 40, 530 , 2);
		
		//The user double-clicks a table name.
		myCW.handleMouseEvent(0, 51, 13, 2);
		
		// The user double-clicks below the list of tables to create a column
		myCW.handleMouseEvent(0, 40, 530 , 2);
		
		// go back to TABLES_MODE
		myCW.handleKeyEvent(0, 27, ' ');
		
		// Step 1: The user double-clicks a table name.
		myCW.handleMouseEvent(0, 51, 13, 2);
		
		assertEquals(Loadable_Interfaces.TABLE_ROWS, coMan.getMode());
		
		// Add a row
		myCW.handleMouseEvent(0, 11, 31 , 1);

		System.out.println(coMan.getActiveTable().getRows());
		
		// Step 1: The user clicks the margin to the left of first row.
		myCW.handleMouseEvent(0, 11, 61 , 1);
		
		
		//Step 2: The system indicates that this row is now selected.
		UIRow r = (UIRow) coMan.getActiveUI().locatedAt(10, 60);
		assertEquals(r.isSelected(), true);
		
		// ...
	}

}

