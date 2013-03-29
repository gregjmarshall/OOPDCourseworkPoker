/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oodpcourseworkpoker;

/**
 *
 * @author marshall_gj
 */
public enum Rank {

	TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
	EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

	private int value;

	private Rank(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		String s = super.toString();
		return s.substring(0,1) + s.substring(1).toLowerCase(); 
	}

}


