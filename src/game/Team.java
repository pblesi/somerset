/**
 * 
 */
package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manager.states.CurrentGameState;
import players.Player;

/**
 * @author Patrick Blesi
 *
 */
public class Team {

	public int score;
	public List<String> playerIds;
	
	protected Team() { }
	
	public Team(Map<String, Player> players, Player firstPlayerOnTeam) {
		
		playerIds = new ArrayList<String>();
		
		Player currentPlayer = firstPlayerOnTeam;
		
		// This will add all playerIds if there is an odd number
		do {
			playerIds.add(currentPlayer.getId());
			// Select every other player
			currentPlayer = players.get(currentPlayer.getPlayerToLeft());
			currentPlayer = players.get(currentPlayer.getPlayerToLeft()); 
		} while (currentPlayer != firstPlayerOnTeam);
		
		this.score = 0;
		
	}

	public int getScore() {
		return score;
	}
	
	public void addToScore(int points) {
		this.score += points;
	}
	
	public boolean containsPlayer(String playerId) {
		return playerIds.contains(playerId);
	}
	
	public int calculatePointsWonDuringTrickPhase(CurrentGameState cgs) {
		
		int numPointsWon = 0;
		
		for (String id : playerIds) {
			numPointsWon += cgs.getPlayer(id).getNumberOfPoints();
		}
		
		return numPointsWon;
		
	}
	
	private List<String> getPlayerNames() {
		
		CurrentGameState cgs = CurrentGameState.getCurrentGameState();
		
		List<String> names = new ArrayList<String>();
		
		for (String id : playerIds) {
			names.add(cgs.getPlayer(id).gameState.name);
		}
		
		return names;
	}
	
	public String getTeamName() {
		
		StringBuffer sb = new StringBuffer();
		
		List<String> names = getPlayerNames();
		for (int i = 0; i < names.size()-1; i++) {
			sb.append(names.get(i) + " & ");
		}
		sb.append(names.get(names.size()-1));
		
		return sb.toString();
		
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(getTeamName());
		
		sb.append("\n");
		
		sb.append(this.score);
		
		sb.append("\n");
		
		return sb.toString();
		
	}
	
	public static List<Team> initializeTeams(List<Player> players) {
		
		Map<String, Player> playerMap = new HashMap<String, Player>();
		
		for (Player p : players) {
			playerMap.put(p.getId(), p);
		}
		
		List<Team> teams = new ArrayList<Team>();
		teams.add(new Team(playerMap, players.get(0)));
		teams.add(new Team(playerMap, players.get(1)));
		return teams;
		
	}

	public static Team getPlayersTeam(List<Team> teams, String playerId) {
		
		for (Team t : teams) {
			if (t.containsPlayer(playerId)) return t;
		}
		
		return null; // Should never happen in practice
		
	}
	
	public static boolean areOnSameTeam(Player p1, Player p2) {

		CurrentGameState cgs = CurrentGameState.getCurrentGameState();
		
		Player currentPlayer = p1;
		
		do {
			if (currentPlayer.equals(p2)) { return true; }
			// Select every other player
			currentPlayer = cgs.getPlayer(currentPlayer.getPlayerToLeft());
			currentPlayer = cgs.getPlayer(currentPlayer.getPlayerToLeft());
		} while (currentPlayer != p1);
		
		return false;
		
	}
	
}
