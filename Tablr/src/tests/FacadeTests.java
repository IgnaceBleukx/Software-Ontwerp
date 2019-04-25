package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ui.TableDesignModeUI;
import canvaswindow.MyCanvasWindow;
import domain.Table;
import facades.Tablr;

public class FacadeTests {

	
	@Test
	public void testLoadUIModes() {
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design mode test");
		Tablr tablr = myCW.getTablr();
		Table t = tablr.addEmptyTable();
		System.out.println(tablr);
		tablr.loadTableDesignModeUI(t);
		tablr.loadTableRowsModeUI(t);
	}

}
