/**
 * 
 */
package players;

import java.util.ArrayList;
import java.util.List;

import game.RuleSet;
import util.PlayerInfo;

/**
 * 
 * Is in charge of creating a player object 
 * from a PlayerInfo object	 
 * 
 * @author Patrick Blesi
 *
 */
public final class PlayerFactory {

	private final RuleSet ruleSet;
	private final int numPlayers;
	
	public PlayerFactory(RuleSet ruleSet, int numPlayers) {
		this.ruleSet = ruleSet;
		this.numPlayers = numPlayers;
	}
	
	public Player getPlayer(PlayerInfo playerInfo) {
		
		if (playerInfo.getPlayerType() == PlayerType.HUMAN) {
			
			return new HumanPlayer(playerInfo.getName(), numPlayers, ruleSet);
		
		} else if (playerInfo.getPlayerType() == PlayerType.COMPUTER) {
			
			if (playerInfo.getPlayerDifficulty() == PlayerDifficulty.EASY) {
				return new RandomPlayer(playerInfo.getName(), numPlayers, ruleSet);
			} else if (playerInfo.getPlayerDifficulty() == PlayerDifficulty.HARD) {
				return new StrategicPlayer(playerInfo.getName(), numPlayers, ruleSet);
			}
			
		}
		
		return new StrategicPlayer(playerInfo.getName(), numPlayers, ruleSet);
		
	}
	
	public static List<Player> createPlayerList(List<PlayerInfo> playerInfos, RuleSet ruleSet) {
		
		List<Player> playerList = new ArrayList<Player>();
		
		PlayerFactory playerFactory = new PlayerFactory(ruleSet, playerInfos.size());
		
		for (PlayerInfo playerInfo : playerInfos) {
			playerList.add(playerFactory.getPlayer(playerInfo));
		}
		
		// must set left player of each player
		for (int i = 0; i < playerList.size(); i++) {
			playerList.get(i).setLeftPlayer(playerList.get((i+1) % playerList.size()).getId());
		}
		
		return playerList;
		
	}
	
	
}
