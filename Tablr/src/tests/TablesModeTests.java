package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import facades.CommunicationManager;
import ui.Loadable_Interfaces;

public class TablesModeTests {
	

	private CommunicationManager coMan;

	@Before
	public void setUp() throws Exception {
		coMan = new CommunicationManager();
		coMan.loadUI(Loadable_Interfaces.TABLES);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
