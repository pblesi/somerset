/**
 * 
 */
package manager;

import java.util.LinkedList;
import java.util.List;

import manager.states.CurrentGameState;
import players.Player;
import players.PlayerDifficulty;
import players.PlayerType;
import util.PlayerInfo;
import util.UserInput;
import game.RuleSet;

/**
 * This class allows people to select the names and types of 
 * players they will compete against and play with as well as
 * how many players will be playing and the rules of the game.
 * 
 * 
 * @author Patrick Blesi
 *
 */
public class OptionsConsoleMenu implements OptionsMenu {

	private RuleSet ruleSet;
	private List<PlayerInfo> playerInfos;
	
	private UserInput userInput;
	
	public OptionsConsoleMenu() {
		userInput = new UserInput();
		this.playerInfos = new LinkedList<PlayerInfo>();
	}

	public void getOptions() {
		
		this.ruleSet = getRuleSetFromConsole(RuleSet.KRAEMER);
		
		this.playerInfos = getPlayerInfosFromConsole(getNumPlayersFromConsole(4), CurrentGameState.getCurrentGameState());
		
	}
	
	public void getOptions(CurrentGameState defaultOptions) {
		
		RuleSet defaultRuleSet = defaultOptions.getRuleSet();
		this.ruleSet = getRuleSetFromConsole(defaultRuleSet);
		
		int defaultNumPlayers = defaultOptions.getNumPlayers();
		int numPlayersFromConsole = getNumPlayersFromConsole(defaultNumPlayers);
		
		this.playerInfos = getPlayerInfosFromConsole(numPlayersFromConsole, defaultOptions);
		
	}
	
	private int getNumPlayersFromConsole(int defaultVal) {
		
		int numPlayers = 0;
		
		ConsoleMenu.clearScreen();
		printNumPlayersSelectionMenu(defaultVal);
		Integer intResponse = userInput.getInteger(1, 2, true);
		
		if (intResponse.equals(Integer.MIN_VALUE)) {
			return defaultVal;
		}
		
		switch(intResponse) {
		case 1:
			numPlayers = 4;
			break;
		case 2:
			numPlayers = 6;
			break;
		}
		
		return numPlayers;
		
	}
	
	private RuleSet getRuleSetFromConsole(RuleSet defaultVal) {
		
		RuleSet ruleSet = RuleSet.KRAEMER;
		
		ConsoleMenu.clearScreen();
		printRuleSetSelectionMenu(defaultVal);
		Integer intResponse = userInput.getInteger(1, 2, true);
		
		if (intResponse.equals(Integer.MIN_VALUE)) {
			return defaultVal;
		}
		
		switch(intResponse) {
		case 1:
			ruleSet = RuleSet.KRAEMER;
			break;
		case 2:
			ruleSet = RuleSet.EMRY;
			break;
		}

		return ruleSet;
		
	}
	
	private List<PlayerInfo> getPlayerInfosFromConsole(int numPlayers, CurrentGameState defaultOptions) {
		
		List<PlayerInfo> playerInfos = new LinkedList<PlayerInfo>();
		
		List<String> defaultPlayerIds = defaultOptions.getPlayerIds();
		String defaultPlayerName;
		PlayerType defaultPlayerType;
		PlayerDifficulty defaultPlayerDifficulty;
		
		for (int i = 1; i <= numPlayers; i++) {

			if (defaultPlayerIds != null && i - 1 < defaultPlayerIds.size()) {
				Player defaultPlayer = defaultOptions.getPlayer(defaultPlayerIds.get(i - 1));
				defaultPlayerName = defaultPlayer.getName();
				defaultPlayerType = defaultPlayer.getPlayerType();
				defaultPlayerDifficulty = defaultPlayer.getPlayerDifficulty();
			} else {
				defaultPlayerName = new Integer(i).toString();
				defaultPlayerType = PlayerType.COMPUTER;
				defaultPlayerDifficulty = PlayerDifficulty.HARD;
			}
			
			String name = getPlayerNameFromConsole(i, defaultPlayerName);
			
			if (i == 1) { // First player is always human
				playerInfos.add(new PlayerInfo(name, PlayerType.HUMAN));
			} else {
				PlayerType playerType = getPlayerTypeFromConsole(defaultPlayerType);
				if (playerType == PlayerType.HUMAN) {
					playerInfos.add(new PlayerInfo(name, PlayerType.HUMAN));
				} else {
					if (defaultPlayerDifficulty.equals(PlayerDifficulty.NA)) {
						defaultPlayerDifficulty = PlayerDifficulty.HARD;
					}
					PlayerDifficulty playerDifficulty = getPlayerDifficultyFromConsole(defaultPlayerDifficulty);
					playerInfos.add(new PlayerInfo(name, playerType, playerDifficulty));
				}
				
			}
			
		}
		
		return playerInfos;
		
	}
	
