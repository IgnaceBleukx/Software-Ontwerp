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
		
		
		
//		//test x (hier 10) keer voor random klikposities binnen de ListView
//		
//		
//		
//		int randomX;
//		int randomY;
//		for(int i = 0; i < 10; ++i){
//			for(int j = 0; j < 20; ++j){
//				//Clear the UI each iteration
//				coMan.clearUI();
//				
//				//Creating an empty table and setting it as active
//				coMan.addEmptyTable();
//				coMan.setActiveTable(coMan.getTables().get(0));
//				//add 2 rows so we can check if they update accordingly when columns get added
//				coMan.getActiveTable().addRow();
//				coMan.getActiveTable().addRow();				
//				
//				//Step 1 --
//				
//				//the table is empty, no columns yet. Add a column by clicking anywhere in the ListView.
//				assertEquals(coMan.getColumns(coMan.getActiveTable()).size(), 0);
//				randomX = ThreadLocalRandom.current().nextInt(10, 560 + 1 + 10);
//				randomY = ThreadLocalRandom.current().nextInt(30, 520 + 1 + 30);
//				myCW.handleMouseEvent(0, randomX, randomY, 2);
//				
//				//Step 2 --
//				
//				/*Now there is one column in the table with name "Column" and then some random string added to that. (in assignment denoted by N)
//				 * its type is STRING, blanks are allowed and the default value is blank
//				 */
//				assertEquals(coMan.getColumns(coMan.getActiveTable()).size(), 1);
//				assert(coMan.getColumns(coMan.getActiveTable()).get(0).getName().startsWith("Column"));
//				assertEquals(coMan.getColumns(coMan.getActiveTable()).get(0).getColumnType(), Type.STRING);
//				assert(coMan.getColumns(coMan.getActiveTable()).get(0).getBlankingPolicy()); //blankingPolicy must be true
//				assertNull(coMan.getColumns(coMan.getActiveTable()).get(0).getDefault()); //Default value must be null (that's how we store blanks)
//				
//				//Create 4 more columns by clicking in random, valid spots (under all existing columns)
//				for (int z = 0; z < 4; ++z){
//					randomX = ThreadLocalRandom.current().nextInt(10, 560 + 1 + 10);
//					randomY = ThreadLocalRandom.current().nextInt(30 + 50*coMan.getColumns(coMan.getActiveTable()).size(), 520 + 1 + 30);
//					myCW.handleMouseEvent(0, randomX, randomY, 2);
//					
//					//Check if all values in the row (both rows) are indeed null (blank)
//					for (int r = 0; r < coMan.getRows(coMan.getActiveTable()).size(); ++r){
//						for (Column newCol : coMan.getColumns(coMan.getActiveTable())){
//							assertNull(newCol.getCells().get(r).getValue());
//						}
//					}
//				}
//				
//				//All names are different, yet start with "Column"
//				for (Column c : coMan.getColumns(coMan.getActiveTable())){
//					assert(c.getName().startsWith("Column"));
//					for (Column c2 : coMan.getColumns(coMan.getActiveTable())){
//						if (c!=c2) assert(!c.getName().equals(c2.getName()));
//					}
//				}
//			}
//		}
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
		
		//Extension 1a:
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
		
		
		//Extension 1b:
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
		
		//Extension 1c:
		//Setting the default value to "true"
		myCW.handleMouseEvent(0,500,60,1);
		d = "true";
		for (int i=0;i<d.length();i++) myCW.handleKeyEvent(0,0,d.charAt(i));
		//Changing the type to BOOLEAN:
		myCW.handleMouseEvent(0,300,50,1);		
		myCW.handleMouseEvent(0,300,50,1);
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
		
		
//		//Clear UI before test
//		coMan.clearUI();
//		
//		//Creating an empty table and setting it as active
//		coMan.addEmptyTable();
//		coMan.setActiveTable(coMan.getTables().get(0));
//		Table table = coMan.getActiveTable(); //for brevity's sake
//		//add 2 columns
//		coMan.getActiveTable().addEmptyColumn(Type.STRING, null);
//		coMan.getActiveTable().addEmptyColumn(Type.STRING, null);
//		
//		assertEquals("Column0",coMan.getColumns(table).get(0).getName());
//		assertEquals("Column1",coMan.getColumns(table).get(1).getName());		
//
//		// Step 1: The user clicks the first column's name and the textfield gets selected
//		
//		myCW.handleMouseEvent(0, 40, 40, 1);
//		TextField t = (TextField) coMan.getActiveUI().locatedAt(40, 40);
//		assertEquals(t.isSelected(), true);
//		
//		// Step 2: editing the name
//		
//		// Remove the last character of the highlighted table name
//		//(8 is backspace)
//		myCW.handleKeyEvent(1, 8, ' ');
//		assertEquals("Column", table.getColumns().get(0).getName());
//		
//		// Add a 1 (Keycode 97), making both column's names "Column1" which should give an error for the currently selected column's TextField
//		assertEquals(t.getError(), false); // name:"Column"
//		myCW.handleKeyEvent(1, 97, '1');
//		assertEquals(table.getColumns().get(0).getName(), table.getColumns().get(1).getName());
//		assertEquals("Column1", table.getColumns().get(1).getName());
//		assertEquals(t.getError(), true); // name:"Column1"
//		
//		// Delete a character again: no longer error state
//		myCW.handleKeyEvent(1, 8, ' ');
//		assertEquals(t.getError(), false); // name:"Column"
//		
//		// Delete all characters: name is "" and should give an error (empty name is invalid)
//		for(int i = 0; i<6; i++){
//			myCW.handleKeyEvent(1, 8, ' ');
//		}
//		assertEquals("", coMan.getTables().get(0).getName());
//		assertEquals(t.getError(), true); // name:""
//		
//
//		// Press Enter to finish editing
//		myCW.handleKeyEvent(1, 10, ' ');
//		assertEquals(t.isSelected(), false);
//		
//		// Or click outside column name 
//		myCW.handleMouseEvent(0, 40, 40, 1);
//		assertEquals(t.isSelected(), true);
//		myCW.handleMouseEvent(0, 51, 300, 1);
//		assertEquals(t.isSelected(), false);
//		
//
//		Column c = table.getColumns().get(0); //for brevity's sake
//		
//		
//		//Step 1a: click Type
//		//TODO: behaviour van hard locks checken? ik vind hun manier van locks redelijk vaag en hij komt niet overeen met hoe wij onze default values bijhouden.
//		//ook voor values in rows: redelijk vaag, boolean houdt toch niet de string value van email bij, en integer moet dan ineens weer wel die value terug oproepen? Of niet? Vaag.
//		
//		assertEquals(c.getColumnType(), Type.STRING);
//		myCW.handleMouseEvent(0, 240, 40, 1);
//		assertEquals(c.getColumnType(), Type.EMAIL);
//		//default value 
//		
//		
//		myCW.handleMouseEvent(0, 240, 40, 1);
//		assertEquals(c.getColumnType(), Type.BOOLEAN);
//		myCW.handleMouseEvent(0, 240, 40, 1);
//		assertEquals(c.getColumnType(), Type.INTEGER);
//		
//		
//		//Step 1b: click Blanks
//
//
//		Checkbox cb = (Checkbox) coMan.getActiveUI().locatedAt(400, 50);
//		assert(c.getBlankingPolicy());
//		myCW.handleMouseEvent(0, 400, 50, 1);
//		assert(!c.getBlankingPolicy()); //after clicking, blanks are no longer allowed, checkbox is empty
//		
//		//The default value of c was blank, thus changing blanking policy to false should put a hard lock on this checkbox
//		//(because a column can't disallow blanks and have a blank default value)
//		//It has to be clicked again before the user can do anything else
//		assert(cb.getError());
//		
//
//		assertEquals(c.getColumnType(), Type.INTEGER);
//		myCW.handleMouseEvent(0, 240, 40, 1); //Click on the Type box, as we did before. To illustrate that we can't do or select anything else.
//		assertEquals(c.getColumnType(), Type.INTEGER); // hasn't changed
//		
//		myCW.handleMouseEvent(0, 400, 50, 1); //Click on checkbox again. Return to blanks allowed: error is removed.
//		assert(!cb.getError());
//		assert(c.getBlankingPolicy());
//		
//		
//		//Step 1c: click Default Value
//		
//		TextField tf = (TextField) coMan.getActiveUI().locatedAt(440, 30);
//
//		assertEquals(c.getColumnType(), Type.INTEGER);
//		myCW.handleMouseEvent(0, 440, 30, 1); //Click in the default value textbox
//		myCW.handleKeyEvent(1, 97, '1');
//		myCW.handleKeyEvent(1, 97, '1');
//		assertEquals(c.getDefault(), 11); //we typed in 11
//		assert(!tf.getError()); //11 is a valid integer
//		myCW.handleKeyEvent(1, 81, 'a');
//		assert(tf.getError()); //11a is NOT a valid integer
//
//		myCW.handleMouseEvent(0, 240, 30, 1); //Click type
//		assertEquals(c.getColumnType(), Type.STRING);
//		myCW.handleMouseEvent(0, 240, 30, 1); //Click type
//		assertEquals(c.getColumnType(), Type.EMAIL);
//		myCW.handleMouseEvent(0, 440, 30, 1); //Click in the default value textbox
//		myCW.handleKeyEvent(1, 97, '1');
//		myCW.handleKeyEvent(1, 97, '1');
//		assert(tf.getError()); //11 is NOT a valid email
//		myCW.handleKeyEvent(1, -1, '@'); //TODO: waar vind ik een @, hoe doe je dat -> -1 gebruiken
//		assert(!tf.getError()); //11@ is a valid email
//
//		//Loop for boolean: null-true-false-_, or true-false-_
//		assert(c.getBlankingPolicy());
//		myCW.handleMouseEvent(0, 240, 30, 1); 
//		assertEquals(c.getColumnType(), Type.BOOLEAN);
//		myCW.handleMouseEvent(0, 490, 50, 1); //Click on the boolean checkbox
//		assertEquals(c.getDefault(), true);
//		myCW.handleMouseEvent(0, 490, 50, 1); //Click on the boolean checkbox
//		assertEquals(c.getDefault(), false);
//		myCW.handleMouseEvent(0, 490, 50, 1); //Click on the boolean checkbox
//		assertEquals(c.getDefault(), null);
//		
//		myCW.handleMouseEvent(0, 400, 50, 1); //Click on the blanks: Disallow blanks
//		assert(!c.getBlankingPolicy());
//		assertEquals(c.getDefault(), true);
//		myCW.handleMouseEvent(0, 490, 50, 1); //Click on the boolean checkbox
//		assertEquals(c.getDefault(), false);
//		myCW.handleMouseEvent(0, 490, 50, 1); //Click on the boolean checkbox
//		assertEquals(c.getDefault(), true);
	}
	
	@Test
	public void useCase7() {
		MyCanvasWindow myCW = prepareTable();
		CommunicationManager coMan = myCW.getCommunicationManager();
		//test x (hier 10) keer voor random klikposities binnen de ListView
		coMan.clearUI();
		//Creating an empty table and setting it as active
		coMan.addEmptyTable();
		coMan.setActiveTable(coMan.getTables().get(0));
		//add 1 column
		coMan.getActiveTable().addEmptyColumn(Type.STRING, null);
		coMan.getActiveTable().addEmptyColumn(Type.STRING, null);
		assertEquals(coMan.getActiveTable().getColumns().size(), 2);
		Column c = coMan.getActiveTable().getColumns().get(0); //for brevity's sake. This is the uppermost column.

		//Step 1: user clicks margin of uppermost column
		myCW.handleMouseEvent(0, 15, 40, 1);
		
		UIRow r = (UIRow) coMan.getActiveUI().locatedAt(15, 40);
		//Step 2: column is now selected
		assert(r.isSelected());
		
		
		//Step 3: User presses delete
		myCW.handleKeyEvent(1, 127, ' ');
		
		//Step 4.1: System removes column from list of columns
		assert(!coMan.getActiveTable().getColumns().contains(c));
		//Step 4.2: System removes the value for the deleted column from all the rows
		//our domain doesn't store cells in rows. This is automatically true.
	}
}
