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
public class DealerPlayerTest {

	@Test
	public void testGetName() {
		Player player = new DealerPlayer();
		String outputName = player.getName();
		assertNotNull(outputName);
	}

	@Test
	public void testDealerPickerRandom() { //Test that the random number generated is between 0 and 9 (inclusive)
		for (int i = 0; i < 1000; i++) { //Test plenty of times to be sure
			int dealerRef = (int) (Math.random() * 10); //This is the exact code used in the method (not externally accessible)
			assertTrue(dealerRef >= 0 && dealerRef < 10);
		}		
	}

	@Test
	public void testDealerPicker() { //Tests that dealerPicker assigns a name upon construction
		Player testPlayer = new DealerPlayer();
		String output = testPlayer.getName();
		assertNotNull(output);
	}

	/**
     * Test of receiveCard method, of class DealerPlayer.
     */
    @Test
    public void testReceiveCard() {
    	Player testPlayer = new DealerPlayer();
        Card c = new CardImpl(Rank.TWO, Suit.CLUBS);
        testPlayer.receiveCard(c);
        Card receivedCard = testPlayer.getHand().getContents()[0]; //Accesses the player's hand (the first card of it)
        assertEquals(receivedCard, c); //Checks that the card was assigned to the hand via receiveCard()     
    }

    /**
     * Test of getHand method, of class DealerPlayer.
     */
    @Test
    public void testGetHand() {
        Player testPlayer = new DealerPlayer();
        Hand output = testPlayer.getHand();
        assertTrue(output instanceof Hand);
    }

    
    //The value of cardsChanged will be useful here; check that it's 0 for quads/straight/flush
    @Test
    public void testChooseDiscardQuads() {  //Tests that no cards are discarded for four of a kind 
       Player testPlayer = new DealerPlayer();
       
       for (int i = 0; i < 4; i++) { //Creates four cards of same rank
    	   Card c = new CardImpl(Rank.ACE, Suit.values()[i]);
    	   testPlayer.receiveCard(c);
       }
       Card twoD = new CardImpl(Rank.TWO, Suit.DIAMONDS); //Extra card
       testPlayer.receiveCard(twoD);
             
       int output = testPlayer.chooseDiscard();
       int expected = 0;
       assertEquals(expected, output);
    }
    
    public void testChooseDiscardStraight() { //Tests that no cards are discarded ffrom a straight
        Player testPlayer = new DealerPlayer();
        Card jackC = new CardImpl(Rank.JACK, Suit.CLUBS);
        Card tenH = new CardImpl(Rank.TEN, Suit.HEARTS);
        Card nineD = new CardImpl(Rank.NINE, Suit.DIAMONDS);
        Card eightD = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
        Card sevenH = new CardImpl(Rank.SEVEN, Suit.HEARTS);
        testPlayer.receiveCard(tenH);
        testPlayer.receiveCard(jackC);
        testPlayer.receiveCard(nineD);
        testPlayer.receiveCard(eightD);
        testPlayer.receiveCard(sevenH);
        
        int output = testPlayer.chooseDiscard();
        int expected = 0;
        assertEquals("Testing number of cards discarded from straight: ",expected, output);
     }
    
    public void testChooseDiscardFlush() { //Tests that cards are correctly discarded for Straight
        Player testPlayer = new DealerPlayer();
        Card jackH = new CardImpl(Rank.JACK, Suit.HEARTS);
        Card tenH = new CardImpl(Rank.TEN, Suit.HEARTS);
        Card nineH = new CardImpl(Rank.NINE, Suit.HEARTS);
        Card eightH = new CardImpl(Rank.EIGHT, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(tenH);
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(nineH);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);
        
        int output = testPlayer.chooseDiscard();
        int expected = 0;
        assertEquals("Testing number of cards discarded from flush: ",expected, output);
     }
    	
