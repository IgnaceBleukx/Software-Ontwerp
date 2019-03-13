package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import canvaswindow.CanvasWindow;
import canvaswindow.MyCanvasWindow;
import facades.CommunicationManager;
import ui.Loadable_Interfaces;
import uielements.Button;
import uielements.ListView;
import uielements.TextField;
import uielements.UI;
import uielements.UIRow;

public class TablesModeTests {
	
	@Before
	public void setUp() throws Exception {
	}

	// TODO: clearUI werkt nog niet. Wanneer er in MyCanvasWindow een TABLE_DESIGN wordt geladen, wordt er al een tabel geladen, die ervoor zorgt dat testen fout zijn
	
	/**
	 * use case 4.1: Create a table
	 */
	@Test
	public void useCase1() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		CommunicationManager coMan = myCW.getCommunicationManager();
		coMan.clearUI();
		coMan.loadUI(Loadable_Interfaces.TABLES);
		// There are no tables yet
		assertEquals(coMan.getTables().size(), 0);
		// Step 2: The user double-clicks below the list of tables
		myCW.handleMouseEvent(0, 40, 530 , 2);
		// Step 3: There is a table added to the tables list
		assertEquals(coMan.getTables().size(), 1);
	}
	
	
	/**
	 * use case 4.2: Edit table name
	 * 
	 * TODO: 5.a en 6.a (nog niet geimplementeerd op 11/03/2019 om 15:31
	 */
	@Test
	public void useCase2() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		CommunicationManager coMan = myCW.getCommunicationManager();
		coMan.clearUI();
		coMan.loadUI(Loadable_Interfaces.TABLES);
		// Create an empty table with a simulated double click
		myCW.handleMouseEvent(0, 40, 530, 2);
		// Check the name of the added table
		assertEquals("Table0",coMan.getTables().get(0).getName());
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
	}
	
	/**
	 * use case 4.3: Delete Table
	 * 
	 */
	@Test
	public void useCase3() {
		// Step 1: Load the window
		MyCanvasWindow myCW = new MyCanvasWindow("Tables Mode");
		CommunicationManager coMan = myCW.getCommunicationManager();
		coMan.clearUI();
		coMan.loadUI(Loadable_Interfaces.TABLES);
		// Create an empty table with a simulated double click
		myCW.handleMouseEvent(0, 40, 530, 2);
		
		ListView l = (ListView) coMan.getActiveUI().locatedAt(200, 200);
		UIRow r = (UIRow) l.getElements().get(0);
		
		// Step 1: The user clicks the margin to the left of a table name.
		myCW.handleMouseEvent(0, 13, 13, 1);

		// Step 2: row is selected
		assertEquals(l.getSelectedElement(), r);

		// Step 3: User presses Delete key
		myCW.handleKeyEvent(0, 127, ' ');
		
		// Step 4: The system removes the table and shows the updated list of tables
		assertEquals(coMan.getTables().size(), 0);
		assertEquals(l.getElements().size(), 0);
	}
}
