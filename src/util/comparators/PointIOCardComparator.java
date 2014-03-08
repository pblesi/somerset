/**
 * 
 */
package util.comparators;

import game.RuleSet;
import game.deck.Card;
import game.deck.Suit;

/**
 * Point Intrinsic Order Card Comparator
 * 
 * This comparator sorts by points first, and 
 * then by Intrinsic Order
 * 
 * @author Patrick Blesi
 *
 *
 *
 */
public class PointIOCardComparator extends IntrinsicOrderCardComparator {

	public PointIOCardComparator(Suit trump, RuleSet ruleSet, boolean oughtOughtLead, boolean oughtOughtPlayed) {
		super(trump, ruleSet, oughtOughtLead, oughtOughtPlayed);
	}
	
	@Override
	public int compare(Card o1, Card o2) {
		
		if (o1.numberOfPoints != o2.numberOfPoints) {
			return o1.numberOfPoints - o2.numberOfPoints;
		} else {
			return super.compare(o1, o2);
		}
		
	}

}
