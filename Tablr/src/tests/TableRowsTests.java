package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import canvaswindow.MyCanvasWindow;
import facades.CommunicationManager;
import ui.Loadable_Interfaces;

public class TableRowsTests {
	
	@Test
	public void useCase8(){
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
		assertEquals(Loadable_Interfaces.TABLE_DESIGN, coMan.getMode());
		myCW.handleMouseEvent(0, 260, 230, 2);
		assertEquals(1,coMan.getColumns(coMan.getActiveTable()).size());
		myCW.handleMouseEvent(0,500,60, 1);
		String d = "default";
		for(int i=0;i<d.length();i++){
			myCW.handleKeyEvent(0, 0, d.charAt(i));
		}
		myCW.handleKeyEvent(0,10,' ');
		myCW.handleKeyEvent(0,27,' ');
		myCW.handleMouseEvent(0,85, 30, 2);
		assertEquals(Loadable_Interfaces.TABLE_ROWS,coMan.getMode());
		myCW.handleMouseEvent(0, 40, 530 , 2);
		assertEquals(1,coMan.getActiveTable().getRows().size());
	
	
	}
	
}

