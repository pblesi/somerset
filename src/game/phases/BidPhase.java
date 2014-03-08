/**
 * 
 */
package game.phases;

import manager.states.CurrentGameState;
import game.util.PlayerPointMap;
import handlers.GameHandler;
import players.Player;

/**
 * @author Patrick Blesi
 *
 */
public class BidPhase {

	private final CurrentGameState cgs;
	private final GameHandler gameHandler;
	
	public BidPhase(CurrentGameState cgs, GameHandler gameHandler) {
		this.cgs = cgs;
		this.gameHandler = gameHandler;
	}
	
	public void executeBidPhase() {
		
		int numPlayers = cgs.getNumPlayers();
		int maxPoints = PlayerPointMap.PLAYER_POINTS.get(numPlayers);
		
		switch(cgs.getCurrentPose()) {
		
		default:
		case BID_UNINITIALIZED:
			
			cgs.setBidPhaseState(new BidPhaseState(cgs.getPlayer(cgs.getDealerId())));
			cgs.setCurrentPose(CurrentPose.BID_INITIALIZED);
				
		case BID_INITIALIZED:
			
			BidPhaseState bps = cgs.getBidPhaseState();
			
			while (numPlayersPassed(cgs) < numPlayers - 1 && bps.winningBid < maxPoints) {
					
				Player currentPlayer = cgs.getPlayer(bps.currentPlayerId);
				
				if (!currentPlayer.alwaysPass()) {
					
					bps.currentPlayerBid = currentPlayer.getBid(bps.winningPlayerId, bps.winningBid);
						
					if (bps.currentPlayerBid > bps.winningBid) {
						bps.winningBid = bps.currentPlayerBid;
						bps.winningPlayerId = currentPlayer.getId();
						clearPassed(cgs, currentPlayer);
						gameHandler.handlePlayerMadeBid(cgs, bps.winningPlayerId, bps.winningBid);
					} else {
						currentPlayer.setPassed(true);
						gameHandler.handlePlayerPassedOnBid(currentPlayer);
					}
						
				} else {
					gameHandler.handlePlayerPassedOnBid(currentPlayer);
				}
					
				bps.currentPlayerId = currentPlayer.getPlayerToLeft();
				cgs.commitToDisk();
					
			}
			
			cgs.setWinningBid(bps.winningBid);
			cgs.setBidWinningPlayerId(bps.winningPlayerId);
			gameHandler.handleBidPhaseCompleted(cgs, bps.winningPlayerId, bps.winningBid);
			
		}
		
	}
	
	/**
	 * Counts the total number of players
	 * that have passed.
	 * 
	 * @param p person to start counting where players 
	 * have passed ( can be any person playing the game)
	 * @return the total number of players who have passed
	 */
	private static int numPlayersPassed(CurrentGameState cgs) {
		
		int numPlayersPassed = 0;
		Player dealer = cgs.getPlayer(cgs.getDealerId());
		Player currentPlayer = dealer;
		
		do {
			numPlayersPassed += currentPlayer.passed() ? 1 : 0;
			currentPlayer = cgs.getPlayer(currentPlayer.getPlayerToLeft());
		} while (currentPlayer != dealer);
		
		return numPlayersPassed;
	}

	/**
	 * clears all the players' passed flags
	 * 
	 * @param p a player in the game
	 */
	private static void clearPassed(CurrentGameState cgs, Player p) {
		
		Player currentPlayer = p;
		
		do {
			currentPlayer.setPassed(false);
			currentPlayer = cgs.getPlayer(currentPlayer.getPlayerToLeft());
		} while (currentPlayer != p);
		
	}

}
