
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author marshall_gj
 */
import java.util.Arrays;
public class FiveCardHand implements Hand {
    private String handValue; 

    private int handValueScore; //The numerical score associated with a hand
    int processingValue = 0; //For use evaluating which cards to keep when discarding from dealer's hand

    int pairValue; // The rank value of Four of a Kind, Three of a Kind and Pairs are stored here for easy comparison
                               // in the event that the two players have a hand of the same type
    int lowerPairValue; //For Two Pair hands only; if two players have the same higher pair, the program then compares on the
                                            // lower pair.

    private static final int SIZE = 5;
    private Card[] handContents = new CardImpl[SIZE];


    @Override
    public Card[] getContents() {
            return handContents;
    }


    @Override
    public void addCard(Card c) {
            try {
                    for(int i = 0; i < 5; i++) {
            if(handContents[i] == null){
                handContents[i] = c;
                break;
            }
                    }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("(!) Attempting to add more cards to a hand than is allowed.");
            }		
    }

    @Override
    public void discardCard(int index) {
            handContents[index - 1] = null;
            return;
    }

    @Override
    public void sort() {
            Arrays.sort(handContents);		
    }

    @Override
    public void evaluateHand() { //This method is to evaluate the the hand so as it is translated into a readable value
            //First sort the hand; this makes it easier to evaluate
            this.sort();
            //Check for four of a kind
            boolean fourOfAKind = true;
            for (int i = 0; i < 3; i++) { //Test the first four cards
                    if (handContents[i].getRankValue() != handContents[i+1].getRankValue()) {
                            fourOfAKind = false;
                            break;
                    }
            }
            if (fourOfAKind) {
                    handValue = "Four of a Kind";
                    processingValue = 5;
                    handValueScore = 7;
                    pairValue = handContents[0].getRankValue(); //Takes value of first card that forms Four of a Kind for comparison
                    return; //Don't test any further once a handValue is found
            }

            fourOfAKind = true; //Reset the value here for the benefit of the next test
            for (int i = 1; i < 4; i++) { //Test the last four cards
                    if (handContents[i].getRankValue() != handContents[i+1].getRankValue()) {
                            fourOfAKind = false;
                            break;
                    }
            }

            if (fourOfAKind) {
                    handValue = "Four of a Kind";
                    processingValue = 5;
                    handValueScore = 7;
                    pairValue = handContents[4].getRankValue();//Takes value of the last card that forms Four of a Kind for comparison
                    return; //Don't test for any other handValues if Four of a Kind is found
            }

            //Test for flush (only reachable if fourOfAKind is false)
            boolean flush = true;
            for (int i = 0; i < 4; i++) {
                    if (handContents[i].getSuit() != handContents[i+1].getSuit()) {
                            flush = false;
                            break; //Stop testing for flush as soon as off-suit card is located
                    }
            }
            if (flush) {
                    handValue = "Flush";
                    processingValue = 5;
                    handValueScore = 6;
                    return; //Don't test for any other handValues if a Flush is found
            }

            //Test for Straight (only reachable if fourOfAKind and flush are false)
            boolean straight = true;
            //Test first for the case where Ace can be low 
            if ((handContents[4].getRankValue() == 14) && (handContents[3].getRankValue() == 5)) { 
                    //If the highest card in the hand is Ace and a 5 is preceding, test for straight where Ace would be low
                    for (int i = 2; i > -1; i--) {
                            if (handContents[i].getRankValue() != (handContents[i+1].getRankValue() - 1)) {
                                    straight = false;
                            }
                    }
                    if (straight) {
                            handValue = "Straight";
                            processingValue = 5;
                            handValueScore = 5;
                            return;
                    }			

            }

            //All other possible straight hands tested next (unreachable if another hand has already been found)
            for (int i = 0; i < 4; i++) {
                    if (handContents[i].getRankValue() != (handContents[i+1].getRankValue() - 1)) {
                            straight = false;
                    }
            }
            if (straight) {
                    handValue = "Straight";
                    processingValue = 5;
                    handValueScore = 5;
                    return;
            }

            //Test for three of a kind
            boolean threeOfAKind = true;
            for (int i = 0; i < 2; i++) { //Test first three cards (TTTXX)
                    if (handContents[i].getRankValue() != handContents[i+1].getRankValue()) {
                            threeOfAKind = false;
                            break;
                    }
            }
            if (threeOfAKind) {
                    handValue = "Three of a Kind";
                    processingValue = 1;
                    handValueScore = 4;
                    pairValue = handContents[0].getRankValue();
                    return;
            }

            threeOfAKind = true; //Must be reset here for the next test
            for (int i = 1; i < 3; i++) { //Test middle three cards (XTTTX)
                    if (handContents[i].getRankValue() != handContents[i+1].getRankValue()) {
                            threeOfAKind = false;
                            break;
                    }
            }
            if (threeOfAKind) {
                    handValue = "Three of a Kind";
                    processingValue = 2;
                    handValueScore = 4;
                    pairValue = handContents[1].getRankValue();
                    return;
            }

            threeOfAKind = true; //Must be reset here for the last test
            for (int i = 2; i < 4; i++) { //Test last three cards (XXTTT)
                    if (handContents[i].getRankValue() != handContents[i+1].getRankValue()) {
                            threeOfAKind = false;
                            break;
                    }
            }
            if (threeOfAKind) {
                    handValue = "Three of a Kind";
                    processingValue = 3;
                    handValueScore = 4;
                    pairValue = handContents[2].getRankValue();
                    return;
            }

            //Test for Pairs
            boolean onePair = false;
            //The following tests for a pair in the first position (PPXXX), potentially followed by another pair
            if (handContents[0].getRankValue() == handContents[1].getRankValue()) {
                    onePair = true;
                    processingValue = 1;
                    handValueScore = 2;
                    pairValue = handContents[0].getRankValue();
                    handValue = "One Pair";

                    if (handContents[2].getRankValue() == handContents[3].getRankValue()) { // This would mean two consecutive pairs at the front end of hand
                            handValue = "Two Pair";												// PPPPX
                            processingValue = 1;
                            handValueScore = 3;
                            if (handContents[2].getRankValue() > pairValue) {
                                    lowerPairValue = pairValue; //Saves the lower pair in case it's required during comparison
                                    pairValue = handContents[2].getRankValue(); //For Two Pair, take the higher pair value
                            }
                            else {
                                    lowerPairValue = handContents[2].getRankValue(); 
                            }

                            return; //Don't test any further
                    }

                    else if (handContents[3].getRankValue() == handContents[4].getRankValue()) { //This means PPXPP formation
                            handValue = "Two Pair";
                            processingValue = 2;
                            handValueScore = 3;
                            if (handContents[3].getRankValue() > pairValue) {
                                    lowerPairValue = pairValue; //Saves the lower pair in case it's required during comparison
                                    pairValue = handContents[3].getRankValue(); //For Two Pair, take the higher pair value
                            }
                            else {
                                    lowerPairValue = handContents[3].getRankValue();
                            }

                            return; //Don't test any further
                    }
                    else if (onePair) {
                            return; // Hand is PPXXX
                    }				
            }

            //The following tests for a pair in the second position (XPPXX), potentially followed by another pair (XPPPP)
            if (handContents[1].getRankValue() == handContents[2].getRankValue()) {
                    onePair = true;
                    handValue = "One Pair";
                    processingValue = 2;
                    handValueScore = 2;
                    pairValue = handContents[1].getRankValue();

                    if (handContents[3].getRankValue() == handContents[4].getRankValue()) { //Checks for XPPPP
                            handValue = "Two Pair";
                            processingValue = 3;
                            handValueScore = 3;
                            if (handContents[3].getRankValue() > pairValue) {
                                    pairValue = handContents[3].getRankValue(); //For Two Pair, take the higher pair value
                            }

                            return; // Don't test any further
                    }
                    else if (onePair) {
                            return; //Hand is XPPXX
                    }
            }

            //The following tests for a pair in the third position (XXPPX)
            if (handContents[2].getRankValue() == handContents[3].getRankValue()) {
                    onePair = true;
                    handValue = "One Pair";
                    processingValue = 3;
                    handValueScore = 2;
                    pairValue = handContents[2].getRankValue();
                    return; //Don't test any further
            }

            //The following tests for a pair in the last position (XXXPP)
            if (handContents[3].getRankValue() == handContents[4].getRankValue()) {
                    onePair = true;
                    handValue = "One Pair";
                    processingValue = 4;
                    handValueScore = 2;
                    pairValue = handContents[3].getRankValue();
                    return; //Don't test any further
            }		

            //If no better hand is found, the player has:
            handValue = handContents[4].toString() + " High";
            handValueScore = 1;

    }


    @Override
    public String getHandValue() {
            return handValue;
    }

    @Override
    public int getProcessingValue() {
            return processingValue;
    }


    @Override
    public String displayHand() { //Add card positions to this
            this.sort(); //Hand is easier to deal with if sorted before display
            String handDisplay = "";
            for (int i = 0; i < SIZE; i++) {
                    handDisplay += (i+1) + ": " + handContents[i].toString() + "\n";
            }
            return handDisplay;
    }


    @Override
    public int getHandValueScore() {
            return handValueScore;
    }


    @Override
    public void clearHand() {
            handContents = new CardImpl[SIZE];		
    }

    /**
     * Hands are compared by handValueScore; a better hand has a higher score.
     * Four of a Kind has a score of 7, high card 1.
     */

    public int compareTo(Hand h) {
            FiveCardHand hand = (FiveCardHand) h;
            return this.handValueScore - hand.handValueScore;
            //Positive value: this object is greater than hand														   
            //Negative: this object is less than hand
            //0: this object is equal to hand
    }



    @Override
    public int getPairValue() {
            return pairValue;
    }


    @Override
    public int getLowerPairValue() {
            return lowerPairValue;
    }

}