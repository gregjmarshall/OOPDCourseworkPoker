
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author Greg Marshall
 * MSc Computer Science 
 * Submission date: 31/03/2013
 */
import java.util.List;
public interface Deck {

    /**
     * Returns contents of the deck.
     * @return List<Card>
     */
    public List<Card> getContents();

    /**
     * Shuffles the deck.
     */
    public void shuffleCards();

    /**
     * Creates the 52 cards that form the deck and stores
     * them in a list field.
     */
    public void createCards();

    /**
     * Pops a card off the top of the deck, removing it. Used for
     * dealing cards to players.
     * @return Card the card to be dealt to the player.
     */
    public Card popCard(); 

    /**
     * Prints the contents of the deck.
     */
    public void printDeck();

}