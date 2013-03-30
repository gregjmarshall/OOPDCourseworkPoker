
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author Greg Marshall
 * MSc Computer Science 
 * Submission date: 31/03/2013
 */
public interface Card extends Comparable<Card> {

    public Suit getSuit();

    public Rank getRank();

    /**
     * Returns rank value as int rather than enum type.
     * @return int numerical value of a card's rank.
     */
    public int getRankValue();

    @Override
    public int compareTo(Card c);


}