/**
 * 
 */
package game.phases;

import manager.states.CurrentGameState;
import game.Trick;
import game.deck.Card;
import handlers.GameHandler;
import players.Player;

/**
 * @author Patrick Blesi
 *
 */
public class TrickPhase {
	
	private CurrentGameState cgs;
	private final GameHandler gameHandler;
	
	public TrickPhase(CurrentGameState cgs, GameHandler gameHandler) {
		this.cgs = cgs;
		this.gameHandler = gameHandler;
	}

	public void executeTrickPhase() {
		
		switch(cgs.getCurrentPose()) {
		
		default:
		case TRCK_UNINITIALIZED:
				
			String bwpi = cgs.getBidWinningPlayerId();
			cgs.setTrickPhaseState(new TrickPhaseState(bwpi));
			cgs.setTrump(null);
			cgs.setCurrentPose(CurrentPose.TRCK_INITIALIZED);
			
		case TRCK_INITIALIZED:
		
			TrickPhaseState tps = cgs.getTrickPhaseState();
			
			while (Player.anyPlayerHasCards(cgs)) {
				
				switch(tps.getCurrentTrickPose()) {
				
				default:
				case FIRST_CARD:

					tps.setFirstCard(tps.getCurrentLead(cgs).playLeadCard());
					
					if (cgs.getTrump() == null) {
						cgs.setTrump(tps.getFirstCard().suit);
						gameHandler.handleTrumpSet(cgs.getTrump());
					}
					
					tps.setTrick(new Trick(cgs.getTrump()));
					tps.getTrick().add(tps.getFirstCard(), tps.getCurrentLeadId());
					gameHandler.handleTrickLeadCardPlayed(cgs);
					
					tps.setCurrentPlayerId(tps.getCurrentLead(cgs).getPlayerToLeft());
					tps.setCurrentTrickPose(CurrentTrickPose.NOT_FIRST_CARD);
					cgs.commitToDisk();
					
				case NOT_FIRST_CARD:
					
					if (!tps.getCurrentPlayerId().equals(tps.getCurrentLeadId())) {
						do {
							Card card = tps.getCurrentPlayer(cgs).playCard(tps.getTrick(), tps.getFirstCard().suit, cgs.getTrump()); 
							tps.getTrick().add(card, tps.getCurrentPlayerId());
							gameHandler.handleTrickCardPlayed(tps.getTrick(), tps.getCurrentPlayer(cgs), card);
							tps.setCurrentPlayerId(tps.getCurrentPlayer(cgs).getPlayerToLeft());
							cgs.commitToDisk();
						} while (!tps.getCurrentPlayerId().equals(tps.getCurrentLeadId())); 
					}
					
					tps.getTrick().calculateWinner();
					int numPoints = tps.getTrick().getNumPoints();
					String winner = tps.getTrick().getWinner();
					cgs.getPlayer(winner).addPoints(numPoints);
					
					gameHandler.handlePlayerWonTrick(tps.getTrick());
						
					tps.setCurrentLeadId(tps.getTrick().getWinner());
					tps.setCurrentTrickPose(CurrentTrickPose.FIRST_CARD);
					cgs.commitToDisk();
						
				}
				
			}
			
			gameHandler.handleTrickPhaseCompleted();
						
		}
		
	}
	
}
