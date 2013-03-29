
package oodpcourseworkpoker;

/*
 * *@author Anna Taylor
 * @author Greg Marshall
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DealerPlayerTest { // This test class is to test the functions from the class representing the computer player.
    private Player testPlayer;

    private Card jackC;
    private Card tenH;
    private Card nineD;
    private Card eightD;
    private Card sevenH;
    private Card jackH;
    private Card nineH;
    private Card eightH;
    private Card fiveH;
    private Card jackD;
    private Card jackS;
    private Card eightS;
    private Card threeH;
    private Card kingH;
    private Card tenS;

    @Before
    public void setUp() {
        testPlayer = new DealerPlayer();

        jackC = new CardImpl(Rank.JACK, Suit.CLUBS);
        tenH = new CardImpl(Rank.TEN, Suit.HEARTS);
        nineD = new CardImpl(Rank.NINE, Suit.DIAMONDS);
        eightD = new CardImpl(Rank.EIGHT, Suit.DIAMONDS);
        sevenH = new CardImpl(Rank.SEVEN, Suit.HEARTS);
        jackH = new CardImpl(Rank.JACK, Suit.HEARTS);
        nineH = new CardImpl(Rank.NINE, Suit.HEARTS);
        eightH = new CardImpl(Rank.EIGHT, Suit.HEARTS);
        fiveH = new CardImpl(Rank.FIVE, Suit.HEARTS);
        jackD = new CardImpl(Rank.JACK, Suit.DIAMONDS);
        jackS = new CardImpl(Rank.JACK, Suit.SPADES);
        eightS = new CardImpl(Rank.EIGHT, Suit.SPADES);
        threeH = new CardImpl(Rank.THREE, Suit.HEARTS);
        kingH = new CardImpl(Rank.KING, Suit.HEARTS);
        tenS = new CardImpl(Rank.TEN, Suit.SPADES);
    }

    @Test
    public void testGetName() {
        String outputName = testPlayer.getName();
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
        String output = testPlayer.getName();
        assertNotNull(output);
    }

        /**
     * Test of receiveCard method, of class DealerPlayer.
     */
    @Test
    public void testReceiveCard() {
        testPlayer.receiveCard(sevenH);
        Card receivedCard = testPlayer.getHand().getContents()[0]; //Accesses the player's hand (the first card of it)
        assertEquals(receivedCard, sevenH); //Checks that the card was assigned to the hand via receiveCard()     
    }

    /**
     * Test of getHand method, of class DealerPlayer.
     */
    @Test
    public void testGetHand() {
        Hand output = testPlayer.getHand();
        assertTrue(output instanceof Hand);
    }



    @Test
    public void testChooseDiscardQuads() {  //Tests that no cards are discarded for four of a kind        
       for (int i = 0; i < 4; i++) { //Creates four cards of same rank
           Card c = new CardImpl(Rank.ACE, Suit.values()[i]);
           testPlayer.receiveCard(c);
       }
       testPlayer.receiveCard(nineH); //Extra card

       int output = testPlayer.chooseDiscard(); 
       int expected = 0; //chooseDiscard() should return 0, as no cards should be swapped
       assertEquals(expected, output);
    }


    @Test
    public void testChooseDiscardStraight() { //Tests that no cards are discarded from a straight
        testPlayer.receiveCard(tenH);
        testPlayer.receiveCard(jackC);
        testPlayer.receiveCard(nineD);
        testPlayer.receiveCard(eightD);
        testPlayer.receiveCard(sevenH);

        int output = testPlayer.chooseDiscard();
        int expected = 0;
        assertEquals("Testing number of cards discarded from straight: ",expected, output);
     }


    @Test
    public void testChooseDiscardFlush() { //Tests that cards are correctly discarded for Straight
        testPlayer.receiveCard(tenH);
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(nineH);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);

        int output = testPlayer.chooseDiscard();
        int expected = 0;
        assertEquals("Testing number of cards discarded from flush: ",expected, output);
     }


     @Test   
     public void testChooseDiscardTrips() {  //tests that the correct number of cards are discarded from trips hand
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(jackS);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);

        int output = testPlayer.chooseDiscard();
        int expected = 2;        
        assertEquals(output, expected);
     }


      @Test 
      public void testChooseDiscardTripsPartTwo() { // tests that the correct cards are discarded from trips hand
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(jackS);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);
        testPlayer.chooseDiscard();

        Card output1 = testPlayer.getHand().getContents()[0];
        Card output2 = testPlayer.getHand().getContents()[1];

        assertEquals("Checking five of hearts was discarded.", output1, null);
        assertEquals("Checking eight of hearts was discarded.", output2, null);
     }


     @Test 
     public void testChooseDiscardTwoPair() {  //tests that the correct number of cards are discarded from a two pair hand
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);

        int output = testPlayer.chooseDiscard();
        int expected = 1;
        assertEquals("Checking only 1 card is discarded from two pair hand", output, expected);
     }


     @Test
     public void testChooseDiscardTwoPairPartTwo() {  //tests that the correct cards are discarded from a two pair hand
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(eightH);
        testPlayer.receiveCard(fiveH);

        testPlayer.chooseDiscard();
        Card output1 = testPlayer.getHand().getContents()[0];
        assertEquals("Checking five of hearts was discarded.", output1, null);
     }


     @Test 
     public void testChooseDiscardOnePair() {  //tests that the correct number of cards are discarded from a one pair hand
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);

        int output = testPlayer.chooseDiscard();
        int expected = 3;
        assertEquals("Checking 3 cards are discarded from one pair hand", output, expected);
    }


     @Test
     public void testChooseDiscardOnePairPartTwo() {  //tests that the correct cards are discarded from a one pair hand
        testPlayer.receiveCard(jackH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);

        testPlayer.chooseDiscard();
        Card output1 = testPlayer.getHand().getContents()[0];
        Card output2 = testPlayer.getHand().getContents()[1];
        Card output3 = testPlayer.getHand().getContents()[2];
        assertEquals("Checking five of hearts was discarded.", output1, null);
        assertEquals("Checking three of hearts was discarded.", output2, null);
        assertEquals("Checking eight of spades was discarded.", output3, null);
     }


     @Test
     public void testChooseDiscardUnmadeHand() { //tests that the correct number of cards are discarded from particular 
         //unmade hand
        testPlayer.receiveCard(kingH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);

        int output = testPlayer.chooseDiscard();
        int expected = 3;
        assertEquals("Checking 3 cards are discarded from one pair hand", output, expected);
     }


     @Test
     public void testChooseDiscardUnmadeHandPartTwo() {  //tests that the correct cards are discarded from a particular 
         // unmade hand
        testPlayer.receiveCard(kingH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(eightS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);

        testPlayer.chooseDiscard();
        Card output1 = testPlayer.getHand().getContents()[0];
        Card output2 = testPlayer.getHand().getContents()[1];
        Card output3 = testPlayer.getHand().getContents()[2];
        assertEquals("Checking five of hearts was discarded.", output1, null);
        assertEquals("Checking three of hearts was discarded.", output2, null);
        assertEquals("Checking eight of spades was discarded.", output3, null);
     }


     @Test
     public void testChooseDiscardUnmadeHandTenPlus() {  //tests that the correct number of cards are discarded from a 
     //slightly better unmade hand, in this case the 3rd card is ten or higher and only 2 cards should be discarded        
        testPlayer.receiveCard(kingH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(tenS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);

        int output = testPlayer.chooseDiscard();
        int expected = 2;
        assertEquals("Checking 2 cards are discarded from one pair hand", output, expected);
     }


     @Test
     public void testChooseDiscardUnmadeHandTenPlusPartTwo() { //tests that the correct cards are discarded from a 
    //slightly better unmade hand
        testPlayer.receiveCard(kingH);
        testPlayer.receiveCard(jackD);
        testPlayer.receiveCard(tenS);
        testPlayer.receiveCard(threeH);
        testPlayer.receiveCard(fiveH);

        testPlayer.chooseDiscard();
        Card output1 = testPlayer.getHand().getContents()[0];
        Card output2 = testPlayer.getHand().getContents()[1];
        assertEquals("Checking five of hearts was discarded.", output1, null);
        assertEquals("Checking three of hearts was discarded.", output2, null);
     }
}
        
        
        
        
        

    