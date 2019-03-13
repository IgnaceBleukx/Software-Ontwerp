package tests;

import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import canvaswindow.MyCanvasWindow;
import domain.*;
import facades.CommunicationManager;
import ui.Loadable_Interfaces;
import uielements.TextField;

public class TableDesignTests {
	

	private CommunicationManager coMan;
	private MyCanvasWindow myCW;

	@Before
	public void setUp() throws Exception {
		myCW = new MyCanvasWindow("TableDesign Mode");
		coMan = myCW.getCommunicationManager();
		coMan.loadUI(Loadable_Interfaces.TABLE_DESIGN);
	}



	@Test
	public void useCase5() {
		//test x (hier 10) keer voor random klikposities binnen de ListView
		coMan.clearUI();
		int randomX;
		int randomY;
		for(int i = 0; i < 10; ++i){
			for(int j = 0; j < 20; ++j){
				//Clear the UI each iteration
				coMan.clearUI();
				
				//Creating an empty table and setting it as active
				coMan.addEmptyTable();
				coMan.setActiveTable(coMan.getTables().get(0));
				//add 2 rows so we can check if they update accordingly when columns get added
				coMan.getActiveTable().addRow();
				coMan.getActiveTable().addRow();				
				
				//Step 1 --
				
				//the table is empty, no columns yet. Add a column by clicking anywhere in the ListView.
				assertEquals(coMan.getColumns(coMan.getActiveTable()).size(), 0);
				randomX = ThreadLocalRandom.current().nextInt(10, 560 + 1 + 10);
				randomY = ThreadLocalRandom.current().nextInt(30, 520 + 1 + 30);
				myCW.handleMouseEvent(0, randomX, randomY, 2);
				
				//Step 2 --
				
				/*Now there is one column in the table with name "Column" and then some random string added to that. (in assignment denoted by N)
				 * its type is STRING, blanks are allowed and the default value is blank
				 */
				assertEquals(coMan.getColumns(coMan.getActiveTable()).size(), 1);
				assert(coMan.getColumns(coMan.getActiveTable()).get(0).getName().startsWith("Column"));
				assertEquals(coMan.getColumns(coMan.getActiveTable()).get(0).getColumnType(), Type.STRING);
				assert(coMan.getColumns(coMan.getActiveTable()).get(0).getBlankingPolicy()); //blankingPolicy must be true
				assertNull(coMan.getColumns(coMan.getActiveTable()).get(0).getDefault()); //Default value must be null (that's how we store blanks)
				
				//Create 4 more columns by clicking in random, valid spots (under all existing columns)
				for (int z = 0; z < 4; ++z){
					randomX = ThreadLocalRandom.current().nextInt(10, 560 + 1 + 10);
					randomY = ThreadLocalRandom.current().nextInt(30 + 50*coMan.getColumns(coMan.getActiveTable()).size(), 520 + 1 + 30);
					myCW.handleMouseEvent(0, randomX, randomY, 2);
					
					//Check if all values in the row (both rows) are indeed null (blank)
					for (int r = 0; r < coMan.getRows(coMan.getActiveTable()).size(); ++r){
						for (Column newCol : coMan.getColumns(coMan.getActiveTable())){
							assertNull(newCol.getCells().get(r).getValue());
						}
					}
				}
				
				//All names are different, yet start with "Column"
				for (Column c : coMan.getColumns(coMan.getActiveTable())){
					assert(c.getName().startsWith("Column"));
					for (Column c2 : coMan.getColumns(coMan.getActiveTable())){
						if (c!=c2) assert(!c.getName().equals(c2.getName()));
					}
				}
			}
		}
	}
	

	// Step 2: The user clicks a table name and the textfield gets selected
	myCW.handleMouseEvent(0, 51, 13, 1);
	TextField t = (TextField) coMan.getActiveUI().locatedAt(51, 13);
	assertEquals(t.isSelected(), true);
	// Step 3: Remove the last character of the highlighted table name
	// (8 is backspace)
	myCW.handleKeyEvent(1, 8, ' ');
	assertEquals("Table", coMan.getTables().get(0).getName());
	
