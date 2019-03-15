package tests;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import canvaswindow.MyCanvasWindow;
import domain.*;
import facades.CommunicationManager;
import ui.Loadable_Interfaces;
import uielements.Checkbox;
import uielements.ListView;
import uielements.Text;
import uielements.TextField;
import uielements.UIRow;

public class TableDesignTests {
	

//	private CommunicationManager coMan;
//	private MyCanvasWindow myCW;
//
//	@Before
//	public void setUp() throws Exception {
//		myCW = new MyCanvasWindow("TableDesign Mode");
//		coMan = myCW.getCommunicationManager();
//		coMan.loadUI(Loadable_Interfaces.TABLES);
//	}
	
	/**
	 * This method returns a canvas window with a loaded table without columns in tables mode.
	 * @return
	 */
	public MyCanvasWindow prepareTable(){
		MyCanvasWindow myCW = new MyCanvasWindow("TableDesign Mode");
		CommunicationManager coMan = myCW.getCommunicationManager();
		coMan.loadUI(Loadable_Interfaces.TABLES);
		myCW.handleMouseEvent(0, 100,100, 2);
		return myCW;
	}


	@Test
	public void useCase5() {
		//Load tables mode with one table:
		MyCanvasWindow myCW = prepareTable();
		CommunicationManager coMan = myCW.getCommunicationManager();
		//Enter table_design mode:
		myCW.handleMouseEvent(0, 260, 30, 2);
		
		//Adding an initial row:
		coMan.addRow(coMan.getActiveTable());
		
		//Step 1: The user clicks below the list of columns:
		myCW.handleMouseEvent(0, 260, 150, 2);
		Column addedColumn = coMan.getActiveTable().getColumns().get(0);
		assertEquals(1,coMan.getActiveTable().getColumns().size());
		assertEquals("Column0",addedColumn.getName());
		assertEquals(Type.STRING,addedColumn.getColumnType());
		assertTrue(addedColumn.getBlankingPolicy());
		assertEquals("",addedColumn.getDefault());
		
		//Step2: The existing rows got a blank value for the columns index:
		assertEquals("",coMan.getValue(coMan.getColumns(coMan.getActiveTable()).get(0),0));
		
	}
	
