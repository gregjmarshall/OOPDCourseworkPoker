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
public class RankTest {

	@Test
	public void testGetValue() { 
		int expected = 1;
		int output = Rank.ACE.getValue();
		assertEquals("Problem with getValue()", expected, output);
	}

	@Test
	public void testGetValue2() {
		int expected = 13;
		int output = Rank.KING.getValue();
		assertEquals("Problem with getValue()", expected, output);
	}

	@Test
	public void testToString() {
		String expected = "Five";
		String output = Rank.FIVE.toString();
		assertEquals("Problem with toString() override", expected, output);
	}

}