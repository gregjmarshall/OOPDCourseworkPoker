
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author Greg Marshall
 * MSc Computer Science 
 * Submission date: 31/03/2013
 */
public class CardImpl implements Card {
    private final Suit SUIT;
    private final Rank RANK;


    public CardImpl(Rank rank, Suit suit) {
            this.SUIT = suit;
            this.RANK = rank;
    }


    @Override
    public Suit getSuit() {
            return SUIT;
    }


    @Override
    public Rank getRank() {
            return RANK;
    }

    @Override
    public String toString() {
            return "[" + RANK.toString() + " " + SUIT.toString() + "]";

    }

    /**
     * Cards are compared by rank; two cards of the same rank are considered
     * equal.
     */
    @Override
    public int compareTo(Card c) {
            CardImpl card = (CardImpl) c;
            return this.RANK.getValue() - card.RANK.getValue();
            //Postive value: this object is greater than c														   
            //Negative: this object is less than c
            //0: this object is equal to c
    }


    @Override
    public int getRankValue() {
            return RANK.getValue();
    }



}