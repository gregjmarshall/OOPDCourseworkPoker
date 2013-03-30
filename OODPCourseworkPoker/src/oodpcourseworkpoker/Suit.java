
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author Greg Marshall
 * MSc Computer Science 
 * Submission date: 31/03/2013
 */
public enum Suit {

    CLUBS, SPADES, HEARTS, DIAMONDS;


    @Override
    public String toString() {
            String s = super.toString();
            return s.substring(0,1) + s.substring(1).toLowerCase();
    }

}