/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oodpcourseworkpoker;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marshall_gj
 */
public class SuitTest {

	@Test
	public void testToString() {
		String expected = "Diamonds";
		String output = Suit.DIAMONDS.toString();
		assertEquals("Problem with toString() override", expected, output);
	}

	@Test 
	public void equalityAmongSuits() {
		Suit diamond1 = Suit.DIAMONDS;
		Suit diamond2 = Suit.DIAMONDS;
		boolean testEquality = false;
		if (diamond1 != diamond2) {
			testEquality = true;
		}
		assertFalse(testEquality);
	}

}