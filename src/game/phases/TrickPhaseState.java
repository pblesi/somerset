/**
 * 
 */
package game.phases;

import players.Player;
import manager.states.CurrentGameState;
import game.Trick;
import game.deck.Card;

/**
 * @author Patrick Blesi
 *
 */
public class TrickPhaseState {

	public CurrentTrickPose currentTrickPose;
	
	public Trick trick;
	public Card firstCard;
	
	public String currentLeadId;
	public String currentPlayerId;
	
	protected TrickPhaseState() { }
	
	public TrickPhaseState(String bidWinnerId) {
		this.currentTrickPose = CurrentTrickPose.FIRST_CARD;
		this.currentLeadId = bidWinnerId;
		this.currentPlayerId = bidWinnerId;
	}
	
	public CurrentTrickPose getCurrentTrickPose() {
		return this.currentTrickPose;
	}
	
	public void setCurrentTrickPose(CurrentTrickPose currentTrickPose) {
		this.currentTrickPose = currentTrickPose;
	}
	
	public Trick getTrick() {
		return trick;
	}
	
	public void setTrick(Trick trick) {
		this.trick = trick;
	}
	
	public Card getFirstCard() {
		return firstCard;
	}
	
	public void setFirstCard(Card firstCard) {
		this.firstCard = firstCard;
	}
	
	public String getCurrentLeadId() {
		return this.currentLeadId;
	}
	
	public void setCurrentLeadId(String currentLeadId) {
		this.currentLeadId = currentLeadId;
	}
	
	public String getCurrentPlayerId() {
		return this.currentPlayerId;
	}
	
	public void setCurrentPlayerId(String currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}

	public Player getCurrentLead(CurrentGameState cgs) {
		return cgs.getPlayer(this.getCurrentLeadId());
	}
	
	public Player getCurrentPlayer(CurrentGameState cgs) {
		return cgs.getPlayer(this.getCurrentPlayerId());
	}
	
}
