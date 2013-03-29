
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author marshall_gj
 */
public class HumanPlayer implements Player { //This player represents the user player during the game and holds
    private Hand hand = new FiveCardHand();	// his or her hands/cards
    private String userName;


    public HumanPlayer(String userName) {
            this.userName = userName;
    }

    @Override
    public Hand getHand(){
        return hand;
}

    @Override
    public void receiveCard(Card c) {
            hand.addCard(c);
    }

    @Override
    public int chooseDiscard() {
        return 0;
    }


    @Override
    public String getName() {
            return userName;
    }

}