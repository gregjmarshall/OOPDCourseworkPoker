
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author marshall_gj
 */
public class HumanPlayer implements Player {
	private Hand hand = new FiveCardHand();	
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