	// Check to see if table name gets red when it is empty or equal to name of another table
	assertEquals(t.getError(), false);
	for(int i = 0; i<5; i++){
		myCW.handleKeyEvent(1, 8, ' ');
	}
	assertEquals("", coMan.getTables().get(0).getName());
	assertEquals(t.getError(), true);
	
	// Step 4: Add a character to the highlighted table name
	myCW.handleKeyEvent(1, 65, 'a');
	assertEquals("a", coMan.getTables().get(0).getName());
	
	// Step 5: Press Enter to finish editing
	myCW.handleKeyEvent(1, 10, ' ');
	assertEquals(t.isSelected(), false);
	
	// Or click outside table name 
	myCW.handleMouseEvent(0, 51, 13, 1);
	assertEquals(t.isSelected(), true);
	myCW.handleMouseEvent(0, 51, 300, 1);
	assertEquals(t.isSelected(), false);
	
	@Test
	public void useCase6() {
		//Clear UI before test
		coMan.clearUI();
		
		//Creating an empty table and setting it as active
		coMan.addEmptyTable();
		coMan.setActiveTable(coMan.getTables().get(0));
		Table table = coMan.getActiveTable(); //for brevity's sake
		//add 2 columns
		coMan.getActiveTable().addEmptyColumn(Type.STRING, null);
		coMan.getActiveTable().addEmptyColumn(Type.STRING, null);
		
		assertEquals("Column0",coMan.getColumns(table).get(0).getName());
		assertEquals("Column1",coMan.getColumns(table).get(1).getName());		

		//Step 1 --

		// Step 2: The user clicks a column name and the textfield gets selected
		myCW.handleMouseEvent(0, 51, 13, 1);
		TextField t = (TextField) coMan.getActiveUI().locatedAt(51, 13);
		assertEquals(t.isSelected(), true);
		// Step 3: Remove the last character of the highlighted table name
		// (8 is backspace)
		myCW.handleKeyEvent(1, 8, ' ');
		assertEquals("Table", coMan.getTables().get(0).getName());
		
		
		
		//Step 2 --
		
		/*Now there is one column in the table with name "Column" 
		 * and then some random string. (in assignment denoted by N)
		 * its type is STRING, blanks are allowed and the default value is blank
		 */
		assertEquals(coMan.getColumns(coMan.getActiveTable()).size(), 1);
		assert(coMan.getColumns(coMan.getActiveTable()).get(0).getName().startsWith("Column"));
		assertEquals(coMan.getColumns(coMan.getActiveTable()).get(0).getColumnType(), Type.STRING);
		assert(coMan.getColumns(coMan.getActiveTable()).get(0).getBlankingPolicy()); //blankingPolicy must be true
		assertNull(coMan.getColumns(coMan.getActiveTable()).get(0).getDefault()); //Default value must be null (that's how we store blanks)
		
		//Create 4 more columns by clicking in random, valid spots (under all existing columns)
		for (int z = 0; z < 4; ++z){
			randomX = ThreadLocalRandom.current().nextInt(10, 560 + 1 + 10);
			randomY = ThreadLocalRandom.current().nextInt(30 + 50*coMan.getColumns(coMan.getActiveTable()).size(), 520 + 1 + 30);
			myCW.handleMouseEvent(0, randomX, randomY, 2);
			
			//Check if all values in the row (both rows) are indeed null (blank)
			for (int r = 0; r < coMan.getRows(coMan.getActiveTable()).size(); ++r){
				for (Column newCol : coMan.getColumns(coMan.getActiveTable())){
					assertNull(newCol.getCells().get(r).getValue());
				}
			}
		}
		
		//All names are different, yet start with "Column"
		for (Column c : coMan.getColumns(coMan.getActiveTable())){
			assert(c.getName().startsWith("Column"));
			for (Column c2 : coMan.getColumns(coMan.getActiveTable())){
				if (c!=c2) assert(!c.getName().equals(c2.getName()));
			}
		}
	}
}