     public void testChooseDiscardTrips() {  //tests that the correct number of cards are discarded from trips hand
        Player testPlayer = new DealerPlayer();
        Card jackH = new CardImpl(Rank.JACK, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card jackS = new CardImpl(Rank.JACK, Suit.SPADES);
        Card eightH = new CardImpl(Rank.EIGHT, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(jackS);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);
        int output = testPlayer.chooseDiscard();
        int expected = 2;
        assertEquals(output, expected);
     }
     
      public void testChooseDiscardTripsPartTwo() { // tests that the correct cards are discarded from trips hand
        Player testPlayer = new DealerPlayer();
        Card jackH = new CardImpl(Rank.JACK, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card jackS = new CardImpl(Rank.JACK, Suit.SPADES);
        Card eightH = new CardImpl(Rank.EIGHT, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(jackS);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);
        testPlayer.chooseDiscard();
        Card output1 = testPlayer.getHand().getContents()[0];
        Card output2 = testPlayer.getHand().getContents()[1];
        assertFalse("Checking five of hearts was discarded.", output1.equals(fiveH));
        assertFalse("Checking eight of hearts was discarded.", output2.equals(eightH));
     }
     
     public void testChooseDiscardTwoPair() {  //tests that the correct number of cards are discarded from a two pair hand
        Player testPlayer = new DealerPlayer();
        Card jackH = new CardImpl(Rank.JACK, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card eightS = new CardImpl(Rank.EIGHT, Suit.SPADES);
        Card eightH = new CardImpl(Rank.EIGHT, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);
        int output = testPlayer.chooseDiscard();
        int expected = 1;
        assertEquals("Checking only 1 card is discarded from two pair hand", output, expected);
     }
     
     public void testChooseDiscardTwoPairPartTwo() {  //tests that the correct cards are discarded from a two pair hand
        Player testPlayer = new DealerPlayer();
        Card jackH = new CardImpl(Rank.JACK, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card eightS = new CardImpl(Rank.EIGHT, Suit.SPADES);
        Card eightH = new CardImpl(Rank.EIGHT, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);
        testPlayer.chooseDiscard();
        Card output1 = testPlayer.getHand().getContents()[0];
        assertFalse("Checking five of hearts was discarded.", output1.equals(fiveH));
     }
      
     public void testChooseDiscardOnePair() {  //tests that the correct number of cards are discarded from a one pair hand
        Player testPlayer = new DealerPlayer();
        Card jackH = new CardImpl(Rank.JACK, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card eightS = new CardImpl(Rank.EIGHT, Suit.SPADES);
        Card threeH = new CardImpl(Rank.THREE, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);
        int output = testPlayer.chooseDiscard();
        int expected = 3;
        assertEquals("Checking 3 cards are discarded from one pair hand", output, expected);
    }
     
     public void testChooseDiscardOnePairPartTwo() {  //tests that the correct cards are discarded from a one pair hand
        Player testPlayer = new DealerPlayer();
        Card jackH = new CardImpl(Rank.JACK, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card eightS = new CardImpl(Rank.EIGHT, Suit.SPADES);
        Card threeH = new CardImpl(Rank.THREE, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);
        testPlayer.chooseDiscard();
        Card output1 = testPlayer.getHand().getContents()[0];
        Card output2 = testPlayer.getHand().getContents()[1];
        Card output3 = testPlayer.getHand().getContents()[2];
        assertFalse("Checking three of hearts was discarded.", output1.equals(threeH));
        assertFalse("Checking five of hearts was discarded.", output2.equals(fiveH));
        assertFalse("Checking eight of spades was discarded.", output3.equals(eightS));
     }
     
     public void testChooseDiscardUnmadeHand() {  //tests that the correct number of cards are discarded from a particular unmade hand
        Player testPlayer = new DealerPlayer();
        Card kingH = new CardImpl(Rank.KING, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card eightS = new CardImpl(Rank.EIGHT, Suit.SPADES);
        Card threeH = new CardImpl(Rank.THREE, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(kingH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);
        int output = testPlayer.chooseDiscard();
        int expected = 3;
        assertEquals("Checking 3 cards are discarded from one pair hand", output, expected);
     }
     
     public void testChooseDiscardUnmadeHandPartTwo() {  //tests that the correct cards are discarded from a particular unmade hand
        Player testPlayer = new DealerPlayer();
        Card kingH = new CardImpl(Rank.KING, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card eightS = new CardImpl(Rank.EIGHT, Suit.SPADES);
        Card threeH = new CardImpl(Rank.THREE, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(kingH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);
        testPlayer.chooseDiscard();
        Card output1 = testPlayer.getHand().getContents()[0];
        Card output2 = testPlayer.getHand().getContents()[1];
        Card output3 = testPlayer.getHand().getContents()[2];
        assertFalse("Checking three of hearts was discarded.", output1.equals(threeH));
        assertFalse("Checking five of hearts was discarded.", output2.equals(fiveH));
        assertFalse("Checking eight of spades was discarded.", output3.equals(eightS));
     }
     
     public void testChooseDiscardUnmadeHandTenPlus() {  //tests that the correct number of cards are discarded from a slightly better unmade hand
        Player testPlayer = new DealerPlayer(); // in this case the 3rd card is ten or higher and only 2 cards should be discarded
        Card kingH = new CardImpl(Rank.KING, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card tenS = new CardImpl(Rank.TEN, Suit.SPADES);
        Card threeH = new CardImpl(Rank.THREE, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(kingH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(tenS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);
        int output = testPlayer.chooseDiscard();
        int expected = 2;
        assertEquals("Checking 2 cards are discarded from one pair hand", output, expected);
     }
     
     public void testChooseDiscardUnmadeHandTenPlusPartTwo() {  //tests that the correct cards are discarded from a slightly better unmade hand
        Player testPlayer = new DealerPlayer();
        Card kingH = new CardImpl(Rank.KING, Suit.HEARTS);
        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        Card tenS = new CardImpl(Rank.TEN, Suit.SPADES);
        Card threeH = new CardImpl(Rank.THREE, Suit.HEARTS);
        Card fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        testPlayer.receiveCard(kingH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(tenS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);
        testPlayer.chooseDiscard();
        Card output1 = testPlayer.getHand().getContents()[0];
        Card output2 = testPlayer.getHand().getContents()[1];
        assertFalse("Checking three of hearts was discarded.", output1.equals(threeH));
        assertFalse("Checking five of hearts was discarded.", output2.equals(fiveH));
     }
     
}
        
        
//        Player instance = new DealerPlayer(); //no longer contain those cards
//        Card aceH = new CardImpl(Rank.ACE, Suit.HEARTS);
//        Card aceS = new CardImpl(Rank.ACE, Suit.SPADES);
//        Card jackS = new CardImpl(Rank.JACK, Suit.SPADES);
//        Card nineC = new CardImpl(Rank.NINE, Suit.CLUBS);
//        Card twoD = new CardImpl(Rank.TWO, Suit.DIAMONDS);
//        instance.receiveCard(aceS);
//        instance.receiveCard(aceH);
//        instance.receiveCard(jackS);
//        instance.receiveCard(nineC);
//        instance.receiveCard(twoD);
//        instance.getHand().evaluateHand();
//        int actualPairDiscard = instance.chooseDiscard();
//        int expectedPairDiscard = 3;
//        assertEquals(actualPairDiscard, expectedPairDiscard);
//        assertFalse(instance.getHand().getContents()[0].equals(twoD));
//        assertFalse(instance.getHand().getContents()[1].equals(nineC));
//        assertFalse(instance.getHand().getContents()[2].equals(jackS)); //this first section is for a one pair hand
//        
//        instance.getHand().clearHand();
//        Card jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
//        instance.receiveCard(jackD);
//        instance.receiveCard(aceH);
//        instance.receiveCard(aceS);
//        instance.receiveCard(jackS);
//        instance.receiveCard(twoD);
//        instance.getHand().evaluateHand();
//        int actualTwoPairDiscard = instance.chooseDiscard();
//        int expectedTwoPairDiscard = 1;
//        assertEquals(actualTwoPairDiscard, expectedTwoPairDiscard);
//        assertFalse(instance.getHand().getContents()[0].equals(twoD)); //this sections is for a two pair hand
//        
//        
//        instance.getHand().clearHand();
//        Card aceD = new CardImpl(Rank.ACE, Suit.DIAMONDS);
//        instance.receiveCard(aceD);
//        instance.receiveCard(aceH);
//        instance.receiveCard(aceS);
//        instance.receiveCard(jackS);
//        instance.receiveCard(twoD);
//        instance.getHand().evaluateHand();
//        int actualTripsDiscard = instance.chooseDiscard();
//        int expectedTripsDiscard = 2;
//        assertEquals(actualTripsDiscard, expectedTripsDiscard);
//        assertFalse(instance.getHand().getContents()[0].equals(twoD));
//        assertFalse(instance.getHand().getContents()[1].equals(jackS)); //this section is for a trips hand
//        
        
        
        
        
    