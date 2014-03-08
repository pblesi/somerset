/**
 * 
 */
package players.states;

import game.RuleSet;

import java.util.UUID;

import players.PlayerDifficulty;
import players.PlayerType;
import manager.states.CurrentGameState;

/**
 * This class contains a player's state that does not change during
 * the entirety of the game, but can change from game to game.
 * 
 * @author Patrick Blesi
 *
 */
public class GameState {

	public String id;
	
	// Name of the player, used as a unique id
	public String name;
	
	public PlayerType playerType;
	
	public PlayerDifficulty playerDifficulty;
	
	public int numPlayers;
	
	// So the player knows which rule set to follow
	public RuleSet ruleSet;
	
	public String leftPlayerId; // Setup at the beginning of the game,
	 							 // does not change throughout game
	
	public GameState() { }
	
	public GameState(String name, int numPlayers, RuleSet ruleSet) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.numPlayers = numPlayers;
		this.ruleSet = ruleSet;
		this.leftPlayerId = null;
	}
	
	public void setLeftPlayer(String leftPlayerId) {
		if (this.leftPlayerId == null) {
			this.leftPlayerId = leftPlayerId;
		}
	}
	
	public String getLeftPlayer() {
		return this.leftPlayerId;
	}
	
	@Override
	public String toString() {
		
		CurrentGameState cgs = CurrentGameState.getCurrentGameState();
		
		return "name: " + name + "\n" +
			   "num Players: " + numPlayers + "\n" +
			   "ruleSet: " + ruleSet + "\n" +
			   "leftPlayerId: " + cgs.getPlayer(leftPlayerId).getName() + "\n";
	}

}
