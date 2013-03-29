/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oodpcourseworkpoker;

/**
 *
 * @author marshall_gj
 */
public enum Suit {

	CLUBS, SPADES, HEARTS, DIAMONDS;


	@Override
	public String toString() {
		String s = super.toString();
		return s.substring(0,1) + s.substring(1).toLowerCase();
	}

}
