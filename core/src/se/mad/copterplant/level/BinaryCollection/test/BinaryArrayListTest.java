package se.mad.copterplant.level.BinaryCollection.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.mad.copterplant.level.BinaryCollection.BinaryArrayList;

public class BinaryArrayListTest {
	
	BinaryArrayList list;
	
	@Before
	public void setUp() throws Exception {
		list = new BinaryArrayList(1280);
	}

	@After
	public void tearDown() throws Exception {
		list = null;
	}

	/*@Test
	public void testBinaryArrayList() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testGetBoolean() {
		list.setTrue(0);
		list.setTrue(10);
		list.setTrue(31);
		list.setTrue(32);
		list.setTrue(33);
		list.setTrue(63);
		list.setTrue(64);
		list.setTrue(65);
		
		assertEquals(true, list.getBoolean(0));
		assertEquals(true, list.getBoolean(10));
		assertEquals(true, list.getBoolean(31));
		assertEquals(true, list.getBoolean(32));
		assertEquals(true, list.getBoolean(33));
		assertEquals(true, list.getBoolean(63));
		assertEquals(true, list.getBoolean(64));
		assertEquals(true, list.getBoolean(65));
	}

	/*@Test
	public void testSetTrue() {
		
	}*/

	@Test
	public void testSetFalse() {
		list.setTrue(0);
		list.setTrue(10);
		list.setTrue(31);
		list.setTrue(32);
		list.setTrue(33);
		list.setTrue(63);
		list.setTrue(64);
		list.setTrue(65);
		
		list.setFalse(0);
		list.setFalse(10);
		list.setFalse(31);
		list.setFalse(32);
		list.setFalse(33);
		list.setFalse(63);
		list.setFalse(64);
		list.setFalse(65);
		
		assertEquals(false, list.getBoolean(0));
		assertEquals(false, list.getBoolean(10));
		assertEquals(false, list.getBoolean(31));
		assertEquals(false, list.getBoolean(32));
		assertEquals(false, list.getBoolean(33));
		assertEquals(false, list.getBoolean(63));
		assertEquals(false, list.getBoolean(64));
		assertEquals(false, list.getBoolean(65));
	}
	
	@Test
	public void testSetMultipleTrue() {
		list.setTrue(5, 25);
		
		for (int i = 0; i < 5; i++) {
			assertFalse(list.getBoolean(i));
		}
		for (int i = 5; i < 26; i++) {
			assertTrue(list.getBoolean(i));
		}
		for (int i = 26; i < 32; i++) {
			assertFalse(list.getBoolean(i));
		}
		
		list.setTrue(15, 70);
		
		for (int i = 15; i < 71; i++) {
			assertTrue(list.getBoolean(i));
		}
		for (int i = 71; i < 90; i++) {
			assertFalse(list.getBoolean(i));
		}
	}

	@Test
	public void testFirstTrueBit() {		
		list.setTrue(0);
		list.setTrue(10);
		list.setTrue(31);
		list.setTrue(32);
		list.setTrue(33);
		list.setTrue(63);
		list.setTrue(64);
		list.setTrue(65);
		
		assertEquals(0, list.firstTrueBit());
		list.setFalse(0);

		assertEquals(10, list.firstTrueBit());
		list.setFalse(10);

		assertEquals(31, list.firstTrueBit());
		list.setFalse(31);

		assertEquals(32, list.firstTrueBit());
		list.setFalse(32);

		assertEquals(33, list.firstTrueBit());
		list.setFalse(33);

		assertEquals(63, list.firstTrueBit());
		list.setFalse(63);

		assertEquals(64, list.firstTrueBit());
		list.setFalse(64);

		assertEquals(65, list.firstTrueBit());
		list.setFalse(65);
		
		assertEquals(-1, list.firstTrueBit());
	}

	@Test
	public void testLastTrueBit() {
		list.setTrue(0);
		list.setTrue(10);
		list.setTrue(31);
		list.setTrue(32);
		list.setTrue(33);
		list.setTrue(63);
		list.setTrue(64);
		list.setTrue(65);
		
		assertEquals(65, list.lastTrueBit());
		list.setFalse(65);
		
		assertEquals(64, list.lastTrueBit());
		list.setFalse(64);
		
		assertEquals(63, list.lastTrueBit());
		list.setFalse(63);
		
		assertEquals(33, list.lastTrueBit());
		list.setFalse(33);
		
		assertEquals(32, list.lastTrueBit());
		list.setFalse(32);
		
		assertEquals(31, list.lastTrueBit());
		list.setFalse(31);
		
		assertEquals(10, list.lastTrueBit());
		list.setFalse(10);
		
		assertEquals(0, list.lastTrueBit());
		list.setFalse(0);
		
		assertEquals(-1, list.lastTrueBit());
		
	}

