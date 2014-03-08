/**
 * 
 */
package game.phases;

import manager.states.CurrentGameState;
import game.Hand;
import game.deck.Card;
import handlers.GameHandler;
import players.Player;

/**
 * @author Patrick Blesi
 *
 */
public class DiscardPhase {
	
	private final CurrentGameState cgs;
	private final GameHandler gameHandler; 
	
	public DiscardPhase(CurrentGameState cgs, GameHandler gameHandler) {
		this.cgs = cgs;
		this.gameHandler = gameHandler;
	}

	public void executeDiscardPhase() {
		
		Hand<Card> bidHand = cgs.getBidHand();
		Player winningPlayer = cgs.getPlayer(cgs.getBidWinningPlayerId());
		Card discardedCard;
				
		switch(cgs.getCurrentPose()) {
		
			default:
			case DCRD_UNINITIALIZED:
				
				cgs.setDiscardPhaseState(new DiscardPhaseState(bidHand.size()));
				cgs.setCurrentPose(CurrentPose.DCRD_UNINITIALIZED);
				
			case DCRD_DEAL_BID:
				
				winningPlayer.addHandToHand(bidHand);
				gameHandler.handleAddedBidCardsToHand(winningPlayer);
				cgs.setCurrentPose(CurrentPose.DCRD_DCRD_CARDS);
				
			case DCRD_DCRD_CARDS:
				
				DiscardPhaseState dps = cgs.getDiscardPhaseState();
				
				// Discard cards
				while (dps.getNumCardsToDiscard() > 0) {
					discardedCard = winningPlayer.discardCard();
					gameHandler.handlePlayerDiscardedCard(winningPlayer, discardedCard);
					dps.decrementNumCardsToDiscard();
					cgs.commitToDisk();
				}
				
				gameHandler.handleDiscardPhaseCompleted(winningPlayer);
				
		}
		
	}
	
}
