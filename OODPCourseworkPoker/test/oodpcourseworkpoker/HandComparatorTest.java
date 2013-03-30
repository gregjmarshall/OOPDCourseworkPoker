
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
public class HandComparatorTest {
	private HandComparator testComparator;
	private Player humanPlayer;
	private Player dealerPlayer;

	//Extra cards
	private Card twoD;
	private Card twoC;
	private Card threeH;
	private Card fourC;
	private Card fourH;
	private Card fiveD;
	private Card fiveH;
	private Card sevenS;
	private Card eightS;
	private Card kingC;
	private Card kingH;
	private Card queenD;
	private Card queenS;


	@Before
	public void setUp() {
		testComparator = new HandComparatorImpl();
		humanPlayer = new HumanPlayer("Dummy");
		dealerPlayer = new DealerPlayer();

		twoD = new CardImpl(Rank.TWO, Suit.DIAMONDS);
		twoC = new CardImpl(Rank.TWO, Suit.CLUBS);
		threeH = new CardImpl(Rank.THREE, Suit.HEARTS);
		fourC = new CardImpl(Rank.FOUR, Suit.CLUBS);
		fourH = new CardImpl(Rank.FOUR, Suit.HEARTS);
		fiveD = new CardImpl(Rank.FIVE, Suit.DIAMONDS);
		sevenS = new CardImpl(Rank.SEVEN, Suit.SPADES);
		eightS = new CardImpl(Rank.EIGHT, Suit.SPADES);
		kingC = new CardImpl(Rank.KING, Suit.CLUBS);
		kingH = new CardImpl(Rank.KING, Suit.HEARTS);
		queenD = new CardImpl(Rank.QUEEN, Suit.DIAMONDS);
		queenS = new CardImpl(Rank.QUEEN, Suit.SPADES);		
		fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
	}

	/**
	 * Not necessary to test all combinations of hands because we know from HandTest that different
	 * hands are compared correctly. What matters is that a better hand for each player selects the
	 * correct winner.
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

	@Test
	public void testHighCardComparison1() { //Tests that player with highest card wins in absence of better hand
		humanPlayer.receiveCard(kingH); //Human player should win with this card
		humanPlayer.receiveCard(fourC);
		humanPlayer.receiveCard(twoD);
		humanPlayer.receiveCard(eightS);
		humanPlayer.receiveCard(fiveH);

		dealerPlayer.receiveCard(queenS);
		dealerPlayer.receiveCard(twoC);
		dealerPlayer.receiveCard(threeH);
		dealerPlayer.receiveCard(fiveD);
		dealerPlayer.receiveCard(sevenS);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.highCardComparison(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand with the best high card.";
		assertEquals(output, expected);
	}

	@Test
	public void testHighCardComparison2() { //Tests that player with highest card wins in absence of better hand
		humanPlayer.receiveCard(kingH); 
		humanPlayer.receiveCard(fourC);
		humanPlayer.receiveCard(twoD);
		humanPlayer.receiveCard(eightS);
		humanPlayer.receiveCard(fiveH);

		dealerPlayer.receiveCard(kingC); // Both highest cards are same rank
		dealerPlayer.receiveCard(queenS); //Dealer player should win with this card
		dealerPlayer.receiveCard(threeH);
		dealerPlayer.receiveCard(fiveD);
		dealerPlayer.receiveCard(sevenS);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.highCardComparison(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The computer has won the hand with the best high card! Better luck next time!";
		assertEquals(output, expected);
	}

	@Test
	public void testHighCardComparison3() { //Tests that player with highest card wins in absence of better hand
		humanPlayer.receiveCard(kingH); 
		humanPlayer.receiveCard(queenD);
		humanPlayer.receiveCard(fiveD);
		humanPlayer.receiveCard(fourH);
		humanPlayer.receiveCard(threeH); //HumanPlayer should win based on this card (higher than twoC)

		dealerPlayer.receiveCard(kingC); // All higher cards are same rank
		dealerPlayer.receiveCard(queenS); 
		dealerPlayer.receiveCard(fiveH);
		dealerPlayer.receiveCard(fourC);
		dealerPlayer.receiveCard(twoC);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer); //Testing this time that compareHands() calls highCardComparison()

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand with the best high card.";
		assertEquals(output, expected);
	}

	@Test
	public void testHighCardComparisonDraw() { //Tests that player with highest card wins in absence of better hand
		humanPlayer.receiveCard(kingH); 
		humanPlayer.receiveCard(queenD);
		humanPlayer.receiveCard(fiveD);
		humanPlayer.receiveCard(fourH);
		humanPlayer.receiveCard(twoD); //HumanPlayer should win based on this card (higher than twoC)

		dealerPlayer.receiveCard(kingC); // All higher cards are same rank
		dealerPlayer.receiveCard(queenS); 
		dealerPlayer.receiveCard(fiveH);
		dealerPlayer.receiveCard(fourC);
		dealerPlayer.receiveCard(twoC);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The hand is a draw! Please play again.";
		assertEquals(output, expected);
	}

	@Test
	public void testHighCardAccessViaCompareHands() { 

	}

	/**
	 * The following tests compare cases where both players have 'equal' hands (i.e. both have quads, trips, or pairs) 
	 * and further comparison is needed.
	 */

