/**
 * 
 */
package util;

import players.PlayerDifficulty;
import players.PlayerType;

/**
 * @author Patrick Blesi
 *
 */
public class PlayerInfo {

	private final String name;
	private final PlayerType playerType;
	private final PlayerDifficulty playerDifficulty;
	
	public PlayerInfo(String name, PlayerType playerType) {
		this.name = name;
		this.playerType = playerType;
		this.playerDifficulty = PlayerDifficulty.EASY;
	}
	
	/**
	 * 
	 */
	public PlayerInfo(String name, PlayerType playerType, PlayerDifficulty playerDifficulty) {
		 this.name = name;
		 this.playerType = playerType;
		 this.playerDifficulty = playerDifficulty;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the playerType
	 */
	public PlayerType getPlayerType() {
		return playerType;
	}

	/**
	 * @return the playerDifficulty
	 */
	public PlayerDifficulty getPlayerDifficulty() {
		return playerDifficulty;
	}

}
