
package oodpcourseworkpoker;

/*
 * *@author Anna Taylor
 * @author Greg Marshall
 */
public class HandComparatorImpl implements HandComparator {
    private String result;

    public String getResult() {
            return result;
    }

    @Override
    public void compareHands(Player humanPlayer, Player dealerPlayer) {

            int comparison = humanPlayer.getHand().compareTo(dealerPlayer.getHand()); //this section compares hands at showdown
        if(comparison > 0) {                                                    //if this value is positive
            result = "Congratulations! You have won the hand!";      //the first hand is higher in value
            return;
        }

        else if (comparison < 0) {                                               // negative indicates a lower value
            result = "The computer has won the hand! Better luck next time!";
            return;
        }

        else if (comparison == 0) { //if the comparison or rank is equal, the hand needs to be compared further

            if (humanPlayer.getHand().getHandValueScore() == 7) { //represents quads by score
                if (humanPlayer.getHand().getPairValue() > dealerPlayer.getHand().getPairValue()) {
                    result = "Congratulations! You have won the hand with the higher quads!";
                    return;
                }
                else {  //there can only be 1 quad or trip hand of any value and so the winner is clear
                    result = "The computer has won the hand with the higher quads! Better luck next time!"; 
                    return;
                }                                                                           		
            }

            else if (humanPlayer.getHand().getHandValueScore() == 4){ //represents trips by score
                if (humanPlayer.getHand().getPairValue() > dealerPlayer.getHand().getPairValue()) {
                    result = "Congratulations! You have won the hand with the higher trips!";
                    return;
                }
                else { //there can only be 1 quad or trip hand of any value
                    result = "The computer has won the hand with the higher trips! Better luck next time!"; 
                }                          
            }

            else if (humanPlayer.getHand().getHandValueScore() == 2) { //represents one pair
                if (humanPlayer.getHand().getPairValue() > dealerPlayer.getHand().getPairValue()) {
                    result = "Congratulations! You have won the hand with the higher pair!";
                    return;
                }
                else if(humanPlayer.getHand().getPairValue() < dealerPlayer.getHand().getPairValue()) {
                    result = "The computer has won the hand with the higher pair! Better luck next time!";
                    return;
                }
                else { //equal one pair hands call this method to evaluate the rest of the cards to establish winner
                    highCardComparison(humanPlayer, dealerPlayer); 
                }
            }

            else if (humanPlayer.getHand().getHandValueScore() == 3) {  //same applies as above for 2 pair hands 
                if (humanPlayer.getHand().getPairValue() > dealerPlayer.getHand().getPairValue()) {
                    result = "Congratulations! You have won the hand with the higher of the two pair!";
                    return;
                }
                else if (humanPlayer.getHand().getPairValue() < dealerPlayer.getHand().getPairValue()) {
                    result = "The computer has won the hand with the higher of the two pair! Better luck next time!";
                    return;
                }
                else if (humanPlayer.getHand().getLowerPairValue() > dealerPlayer.getHand().getLowerPairValue()) {
                    result = "Congratulations! You have won the hand with the higher second pair!";  
                    //Two Pair hands use getLowerPairValue() to compare the lower pair when higher pair is equal in rank
                    return;
                }
                else if (humanPlayer.getHand().getLowerPairValue() < dealerPlayer.getHand().getLowerPairValue()) {
                    result = "The computer has won the hand with the higher second pair! Better luck next time!";
                    return;
                }

                else {
                    highCardComparison(humanPlayer, dealerPlayer); //To determine winner based on high card
                }
            }

            else { //For high card hands
                highCardComparison(humanPlayer, dealerPlayer); 
            }
        }

    }



    @Override
    public void highCardComparison(Player humanPlayer, Player dealerPlayer) { //To evaluate high card hands
            for(int i = 4; i >= 0 ; i--) {
        int humanDraw = humanPlayer.getHand().getContents()[i].getRankValue();           
        int compDraw = dealerPlayer.getHand().getContents()[i].getRankValue();             
        if (humanDraw > compDraw) {
            result = "Congratulations! You have won the hand with the best high card."; 
            return;
        }
        else if (compDraw > humanDraw) {
            result = "The computer has won the hand with the best high card! Better luck next time!";     
            return;
        }
        if (i == 0 && compDraw == humanDraw) {
            result = "The hand is a draw! Please play again.";
        }      
    }

    }

}