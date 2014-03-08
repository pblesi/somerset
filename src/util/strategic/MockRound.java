/**
 * 
 */
package util.strategic;

import java.util.ArrayList;
import java.util.List;

import manager.states.CurrentGameState;
import manager.states.MockGameState;
import game.Hand;
import game.Team;
import game.deck.Card;
import game.deck.Suit;
import game.phases.DiscardPhase;
import game.phases.ScorePhase;
import game.phases.TrickPhase;
import handlers.GameHandler;
import handlers.PlayerNotifierGameHandler;
import players.Player;
import players.StrategicPlayer;
import players.states.GameState;
import players.states.StrategicRoundState;
import util.Tuple;

/**
 * Plays a round using the current gameState and 
 * roundState and reports on the number of points
 * earned.
 * 
 * Each player is a strategicpPlayer is assumed to 
 * be a strategic player.
 * 
 * 
 * @author Patrick Blesi
 *
 */
public class MockRound {

	private final StrategicRoundState strategicRoundState;
	private final Tuple<Suit, Integer> bestTuple;
	
	private final CurrentGameState mgs;
	
	private DiscardPhase discardPhase;
	private TrickPhase trickPhase;
	private ScorePhase scorePhase;
	
	/**
	 * 
	 */
	public MockRound(GameState gameState, StrategicRoundState strategicRoundState, Tuple<Suit, Integer> bestTuple) {

		this.strategicRoundState = strategicRoundState;
		this.bestTuple = bestTuple;
		
		mgs = MockGameState.getClonedGameState();
		// first player corresponds to "me".
		List<Player> players = createListOfPlayers(gameState, strategicRoundState, bestTuple);
		mgs.setPlayers(players);
		
	}
	
	public int getNumPointsWon() {
		return Team.getPlayersTeam(mgs.getTeams(), mgs.getPlayerIds().get(0)).getScore();
	}
	
	public void executeRound() {
		
		GameHandler gameHandler = new PlayerNotifierGameHandler(mgs.getPlayers());
		
		for (Card c : strategicRoundState.getBidCards()) {
			gameHandler.handleBidCardDisplayed(c);
		}
		
		mgs.setBidWinningPlayerId(mgs.getPlayerIds().get(0)); // Assume bidWinner is "me"
		gameHandler.handleBidPhaseCompleted(mgs, mgs.getBidWinningPlayerId(), bestTuple.y);
		
		discardPhase = new DiscardPhase(mgs, gameHandler);
		discardPhase.executeDiscardPhase();
		
		trickPhase = new TrickPhase(mgs, gameHandler);
		trickPhase.executeTrickPhase();
		
		scorePhase = new ScorePhase(mgs, gameHandler); // 0 bid ensures we count points TODO: double check this on test
		scorePhase.executeScorePhase();
		
	}

	// May want to factor this into player factorys
	private static List<Player> createListOfPlayers(GameState gameState, StrategicRoundState strategicRoundState, Tuple<Suit, Integer> bestTuple) {
		
		List<Player> players = new ArrayList<Player>();
		
		Player me = new StrategicPlayer("me", gameState.numPlayers, gameState.ruleSet, bestTuple);
		
		
		Hand<Card> myHand = strategicRoundState.getMyHand();
		
		int myHandLength = myHand.size();
		
		for (int i = 0; i < myHandLength; i++) {
			me.addCardToHand(myHand.get(i));
		}
		
		players.add(me);
		
		Hand<Card> otherHands = strategicRoundState.getOtherHands();
		
		int numCardsPerPlayer = otherHands.size()/(gameState.numPlayers-1);
		
		for (int i = 1; i < gameState.numPlayers; i++) {
			
			Player p = new StrategicPlayer("P" + i, gameState.numPlayers, gameState.ruleSet, bestTuple);
			
			for (int j = 0; j < numCardsPerPlayer; j++) {
				int cardToRemove =  (int) (Math.random() * otherHands.size());
				p.addCardToHand(otherHands.remove(cardToRemove));
			}
			
			players.add(p);
			
		}
	
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setLeftPlayer(players.get((i+1) % players.size()).getId());
		}
		
		return players;
		
	}
	
	
	
}
