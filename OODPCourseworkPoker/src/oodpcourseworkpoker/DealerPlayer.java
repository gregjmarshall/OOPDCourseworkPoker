
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author marshall_gj
 */
public class DealerPlayer implements Player {
   
   private String dealerName;
   private Hand hand = new FiveCardHand();
   
   
   public DealerPlayer() {
	   dealerName = this.dealerPicker();
   }
   
   @Override
   public void receiveCard(Card c) {
   		hand.addCard(c);
   }
   
   
   @Override
   public Hand getHand() {
       return hand;
   }
   
   
   @Override
   public int chooseDiscard() {
        int cardsChanged = 0;
        hand.evaluateHand();
        System.out.println("checking for made hand....");
        
        if (hand.getHandValue() == "Four of a Kind" || hand.getHandValue() == "Straight" || hand.getHandValue() == "Flush") {
            System.out.println(dealerName + " doesn't seem to be discarding any cards!");
            return cardsChanged; //Prevents the above hands from being checked further; no need to change anything
        }
        
        else if(hand.getHandValue().equals("Three of a Kind")) {  // hand class returns handValue and for three of a kind 
            System.out.println("checking for three of a kind....");
                if(hand.getProcessingValue() == 1) {      //will return a 'processing value depending on the location of the trips in the hand
                    hand.discardCard(4);                   // 1: first three, 2: second 3, 3: third three
                    hand.discardCard(5);
                    cardsChanged = 2;
                    return cardsChanged;
                }
                else if(hand.getProcessingValue() == 2) {
                    hand.discardCard(1);
                    hand.discardCard(5);
                    cardsChanged = 2;
                    return cardsChanged;
                }
                else {
                    hand.discardCard(1);
                    hand.discardCard(2);
                    cardsChanged = 2;
                    return cardsChanged;
                }
        }
        
        else if(hand.getHandValue().equals("Two Pair")) {
             System.out.println("checking for two pair....");
            if(hand.getProcessingValue() == 1) {          // processing values for 2 pair where P = paired card are 
                hand.discardCard(5);                       // 1: PPPPX, 2: PPXPP, 3: XPPPP.
                cardsChanged = 1;
                return cardsChanged;
            }
            else if(hand.getProcessingValue() == 2) {
                hand.discardCard(3);
                cardsChanged = 1;
                return cardsChanged;
            }
            else {
                hand.discardCard(1);
                cardsChanged = 1;
                return cardsChanged;
            }
        }
        else if(hand.getHandValue().equals("One Pair")) {  //processing values for 1 pair hands where P = paired card are
            System.out.println("checking for one pair...."); // 1: PPXXX, 2: XPPXX, 3: XXPPX, 4: XXXPP
            if(hand.getProcessingValue() ==  1) {      
                hand.discardCard(3);
                hand.discardCard(4);
                hand.discardCard(5);
                cardsChanged = 3;
                return cardsChanged;
            }
            else if(hand.getProcessingValue() == 2) {
                hand.discardCard(1);
                hand.discardCard(4);
                hand.discardCard(5);
                cardsChanged = 3;
                return cardsChanged;
            }
            else if(hand.getProcessingValue() == 3) {
                hand.discardCard(1);
                hand.discardCard(2);
                hand.discardCard(5);
                cardsChanged = 3;
                return cardsChanged;
            }
            else {
                hand.discardCard(1);
                hand.discardCard(2);
                hand.discardCard(3);
                cardsChanged = 3;
                return cardsChanged;
            }
        }
        else if (hand.getProcessingValue() == 0) { // this is for non-made hands (eg high card)
            System.out.println("checking unmade hand for suits....");
            int spades = 0;                        // the first part of the section is to establish 
            int hearts = 0;                        // if the dealer should draw to a flush
            int clubs = 0;
            int diamonds = 0;
            for(int i = 4; i > -1; i--) {
                if(hand.getContents()[i].getSuit() == Suit.CLUBS ) {
                    clubs++;
                }
                else if(hand.getContents()[i].getSuit() == Suit.SPADES ) {
                    spades++;
                }
                 else if(hand.getContents()[i].getSuit() == Suit.DIAMONDS ) {
                    diamonds++;
                }
                 else if(hand.getContents()[i].getSuit() == Suit.HEARTS ) {
                    hearts++;
                }                    
            }
            
            if (spades == 4) {
                for (int i = 0; i < 4; i++) {
                	if (hand.getContents()[i].getSuit() != Suit.SPADES) { //each of these sections locates the off-suit card
                		hand.discardCard(i+1);                               // when 4 of the cards are the same suit so
                        cardsChanged = 1;                                  // it can be discarded to draw to 9 flush outs.
                        return cardsChanged;
                    }
                }                   
            }
            
            if (diamonds == 4) {
                for(int i = 0; i < 4; i++) {
                    if(hand.getContents()[i].getSuit() != Suit.DIAMONDS) {
                        hand.discardCard(i+1);
                        cardsChanged = 1;
                        return cardsChanged;
                    }
                }
            }
            
            if (hearts == 4) {
                for(int i = 0; i < 4; i++) {
                    if(hand.getContents()[i+1].getSuit() != Suit.HEARTS) {
                        hand.discardCard(i);
                        cardsChanged = 1;
                        return cardsChanged;
                    }
                }
            }
            
            if (clubs == 4) {
                for(int i = 0; i < 4; i++) {
                    if(hand.getContents()[i+1].getSuit() != Suit.CLUBS) {
                        hand.discardCard(i);
                        cardsChanged = 1;
                        return cardsChanged;
                    }
                }
            }
            
           if (hand.getProcessingValue() == 0) { //this section tests to see if the first four or
                System.out.println("checking unmade hand for straight draw....");
                boolean lowStrDraw = true;             // last four cards are in sequence to decide whether to draw to a straight
                for (int i = 0; i < 4; i++) {
                	if (hand.getContents()[i].getRankValue() != (hand.getContents()[i+1].getRankValue() - 1)) {
                		lowStrDraw = false;
                	}
                }                
                if (lowStrDraw) {
                    hand.discardCard(5);
                    cardsChanged = 1;
                    return cardsChanged;
                }
                
                boolean highStrDraw = true;
                for (int i = 0; i < 4; i++) {
		            if (hand.getContents()[i].getRankValue() != (hand.getContents()[i+1].getRankValue() - 1)) {
		            	highStrDraw = false;
		            }
                }
                if (highStrDraw) {
                    hand.discardCard(1);
                    cardsChanged = 1;
                    return cardsChanged;
                }
           	}     
        }
        
        System.out.println("checking unmade hand to bin low cards....");
        if(hand.getContents()[2].getRankValue() < 10) { 
	        hand.discardCard(1);
	        hand.discardCard(2);
	        hand.discardCard(3);
	        cardsChanged = 3;
        }
        else{
            hand.discardCard(1);   //or the 2 lowest if all 3 are not lower than ten
            hand.discardCard(2);
            cardsChanged = 2;
        }
        return cardsChanged;
       
   }
                

    @Override
    public String getName() {
            return dealerName;
    }


    public String dealerPicker() { //Selects a random dealer
            int dealerRef = (int) (Math.random() * 10); // To generate a random number between 1 and 9

            String[] dealerNames = {"Malcolm", "Marlene", "Sven", "Rufus", "Michael", "Victoria", "Tseng", "Frank", "Paul", "Adam" };

            return dealerNames[dealerRef];		

    }
}