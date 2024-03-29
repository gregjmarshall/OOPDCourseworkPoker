
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
public class HandTest {
	private Hand testHand;
	private Deck testDeck;

	@Before
	public void setUp() {
		testHand = new FiveCardHand();
		testDeck = new DeckImpl();
	}

	@After
	public void tearDown() {
		testHand = null;
		testDeck = null;
	}


	@Test
	public void testGetContents() {
		Card[] contents = testHand.getContents();
		assertNotNull(contents);

	}

	@Test
	public void testGetContents2() { //Tests the size is correct (5)
		Card[] contents = testHand.getContents();
		int expectedSize = 5;
		int actualSize = contents.length;
		assertEquals("Problem with handContents initialisation", expectedSize, actualSize);
	}

	@Test
	public void testGetContents3()	{
		Card[] contents = testHand.getContents();
		assertNull(contents[0]); //handContents should be an empty array upon creation.
	}

	@Test
	public void testAddCard() {
		for (int i = 0; i < 5; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.values()[0]);
			testHand.addCard(c);
			assertEquals(c, testHand.getContents()[i]);//Tests that each card was in fact added to the hand.
		}		
	}


	@Test
	public void testDisplayHand() {
		for (int i = 0; i < 5; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.values()[0]);
			testHand.addCard(c);
		}
		String expected = "1: [Two Clubs]" + "\n" + "2: [Three Clubs]" + "\n" + "3: [Four Clubs]" + "\n"
				+ "4: [Five Clubs]" + "\n" + "5: [Six Clubs]" + "\n";
		String output = testHand.displayHand();
		assertEquals(expected, output);
	}

	@Test
	public void testSort() {
		testDeck.shuffleCards();
		for (int i = 0; i < 5; i++) {
			testHand.addCard(testDeck.popCard());
		}
		testHand.sort();
		boolean sorted = true;
		for (int i = 0; i < 4; i++) {
			if (testHand.getContents()[i].getRankValue() > testHand.getContents()[i+1].getRankValue()) {
				sorted = false; //If next card is less than current card (i.e. they're not sorted)
			}
		}
		assertTrue(sorted);

	}


	@Test
	public void testEvaluateFourOfAKind() {//Tests for first four cards being four of a kind
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}
		Card c = new CardImpl(Rank.SEVEN, Suit.HEARTS);
		testHand.addCard(c);
		testHand.evaluateHand();

		String expectedValue = "Four of a Kind";
		String outputValue = testHand.getHandValue();
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testEvaluateFourOfAKind2() {//Tests for last four cards being four of a kind
		Card firstCard = new CardImpl(Rank.SEVEN, Suit.HEARTS);
		testHand.addCard(firstCard);
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}
		testHand.evaluateHand();

		String expectedValue = "Four of a Kind";
		String outputValue = testHand.getHandValue();
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testEvaluateFourOfAKindFalse() {//Tests that four of a kind value is not wrongly assigned to borderline case
		Card firstCard = new CardImpl(Rank.SEVEN, Suit.HEARTS);
		testHand.addCard(firstCard);
		Card secondCard = new CardImpl(Rank.SIX, Suit.SPADES);
		testHand.addCard(secondCard);
		for (int i = 0; i < 3; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}
		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		assertFalse(outputValue.equals("Four of a Kind"));
	}

	@Test
	public void testEvaluateFlush() { //Tests that a flush is picked up by evaluateHand();
		for (int i = 0; i < 5; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.HEARTS);
			testHand.addCard(c);
		}
		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "Flush";
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testEvaluateFlushFalse() { //Tests a borderline case where four cards have the same suit
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.HEARTS);
			testHand.addCard(c);
		}
		Card c = new CardImpl(Rank.values()[2], Suit.DIAMONDS);
		testHand.addCard(c);
		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		assertFalse(outputValue.equals("Flush"));
	}

	@Test
	public void testEvaluateFlushOverStraight() { //Tests that a straight flush is assigned flush (as straight flush isn't in the brief)
		for (int i = 0; i < 5; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.HEARTS);
			testHand.addCard(c);
		}		
		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "Flush";
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testLowAceStraight() {
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.HEARTS);
			testHand.addCard(c);
		}	
		Card ace = new CardImpl(Rank.ACE, Suit.CLUBS);
		testHand.addCard(ace);
		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "Straight";
		assertEquals(expectedValue, outputValue);		
	}

	@Test 
	public void testStandardStraight() {
		for (int i = 7; i < 11; i++) { //for a standard straight (i.e. ace isn't low) 
			Card c = new CardImpl(Rank.values()[i], Suit.CLUBS);
			testHand.addCard(c);
		}	
		Card king = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(king);
		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "Straight";
		assertEquals(expectedValue, outputValue);	

	}

	@Test
	public void testThreeOfAKindFirstThree() { //Tests for three of a kind in first position (TTTXX)
		Card first = new CardImpl(Rank.TWO, Suit.CLUBS);
		testHand.addCard(first);
		Card second = new CardImpl(Rank.TWO, Suit.SPADES);
		testHand.addCard(second);		
		Card third = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(third);
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SIX, Suit.values()[i]);
			testHand.addCard(c);
		}
		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "Three of a Kind";
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testThreeOfAKindMiddleThree() { //XTTTX
		Card first = new CardImpl(Rank.THREE, Suit.CLUBS);
		testHand.addCard(first);

		for (int i = 1; i < 4; i++) {
			Card c = new CardImpl(Rank.FOUR, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();
		String outputValue = testHand.getHandValue();
		String expectedValue = "Three of a Kind";
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testThreeOfAKindLastThree() { //XXTTT
		Card first = new CardImpl(Rank.NINE, Suit.CLUBS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.TEN, Suit.DIAMONDS);
		testHand.addCard(second);

		for (int i = 2; i < 5; i++) {
			Card c = new CardImpl(Rank.KING, Suit.values()[i-1]);
			testHand.addCard(c);
		}			
		testHand.evaluateHand();	

		String outputValue = testHand.getHandValue();
		String expectedValue = "Three of a Kind";
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testThreeKindProcessingValueLast() { //To test that processingValue field is set correctly (and that the getter works)
		Card first = new CardImpl(Rank.THREE, Suit.CLUBS); //XXTTT
		testHand.addCard(first);

		Card second = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(second);

		for (int i = 2; i < 5; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i-1]);
			testHand.addCard(c);
		}	

		testHand.evaluateHand();		

		int outputValue = testHand.getProcessingValue();
		int expectedValue = 3;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testThreeKindProcessingValueMid() { //XTTTX
		Card first = new CardImpl(Rank.THREE, Suit.CLUBS);
		testHand.addCard(first);

		for (int i = 1; i < 4; i++) {
			Card c = new CardImpl(Rank.FOUR, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		int outputValue = testHand.getProcessingValue();
		int expectedValue = 2;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testThreeKindProcessingValueFirst() { //TTTXX
		Card first = new CardImpl(Rank.TWO, Suit.CLUBS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.TWO, Suit.SPADES);
		testHand.addCard(second);		

		Card third = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(third);

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SIX, Suit.values()[i]);
			testHand.addCard(c);
		}

		testHand.evaluateHand();
		int outputValue = testHand.getProcessingValue();
		int expectedValue = 1;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testOnePairPosition1() { //PPXXX		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card third = new CardImpl(Rank.NINE, Suit.DIAMONDS);
		testHand.addCard(third);

		Card fourth = new CardImpl(Rank.THREE, Suit.DIAMONDS);
		testHand.addCard(fourth);

		Card fifth = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "One Pair";
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testOnePairPosition2() { //XPPXX		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.NINE, Suit.DIAMONDS);
		testHand.addCard(second);

		Card fifth = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "One Pair";
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testOnePairPosition3() { //XXPPX		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SEVEN, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.THREE, Suit.DIAMONDS);
		testHand.addCard(second);

		Card fifth = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "One Pair";
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testOnePairPosition4() { //XXXPP		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.QUEEN, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.THREE, Suit.DIAMONDS);
		testHand.addCard(second);

		Card third = new CardImpl(Rank.JACK, Suit.SPADES);
		testHand.addCard(third);

		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "One Pair";
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testTwoPairPosition1() { //PPPPX		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.FOUR, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "Two Pair";
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testTwoPairPosition2() { //PPXPP		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "Two Pair";
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testTwoPairPosition3() { //XPPPP		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SIX, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.KING, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.FIVE, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		String outputValue = testHand.getHandValue();
		String expectedValue = "Two Pair";
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testOnePairPosition1ProcessingValue() { //PPXXX		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card third = new CardImpl(Rank.NINE, Suit.DIAMONDS);
		testHand.addCard(third);

		Card fourth = new CardImpl(Rank.THREE, Suit.DIAMONDS);
		testHand.addCard(fourth);

		Card fifth = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(fifth);

		testHand.evaluateHand();
		int outputValue = testHand.getProcessingValue();
		int expectedValue = 1;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testOnePairPosition2ProcessingValue() { //XPPXX		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.NINE, Suit.DIAMONDS);
		testHand.addCard(second);

		Card fifth = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(fifth);

		testHand.evaluateHand();
		int outputValue = testHand.getProcessingValue();
		int expectedValue = 2;
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testOnePairPosition3ProcessingValue() { //XXPPX		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SEVEN, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.THREE, Suit.DIAMONDS);
		testHand.addCard(second);

		Card fifth = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(fifth);

		testHand.evaluateHand();
		int outputValue = testHand.getProcessingValue();
		int expectedValue = 3;
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testOnePairPosition4ProcessingValue() { //XXXPP		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.QUEEN, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.THREE, Suit.DIAMONDS);
		testHand.addCard(second);

		Card third = new CardImpl(Rank.JACK, Suit.SPADES);
		testHand.addCard(third);

		testHand.evaluateHand();
		int outputValue = testHand.getProcessingValue();
		int expectedValue = 4;
		assertEquals(expectedValue, outputValue);
	}


	@Test
	public void testTwoPairPosition1ProcessingValue() { //PPPPX		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.FOUR, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();
		int outputValue = testHand.getProcessingValue();
		int expectedValue = 1;
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testTwoPairPosition2ProcessingValue() { //PPXPP		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();
		int outputValue = testHand.getProcessingValue();
		int expectedValue = 2;
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testTwoPairPosition3ProcesingValue() { //XPPPP		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SIX, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.KING, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.FIVE, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();
		int outputValue = testHand.getProcessingValue();
		int expectedValue = 3;
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testHighCard() { //Test that a non-made hand is correctly evaluated
		for (int i = 0; i < 4; i++) { 
			Card c = new CardImpl(Rank.values()[i+2], Suit.CLUBS); //Create unmade hand
			testHand.addCard(c);
		}	
		Card ace = new CardImpl(Rank.ACE, Suit.SPADES);
		testHand.addCard(ace);

		testHand.evaluateHand();		
		String expectedValue = ace.toString() + " High";
		String outputValue = testHand.getHandValue();
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testEvaluateHandBorderline() { // Test that Three of a Kind is picked over One Pair		
		for (int i = 0; i < 3; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fourth = new CardImpl(Rank.SEVEN, Suit.HEARTS);
		testHand.addCard(fourth);

		Card fifth = new CardImpl(Rank.SEVEN, Suit.SPADES);
		testHand.addCard(fifth);

		testHand.evaluateHand();
		String expectedValue = "Three of a Kind";
		String outputValue = testHand.getHandValue();
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testHandValueScoreQuads() { //Test the handScore value for quads
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}
		Card c = new CardImpl(Rank.SEVEN, Suit.HEARTS);
		testHand.addCard(c);
		testHand.evaluateHand();

		int expectedValue = 7;
		int outputValue = testHand.getHandValueScore();
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testHandValueScoreFlush() {
		for (int i = 0; i < 5; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.HEARTS);
			testHand.addCard(c);
		}
		testHand.evaluateHand();

		int outputValue = testHand.getHandValueScore();
		int expectedValue = 6;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testHandValueScoreStraight() {
		for (int i = 7; i < 11; i++) { //for a standard straight (i.e. ace isn't low) 
			Card c = new CardImpl(Rank.values()[i], Suit.CLUBS);
			testHand.addCard(c);
		}	
		Card king = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(king);
		testHand.evaluateHand();

		int outputValue = testHand.getHandValueScore();
		int expectedValue = 5;
		assertEquals(expectedValue, outputValue);	
	}

	@Test
	public void testHandValueScoreTrips() {
		Card first = new CardImpl(Rank.NINE, Suit.CLUBS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.TEN, Suit.DIAMONDS);
		testHand.addCard(second);

		for (int i = 2; i < 5; i++) {
			Card c = new CardImpl(Rank.KING, Suit.values()[i-1]);
			testHand.addCard(c);
		}			
		testHand.evaluateHand();	

		int outputValue = testHand.getHandValueScore();
		int expectedValue = 4;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testHandValueScoreTwoPair() {
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SIX, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.KING, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.FIVE, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		int outputValue = testHand.getHandValueScore();
		int expectedValue = 3;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testHandValueScorePair() {
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.NINE, Suit.DIAMONDS);
		testHand.addCard(second);

		Card fifth = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		int outputValue = testHand.getHandValueScore();
		int expectedValue = 2;
		assertEquals(expectedValue, outputValue);		
	}

	@Test
	public void testHandValueScoreUnmade() {
		for (int i = 0; i < 4; i++) { 
			Card c = new CardImpl(Rank.values()[i+2], Suit.CLUBS);
			testHand.addCard(c);
		}	
		Card king = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(king);

		testHand.evaluateHand();

		int outputValue = testHand.getHandValueScore();
		int expectedValue = 1;
		assertEquals(expectedValue, outputValue);	
	}

	@Test
	public void testHandComparison() { //Test flush vs four of a kind
		Hand flush = new FiveCardHand();
		for (int i = 0; i < 5; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.HEARTS);
			flush.addCard(c);
		}
		flush.evaluateHand();

		Hand quads = new FiveCardHand();
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			quads.addCard(c);
		}
		Card c = new CardImpl(Rank.SEVEN, Suit.HEARTS);
		quads.addCard(c);
		quads.evaluateHand();

		//Test that four of a kind is greater than a flush
		int output = flush.compareTo(quads);
		assertTrue(output < 0);	//A negative value should be returned because a flush is less than four of a kind	

	}

	@Test
	public void testHandComparison2() { //Test four of a kind vs four of a kind (equality)
		Hand quads = new FiveCardHand();
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			quads.addCard(c);
		}
		Card c = new CardImpl(Rank.SEVEN, Suit.HEARTS);
		quads.addCard(c);
		quads.evaluateHand();

		Hand quads2 = new FiveCardHand();
		for (int i = 0; i < 4; i++) {
			Card d = new CardImpl(Rank.SEVEN, Suit.values()[i]);
			quads2.addCard(d);
		}
		Card e = new CardImpl(Rank.TWO, Suit.HEARTS);
		quads2.addCard(e);
		quads2.evaluateHand();

		//Test that 0 is returned; 0 means equality
		int output = quads.compareTo(quads2);
		assertTrue(output == 0);		
	}

	@Test
	public void testHandComparison3() { //Test flush vs straight
		Hand flush = new FiveCardHand();
		for (int i = 0; i < 5; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.HEARTS);
			flush.addCard(c);
		}
		flush.evaluateHand();

		Hand straight = new FiveCardHand();
		for (int i = 7; i < 11; i++) { //for a standard straight (i.e. ace isn't low) 
			Card c = new CardImpl(Rank.values()[i], Suit.CLUBS);
			straight.addCard(c);
		}	
		Card king = new CardImpl(Rank.KING, Suit.SPADES);
		straight.addCard(king);

		//Assert that straight is less than flush
		int output = straight.compareTo(flush);
		assertTrue(output < 0); //Negative values means object that invoked compareTo is less than parameter object
	}

	@Test
	public void testHandComparisonPairHighCard() { //Test pair vs high card
		Hand pair = new FiveCardHand();

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SEVEN, Suit.values()[i]);
			pair.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		pair.addCard(first);

		Card second = new CardImpl(Rank.THREE, Suit.DIAMONDS);
		pair.addCard(second);

		Card fifth = new CardImpl(Rank.KING, Suit.SPADES);
		pair.addCard(fifth);

		pair.evaluateHand();

		Hand highCard = new FiveCardHand();
		for (int i = 0; i < 4; i++) { 
			Card c = new CardImpl(Rank.values()[i+2], Suit.CLUBS); //Create high card hand
			highCard.addCard(c);
		}	
		Card king = new CardImpl(Rank.KING, Suit.SPADES);
		highCard.addCard(king);

		highCard.evaluateHand();

		//Assert that high card is less than one pair
		int output = highCard.compareTo(pair);
		assertTrue(output < 0); //Negative values means object that invoked compareTo is less than parameter object
	}

	@Test
	public void testTwoPairVsHighCard() { //Tests Two Pair vs High Card
		Hand twoPair = new FiveCardHand();

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			twoPair.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			twoPair.addCard(c);
		}

		Card fifth = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		twoPair.addCard(fifth);

		twoPair.evaluateHand();

		Hand highCard = new FiveCardHand();
		for (int i = 0; i < 4; i++) { //for a standard straight (i.e. ace isn't low) 
			Card c = new CardImpl(Rank.values()[i+2], Suit.CLUBS);
			highCard.addCard(c);
		}	
		Card king = new CardImpl(Rank.KING, Suit.SPADES);
		highCard.addCard(king);

		highCard.evaluateHand();

		int outputValue = highCard.compareTo(twoPair);
		assertTrue(outputValue < 0);

	}

	@Test
	public void testPairValueQuadsPosition1() { //QQQQX
		Card firstCard = new CardImpl(Rank.SEVEN, Suit.HEARTS);
		testHand.addCard(firstCard);
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}
		testHand.evaluateHand();

		int expectedValue = 2;
		int outputValue = testHand.getPairValue();
		assertEquals(expectedValue, outputValue);
	}

	@Test public void testPairValueQuadsPosition2() { //XQQQQ
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}
		Card c = new CardImpl(Rank.SEVEN, Suit.HEARTS);
		testHand.addCard(c);
		testHand.evaluateHand();

		int expectedValue = 2;
		int outputValue = testHand.getPairValue();
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testPairValueTripsPosition1() { //TTTXX
		Card first = new CardImpl(Rank.TWO, Suit.CLUBS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.TWO, Suit.SPADES);
		testHand.addCard(second);		

		Card third = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(third);

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SIX, Suit.values()[i]); //Creates trips
			testHand.addCard(c);
		}

		testHand.evaluateHand();
		int outputValue = testHand.getPairValue();
		int expectedValue = 2;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testPairValueTripsPosition2() { // XTTTX
		Card first = new CardImpl(Rank.THREE, Suit.CLUBS);
		testHand.addCard(first);

		for (int i = 1; i < 4; i++) {
			Card c = new CardImpl(Rank.FOUR, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		int outputValue = testHand.getPairValue();
		int expectedValue = 4;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testPairValueTripsPosition3() { //XXTTT
		Card first = new CardImpl(Rank.THREE, Suit.CLUBS); 
		testHand.addCard(first);

		Card second = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(second);

		for (int i = 2; i < 5; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i-1]);
			testHand.addCard(c);
		}	

		testHand.evaluateHand();		

		int outputValue = testHand.getPairValue();
		int expectedValue = 11;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testPairValueTwoPairPosition1() { //PPPPX
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card third = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(third);

		testHand.evaluateHand();

		int outputValue = testHand.getPairValue();
		int expectedValue = 11;
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testPairValueTwoPairPosition2() { //PPXPP	
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		int outputValue = testHand.getPairValue();
		int expectedValue = 11; //Should be the value of the higher pair
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testPairValueTwoPairPosition3() { //XPPPP
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.EIGHT, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		testHand.evaluateHand();

		int outputValue = testHand.getPairValue();
		int expectedValue = 11;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testPairValueOnePair() {
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SEVEN, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		Card second = new CardImpl(Rank.THREE, Suit.DIAMONDS);
		testHand.addCard(second);

		Card fifth = new CardImpl(Rank.KING, Suit.SPADES);
		testHand.addCard(fifth);

		testHand.evaluateHand();
		int outputValue = testHand.getPairValue();
		int expectedValue = 7;
		assertEquals(expectedValue, outputValue);

	}

	@Test
	public void testLowerPairValuePosition1() { //PPPPX		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.EIGHT, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card fifth = new CardImpl(Rank.JACK, Suit.DIAMONDS);
		testHand.addCard(fifth);

		testHand.evaluateHand();

		int outputValue = testHand.getLowerPairValue();
		int expectedValue = 2;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testLowerPairValuePosition2() { //PPXPP 		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.TWO, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card third = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
		testHand.addCard(third);

		testHand.evaluateHand();

		int outputValue = testHand.getLowerPairValue();
		int expectedValue = 2;
		assertEquals(expectedValue, outputValue);
	}

	@Test
	public void testLowerPairValuePosition3() { //XPPPP		
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.EIGHT, Suit.values()[i]);
			testHand.addCard(c);
		}

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			testHand.addCard(c);
		}

		Card first = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		testHand.addCard(first);

		testHand.evaluateHand();

		int outputValue = testHand.getLowerPairValue();
		int expectedValue = 8;
		assertEquals(expectedValue, outputValue);
	}


}