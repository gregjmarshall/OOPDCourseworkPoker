/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oodpcourseworkpoker;

/**
 *
 * @author marshall_gj
 */
public interface TableTop {

	/**
	 * This method creates a human player and a dealer player, and sets
	 * off gameplay.
	 */
	public void prepareTable(); 

	/**
	 * Implements a short pause for easier, more broken up gameplay.
	 */
	public void pause();

	/**
	 * Introduces the dealer and sets off mainGameCycle().
	 */
	public void commenceGame();

	/**
	 * This is the main cycle of the game that runs on a while loop until the user
	 * opts to quit by entering "N" when asked if they wish to play again. This method calls
	 * sub methods to deal with certain sections of the game, such as the dealer's turn
	 * and comparing player hands.
	 */
	public void mainGameCycle();

	/**
	 * Handles the dealing of cards to player's hands. Cards are dealt to players
	 * alternately, using methods of Deck and Player.
	 */
	public void dealCards();

	/**
	 * This method handles the swapping out of up to three cards, and the replacing of
	 * these cards from the top of the deck (for HumanPlayer).  
	 * The player is asked how many cards they would like to swap (0-3), and is then asked 
	 * to provide the positions of the cards they wish to change. Once the swaps have been made
	 * (if any), new cards are dealt from the deck and the hand is re-evaluate in the mainGameCycle().
	 */
	public void discardAndReplace();

	/**
	 * This handles the program-controlled DealerPlayer's turn. The  dealer's
	 * chooseDiscard() method is invoked, and any replacements are made as a result of
	 * the the dealer choosing which cards to swap in chooseDiscard(). The dealer's hand
	 * is then re-evaluated.
	 */
	public void dealerTurn();

	/**
	 * Calls clearHand() method of Hand for each player, clearing the players' hands before the start
	 * of the next game round (should the player choose to continue playing).
	 */
	public void clearHands();

	/**
	 * One of two methods which deal with comparing player hands. Firstly, hands are compared using
	 * the compareTo() method of Hand.  If the hands are different (i.e. the result of compareTo() is
	 * not 0), a winner is decided there and then.  If the result is 0, the hands both have the same
	 * value (i.e. both players have a pair, or both have a flush etc) and further comparison begins.
	 * 
	 * If the players both have quads, the pairValue from the Hand class is compared to see which quads are 
	 * of a higher rank and a winner is decided. The same process is used for trips.
	 * 
	 * If the players have one pair, the pairs are compared by their rank using getPairValue() from Hand. If
	 * the ranks are found to be equal, standardHandComparison() of TableTop is called to then compare
	 * on the basis of high cards.
	 * 
	 * If both players have Two Pair, the higher ranking pairs are first compared, and if found to be the same,
	 * the lower ranking pairs are compared using lowerPairValue of Hand. If these are also the same, 
	 * standardHandComparison() is called to evaluate the remaining cards.	 * 
	 */
	public void compareHands();

	/**
	 * The other of the two methods that deal with comparing player hands.  If players have a Flush, Straight,
	 * or High Card, this method is used to compare which player as the highest cards and determine a winner.
	 * This method is also used if players both have a pair or two pairs that are the same, so that hands may be
	 * compared using the remaining cards.
	 */
	public void standardHandComparison();
}