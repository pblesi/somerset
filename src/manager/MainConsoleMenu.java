/**
 * 
 */
package manager;

import game.Game;
import game.RuleSet;
import handlers.ConsoleGameHandler;
import handlers.GameHandler;
import handlers.GameHandlerSet;
import handlers.PlayerNotifierGameHandler;
import handlers.StatisticsGameHandler;

import java.util.LinkedList;
import java.util.List;

import manager.states.CurrentGameState;
import manager.states.StatsState;
import players.Player;
import players.PlayerFactory;
import util.UserInput;

/**
 * @author Patrick Blesi
 *
 */
public class MainConsoleMenu extends ConsoleMenu {

	private CurrentGameState cgs;
	
	private UserInput userInput;
	private boolean keepPlaying;
	
	public MainConsoleMenu(CurrentGameState cgs) {
		
		this.cgs = cgs;
		this.userInput = new UserInput();
		this.keepPlaying = true;
		
	}

	public boolean getKeepPlaying() {
		return this.keepPlaying;
	}
	
	private void setKeepPlaying(boolean keepPlaying) {
		this.keepPlaying = keepPlaying;
	}
	
	public void handleMenuSelection() {
	
		boolean currentGameExists = this.cgs.currentGameExists();
		
		ConsoleMenu.clearScreen();
		printMenu(currentGameExists);
		
		if (currentGameExists) {
		
			Integer intResponse = userInput.getInteger(1, 5, false); 
			
			switch(intResponse) {
			case 1:
				startNewGame();
				break;
			case 2:
				continueGame();
				break;
			case 3:
				displayHowToPlay();
				break;
			case 4:
				displayGameStats();
				optionallyClearStats();
				break;
			case 5:
				displayExitText();
				setKeepPlaying(false);
				break;
			}
			
		} else {
			
			Integer intResponse = userInput.getInteger(1, 4, false); 
			
			switch(intResponse) {
			case 1:
				startNewGame();
				break;
			case 2:
				displayHowToPlay();
				break;
			case 3:
				displayGameStats();
				optionallyClearStats();
				break;
			case 4:
				displayExitText();
				setKeepPlaying(false);
				userInput.close();
				break;
			}
			
		}
		
	}
	
	private void printMenu(boolean currentGameExists) {
		
		String menu = "";
		
		menu += "1. Start New Game\n";
		
		if (currentGameExists) {
			menu += "2. Continue Game\n";
			menu += "3. How to Play\n";
			menu += "4. Game Statistics\n";
			menu += "5. Quit\n";
		} else {
			menu += "2. How to Play\n";
			menu += "3. Game Statistics\n";
			menu += "4. Quit\n";
		}
		
		menu += "Please select a number: ";
		
		System.out.println(menu);
		
	}
	
	private static void startNewGame() {
		
		OptionsConsoleMenu optionsConsoleMenu = new OptionsConsoleMenu();
		CurrentGameState defaultOpts = CurrentGameState.getCurrentGameState();
		if (defaultOpts.ruleSet != null && defaultOpts.playerList != null) { // Potential issue if player list not well formed
			optionsConsoleMenu.getOptions(defaultOpts);
		} else {
			optionsConsoleMenu.getOptions();
		}
				
		RuleSet ruleSet = optionsConsoleMenu.getRuleSet();
		List<Player> playerList = PlayerFactory.createPlayerList(optionsConsoleMenu.getPlayerInfos(), ruleSet);
		
		CurrentGameState cgs = CurrentGameState.getNewGameState(ruleSet, playerList);
		
		List<GameHandler> gameHandlers = new LinkedList<GameHandler>();
		gameHandlers.add(new PlayerNotifierGameHandler(playerList));
		gameHandlers.add(new StatisticsGameHandler());
		gameHandlers.add(new ConsoleGameHandler());
		
		Game g = new Game(cgs, new GameHandlerSet(gameHandlers));
		g.play();
		
	}
	
	private static void continueGame() {
		
		CurrentGameState cgs = CurrentGameState.getCurrentGameState();
		cgs.printCurrentGameState();
		
		List<GameHandler> gameHandlers = new LinkedList<GameHandler>();
		gameHandlers.add(new PlayerNotifierGameHandler(cgs.getPlayers()));
		gameHandlers.add(new StatisticsGameHandler());
		gameHandlers.add(new ConsoleGameHandler());
		
		Game g = new Game(cgs, new GameHandlerSet(gameHandlers));
		g.play();
		
	}

	private static void displayHowToPlay() {
		System.out.println("Full instructions for game play "
				        +  "can be found at: "
				        +  "http://www.somersetgame.com/double-somerset-rules.html");
	}
	
	private static void displayGameStats() {
		StatsState gameStats = StatsState.getStatsState();
		System.out.println(gameStats);
	}
	
	private void optionallyClearStats() {
		
		System.out.println("Would you like to clear these stats? (N/y) ");
		boolean shouldClearStats = userInput.getYesNo(); 
		
		if (shouldClearStats) {
			StatsState.getStatsState().clearStatistics();
		}
		
	}
	
	private static void displayExitText() {
		System.out.println("Good bye! Thanks for playing.");
	}
	
}
