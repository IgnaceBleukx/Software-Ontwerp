package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import canvaswindow.MyCanvasWindow;
import domain.Type;
import facades.CommunicationManager;
import ui.Loadable_Interfaces;
import uielements.Checkbox;
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
		//Going into tables design mode:
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
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		CommunicationManager coMan = myCW.getCommunicationManager();
		coMan.clearUI();
		coMan.loadUI(Loadable_Interfaces.TABLES);
		//Loading a table and filling it with Columns:
		//Creating table:
		myCW.handleMouseEvent(0, 40, 530 , 2);
		//Going into tables design mode:
		myCW.handleMouseEvent(0,85, 30, 2);
		//Adding columns:
		myCW.handleMouseEvent(0, 260, 230, 2);
		myCW.handleMouseEvent(0, 260, 260, 2);
		myCW.handleMouseEvent(0, 260, 290, 2);
		myCW.handleMouseEvent(0, 260, 320, 2);
		myCW.handleMouseEvent(0, 260, 510, 2);
		myCW.handleMouseEvent(0, 260, 510, 2);
		myCW.handleMouseEvent(0, 260, 510, 2);
		myCW.handleMouseEvent(0, 260, 510, 2);

		//Changing default value for column0.
		// String met default = "default" en Blanks_al = true
		myCW.handleMouseEvent(0,500,50, 1);
		String d1 = "default";
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 0, d1.charAt(i));
		}
		//Changing default value for column1.
		// String met default = "default" en Blanks_al = false
		myCW.handleMouseEvent(0,500,100, 1);
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 0, d1.charAt(i));
		}		
		//Toggle Blanks
		myCW.handleMouseEvent(0, 400, 100, 1);
		
		//Changing default value for column2:
		// Email met default = "default@email" en Blanks_al = true
		myCW.handleMouseEvent(0, 300, 150, 1);
		myCW.handleMouseEvent(0, 500, 150, 1);
		String d2 = "default@email";
		for(int i=0;i<d2.length();i++){
			myCW.handleKeyEvent(0, 0, d2.charAt(i));
		}
		
		//Changing default value for column3:
		// Email met default = "default@email" en Blanks_al = false
		myCW.handleMouseEvent(0, 300, 200, 1);
		myCW.handleMouseEvent(0, 500, 200, 1);
		for(int i=0;i<d2.length();i++){
			myCW.handleKeyEvent(0, 0, d2.charAt(i));
		}
		//Toggle Blanks
		myCW.handleMouseEvent(0, 400, 200, 1);
		
		//Changing default value for column4:
		// Boolean met default = grijs  en Blanks_al = true
		myCW.handleMouseEvent(0, 300,250,1);
		myCW.handleMouseEvent(0, 300,250,1);
		

		//Changing default value for column5:
		// Boolean met default = true  en Blanks_al = false
		myCW.handleMouseEvent(0, 300,300,1);
		myCW.handleMouseEvent(0, 300,300,1);
		//set default
		myCW.handleMouseEvent(0, 500,300,1);
		//Toggle Blanks
		myCW.handleMouseEvent(0, 400, 300, 1);
		
		
		//Changing default value for column6:
		// Integer met default = 999  en Blanks_al = true
		myCW.handleMouseEvent(0, 300,350,1);
		myCW.handleMouseEvent(0, 300,350,1);
		myCW.handleMouseEvent(0, 300,350,1);
		myCW.handleMouseEvent(0, 500,350, 1);
		String d4 = "999";
		for(int i=0;i<d4.length();i++){
			myCW.handleKeyEvent(0, 0, d4.charAt(i));
		}
		
		//Changing default value for column7:
		// Integer met default = 999  en Blanks_al = false
		myCW.handleMouseEvent(0, 300,400,1);
		myCW.handleMouseEvent(0, 300,400,1);
		myCW.handleMouseEvent(0, 300,400,1);
		myCW.handleMouseEvent(0, 500,400, 1);
		for(int i=0;i<d4.length();i++){
			myCW.handleKeyEvent(0, 0, d4.charAt(i));
		}
		//Toggle Blanks
		myCW.handleMouseEvent(0, 400, 400, 1);
		
		//Escaping to table mode:
		myCW.handleKeyEvent(0,27,' ');
		
		// got to ROWS mode
		myCW.handleMouseEvent(0, 80, 30, 2);
				
		assertEquals(Loadable_Interfaces.TABLE_ROWS, coMan.getMode());
				
		// Add a row
		myCW.handleMouseEvent(0, 15, 35 , 2);
		
		// Step 1: Click value of first row for first column
		myCW.handleMouseEvent(0, 40, 70 , 1);

		// Step 2: The system indicates that this row is now selected.
		//Column 0
		TextField textField = (TextField) coMan.getActiveUI().locatedAt(40, 70);
		assertTrue(textField.isSelected());
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// No error when the field is blank
		assertEquals(false, textField.getError());
		
		//Column 1
		myCW.handleMouseEvent(0, 100, 70 , 1);
		textField = (TextField) coMan.getActiveUI().locatedAt(100, 70);
		assertTrue(textField.isSelected());
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// Error when the field is blank
		assertEquals(true, textField.getError());
		// Add char to prevent lock
		myCW.handleKeyEvent(0, 0, '5');
		
		
		// Column 2: @ verwijderen
		myCW.handleMouseEvent(0, 180, 70 , 1);
		textField = (TextField) coMan.getActiveUI().locatedAt(180, 70);
		for(int i=0;i<6;i++){
			myCW.handleKeyEvent(0, 8, ' ');
			System.out.println(textField.getText());
		}
		// Error when the field is blank
		assertEquals(true, textField.getError());
		// Add @ to prevent lock
		myCW.handleKeyEvent(0, 0, '@');
		
		
		// Column 3: @ verwijderen
		myCW.handleMouseEvent(0, 250, 70 , 1);
		textField = (TextField) coMan.getActiveUI().locatedAt(250, 70);
		for(int i=0;i<d2.length();i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// Error when the field is blank
		assertEquals(true, textField.getError());
		// Add @ to prevent lock
		myCW.handleKeyEvent(0, 0, '@');
		
		
		// Column 4: 
		myCW.handleMouseEvent(0, 330, 70 , 1);
		Checkbox checkbox = (Checkbox) coMan.getActiveUI().locatedAt(330, 70);
		myCW.handleMouseEvent(0, 330, 70, 1);
		//assertEquals(true, checkbox.isChecked());
		myCW.handleMouseEvent(0, 330, 70, 1);
		//assertEquals(false, checkbox.isChecked());
		
		
		// Click on checkbox of column 5 
		checkbox = (Checkbox) coMan.getActiveUI().locatedAt(400, 70);
		assertEquals(true, checkbox.isChecked());
		myCW.handleMouseEvent(0, 400, 70, 1);
		assertEquals(false, checkbox.isChecked());
		
		
		
		// Column 6: @ verwijderen
		myCW.handleMouseEvent(0, 450, 70 , 1);
		textField = (TextField) coMan.getActiveUI().locatedAt(450, 70);
		assertTrue(textField.isSelected());
		for(int i=0;i<d4.length();i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// No error when the field is blank
		assertEquals(false, textField.getError());
		
		
		// Column 7: @ verwijderen
		myCW.handleMouseEvent(0, 510, 70 , 1);
		textField = (TextField) coMan.getActiveUI().locatedAt(510, 70);
		assertTrue(textField.isSelected());
		for(int i=0;i<d4.length();i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// Error when the field is blank
		assertEquals(true, textField.getError());
		// Add char to prevent lock
		myCW.handleKeyEvent(0, 0, '5');
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