	private String getPlayerNameFromConsole(int currentPlayer, String defaultVal) {
		ConsoleMenu.clearScreen();
		printPlayerNameSelectionMenu(currentPlayer, defaultVal);
		String result = userInput.getString(); 
		return result.equals("") ? defaultVal : result;
	}
	
	private PlayerType getPlayerTypeFromConsole(PlayerType defaultVal) {
		
		PlayerType playerType = PlayerType.HUMAN;
		
		ConsoleMenu.clearScreen();
		printPlayerTypeSelectionMenu(defaultVal);
		Integer intResponse = userInput.getInteger(1, 2, true); 
		
		if (intResponse.equals(Integer.MIN_VALUE)) {
			return defaultVal;
		}
		
		switch(intResponse) {
		case 1:
			playerType = PlayerType.HUMAN;
			break;
		case 2:
			playerType = PlayerType.COMPUTER;
			break;
		}
		
		return playerType;
		
	}
	
	private PlayerDifficulty getPlayerDifficultyFromConsole(PlayerDifficulty defaultVal) {
		
		PlayerDifficulty playerDifficulty = PlayerDifficulty.HARD;
		
		ConsoleMenu.clearScreen();
		printPlayerDifficultySelectionMenu(defaultVal);
		Integer intResponse = userInput.getInteger(1, 2, true);
		
		if (intResponse.equals(Integer.MIN_VALUE)) {
			return defaultVal;
		}
		
		switch(intResponse) {
		case 1:
			playerDifficulty = PlayerDifficulty.EASY;
			break;
		case 2:
			playerDifficulty = PlayerDifficulty.HARD;
			break;
		}
		
		return playerDifficulty;
		
	}
	
	private void printNumPlayersSelectionMenu(int defaultVal) {
		
		String menu = "Select the number of players for this game.\n";
		
		menu += "1. 4 Players\n";
		menu += "2. 6 Players\n";
		// Possibly include back option
		menu += "Please select a number[" + defaultVal + "]: ";
		
		System.out.println(menu);
		
	}
	
	private void printRuleSetSelectionMenu(RuleSet defaultVal) {
		
		String menu = "Select the rule set for this game.\n";
		
		menu += "1. Kraemer Rule Set\n";
		menu += "2. Emry Rule Set\n";
		// Possibly include back option
		menu += "Please select a number[" + defaultVal + "]: ";

		System.out.println(menu);
		
	}

	private void printPlayerNameSelectionMenu(int playerNumber, String defaultVal) {
		
		// Possibly include back option
		// Possibly print an is this correct? with option to change it.
		System.out.println("Please enter player " + playerNumber + "'s name [" + defaultVal + "]: ");
		
	}
	
	private void printPlayerTypeSelectionMenu(PlayerType defaultVal) {
		
		String menu = "Select the type of player.\n";
		
		menu += "1. Human Player\n";
		menu += "2. Computer Player\n";
		// Possibly include back option
		menu += "Please select a number [" + defaultVal + "]: ";

		System.out.println(menu);
		
	}
	
	private void printPlayerDifficultySelectionMenu(PlayerDifficulty defaultVal) {
		
		String menu = "Select the player's difficulty.\n";
		
		menu += "1. Easy\n";
		menu += "2. Hard\n";
		// Possibly include back option
		menu += "Please select a number [" + defaultVal + "]: ";

		System.out.println(menu);
		
	}
	
	/**
	 * @return the ruleSet
	 */
	public RuleSet getRuleSet() {
		return ruleSet;
	}

	/**
	 * @return the playerInfos
	 */
	public List<PlayerInfo> getPlayerInfos() {
		return playerInfos;
	}
	
}
