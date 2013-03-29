/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oodpcourseworkpoker;

/**
 *
 * @author marshall_gj
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