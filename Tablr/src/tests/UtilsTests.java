package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import Utils.*;

public class UtilsTests { 
	
	@Test (expected=ClassCastException.class)
	public void testBooleanCaster() {
		BooleanCaster b;
		assertTrue(BooleanCaster.cast("TrUE"));
		assertFalse(BooleanCaster.cast("False"));
		assertNull(BooleanCaster.cast(""));
		BooleanCaster.cast("jeifjwofj");
		
	}
	
	@Test 
	public void testGeometricUtils() {
		int[] intersect = GeometricUtils.intersection(-100, -100, 200, 200, -50, -50, 10, 10);
		assertEquals(-50,intersect[0]);
		assertEquals(-50,intersect[1]);
		assertEquals(10,intersect[2]);
		assertEquals(10,intersect[3]);
	}
	
	@Test
	public void testRound() {
		Rounder r = new Rounder();
		int[] ints = new int[]{r.round(1.5),r.round(1.7),r.round(1.3),r.round(4.1)};
		assertEquals(1,ints[0]);
		assertEquals(2,ints[1]);
		assertEquals(1,ints[2]);
		assertEquals(4,ints[3]);
	}
	
	
}