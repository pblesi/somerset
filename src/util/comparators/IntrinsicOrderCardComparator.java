/**
 * 
 */
package util.comparators;

import game.RuleSet;
import game.deck.Card;
import game.deck.Deck;
import game.deck.Suit;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Patrick Blesi
 *
 */
public class IntrinsicOrderCardComparator implements Comparator<Card> {

	private final Suit trump;
	private final RuleSet ruleSet;
	
	// indicates whether the ought ought was or could be lead
	private final boolean oughtOughtLead; 
	
	// indicates whether the ought ought was or could be played
	private final boolean oughtOughtPlayed; 
		
	/**
	 * 
	 * @param trump indicates trump for the current round
	 * @param ruleSet indicates RuleSet for the current game
	 * @param oughtOughtLead indicates if the ought ought was or could be lead
	 * @param oughtOughtPlayed indicates if the ought ought was or could be played
	 */
	public IntrinsicOrderCardComparator(Suit trump, RuleSet ruleSet, boolean oughtOughtLead, boolean oughtOughtPlayed) {
		this.trump = trump;
		this.ruleSet = ruleSet;
		this.oughtOughtLead = oughtOughtLead;
		this.oughtOughtPlayed = oughtOughtPlayed;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Card o1, Card o2) {
		
		if (o1.isTrumpOrS(trump) && o2.isTrumpOrS(trump)) {
			return o1.number.value - o2.number.value;
		} else if (o1.isTrumpOrS(trump)) { // 1 trump (or s) (trump wins)
			return 1;
		} else if (o2.isTrumpOrS(trump)) { // 1 trump (or s) (trump wins)
			return -1;
		} else { // 0 trump and s/s's
			
			if (ruleSet == RuleSet.KRAEMER) {
				
				// If the ought ought was played and not lead
				if (oughtOughtPlayed && !oughtOughtLead) {
					if (o1.isDouble() && o2.isDouble()) { // reverse order of doubles
						return o1.suit.value - o2.suit.value; // highest suit wins
					}
				}
				
				// if oughtOught not played or oughtOught lead, standard ordering applies
				
			} else {
			
				if (oughtOughtPlayed || oughtOughtLead) {
					if (o1.isDouble() && o2.isDouble()) { // reverse order of doubles
						return o1.suit.value - o2.suit.value; // highest suit wins
					}
				}
				
			}
			
			int suitValDiffDiffs = o1.suitValDiff() - o2.suitValDiff();
			
			if (suitValDiffDiffs == 0) {
				return -(o1.suit.value - o2.suit.value); // smaller suits mean greater value
			} else {
				return -suitValDiffDiffs; // smaller diff means greater value
			}
			
		}
		
	}

	/**
	 * Test method for correctness verification
	 * @param args
	 */
	public static void main(String[] args) {
	
		Deck deck = new Deck();
		
		List<Card> cardList = new LinkedList<Card>();
		
		int numCards = deck.numCards();
		for (int i = 0; i < numCards; i++) {
			cardList.add(deck.drawCard());
		}
		
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("4"), RuleSet.KRAEMER, true, true);		
		IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("s"), RuleSet.KRAEMER, true, true);		
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("0"), RuleSet.KRAEMER, true, true);		
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("12"), RuleSet.KRAEMER, true, true);		
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("12"), RuleSet.KRAEMER, true, false);		
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("12"), RuleSet.KRAEMER, false, false);		
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("12"), RuleSet.KRAEMER, false, true);		
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("12"), RuleSet.EMRY, true, true);		
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("12"), RuleSet.EMRY, true, false);		
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("12"), RuleSet.EMRY, false, false);
		// IntrinsicOrderCardComparator comparator = new IntrinsicOrderCardComparator(new Suit("12"), RuleSet.EMRY, false, true);
				
		Collections.sort(cardList, comparator);
		
		System.out.println(cardList);
		
	}
	
}
