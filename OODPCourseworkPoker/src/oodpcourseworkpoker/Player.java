/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oodpcourseworkpoker;

/**
 *
 * @author marshall_gj
 */
public interface Player {


	public String getName();

	/**
	 * Method that assigns dealt cards to the Hand field.
	 * 
	 * @param Card dealt by dealer off the top of the deck.
	 */
	public void receiveCard(Card c);


	 /**
     * Method to retrieve the hand that the player is holding
     * 
     * @return Hand of player
     */
    public Hand getHand();
    
    
    /**
     * Method that makes decisions for DealerPlayer about which cards
     * (if any) to discard from a hand.  If the dealer has quads, a straight
     * or a flush he won't make any replacements as these are already very good
     * hands.
     * 
     * If the dealer has trips or pairs, he keeps those cards and switches out the 
     * remaining cards.
     * 
     * If the dealer has a partial (4 out of 5 cards) flush or straight, he keeps the cards that form 
     * the partial hand and switches out the other in the hope of completing the
     * flush or straight.
     * 
     * Otherwise (if the dealer doesn't have one of the above hands), he swaps out up to three cards 
     * with a value less than ten in the hope of getting a good high card to have a chance of winning.
     * 
     * This method returns a default of zero for HumanPlayer.
     * 
     * @return int representing how many cards it has discarded
     * so that TableTopImpl knows how many cards to replace.
     */
    public int chooseDiscard();


}