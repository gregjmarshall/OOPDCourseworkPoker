
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author marshall_gj
 */
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
public class DeckImpl implements Deck {
    private List<Card> deckContents = new LinkedList<Card>();


    public DeckImpl() {
            this.createCards();
    }

    @Override
    public List<Card> getContents() {
            return deckContents;
    }

    @Override
    public void shuffleCards() {
            Collections.shuffle(deckContents);
    }


    @Override
    public void createCards() {		
            for (int i = 0; i < 4; i++) { //Creates four lots of 13 cards, one set of each suit
                    for (int j = 0; j < 13; j++) {
                            Card c = new CardImpl(Rank.values()[j], Suit.values()[i]);//Enum values() is an array of the enum values.
                            deckContents.add(c);
                    }
            }		
    }

    @Override
    public Card popCard() {		
            Card topCard = deckContents.remove(0); //List.remove() shifts the rest of the deck up one index after removal of top item
            return topCard;
    }

    /**
     * For visual testing of card creation and shuffling.
     */
    @Override
    public void printDeck() {
            for (int i = 0; i < deckContents.size(); i++) {
                    System.out.println(deckContents.get(i).toString());
            }
    }

    /**
     * @param args
     */
	

}