	@Test
	public void testBothQuadsHumanPlayerWins() { //Tests that if both have quads, HumanPlayer wins with better hand
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


	@Test
	public void testBothQuadsDealerPlayerWins() { //Tests that if both have quads, DealerPlayer wins with better hand
		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.EIGHT, Suit.values()[i]); //Creates quads
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD);

		for (int i = 0; i < 4; i++) {
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]); //Creates quads
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingC); //HumanPlayer has the better kicker but should still lose by having worse quads

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The computer has won the hand with the higher quads! Better luck next time!";
		assertEquals(expected, output);
	}


	@Test
	public void testBothTripsHumanPlayerWins() { //Tests that if both have trips, HumanPlayer wins with better hand
		for (int i = 0; i < 3; i++) {
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]); //Creates trips
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD);
		humanPlayer.receiveCard(eightS);

		for (int i = 0; i < 3; i++) {
			Card c = new CardImpl(Rank.THREE, Suit.values()[i]); //Creates trips
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingC); //Dealer has the better kicker but should still lose by having worse trips
		dealerPlayer.receiveCard(queenD);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand with the higher trips!"; //HumanPlayer should win
		assertEquals(expected, output);
	}

	@Test
	public void testBothTripsDealerPlayerWins() { //Tests that if both have trips, DealerPlayer can win with better hand
		for (int i = 0; i < 3; i++) {
			Card c = new CardImpl(Rank.NINE, Suit.values()[i]); //Creates trips
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(kingC);
		humanPlayer.receiveCard(eightS);

		for (int i = 0; i < 3; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]); //Creates trips
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(twoD); //HumanPlayer has the better kicker but should still lose by having worse trips
		dealerPlayer.receiveCard(queenD);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The computer has won the hand with the higher trips! Better luck next time!";//DealerPlayer should win
		assertEquals(expected, output);
	}

	@Test
	public void testBothOnePairHumanPlayerWins() { //Tests that if both have a pair, HumanPlayer can win with better hand
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD); 
		humanPlayer.receiveCard(eightS);
		humanPlayer.receiveCard(threeH);

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.THREE, Suit.values()[i]); //Creates a pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingC); //Dealer has the better kickers but should still lose by having worse pair
		dealerPlayer.receiveCard(queenD);
		dealerPlayer.receiveCard(fiveH);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand with the higher pair!"; //HumanPlayer should win
		assertEquals(expected, output);
	}


	@Test
	public void testBothOnePairDealerPlayerWins() { //Tests that if both have a pair, DealerPlayer can win with better hand
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.SIX, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(kingC); //HumanPlayer has the better kickers but should still lose by having worse pair
		humanPlayer.receiveCard(eightS);
		humanPlayer.receiveCard(threeH);

		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]); //Creates a pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(twoD); 
		dealerPlayer.receiveCard(queenD);
		dealerPlayer.receiveCard(fiveH);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The computer has won the hand with the higher pair! Better luck next time!";//DealerPlayer should win
		assertEquals(expected, output);
	}


	@Test
	public void testEqualPairHumanPlayerWins() { //If both have an equal pair, HumanPlayer should win with better high card
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(kingC); //This card should enable HumanPlayer to win
		humanPlayer.receiveCard(eightS);
		humanPlayer.receiveCard(threeH);

		for (int i = 2; i < 4; i++) {
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]); //Creates a pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(twoD); 
		dealerPlayer.receiveCard(queenD);
		dealerPlayer.receiveCard(fiveH);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand with the best high card."; //HumanPlayer should win
		assertEquals(expected, output);
	}

	@Test
	public void testEqualPairDealerToWin() { //If both have an equal pair, DealerPlayer should win with better high card
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(kingC); 
		humanPlayer.receiveCard(eightS);
		humanPlayer.receiveCard(threeH);

		for (int i = 2; i < 4; i++) {
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]); //Creates a pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingH);
		dealerPlayer.receiveCard(queenD); //This card should enable DealerPlayer to win, as it beats eightS
		dealerPlayer.receiveCard(fiveH);

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The computer has won the hand with the best high card! Better luck next time!";//DealerPlayer should win
		assertEquals(expected, output);
	}

	@Test
	public void testBothTwoPairHumanPlayerWins() { //Tests that HumanPlayer is winner with better Two Pair
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]); //Creats another pair for Two Pair
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD); //Kicker

		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.SIX, Suit.values()[i]);
			dealerPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.FOUR, Suit.values()[i]); //Creates another pair for Two Pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingC); //Kicker

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand with the higher of the two pair!";
		assertEquals(expected, output);		
	}


	@Test
	public void testBothTwoPairDealerPlayerWins() { //Tests that DealerPlayer is winner with better Two Pair
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.NINE, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]); //Creats another pair for Two Pair
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD); //Kicker

		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.THREE, Suit.values()[i]);
			dealerPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.QUEEN, Suit.values()[i]); //Creates another pair for Two Pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingC); //Kicker

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The computer has won the hand with the higher of the two pair! Better luck next time!";
		assertEquals(expected, output);		
	}

	/**
	 * Tests that the HumanPlayer can win if both players have their higher pair of the same rank,
	 * but the HumanPlayer has a better lower ranking pair than the DealerPlayer.
	 */
	@Test
	public void testBothTwoPairHumanPlayerWinsLowerPair() {
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.TEN, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]); //Creats another pair for Two Pair
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD); //Kicker

		for (int i = 2; i < 4; i++) { //Creates a pair
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			dealerPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.FOUR, Suit.values()[i]); //Creates another pair for Two Pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingC); //Kicker

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand with the higher second pair!";
		assertEquals(expected, output);		
	}

	/**
	 * Tests that the DealerPlayer can win if both players have their higher pair of the same rank,
	 * but the DealerPlayer has a better lower ranking pair than the HumanPlayer.
	 */
	@Test
	public void testBothTwoPairDealerPlayerWinsLowerPair() {
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]); //Creats another pair for Two Pair
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD); //Kicker

		for (int i = 2; i < 4; i++) { //Creates a pair
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			dealerPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.SEVEN, Suit.values()[i]); //Creates another pair for Two Pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingC); //Kicker

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The computer has won the hand with the higher second pair! Better luck next time!";
		assertEquals(expected, output);		
	}

	/**
	 * Tests that the DealerPlayer can win if both players have an equal Two Pair using
	 * a better high card.
	 */
	@Test
	public void testBothTwoPairDealerPlayerWinsHighCard() {
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]); //Creats another pair for Two Pair
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(twoD); //Kicker

		for (int i = 2; i < 4; i++) { //Creates a pair
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			dealerPlayer.receiveCard(c);
		}
		for (int i = 2; i < 4; i++) {
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]); //Creates another pair for Two Pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(kingC); //Kicker

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "The computer has won the hand with the best high card! Better luck next time!";
		assertEquals(expected, output);		
	}

	/**
	 * Tests that the HumanPlayer can win if both players have an equal Two Pair using
	 * a better high card.
	 */
	@Test
	public void testBothTwoPairHumanPlayerWinsHighCard() {
		for (int i = 0; i < 2; i++) { //Creates a pair
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]);
			humanPlayer.receiveCard(c);
		}
		for (int i = 0; i < 2; i++) {
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]); //Creats another pair for Two Pair
			humanPlayer.receiveCard(c);
		}
		humanPlayer.receiveCard(queenS); //Kicker

		for (int i = 2; i < 4; i++) { //Creates a pair
			Card c = new CardImpl(Rank.JACK, Suit.values()[i]);
			dealerPlayer.receiveCard(c);
		}
		for (int i = 2; i < 4; i++) {
			Card c = new CardImpl(Rank.FIVE, Suit.values()[i]); //Creates another pair for Two Pair
			dealerPlayer.receiveCard(c);
		}
		dealerPlayer.receiveCard(fourH); //Kicker

		humanPlayer.getHand().evaluateHand();
		dealerPlayer.getHand().evaluateHand();
		testComparator.compareHands(humanPlayer, dealerPlayer);

		String output = testComparator.getResult();
		String expected = "Congratulations! You have won the hand with the best high card.";
		assertEquals(expected, output);		
	}

	/**
	 * There is no need to test both players having a Flush or Straight, as these are compared exactly the same
	 * way as high card hands (which have already been tested).
	 */

}