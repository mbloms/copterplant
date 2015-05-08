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

	/*@Test
	public void testFirstFalseBit() {
		fail("Not yet implemented");
	}

	@Test
	public void testLastTrueBit() {
		fail("Not yet implemented");
	}

	@Test
	public void testLastFalseBit() {
		fail("Not yet implemented");
	}*/

}