	@Test
	public void useCase6() {
		MyCanvasWindow myCW = prepareTable();
		CommunicationManager coMan = myCW.getCommunicationManager();
		//Entering table design mode:
		myCW.handleMouseEvent(0, 260, 30, 2);
		//Adding two columns:
		myCW.handleMouseEvent(0, 260, 100, 2);
		myCW.handleMouseEvent(0, 260, 100, 2);

		
		//Step1: The user clicks on the name of a column:
		myCW.handleMouseEvent(0, 80, 50, 1);
		
		//Step2: The user tries to change the name of the column:
		//Step2.1: The user sets a valid name for the column:
		//Clearing the name:
		for(int i=0;i<7;i++) myCW.handleKeyEvent(0,8,' ');
		String name = "name";
		for(int i=0;i<name.length();i++){
			myCW.handleKeyEvent(0,0,name.charAt(i));
		}
		assertEquals(name,coMan.getColumnName(coMan.getColumns(coMan.getActiveTable()).get(0)));
		
		//Step2.2: The user enters an invalid name:
		TextField t = (TextField) coMan.getActiveUI().locatedAt(80,50);
		//Clearing the name:
		for(int i=0;i<4;i++) myCW.handleKeyEvent(0,8,' ');
		assertTrue(t.getError());
		name = "Column1";
		for(int i=0;i<name.length();i++){
			myCW.handleKeyEvent(0,0,name.charAt(i));
		}
		assertNotEquals(name,coMan.getColumnName(coMan.getColumns(coMan.getActiveTable()).get(0)));
		assertTrue(t.isSelected());
		assertTrue(t.getError());
		
		//Backspace so the name becomes valid:
		myCW.handleKeyEvent(0,8,' ');
		assertFalse(t.getError());
		
		//The user clicks outside the textfield to finish editing the column name:
		myCW.handleMouseEvent(0,260,200, 1);
		assertFalse(t.isSelected());
		
		//Or the user presses enter
		myCW.handleMouseEvent(0, 80,60,1);
		assertTrue(t.isSelected());
		myCW.handleKeyEvent(0,10, ' ');
		assertFalse(t.isSelected());
		
		/**
		 * Extension 1a
		 */
		//Step 1: The user clicks the type of some column:
		//The state of the column allows blanks and has a blank value as default, the type of the column is STRING:
		assertEquals(Type.STRING,coMan.getColumnType(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//The user clicks the column type:
		myCW.handleMouseEvent(0,300,50,1);
		//The type of the column changes to EMAIL:
		assertEquals(Type.EMAIL,coMan.getColumnType(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//The user clicks the column type:
		myCW.handleMouseEvent(0,300,50,1);
		//The type of the column changes to BOOLEAN:
		assertEquals(Type.BOOLEAN,coMan.getColumnType(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//The user clicks the column type:
		myCW.handleMouseEvent(0,300,50,1);		
		//The type of the column changes to INTEGER:
		assertEquals(Type.INTEGER,coMan.getColumnType(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//The user clicks the column type:
		myCW.handleMouseEvent(0,300,50,1);
		//The type of the column changes back to STRING:
		assertEquals(Type.STRING,coMan.getColumnType(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//Changing the default value for the column:"
		Text type = (Text) coMan.getActiveUI().locatedAt(300,50);
		myCW.handleMouseEvent(0, 500, 50, 1);
		String d = "default";
		for(int i=0;i<d.length();i++){
			myCW.handleKeyEvent(0,0,d.charAt(i));
		}
		//Trying to change the type of the column to email:
		myCW.handleMouseEvent(0,300,50,1);
		//However, the current default value "default" is not valid for the type EMAIL:
		assertTrue(type.getError());
		//The user is not able to click on anything else in the application:
		//Trying to click on the table name:
		myCW.handleMouseEvent(0,80,60,1);
		assertFalse(t.isSelected());
		//Trying to escape window with escape key:
		myCW.handleKeyEvent(0,27,' ');
		assertEquals(Loadable_Interfaces.TABLE_DESIGN,coMan.getMode());
		//The user clicks the type again, it changes to BOOLEAN
		myCW.handleMouseEvent(0,300,50,1);
		//However, "default" is not valid for the type BOOLEAN
		assertTrue(type.getError());
		//The user clicks the type again, it changes to INTEGER
		myCW.handleMouseEvent(0,300,50,1);
		//However, "default" is not valid for the type INTEGER
		assertTrue(type.getError());
		//The user clicks the type again, it changes to STRING again
		myCW.handleMouseEvent(0,300,50,1);
		//"default" is a valid default value for type STRING
		assertFalse(type.getError());
		//Setting default value back to blank:
		myCW.handleMouseEvent(0, 500, 50, 1);
		for (int i=0;i<"default".length();i++) myCW.handleKeyEvent(0,8,' ');
		assertEquals("",coMan.getDefaultString(coMan.getActiveTable().getColumns().get(0)));
		
		
		/**
		 * Extension 1b
		 */
		Checkbox box = (Checkbox) coMan.getActiveUI().locatedAt(405, 55);
		//Changing the default value to a non-blank value:
		myCW.handleMouseEvent(0,500,60,1);
		myCW.handleKeyEvent(0,0,'d');
		assertEquals("d",coMan.getDefaultString(coMan.getActiveTable().getColumns().get(0)));
		//Step 1: The user clicks on the checkbox indicating whether it allows blanks or not:
		myCW.handleMouseEvent(0,405,55,1);
		//The current blanking policy is true, it is changed to false:
		assertFalse(coMan.getBlankingPolicy(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//Changing the blanking policy back to true:
		myCW.handleMouseEvent(0,405,55,1);
		//Changing the default value to blank:
		myCW.handleMouseEvent(0,500,60,1);
		myCW.handleKeyEvent(0,8,' ');
		//Trying to change the blanking policy back to false:
		myCW.handleMouseEvent(0,405,55,1);
		/*However, the current default value is blank, the checkbox becomes red and
		the user is not able to click on anything else but the checkbox*/
		assertTrue(box.getError());
		assertEquals(box,coMan.getLockedElement());
		//Trying to click on the table name:
		myCW.handleMouseEvent(0,80,60,1);
		assertFalse(t.isSelected());
		//Trying to escape window with escape key:
		myCW.handleKeyEvent(0,27,' ');
		assertEquals(Loadable_Interfaces.TABLE_DESIGN,coMan.getMode());
		//The user clicks the checkbox again, so the blanking policy changes to true:
		myCW.handleMouseEvent(0,405,55,1);
		assertFalse(box.getError());
		assertTrue(coMan.getBlankingPolicy(coMan.getColumns(coMan.getActiveTable()).get(0)));
		
		/**
		 * Extension 1c
		 */
	    //Setting the default value to "true"
		myCW.handleMouseEvent(0,500,60,1);
		d = "true";
		for (int i=0;i<d.length();i++) myCW.handleKeyEvent(0,0,d.charAt(i));
		//Changing the type to BOOLEAN:
		myCW.handleMouseEvent(0,300,50,1);		
		myCW.handleMouseEvent(0,300,50,1);
		//The type is BOOLEAN, so the default value is represented in a checkbox
		assertTrue(coMan.getActiveUI().locatedAt(490,50) instanceof Checkbox);
		assertEquals(true, coMan.getDefaultValue(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//Step1: The user clicks the default value for some column:
		myCW.handleMouseEvent(0, 490,50,1);
		//The default value was true, it becomes false:"
		assertEquals(false, coMan.getDefaultValue(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//The user clicks the checkbox again, the default value becomes blank:
		myCW.handleMouseEvent(0, 490,50,1);
		assertEquals(null, coMan.getDefaultValue(coMan.getColumns(coMan.getActiveTable()).get(0)));
		assertEquals("", coMan.getDefaultString(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//The default value is blank and the type is boolean, the checkbox is greyed out:
		assertTrue(((Checkbox) coMan.getActiveUI().locatedAt(490,50)).getGreyedOut());
		//The user clicks the checkbox again, the default value becomes true:
		myCW.handleMouseEvent(0, 490,50,1);
		assertEquals(true, coMan.getDefaultValue(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//The user clicks the checkbox, indicating the blanking policy of the column:
		myCW.handleMouseEvent(0,400, 50,1);
		assertFalse(coMan.getBlankingPolicy(coMan.getColumns(coMan.getActiveTable()).get(0)));

		//The user clicks the default value checkbox
		myCW.handleMouseEvent(0, 490,50,1);
		//The default value was true, it becomes false:
		assertEquals(false, coMan.getDefaultValue(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//The user clicks the default value checkbox again:
		myCW.handleMouseEvent(0, 490,50,1);
		//The default value was true, it becomes true again:
		assertEquals(true, coMan.getDefaultValue(coMan.getColumns(coMan.getActiveTable()).get(0)));

		//The user changes the type to STRING by clicking the column type twice:
		myCW.handleMouseEvent(0,300,50,1);		
		myCW.handleMouseEvent(0,300,50,1);
		//The user tries to change the default value to blank by deleting all characters:
		myCW.handleMouseEvent(0,500,60,1);
		TextField defaultString = (TextField) coMan.getActiveUI().locatedAt(500,60);
		for(int i=0;i<4;i++){
			myCW.handleKeyEvent(0,8,' ');
		}
		assertEquals(0,defaultString.getText().length());
		//However, the column does not allow blanks:
		assertTrue(defaultString.getError());
		//Changing the default value to a non-empty value:
		myCW.handleKeyEvent(0, 0, 'd');
		//Allowing blanks again:
		myCW.handleMouseEvent(0,400, 50, 1);
		//Deleting the current default value:
		myCW.handleMouseEvent(0,500, 60, 1);
		myCW.handleKeyEvent(0,8,' ');
		
		//The user changes the type of the column to EMAIL by clicking on the column type:
		myCW.handleMouseEvent(0,300,50,1);
		assertEquals(Type.EMAIL,coMan.getColumnType(coMan.getColumns(coMan.getActiveTable()).get(0)));
		TextField defaultEmail = (TextField) coMan.getActiveUI().locatedAt(500,60);
		//The user selects the default value textfield:
		myCW.handleMouseEvent(0,500, 60, 1);
		//The user enters a valid default value for the column:
		d = "valid.email@tests.com";
		for (int i=0;i<d.length();i++) myCW.handleKeyEvent(0, 0, d.charAt(i));
		//The default value of the column is changed successfully:
		assertEquals(d, coMan.getDefaultValue(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//Deleting the current default value:
		myCW.handleMouseEvent(0,500, 60,1);
		for(int i =0;i<d.length();i++) myCW.handleKeyEvent(0, 8,' ');
		assertEquals(0,defaultEmail.getText().length());
		//The user tries to enter a non-valid default value:
		d = "wrong@Value@default.com";
		for (int i=0;i<d.length();i++) myCW.handleKeyEvent(0, 0, d.charAt(i));
		assertTrue(defaultEmail.getError());
		//Deleting the current default value:
		myCW.handleMouseEvent(0,500, 60,1);
		for(int i =0;i<d.length();i++) myCW.handleKeyEvent(0, 8,' ');
		assertEquals(0,defaultEmail.getText().length());

		//The user changes the type of the column to INTEGER by clicking the column type twice:
		myCW.handleMouseEvent(0,300,50,1);
		myCW.handleMouseEvent(0,300,50,1);
		assertEquals(Type.INTEGER,coMan.getColumnType(coMan.getColumns(coMan.getActiveTable()).get(0)));
		TextField defaultInteger = (TextField) coMan.getActiveUI().locatedAt(500,60);
		//The user clicks on the textfield containing the default value:
		myCW.handleMouseEvent(0,500, 60,1);
		//The user enters a valid default value:
		d = "999";
		for (int i=0;i<d.length();i++) myCW.handleKeyEvent(0, 0, d.charAt(i));
		//The default value is updated successfully
		assertEquals(999, coMan.getDefaultValue(coMan.getColumns(coMan.getActiveTable()).get(0)));
		//Clearing the textfield
		for (int i=0;i<d.length();i++) myCW.handleKeyEvent(0, 8,' ');
		//The user tries to enter a non-valid default value:
		d = "007";
		for (int i=0;i<d.length();i++) myCW.handleKeyEvent(0, 0, d.charAt(i));
		//The textfield shows a red border around it.
		assertTrue(defaultInteger.getError());
	}
	
	@Test
	public void useCase7() {
		MyCanvasWindow myCW = prepareTable();
		CommunicationManager coMan = myCW.getCommunicationManager();
		
		//Entering tables mode:
		myCW.handleMouseEvent(0, 260, 30, 2);
		//Adding two columns
		myCW.handleMouseEvent(0, 260, 100, 2);
		myCW.handleMouseEvent(0, 260, 100, 2);
		//Adding two rows
		coMan.addRow(coMan.getActiveTable());
		coMan.addRow(coMan.getActiveTable());

		//Step 1: The user clicks on a column
		myCW.handleMouseEvent(0, 20, 50, 1);
		UIRow firstColumn = (UIRow) coMan.getActiveUI().locatedAt(20,50);
		ListView vieuw = (ListView) coMan.getActiveUI().locatedAt(300,450);
		//Step 2: The system indicates that the column is now selected
		assertEquals(firstColumn,vieuw.getSelectedElement());
		//Step 3: The user presses the delete key:
		myCW.handleKeyEvent(0, 127,' ');
		//Step 4: The system removes the column from the list of columns...
		assertEquals(1,vieuw.getElements().size());
		assertEquals(1,coMan.getColumns(coMan.getActiveTable()).size());
		assertFalse(coMan.getColumnNames(coMan.getActiveTable()).contains("Column0"));
		// ... and removes the value for the deleted column from all of the table's rows.
		//The domain does not store values in rows, so this is automatically true when the column is deleted.
	}
}
