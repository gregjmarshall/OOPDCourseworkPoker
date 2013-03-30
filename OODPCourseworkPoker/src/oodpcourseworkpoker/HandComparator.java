
package oodpcourseworkpoker;


/*
 * *@author Anna Taylor
 * @author Greg Marshall
 */
public interface HandComparator {

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
     * the ranks are found to be equal, highCardComparison() is called to then compare
     * on the basis of high cards.
     * 
     * If both players have Two Pair, the higher ranking pairs are first compared, and if found to be the same,
     * the lower ranking pairs are compared using lowerPairValue of Hand. If these are also the same, 
     * standardHandComparison() is called to evaluate the remaining cards.
     * 
     * @param Player humanPlayer the HumanPlayer object passed from TableTop.
     * @param Player dealerPlayer the DealerPlayer object passed from TableTop.	 
     */
    public void compareHands(Player humanPlayer, Player dealerPlayer);

    /**
     * The other of the two methods that deal with comparing player hands.  If players have a Flush, Straight,
     * or High Card (i.e. 'nothing'), this method is used to compare which player as the highest cards and determine a winner.
     * This method is also used if players both have a pair or two pairs that are the same, so that hands may be
     * compared using the remaining cards.
     * 
     * @param Player humanPlayer the HumanPlayer object passed from TableTop.
     * @param Player dealerPlayer the DealerPlayer object passed from TableTop.
     */
    public void highCardComparison(Player humanPlayer, Player dealerPlayer);

    /**
     * Accesses the result statement (who has won).
     * 
     * @return String result of the comparison.
     */
    public String getResult();

}