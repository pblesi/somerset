/**
 * 
 */
package game;

import game.phases.BidPhase;
import game.phases.CurrentPhase;
import game.phases.DealPhase;
import game.phases.DiscardPhase;
import game.phases.ScorePhase;
import game.phases.TrickPhase;
import game.util.Constants;
import handlers.GameHandler;

import java.util.List;

import manager.states.CurrentGameState;
import players.Player;

/**
 * @author Patrick Blesi
 *
 */
public class Game {
	
	private CurrentGameState cgs;
	
	private GameHandler gameHandler;
	
	private DealPhase dealPhase;
	private BidPhase bidPhase;
	private DiscardPhase discardPhase;
	private TrickPhase trickPhase;
	private ScorePhase scorePhase;
	
 	public Game(CurrentGameState cgs, GameHandler gameHandler) {
 		this.cgs = cgs;
 		this.gameHandler = gameHandler;
 	}
 	
	public void play() {
		
		while (winningTeam(cgs.getTeams()).getScore() < Constants.POINTS_TO_WIN) {
			executeRound();
		}

		gameHandler.handleGameWon(winningTeam(cgs.getTeams()));
		cgs.setCurrentGameExists(false);
		
	}

	private void executeRound() {

		switch(cgs.getCurrentPhase()) {

		default:
		case DEAL_PHASE:

			cgs.updateDealer();
			Player.initializePlayersForRound(cgs);
			gameHandler.handleSetDealer(cgs.getPlayer(cgs.getDealerId()));

			dealPhase = new DealPhase(cgs, gameHandler);
			dealPhase.executeDealPhase();
			cgs.setCurrentPhase(CurrentPhase.BID_PHASE);
			
		case BID_PHASE:

			bidPhase = new BidPhase(cgs, gameHandler);
			bidPhase.executeBidPhase();
			cgs.setCurrentPhase(CurrentPhase.DISCARD_PHASE);
			
		case DISCARD_PHASE:

			discardPhase = new DiscardPhase(cgs, gameHandler);
			discardPhase.executeDiscardPhase();
			cgs.setCurrentPhase(CurrentPhase.TRICK_PHASE);
			
		case TRICK_PHASE:

			trickPhase = new TrickPhase(cgs, gameHandler);
			trickPhase.executeTrickPhase();
			cgs.setCurrentPhase(CurrentPhase.SCORE_PHASE);
			
		case SCORE_PHASE:

			scorePhase = new ScorePhase(cgs, gameHandler);
			scorePhase.executeScorePhase();
			cgs.setCurrentPhase(CurrentPhase.DEAL_PHASE);
			
		}

	}

	private static Team winningTeam(List<Team> teams) {
		
		Team winningTeam = teams.get(0);
		
		for (int i = 1; i < teams.size(); i++) {
			if (teams.get(i).getScore() > winningTeam.getScore()) {
				winningTeam = teams.get(i);
			}
		}
		
		return winningTeam;
	}
	
	@Override
	public String toString() {
		
		// Should take different forms depending on the current phase
		String retval = ""; // dealPhase.bidHandString();
		
		for (String id : cgs.getPlayerIds()) {
			retval += cgs.getPlayer(id).toString() + "\n"; 
		}
		
		return retval;
	}
	
}
