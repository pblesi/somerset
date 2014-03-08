/**
 * 
 */
package util.strategic;

import game.deck.Card;

/**
 * @author Patrick Blesi
 *
 */
public class AnnotatedCard extends Card {

	// Represents a label for the card
	// generally indicating which hand the
	// card is in if it's unknown, etc
	public String label; 

	protected AnnotatedCard() { }
	
	/**
	 * @param name
	 */
	public AnnotatedCard(AnnotatedCard c) {
		super(c.name);
		this.label = c.label;
	}
	
	/**
	 * @param name
	 */
	public AnnotatedCard(String label, Card c) {
		super(c.name);
		this.label = label;
	}

	/**
	 * @param name
	 */
	public AnnotatedCard(Card c) {
		super(c.name);
		this.label = "";
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean isInPlayersHand(String playerId) {
		return this.label.equals("p_" + playerId);
	}
	
	public String toString() {
		return this.label + ": " + super.toString();
	}
	
}
