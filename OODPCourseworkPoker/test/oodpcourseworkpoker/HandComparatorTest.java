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
public class HandComparatorTest {
	private HandComparator testComparator;
	private Player humanPlayer;
	private Player dealerPlayer;

	//Extra cards
	private Card twoD;
	private Card eightS;
	private Card kingC;

	@Before
	public void setUp() {
		testComparator = new HandComparatorImpl();
		humanPlayer = new HumanPlayer("Dummy");
		dealerPlayer = new DealerPlayer();

		twoD = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		eightS = new CardImpl(Rank.EIGHT, Suit.SPADES);
		kingC = new CardImpl(Rank.KING, Suit.CLUBS);
	}

	/**
	 * Not necessary to test all combinations of hands because we know from HandTest that different
	 * hands are compared correctly. What matters is that a better hand for each player selects the
	 * correct winner/
	 */
	@Test
	public void testCompareHandsHumanPlayerWins() { //Tests a better hand wins for the HumanPlayer
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.ACE, Suit.values()[i]); //Creates quads
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD);

		for (int i = 0; i < 3; i++) { //Creates trips
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]);
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(eightS);
		dealerPlayer.receiveCard(kingC);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();		
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand!"; //This is the result String if HumanPlayer wins
		assertEquals(expected, output);		
	}

	@Test
	public void testCompareHandsDealerWins() { //Tests a better hands wins for DealerPlayer
		for (int i = 0; i < 5; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.HEARTS); //Creates a flush
			dealerPlayer.receiveCard(c);
		}

		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]); //Creats another pair for Two Pair
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD); //Kicker

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The computer has won the hand! Better luck next time!"; //Result String if DealerPlayer wins
		assertEquals(expected, output);
	}

	/**
	 * The following tests compare cases where both players have 'equal' hands (i.e. both have quads, trips, or pairs) 
	 * and further comparison is needed.
	 */

	@Test
	public void testCompareHandsBothQuadsHumanPlayerWins() { //Tests that if both have quads, HumanPlayer wins with better hand
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.ACE, Suit.values()[i]); //Creates quads
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD);

		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]); //Creates quads
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingC); //Dealer has the better kicker but should still lose by having worse quads

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand with the higher quads!"; //HumanPlayer should win
		assertEquals(expected, output);
	}

	//Now to test for equal hands
	//1. Both quads
	//2. Both trips
	//3. Both have a pair - first better pair vs weaker, then equal pairs and high cards must be compared
	//3. Both have Two Pair
	//4. Flush, Straight and High Card evaluated in next method

}