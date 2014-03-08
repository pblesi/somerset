/**
 * 
 */
package manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import manager.states.CurrentGameState;
import players.Player;
import players.RandomPlayer;
import players.StrategicPlayer;
import game.Game;
import game.RuleSet;
import handlers.ConsoleGameHandler;
import handlers.GameHandler;
import handlers.GameHandlerSet;
import handlers.PlayerNotifierGameHandler;
import handlers.StatisticsGameHandler;

/**
 * @author Patrick Blesi
 *
 * This class will be in charge of providing
 * all the configuration necessary to start the 
 * game.  
 * 
 * It will also handle the saving and resuming of 
 * game state.
 * 
 * It will be in charge of the following menu
 * 
 * New Game
 *  - Player type and name selection
 *
 *        3
 *     2     4
 *        1
 *        
 *     3   4   5   
 *     2   1   6
 *     
 *          Player 1
 *     Name        Patrick
 *     Human/NPC   Human   
 *     Difficulty  easy / medium /hard or 1 2 3 4 5 6 7 8 9 10
 * 
 * Continue
 *  - Instant resume of game state 
 * 
 * Stats
 *  - Games Played
 *  - Games Won
 *  - Win Percentage
 *  - Reset Button
 *  
 * Settings
 * - 4/6 Players
 * - Standard Rules
 * - Emory Rules
 * - Kraemer Rules
 * 
 * Current Game State 
 * - Display Trump
 * - Display Each Team's Score
 * - Display Current Round Score
 * - Display Dealer
 * - Display Bid Amount
 * - Display Bid Winner
 * - Return To Menu
 * 
 * - Settings have memory of last settings
 *
 */
public class ConsoleLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RuleSet ruleSet = RuleSet.KRAEMER;
		
		CurrentGameState cgs = CurrentGameState.getNewGameState(ruleSet, initializePlayers(ruleSet));
		
		List<GameHandler> gameHandlers = new LinkedList<GameHandler>();
		gameHandlers.add(new PlayerNotifierGameHandler(cgs.getPlayers()));
		gameHandlers.add(new StatisticsGameHandler());
		gameHandlers.add(new ConsoleGameHandler());
		
		Game game = new Game(cgs, new GameHandlerSet(gameHandlers));
		
		game.play();
		
	}
	
	private static List<Player> initializePlayers(RuleSet ruleSet) {
		
		Player p1 = new StrategicPlayer("Patrick", 4, ruleSet);
		Player p2 = new RandomPlayer("Krystal", 4, ruleSet);
		Player p3 = new RandomPlayer("Cindy", 4, ruleSet);
		Player p4 = new StrategicPlayer("Dave", 4, ruleSet);
		
		p1.setLeftPlayer(p2.getId());
		p2.setLeftPlayer(p3.getId());
		p3.setLeftPlayer(p4.getId());
		p4.setLeftPlayer(p1.getId());
		
		List<Player> playerList = new ArrayList<Player>();
		
		playerList.add(p1);
		playerList.add(p2);
		playerList.add(p3);
		playerList.add(p4);
		
		return playerList;
		
	}

}
