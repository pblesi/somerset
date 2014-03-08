/**
 * 
 */
package util.comparators.trickcomparators;

import game.deck.Card;
import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 * Lead Ought Ought Trick Comparator
 * Comparator for when the ought ought is lead
 *
 * Special rules if leadSuit = 0
 * only thing that takes it is trump		
 *
 */
public class LeadOughtOughtTComparator extends TrickComparator {

	

	/**
	 * @param trump
	 * @param leadSuit
	 */
	public LeadOughtOughtTComparator(Suit trump, Suit leadSuit) {
		super(trump, leadSuit);
	}

	/** 
	 * 
	 * Note: This implementation is the same as the standard trick comparator
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Card o1, Card o2) {
		// 2 trump (or 1 trump, 1 s) (highest wins)
		if (o1.isTrumpOrS(trump) && o2.isTrumpOrS(trump)) {
			return o1.number.value - o2.number.value;
		} else if (o1.isTrumpOrS(trump)) { // 1 trump (or s) (trump wins)
			return 1;
		} else if (o2.isTrumpOrS(trump)) { // 1 trump (or s) (trump wins)
			return -1;
		} else { // 0 trump

			// 2 match leadSuit (highest wins)
			if (o1.suit.equals(leadSuit) && o2.suit.equals(leadSuit)) {
				return o1.number.value - o2.number.value;
			} else if (o1.suit.equals(leadSuit)) { // 1 matches leadSuit (match wins)
				return 1;
			} else if (o2.suit.equals(leadSuit)) { // 1 matches leadSuit (match wins)
				return -1;
			} else { // 0 match leadSuit (tie)
				return 0;
			}

		}		
	}

}
