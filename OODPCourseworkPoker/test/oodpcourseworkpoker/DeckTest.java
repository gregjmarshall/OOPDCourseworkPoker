
package oodpcourseworkpoker;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * *@author Anna Taylor
 * @author Greg Marshall
 */
public class DeckTest {

	@Test
	public void testCreateCards() { //Checks that 52 cards are created
		Deck testDeck = new DeckImpl();
		int expectedDeckSize = 52;
		int outputDeckSize = testDeck.getContents().size();

		assertEquals("Problem with createCards()", expectedDeckSize, outputDeckSize);
	}

	@Test
	public void testGetContents() {
		Deck testDeck = new DeckImpl();
		List<Card> output = testDeck.getContents();

		assertNotNull(output);
	}

	@Test
	public void testCardsCreatedClubs()	{ //Tests that all club cards are contained in the deck
		Deck testDeck = new DeckImpl();
		String[] clubs = new String[13];
		for (int i = 0; i < 13; i++) { //Creates array of clubs, for comparison with clubs created by the deck
			Card c = new CardImpl(Rank.values()[i], Suit.CLUBS);
			clubs[i] = c.toString();
		}
		int k = 0;//Corresponds to clubs array index value
		for (int j = 0; j < 13; j++) {
			assertEquals("Problem with createCards()", clubs[k], testDeck.getContents().get(j).toString());
			k++;
		}
	}

	@Test
	public void testCardsCreatedSpades() { //Tests that all spade cards are contained in the deck
		Deck testDeck = new DeckImpl();
		String[] spades = new String[13];
		for (int i = 0; i < 13; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.SPADES);
			spades[i] = c.toString();
		}
		int k = 0;//Corresponds to spades array index value
		for (int j = 13; j < 26; j++) {
			assertEquals("Problem with createCards()", spades[k], testDeck.getContents().get(j).toString());
			k++;
		}
	}

	@Test
	public void testCardsCreatedHearts() { //Tests that all heart cards are contained in the deck
		Deck testDeck = new DeckImpl();
		String[] hearts = new String[13];
		for (int i = 0; i < 13; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.HEARTS);
			hearts[i] = c.toString();
		}
		int k = 0;//Corresponds to hearts array index value
		for (int j = 26; j < 39; j++) {
			assertEquals("Problem with createCards()", hearts[k], testDeck.getContents().get(j).toString());
			k++;
		}
	}

	@Test
	public void testCardsCreatedDiamonds() { //Tests that all diamond cards are contained in the deck
		Deck testDeck = new DeckImpl();
		String[] diamonds = new String[13];
		for (int i = 0; i < 13; i++) {
			Card c = new CardImpl(Rank.values()[i], Suit.DIAMONDS);
			diamonds[i] = c.toString();
		}
		int k = 0;//Corresponds to diamonds array index value
		for (int j = 39; j < 52; j++) {
			assertEquals("Problem with createCards()", diamonds[k], testDeck.getContents().get(j).toString());
			k++;
		}
	}

	@Test
	public void testDeckShuffle() {
		Deck testDeck = new DeckImpl();
		testDeck.shuffleCards();
		String[] firstShuffleResult = new String[52];
		for (int i = 0; i < 52; i++) {
			firstShuffleResult[i] = testDeck.getContents().get(i).toString();
		}
		String[] secondShuffleResult = new String[52];
		for (int i = 0; i < 52; i++) {
			secondShuffleResult[i] = testDeck.getContents().get(i).toString();
		}

		for (int i = 0; i < 52; i++) {
			assertEquals(firstShuffleResult[i], secondShuffleResult[i]);
		}		
	}

	@Test	
	public void testDeckShuffleIsDifferent() {
		Deck testDeck = new DeckImpl();
		testDeck.shuffleCards();//Shuffle the first time and represent state of deck in a String array
		String[] firstShuffleResult = new String[52];
		for (int i = 0; i < 52; i++) {
			firstShuffleResult[i] = testDeck.getContents().get(i).toString();
		}

		testDeck.shuffleCards();//Shuffle again and represent state of deck in a second String array
		String[] secondShuffleResult = new String[52];
		for (int i = 0; i < 52; i++) {
			secondShuffleResult[i] = testDeck.getContents().get(i).toString();
		}

		for (int z = 0; z < 100; z++) { //Make 100 comparisons of array pairs
			//System.out.println("Test round: " + z);
			for (int j = 0; j < 52; j++) { //Compare the two String array representations of the shuffled deck
			//	System.out.println("Position reached: " + j);
				assertFalse(firstShuffleResult[j].equals(secondShuffleResult[j]));
			}//This test will not fail every time; some cards may
			//hold equal positions, and so the test may fail sometimes and not others.  This doesn't mean
			//the deck isn't 'well shuffled', however. It fails as soon as it hits two identical cards in the same
			//array position.
			//Thus, this is really a test to show that each shuffle of the deck produces a 'sufficiently different'
			//ordering of cards, which is what's needed. 
			//Tested for 99 pairs of arrays; 99 times, no matching cards were found in the same position (best case). 

		}

	}

	@Test
	public void testPopCard() {
		Deck testDeck = new DeckImpl();
		Card c = testDeck.popCard();
		Card expectedCard = new CardImpl(Rank.ACE, Suit.CLUBS);//First card in a new deck is ace of clubs
		assertTrue(expectedCard.toString().equals(c.toString()));//Assert that both cards are of the same suit and rank
	}

	@Test
	public void testPopCardRemoval() {//Tests that the first card after removal is what's expected
		Deck testDeck = new DeckImpl();
		testDeck.popCard();
		Card topCard = testDeck.getContents().get(0);
		Card expectedCard = new CardImpl(Rank.TWO, Suit.CLUBS);
		assertTrue(expectedCard.toString().equals(topCard.toString()));
	}


}