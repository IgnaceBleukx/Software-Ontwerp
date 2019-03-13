package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import canvaswindow.MyCanvasWindow;
import facades.CommunicationManager;
import ui.Loadable_Interfaces;
import uielements.TextField;
import uielements.UIRow;
import uielements.UITable;

public class TableRowsTests {
	
	public MyCanvasWindow prepareTable(){
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		CommunicationManager coMan = myCW.getCommunicationManager();
		coMan.clearUI();
		coMan.loadUI(Loadable_Interfaces.TABLES);
		//Loading a table and filling it with Columns:
		//Creating table:
		myCW.handleMouseEvent(0, 40, 530 , 2);
		//Going into tables mode:
		myCW.handleMouseEvent(0,85, 30, 2);
		//Adding columns:
		myCW.handleMouseEvent(0, 260, 230, 2);
		myCW.handleMouseEvent(0, 260, 260, 2);
		myCW.handleMouseEvent(0, 260, 290, 2);
		myCW.handleMouseEvent(0, 260, 320, 2);

		//Changing default value for column0.
		myCW.handleMouseEvent(0,500,50, 1);
		String d1 = "default";
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 0, d1.charAt(i));
		}
		//Changing default value for column1:
		myCW.handleMouseEvent(0, 300, 100, 1);
		myCW.handleMouseEvent(0, 500, 100, 1);
		String d2 = "default@email";
		for(int i=0;i<d2.length();i++){
			myCW.handleKeyEvent(0, 0, d2.charAt(i));
		}
		//Changing default value for column2:
		myCW.handleMouseEvent(0, 300,150,1);
		myCW.handleMouseEvent(0, 300,150,1);
		//Changing default value for column3:
		myCW.handleMouseEvent(0, 300,200,1);
		myCW.handleMouseEvent(0, 300,200,1);
		myCW.handleMouseEvent(0, 300,200,1);
		myCW.handleMouseEvent(0, 500,200, 1);
		String d4 = "999";
		for(int i=0;i<d4.length();i++){
			myCW.handleKeyEvent(0, 0, d4.charAt(i));
		}
		myCW.handleKeyEvent(0,10,' ');
		//Escaping to table mode:
		myCW.handleKeyEvent(0,27,' ');
		return myCW;
		
	}
	
	
	@Test
	public void useCase8(){
	MyCanvasWindow myCW = prepareTable();
	CommunicationManager coMan = myCW.getCommunicationManager();
	
	//Entering table_rows mode:
	myCW.handleMouseEvent(0,85, 30, 2);
	assertEquals(Loadable_Interfaces.TABLE_ROWS,coMan.getMode());
	// Step 1: The user double clicks below the list of tables:
	myCW.handleMouseEvent(0, 40, 530 , 2);
	/* Step 2: The system adds a new row to the end of the table.
				Its value for each column is the columns default. */
	assertEquals(1,coMan.getActiveTable().getRows().size());
	assertEquals("default",coMan.getValue(coMan.getColumns(coMan.getActiveTable()).get(0),0));
	assertEquals("default@email",coMan.getValue(coMan.getColumns(coMan.getActiveTable()).get(1),0));
	assertNull(coMan.getValue(coMan.getColumns(coMan.getActiveTable()).get(2),0));
	assertEquals(999,coMan.getValue(coMan.getColumns(coMan.getActiveTable()).get(3),0));


}

	/**
	 * use Case 4.9: Edit row value
	 */
	@Test
	public void useCase9() {
//		// Step 1: Load the window
//		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
//		CommunicationManager coMan = myCW.getCommunicationManager();
//		coMan.clearUI();
//		coMan.loadUI(Loadable_Interfaces.TABLES);
//		// The user double-clicks below the list of tables to create a table
//		myCW.handleMouseEvent(0, 40, 530 , 2);
//		
//		//The user double-clicks a table name.
//		myCW.handleMouseEvent(0, 51, 13, 2);
//		
//		// The user double-clicks to create a column
//		myCW.handleMouseEvent(0, 40, 530 , 2);
//				
//		// go back to TABLES_MODE
//		myCW.handleKeyEvent(0, 27, ' ');
//			
//		//The user double-clicks the table name. (Go back to TABLE_ROWS
		MyCanvasWindow myCW = prepareTable();
		CommunicationManager coMan = myCW.getCommunicationManager();
		
		myCW.handleMouseEvent(0, 51, 13, 2);
				
		assertEquals(Loadable_Interfaces.TABLE_ROWS, coMan.getMode());
				
		// Add a row
		myCW.handleMouseEvent(0, 11, 31 , 2);
		
		// Step 1: Click value of first row for first column
		myCW.handleMouseEvent(0, 40, 70 , 1);

		// Step 2: The system indicates that this row is now selected.
		TextField textField = (TextField) coMan.getActiveUI().locatedAt(40, 70);
		assertTrue(textField.isSelected());
	}
	
	/**
	 * use case 4.10: Delete Row 
	 */
	@Test
	public void useCase10() {
//		// Step 1: Load the window
//		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
//		CommunicationManager coMan = myCW.getCommunicationManager();
//		coMan.clearUI();
//		coMan.loadUI(Loadable_Interfaces.TABLES);
//		// The user double-clicks below the list of tables to create a table
//		myCW.handleMouseEvent(0, 40, 530 , 2);
//		
//		//The user double-clicks a table name.
//		myCW.handleMouseEvent(0, 51, 13, 2);
//		
//		// The user double-clicks to create a column
//		myCW.handleMouseEvent(0, 40, 530 , 2);
//		
//		// go back to TABLES_MODE
//		myCW.handleKeyEvent(0, 27, ' ');
		
		MyCanvasWindow myCW = prepareTable();
		CommunicationManager coMan = myCW.getCommunicationManager();
		
		// Step 1: The user double-clicks the table name.
		myCW.handleMouseEvent(0, 51, 13, 2);
		
		assertEquals(Loadable_Interfaces.TABLE_ROWS, coMan.getMode());
		
		// Add a row
		myCW.handleMouseEvent(0, 11, 31 , 2);
		
		// Step 1: The user clicks the margin to the left of first row.
		myCW.handleMouseEvent(0, 11, 61 , 1);
		
		// Step 2: The system indicates that this row is now selected.
		UIRow r = (UIRow) coMan.getActiveUI().locatedAt(11, 61);
		UITable table = (UITable) coMan.getActiveUI().locatedAt(200, 200);
		assertEquals(table.getSelected(), r);

		// Step 3: User presses Delete key
		myCW.handleKeyEvent(0, 127, ' ');
		
		// Step 4: The system removes the row from the table and shows the updated list of rows
		assertEquals(0,coMan.getTables().get(0).getRows().size());
		assertEquals(0,table.getRows().size());
	}
	
}

