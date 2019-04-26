package tests;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import org.junit.Before;
import org.junit.Test;

import canvaswindow.MyCanvasWindow;
import domain.Type;
import facades.Tablr;
import uielements.Checkbox;
import uielements.TextField;
import uielements.UIRow;
import uielements.UITable;

public class TableRowsTests {
	
	public MyCanvasWindow prepareTable(){
		// Step 1: Load the tables mode window
		MyCanvasWindow myCW = new MyCanvasWindow("Table Rows Mode");
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');

		// Double click on listview to create a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		// The user double-clicks a table name.
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 40, 2);
		// The user double clicks below the list of columns:
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 170, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 170, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 170, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 170, 2);

		//Changing default value for column0.
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED,540, 48, 1);
		String d1 = "default";
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 0, d1.charAt(i));
		}
		//Changing default value for column1:
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 545, 75, 1);
		String d2 = "default@email";
		for(int i=0;i<d2.length();i++){
			myCW.handleKeyEvent(0, 0, d2.charAt(i));
		}
		//Changing type to Boolean for column2:
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,105,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,105,1);
		
		//Changing type to integer for column3:
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,135,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,135,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,135,1);
		//Changing default value for column3 to 999:
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 550,132,1);
		String d3 = "999";
		for(int i=0;i<d3.length();i++){
			myCW.handleKeyEvent(0, 0, d3.charAt(i));
		}
		
		// The user double-clicks the table name to open rows mode
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 37, 2);
		
		// The user double clicks below the list of rows:
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 480, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 480, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 480, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 480, 2);

		return myCW;
	}
	
	/**
	 * Use case 8: Add a row
	 * 
	 */
	@Test
	public void useCase8(){
		MyCanvasWindow myCW = prepareTable();
		Tablr tablr = myCW.getTablr();
	
		// Step 2: The system added four new rows in prepareTable(). Its value for each column is the columns default.
		assertEquals("default",tablr.getColumns(tablr.getTables().get(0)).get(0).getDefault());
		assertEquals("",tablr.getColumns(tablr.getTables().get(0)).get(1).getDefault());
		assertNull(tablr.getColumns(tablr.getTables().get(0)).get(2).getDefault());
		assertEquals(999,tablr.getColumns(tablr.getTables().get(0)).get(3).getDefault());
	}

	/**
	 * use Case 4.9: Edit row value
	 */
	@Test
	public void useCase9() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Table Rows Mode");
		Tablr tablr = myCW.getTablr();
		myCW.handleKeyEvent(1, 17, ' ');
		myCW.handleKeyEvent(1, 84, ' ');

		// Double click on listview to create a new table
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 155, 152, 2);
		// The user double-clicks a table name.
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 40, 2);
		// The user double clicks below the list of columns:
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 276, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 276, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 276, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 276, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 276, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 276, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 276, 2);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 276, 2);
		
		//Changing default value for column0.
		// String met default = "default" en Blanks_al = true
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED,540,50, 1);
		String d1 = "default";
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 0, d1.charAt(i));
		}
		//Changing default value for column1.
		// String met default = "default" en Blanks_al = false
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED,540,75, 1);
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 0, d1.charAt(i));
		}		
		//Toggle Blanks
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED,500,75, 1);
		
		//Changing default value for column2:
		// Email met default = "default@email" en Blanks_al = true
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 105, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 540, 100, 1);
		String d2 = "default@email";
		for(int i=0;i<d2.length();i++){
			myCW.handleKeyEvent(0, 0, d2.charAt(i));
		}
		
		//Changing default value for column3:
		// Email met default = "default@email" en Blanks_al = false
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 135, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 540, 135, 1);
		for(int i=0;i<d2.length();i++){
			myCW.handleKeyEvent(0, 0, d2.charAt(i));
		}
		//Toggle Blanks
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 500, 135, 1);
		
		//Changing default value for column4:
		// Boolean: default = Blank  && Blanks_al = true
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,165,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,165,1);
		

		//Changing default value for column5:
		// Boolean: default = true && Blanks_al = false
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,195,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,195,1);
		//set default
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 560,195,1);
		//Toggle Blanks
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 504, 195, 1);
		
		
		//Changing default value for column6:
		// Integer: default = 999  && Blanks_al = true
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,225,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,225,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,225,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 540,225, 1);
		String d4 = "999";
		for(int i=0;i<d4.length();i++){
			myCW.handleKeyEvent(0, 0, d4.charAt(i));
		}
		
		//Changing default value for column7:
		// Integer met default = 999  en Blanks_al = false
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,250,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,250,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450,250,1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 540,250, 1);
		for(int i=0;i<d4.length();i++){
			myCW.handleKeyEvent(0, 0, d4.charAt(i));
		}
		//Toggle Blanks
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 505, 250, 1);
		
		// got to ROWS mode
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 60, 35, 2);
				
		// Add a row
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 480, 550 , 2);
		
		// Step 1: Click value of first row for first column
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 332, 362 , 1);

		// Step 2: The system indicates that this row is now selected.
		//Column 0
		TextField textField = (TextField) tablr.getUIAt(332, 362).locatedAt(332, 362);
		assertTrue(textField.isSelected());
		// Remove the default string
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// No error when the field is blank
		assertEquals(false, textField.getError());
		
		//Column 1
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 374, 362 , 1);
		textField = (TextField) tablr.getUIAt(374, 362).locatedAt(374, 362);
		assertTrue(textField.isSelected());
		// Remove the default string
		for(int i=0;i<d1.length();i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// Error when the field is blank
		assertEquals(true, textField.getError());
		// Add char to prevent lock
		myCW.handleKeyEvent(0, 0, '5');
		
		
		// Column 2: @ verwijderen
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 415, 363 , 1);
		textField = (TextField) tablr.getUIAt(415, 363).locatedAt(415, 363);
		for(int i=0;i<6;i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// Error when the field is blank
		assertTrue(textField.getError());
		// Add @ to prevent lock
		myCW.handleKeyEvent(0, 0, '@');
		
		
		// Column 3: @ verwijderen
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 450, 363 , 1);
		textField = (TextField) tablr.getUIAt(450, 363).locatedAt(450, 363);
		for(int i=0;i<d2.length();i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// Error when the field is blank
		assertTrue(textField.getError());
		// Add @ to prevent lock
		myCW.handleKeyEvent(0, 0, '@');
		
		
		// Column 4: 
		Checkbox cb = (Checkbox) tablr.getUIAt(492, 365).locatedAt(492, 365);
		assertEquals(cb.isChecked(), null);
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 492, 365 , 1);
		assertTrue(cb.isChecked());
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 492, 365, 1);
		assertFalse(cb.isChecked());
		
		
		// Click on checkbox of column 5 
		cb = (Checkbox) tablr.getUIAt(533, 365).locatedAt(492, 365);
		assertTrue(cb.isChecked());
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 533, 365, 1);
		assertFalse(cb.isChecked());
		
		
		
		// Column 6: Remove all digits
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 561, 363 , 1);
		textField = (TextField) tablr.getUIAt(561, 363).locatedAt(561, 363);
		assertTrue(textField.isSelected());
		for(int i=0;i<d4.length();i++){
			myCW.handleKeyEvent(0, 8, ' ');
		}
		// No error when the field is blank
		assertEquals(false, textField.getError());
		
		// Resize the Rows Mode subwindow to see the last column
		myCW.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 301, 404, 1);
		myCW.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 255, 404, 1);
		
		// Column 7: Remove all the digits
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 570, 363 , 1);
		textField = (TextField) tablr.getUIAt(570, 363).locatedAt(570, 363);
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
		MyCanvasWindow myCW = prepareTable();
		Tablr tablr = myCW.getTablr();
		
		// Step 1: The user clicks the margin to the left of second row.
		myCW.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 315, 393 , 1);
		
		// Step 2: The system indicates that this row is now selected.
		UIRow r = (UIRow) tablr.getUIAt(315, 393).locatedAt(315, 393);
		UITable table = (UITable) tablr.getUIAt(503, 537).locatedAt(503, 537);
		assertEquals(table.getSelected(), r);

		// Step 3: User presses Delete key
		myCW.handleKeyEvent(0, 127, ' ');
		
		// Step 4: The system removes the row from the table and shows the updated list of rows
		assertEquals(3,tablr.getTables().get(0).getRows());
	}
}

