/**
 * 
 */
package util.comparators;

import game.deck.Card;

import java.util.Comparator;

/**
 * @author Patrick Blesi
 *
 */
public class HandOrderCardComparator implements Comparator<Card> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Card o1, Card o2) {
		
		int suitOrder = o1.suit.value - o2.suit.value;
		
		if (suitOrder == 0) {
			return o1.number.value - o2.number.value;
		} else {
			return suitOrder;
		}
			
		
		
	}

}
