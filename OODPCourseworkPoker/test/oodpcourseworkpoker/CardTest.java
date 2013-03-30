
package oodpcourseworkpoker;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *@author Anna Taylor
 * @author Greg Marshall
 * MSc Computer Science 
 * Submission date: 31/03/2013
 */

public class CardTest {

	@Test
	public void testGetSuit() {
		Suit expectedSuit = Suit.HEARTS;
		Card card = new CardImpl(Rank.ACE, expectedSuit);
		Suit outputSuit = card.getSuit();

		assertEquals("Problem with getSuit()", expectedSuit, outputSuit);		
	}

	@Test
	public void testGetRank() {
		Rank expectedRank = Rank.ACE;
		Card card = new CardImpl(expectedRank, Suit.SPADES);
		Rank outputRank = card.getRank();

		assertEquals("Problem with getRank()", expectedRank, outputRank);		
	}

	@Test
	public void testToString() {
		Card card = new CardImpl(Rank.TWO, Suit.HEARTS);
		String output = card.toString();
		String expected = "[Two Hearts]";

		assertEquals("Problem with toString()", expected, output);
	}

	@Test
	public void testToString2() {
		Card card = new CardImpl(Rank.JACK, Suit.CLUBS);
		String output = card.toString();
		String expected = "[Jack Clubs]";

		assertEquals("Problem with toString()", expected, output);
	}

	@Test
	public void testComparison() {
		Card kingHearts = new CardImpl(Rank.KING, Suit.HEARTS);
		Card kingSpades = new CardImpl(Rank.KING, Suit.HEARTS);
		int expected = 0; //compareTo should return 0 if two cards are of equal rank
		assertEquals("CompareTo not working correctly", expected, kingHearts.compareTo(kingSpades));
	}

	@Test
	public void testComparison2() {
		Card jackDiamonds = new CardImpl(Rank.JACK, Suit.DIAMONDS);
		Card tenClubs = new CardImpl(Rank.TEN, Suit.CLUBS);
		int expected = 1; //compareTo should return 1 if the current card is greater than the second
		assertEquals("CompareTo not working correctly", expected, jackDiamonds.compareTo(tenClubs));
	}

	@Test
	public void testComparison3() {
		Card twoSpades = new CardImpl(Rank.TWO, Suit.SPADES);
		Card nineHearts = new CardImpl(Rank.NINE, Suit.HEARTS);
		int expected = -7; //compareTo should return 1 if the current card is greater than the second
		assertEquals("CompareTo not working correctly", expected, twoSpades.compareTo(nineHearts));
	}





}