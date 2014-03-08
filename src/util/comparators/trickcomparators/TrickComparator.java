/**
 * 
 */
package util.comparators.trickcomparators;

import game.deck.Card;
import game.deck.Suit;

import java.util.Comparator;

/**
 * @author Patrick Blesi
 *
 */
public abstract class TrickComparator implements Comparator<Card> {

	protected Suit trump;
	protected Suit leadSuit;
	
	/**
	 * 
	 */
	public TrickComparator(Suit trump, Suit leadSuit) {
		this.trump = trump;
		this.leadSuit = leadSuit;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public abstract int compare(Card o1, Card o2);

}