	@Test
	public void testFirstTrueBitAfter() {
		list.setTrue(0);
		list.setTrue(10);
		list.setTrue(31);
		list.setTrue(32);
		list.setTrue(33);
		list.setTrue(63);
		list.setTrue(64);
		list.setTrue(65);
		
		assertEquals(10, list.firstTrueBitAfter(2));
		assertEquals(31, list.firstTrueBitAfter(30));
		assertEquals(32, list.firstTrueBitAfter(31));
		assertEquals(33, list.firstTrueBitAfter(32));
		assertEquals(63, list.firstTrueBitAfter(62));
		assertEquals(64, list.firstTrueBitAfter(63));
		assertEquals(65, list.firstTrueBitAfter(64));
		assertEquals(-1, list.firstTrueBitAfter(65));
	}
	
	@Test
	public void testLastTrueBitBefore() {
		
		list.setTrue(0);
		list.setTrue(10);
		list.setTrue(31);
		list.setTrue(32);
		list.setTrue(33);
		list.setTrue(63);
		list.setTrue(64);
		list.setTrue(65);
		
		assertEquals(0, list.lastTrueBitBefore(5));
		assertEquals(10, list.lastTrueBitBefore(11));
		assertEquals(31, list.lastTrueBitBefore(32));
		assertEquals(32, list.lastTrueBitBefore(33));
		assertEquals(33, list.lastTrueBitBefore(34));
		assertEquals(63, list.lastTrueBitBefore(64));
		assertEquals(64, list.lastTrueBitBefore(65));
		assertEquals(65, list.lastTrueBitBefore(66));
		list.setFalse(0);
		assertEquals(-1, list.lastTrueBitBefore(2));
	}
	
	@Test
	public void testFirstFalseBitAfter(){
		list.setAllTrue();

		list.setFalse(0);
		list.setFalse(10);
		list.setFalse(31);
		list.setFalse(32);
		list.setFalse(33);
		list.setFalse(63);
		list.setFalse(64);
		list.setFalse(65);
		
		assertEquals(10, list.firstFalseBitAfter(2));
		assertEquals(31, list.firstFalseBitAfter(30));
		assertEquals(32, list.firstFalseBitAfter(31));
		assertEquals(33, list.firstFalseBitAfter(32));
		assertEquals(63, list.firstFalseBitAfter(62));
		assertEquals(64, list.firstFalseBitAfter(63));
		assertEquals(65, list.firstFalseBitAfter(64));
		assertEquals(-1, list.firstFalseBitAfter(65));
	}
	
	@Test
	public void testLastFalseBitBefore() {
		list.setAllTrue();

		list.setFalse(0);
		list.setFalse(10);
		list.setFalse(31);
		list.setFalse(32);
		list.setFalse(33);
		list.setFalse(63);
		list.setFalse(64);
		list.setFalse(65);
		
		assertEquals(0, list.lastFalseBitBefore(5));
		assertEquals(10, list.lastFalseBitBefore(11));
		assertEquals(31, list.lastFalseBitBefore(32));
		assertEquals(32, list.lastFalseBitBefore(33));
		assertEquals(33, list.lastFalseBitBefore(34));
		assertEquals(63, list.lastFalseBitBefore(64));
		assertEquals(64, list.lastFalseBitBefore(65));
		assertEquals(65, list.lastFalseBitBefore(66));
		list.setTrue(0);
		assertEquals(-1, list.lastFalseBitBefore(2));
	}
	
	
	@Test
	public void testWorldView() {
		int shiet = ~0;
		shiet = (shiet<<1) +1;
		assertTrue(shiet==-1);
		
		//System.out.println(Integer.toBinaryString(-1));
		//System.out.println(Integer.toBinaryString(~0));
		
		/*
		shiet = 1<<31;
		shiet = shiet >> 20;
		System.out.println(Integer.toBinaryString(shiet));
		shiet = shiet >>> 5;
		System.out.println(Integer.toBinaryString(shiet));
		*/
	}

	/*@Test
	public void testFirstFalseBit() {
		fail("Not yet implemented");
	}

	

	@Test
	public void testLastFalseBit() {
		fail("Not yet implemented");
	}*/

}
