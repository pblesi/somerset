/**
 * 
 */
package util.strategic;

import util.comparators.IntrinsicOrderCardComparator;
import game.Hand;
import game.RuleSet;
import game.deck.Card;
import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 */
public class AnnotatedHand extends Hand<AnnotatedCard> {

	/**
	 * 
	 */
	public AnnotatedHand() {
		super();
	}

	public void changeHandLabels(Hand<Card> hand, String label) {
		for (Card c : hand) {
			this.getCard(new AnnotatedCard(c)).setLabel(label);
		}		 
	}
	
	public Hand<Card> getAllCardsInPlayersHand(String playerId) {

		Hand<Card> hand = new Hand<Card>();
		
		int size = this.size();
		for (int i = 0; i < size; i++) {
			AnnotatedCard c = this.get(i);
			if (c.getLabel().equals("p_" + playerId)) {
				hand.add(c);
			}
		}
		
		return hand;
		
	}
	
	public Hand<Card> getAllCardsInOthersHands(String playerId) {
		
		Hand<Card> hand = new Hand<Card>();
		
		int size = this.size();
		for (int i = 0; i < size; i++) {
			AnnotatedCard c = this.get(i);
			if (!c.getLabel().equals("p_" + playerId) && 
				(c.getLabel().startsWith("p_") ||
				 c.getLabel().startsWith("u_"))) {
				hand.add(c);
			}
		}
		
		return hand;
		
	}
	
	// Returns 
	// given leadSuit, trump, if oughtOught was played, 
	// if oughtOught lead, and the ruleSet 
	/**
	 * 
	 * @param LeadSuit The suit that was lead
	 * @param Trump This round's trump
	 * @param oughtOughtLead True if the ought ought was or could be lead
	 * @param oughtOughtPlayed True if the ought ought was or could be played
	 * @param ruleSet The rule set for the current game
	 * @return A list of cards that could possibly take the trick (have non zero value i.e. follow suit or trump, etc.)
	 */
	public AnnotatedHand getAllWinnableCards(Suit leadSuit, Suit trump, 
			boolean oughtOughtLead, boolean oughtOughtPlayed, RuleSet ruleSet) {
		
		AnnotatedHand annotatedHand = new AnnotatedHand();
		
		int size = this.size();
		for (int i = 0; i < size; i++) {
			
			AnnotatedCard c = this.get(i);
			
			if (c.suit.equals(leadSuit) || c.isTrumpOrS(trump) ||
				c.isOughtOught()) {
				annotatedHand.add(c);
				continue;
			}
			
			if (ruleSet == RuleSet.KRAEMER) {
				if (oughtOughtPlayed && !oughtOughtLead) {
					if (c.isDouble()) {
						annotatedHand.add(c);
						continue;
					}
				}
			} else {
				if (oughtOughtPlayed || oughtOughtLead) {
					if (c.isDouble()) {
						annotatedHand.add(c);
						continue;
					}
				}	
			}
			
		}
				
		return annotatedHand;
				
	}
	
	/**
	 * Truncates a list of cards based on the specified card.
	 * Essentially returns a list of cards such that each 
	 * card in the list will beat card.
	 * 
	 * Assumes that this annotated hand is already in
	 * intrinsic order.
	 * 
	 * Assumes that this annotated hand is restricted to
	 * only winnable cards.
	 * 
	 * @param card The card on which to truncate
	 * @return A truncated list of cards
	 */
	public AnnotatedHand truncateWinnableCards(Card card, Suit trump, 
			boolean oughtOughtLead, boolean oughtOughtPlayed, RuleSet ruleSet) {
		
		AnnotatedHand annotatedHand = new AnnotatedHand();
		
		IntrinsicOrderCardComparator iocc = new IntrinsicOrderCardComparator(trump, ruleSet, oughtOughtLead, oughtOughtPlayed);
		
		int size = this.size();
		for (int i = 0; i < size; i++) {
			if (iocc.compare(this.get(i), card) > 0) {
				annotatedHand.add(this.get(i));
			}
		}
		
		return annotatedHand;
		
	}
	
	public static AnnotatedHand deepCopyAnnotatedHand(AnnotatedHand hand) {
		
		AnnotatedHand deepCopyHand = new AnnotatedHand();
		
		for (AnnotatedCard c : hand) {
			deepCopyHand.add(AnnotatedCard.deepCopyCard(c));
		}
	
		return deepCopyHand;
		
	}
	
}
