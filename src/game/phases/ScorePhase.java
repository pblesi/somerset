/**
 * 
 */
package game.phases;

import game.RuleSet;
import game.Team;
import handlers.GameHandler;

import manager.states.CurrentGameState;
import players.Player;

/**
 * @author Patrick Blesi
 *
 * This class is equivalent to the score card that gets updated after the 
 * trick phase is over.
 *
 */
public class ScorePhase {

	// Pretty sure this is wrong: Emory gets points for both,
	// and subtracts double when goes set
	// Different set of rules Emory vs. Kraemer
	// Emory: You only score points when win bid
	// Kraemer: team that goes set loses points, 
	// other team gets points they made

	private final CurrentGameState cgs;
	private final GameHandler gameHandler;
	
	public ScorePhase(CurrentGameState cgs, GameHandler gameHandler) {
		
		this.cgs = cgs;
		this.gameHandler = gameHandler;
		
	}
	
	public void executeScorePhase() {
		
		Team bidWinningTeam = Team.getPlayersTeam(cgs.getTeams(), cgs.getBidWinningPlayerId());
		int bidAmount = cgs.getWinningBid();
		Player bidWinningPlayer = cgs.getPlayer(cgs.getBidWinningPlayerId());
		
		boolean bidWinningTeamMadeBid = 
				bidWinningTeam.calculatePointsWonDuringTrickPhase(cgs) >= bidAmount;
		
		int pointsWon = bidWinningTeam.calculatePointsWonDuringTrickPhase(cgs); 
				
		if (bidWinningTeamMadeBid) {
			
			gameHandler.handleBidWinningTeamMadeBid(bidWinningPlayer, bidAmount, pointsWon);
			bidWinningTeam.addToScore(pointsWon);
		
		} else {
			
			gameHandler.handleBidWinningTeamMissedBid(bidWinningPlayer, bidAmount, pointsWon);
			
			int diff = pointsWon - bidAmount;
			bidWinningTeam.addToScore(2*diff);
			
			if (cgs.getRuleSet() == RuleSet.KRAEMER) {
				for (Team t : cgs.getTeams()) {
					if (t != bidWinningTeam) {
						t.addToScore(t.calculatePointsWonDuringTrickPhase(cgs));
					}		
				}	
			}
			
		}
		
		gameHandler.handleScorePhaseCompleted(cgs.getTeams(), bidWinningTeam, bidWinningTeamMadeBid);
		
	}
	
}
