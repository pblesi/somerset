/**
 * 
 */
package game.phases;

/**
 * @author Patrick Blesi
 *
 */
public class DiscardPhaseState {

	public int numCardsToDiscard;
	
	protected DiscardPhaseState() { }
	
	public DiscardPhaseState(int numCardsToDiscard) {
		this.numCardsToDiscard = numCardsToDiscard;
	}
	
	public int getNumCardsToDiscard() {
		return numCardsToDiscard;
	}

	public void decrementNumCardsToDiscard() {
		this.numCardsToDiscard -= 1;
	}
	
}
