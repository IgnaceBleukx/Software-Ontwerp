package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ui.TableDesignModeUI;
import ui.TableRowsModeUI;
import ui.TablesModeUI;
import uielements.CloseButton;
import uielements.UIElement;
import canvaswindow.MyCanvasWindow;
import domain.Column;
import domain.Table;
import domain.Type;
import facades.Tablr;
import facades.WindowManager;

public class FacadeTests {

	
	@Test
	public void testLoadUIModes() {
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design mode test");
		Tablr tablr = myCW.getTablr();
		Table table1 = tablr.addEmptyTable();
		Table table2 = tablr.addEmptyTable();
		table2.addEmptyColumn(Type.STRING, "default");
		tablr.loadTableDesignModeUI(table1);
		tablr.loadTableDesignModeUI(table2);
		tablr.removeTable(table1);
		assertEquals(1,tablr.getTables().size());
		assertTrue(tablr.getTables().contains(table2));
		
		ArrayList<Table> f = tablr.getTablesByName(table2.getName());
		assertTrue(f.contains(table2));
		assertEquals(1,f.size());
		
		tablr.renameTable(table2, "New name");
		ArrayList<Table> f2 = tablr.getTablesByName("New name");
		assertTrue(f2.contains(table2));
		assertTrue(f2.size() == 1);
		
		tablr.addRow(table2);
		assertEquals(1, tablr.getRows(table2));
		Column c = tablr.getColumns(table2).get(0);
		tablr.changeCellValue(c, 0, "New value");
		assertEquals("New value", (String) c.getCell(0).getValue());
		tablr.removeRow(table2, 0);
		assertEquals(0,tablr.getRows(table2));
		
	}
	
	@Test
	public void testControlPress() {
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design mode test");
		Tablr tablr = myCW.getTablr();
		tablr.controlPressed();
	}
	
	@Test
	public void testTablesModeUIs1() {
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design mode test");
		Tablr tablr = myCW.getTablr();
		tablr.addEmptyTable();
		tablr.addTablesModeUI();
		TablesModeUI ui = tablr.getTablesModeUIs().get(0);
		ui.setInactive();
		assertTrue(!(ui == null));
		
		WindowManager m = ui.getWindowManager();
		assertEquals(tablr,m.getCommunicationManager());
		
		m.addTablesModeUI();
		m.clearUIAt(100, 100);
		assertEquals(0,m.getUIAt(100, 100).getElements().size());
	}
	
	@Test
	public void testTablesModeUIs2() {
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design mode test");
		Tablr tablr = myCW.getTablr();
		tablr.addEmptyTable();
		tablr.addTablesModeUI();
		TablesModeUI ui = tablr.getTablesModeUIs().get(0);
		assertTrue(!(ui == null));
		
		WindowManager m = ui.getWindowManager();
		assertEquals(tablr,m.getCommunicationManager());
		
		m.addTablesModeUI();
	}
	
	@Test
	public void testTableRowsModeUIs() {
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design mode test");
		Tablr tablr = myCW.getTablr();
		Table t = tablr.addEmptyTable();
		tablr.addTablesModeUI();
		TablesModeUI ui = tablr.getTablesModeUIs().get(0);
		assertTrue(!(ui == null));
		WindowManager m = ui.getWindowManager();
		
		m.loadTableRowsModeUI(t);
		TableRowsModeUI a = m.getTableRowsUIs().get(t).get(0);
		a.setActive();
		m.loadTableRowsModeUI(t);
	}
	
	@Test
	public void testTableDesignModeUIs() {
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design mode test");
		Tablr tablr = myCW.getTablr();
		Table t = tablr.addEmptyTable();
		tablr.addTablesModeUI();
		TablesModeUI ui = tablr.getTablesModeUIs().get(0);
		assertTrue(!(ui == null));
		WindowManager m = ui.getWindowManager();
		
		m.loadTableDesignModeUI(t);
		TableDesignModeUI a = m.getTableDesignUIs().get(t).get(0);
		a.setActive();
		m.loadTableDesignModeUI(t);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSelectionLockWindowManager() {
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design mode test");
		Tablr tablr = myCW.getTablr();
		Table t = tablr.addEmptyTable();
		tablr.addTablesModeUI();
		TablesModeUI ui = tablr.getTablesModeUIs().get(0);
		assertTrue(!(ui == null));
		WindowManager m = ui.getWindowManager();
		
		ArrayList<UIElement> elements = ui.getElements();
		UIElement e = elements.get(0);
		UIElement e2 = elements.get(1);
		
		tablr.getSelectionLock(e);
		tablr.releaseSelectionLock(e);
		
		tablr.getLock(e);
		tablr.releaseLock(e);
		
		tablr.releaseLock(e2);
		

	}
	
	@Test
	public void testSelectingUIs() {
		MyCanvasWindow myCW = new MyCanvasWindow("Table Design mode test");
		Tablr tablr = myCW.getTablr();
		Table t = tablr.addEmptyTable();
		tablr.addTablesModeUI();
		tablr.addTablesModeUI();
		tablr.addTablesModeUI();


		TablesModeUI ui = tablr.getTablesModeUIs().get(0);
		TablesModeUI ui2 = tablr.getTablesModeUIs().get(1);
		TablesModeUI ui3 = tablr.getTablesModeUIs().get(2);

		assertTrue(!(ui == null));
		WindowManager m = ui.getWindowManager();
		
		CloseButton c = null;
		for (UIElement e : ui.getElements()) {
			if (e instanceof CloseButton)
				c = (CloseButton) e;
		}
		
		assertTrue(c != null);
		c.handleSingleClick(); //Close the first UI
		m.selectNewUI(); //Let the winodwmanager select a new UI
		assertEquals(ui3,tablr.getSelectedUI());
		
		m.selectUI(ui3);
		m.selectNewUI();
		assertEquals(ui2,tablr.getSelectedUI());

	}
	
	

}
