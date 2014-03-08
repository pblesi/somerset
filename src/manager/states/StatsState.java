/**
 * 
 */
package manager.states;

import java.io.File;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 */
public class StatsState extends AbstractAppState {

	protected static final String STATE_FILE_NAME = "stats_state.yml";
	private static StatsState statsState;
	
	public static StatsState getStatsState() {
		
		if (statsState != null) { return statsState; }
		
		String filePath = STATE_PATH + STATE_FILE_NAME;
		
		if (new File(filePath).exists()) {
			try {
		
				Yaml yaml = new Yaml();
				Charset charSet = Charset.defaultCharset();
				String savedState = AbstractAppState.readFile(filePath, charSet);
				statsState = (StatsState) yaml.load(savedState);
				
			} catch (Exception e) {
				System.err.println(e.toString());
				statsState = new StatsState();
			}
		} else {
			statsState = new StatsState();
		}
		
		return statsState;
		
	}
	
	// Player 1 stats
	
	public int numGamesWon;
	
	public int longestWinningStreak;
	public int currentWinningStreak;
	
	public int totalBidsWonP1;
	public int totalBidsMadeP1;
	
	// Whole game stats
	
	public int numGamesPlayed;
	public int totalBids;
	public int totalBidsMade;
	
	public int totalPointsBid;
	public int totalPointsBidForMadeBids;
	public int totalPointsBidForMissedBids;
	
	public Map<String, SuitStats> suitStats;
	
	/**
	 * 
	 */
	protected StatsState() {
		
		suitStats = initializeSuitStats();
		
	}

	private Map<String, SuitStats> initializeSuitStats() {
		
		List<Suit> allSuits = Suit.getAllSuits();
		Map<String, SuitStats> suitStats = new HashMap<String, SuitStats>();
		
		for (Suit s : allSuits) {
			suitStats.put(s.toString(), new SuitStats(s.toString()));
		}
		return suitStats;
		
	}
	
	/**
	 * @return the numGamesWon
	 */
	public int getNumGamesWon() {
		return numGamesWon;
	}

	/**
	 * @param numGamesWon the numGamesWon to set
	 */
	public void incrementNumGamesWon() {
		this.numGamesWon++;
		this.commitToDisk();
	}

	/**
	 * @return the numGamesPlayed
	 */
	public int getNumGamesPlayed() {
		return numGamesPlayed;
	}

	/**
	 * @param numGamesPlayed the numGamesPlayed to set
	 */
	public void incrementNumGamesPlayed() {
		this.numGamesPlayed++;
		this.commitToDisk();
	}

	public double getWinPercentage() {
		return this.numGamesPlayed == 0 ? 0 : this.numGamesWon * 1.0 / numGamesPlayed;
	}
	
	/**
	 * @return the averageBidsWonPerGame
	 */
	public double getAverageBidsWonPerGame() {
		return this.numGamesPlayed == 0 ? 0 : this.totalBidsWonP1 * 1.0 / this.numGamesPlayed;
	}

	/**
	 * @param averageBidsWonPerGame the averageBidsWonPerGame to set
	 */
	public void incrementTotalBidsWon() {
		this.totalBidsWonP1++;
		this.commitToDisk();
	}

	/**
	 * @return the percentBidsWonPerGame
	 */
	public double getPercentBidsWon() {
		return this.totalBids == 0 ? 0 : this.totalBidsWonP1 * 1.0 / this.totalBids;
	}

	/**
	 * @param percentBidsWonPerGame the percentBidsWonPerGame to set
	 */
	public void incrementTotalBids() {
		this.totalBids++;
		this.commitToDisk(); // Possible error in stats if you start and restart
		// Dame in same spot ovar and over
	}

	/**
	 * @return the madeBidPercentage
	 */
	public double getMadeBidPercentage() {
		return this.totalBidsWonP1 == 0 ? 0 : this.totalBidsMadeP1 * 1.0 / this.totalBidsWonP1;
	}

	/**
	 * @param madeBidPercentage the madeBidPercentage to set
	 */
	public void incrementTotalBidsMadeP1() {
		this.totalBidsMadeP1++;
		this.commitToDisk();
	}

	/**
	 * @return the currentWinningStreak
	 */
	public int getCurrentWinningStreak() {
		return currentWinningStreak;
	}

	/**
	 * @param currentWinningStreak the currentWinningStreak to set
	 */
	public void setCurrentWinningStreak(int currentWinningStreak) {
		this.currentWinningStreak = currentWinningStreak;
		if (this.currentWinningStreak > this.longestWinningStreak) {
			this.longestWinningStreak = this.currentWinningStreak;
		}
		this.commitToDisk();
	}

	/**
	 * @return the longestWinningStreak
	 */
	public int getLongestWinningStreak() {
		return longestWinningStreak;
	}
	
	/**
	 * @return the averagePointsPerBid
	 */
	public double getAveragePointsPerBid() {
		return this.totalBids == 0 ? 0 : this.totalPointsBid * 1.0 / this.totalBids;
	}

	/**
	 * @param averagePointsPerBid the averagePointsPerBid to set
	 */
	public void increaseTotalPointsBid(int pointsBid) {
		this.totalPointsBid += pointsBid;
		this.commitToDisk();
	}

	/**
	 * @return the averagePointsPerBidWon
	 */
	public double getAveragePointsPerBidMade() {
		return this.totalBidsMade == 0 ? 0 : this.totalPointsBidForMadeBids / this.totalBidsMade;
	}

	/**
	 * @param madeBidPercentage the madeBidPercentage to set
	 */
	public void incrementTotalBidsMade() {
		this.totalBidsMade++;
		this.commitToDisk();
	}
	
	/**
	 * @param averagePointsPerBidWon the averagePointsPerBidWon to set
	 */
	public void increaseTotalPointsBidForMadeBids(int pointsBidForMadeBid) {
		this.totalPointsBidForMadeBids += pointsBidForMadeBid;
		this.commitToDisk();
	}

	/**
	 * @return the averagePointsPerBidLost
	 */
	public double getAveragePointsPerBidMissed() {
		// Potentially return some sort of N/A if no bids missed
		int totalBidsMissed = (this.totalBids - this.totalBidsMade);
		return totalBidsMissed == 0 ? 0 : this.totalPointsBidForMissedBids * 1.0 / totalBidsMissed;
	}

	/**
	 * @param averagePointsPerBidLost the averagePointsPerBidLost to set
	 */
	public void increaseTotalPointsBidForMissedBids(int pointsBidForMissedBid) {
		this.totalPointsBidForMissedBids += pointsBidForMissedBid;
		this.commitToDisk();
	}
	
	public double getRateSuitMadeTrump(Suit s) {
		return this.totalBids == 0 ? 0 : this.suitStats.get(s.toString()).getTotalRoundsSuitMadeTrump() * 1.0 /
			this.totalBids;
	}
	
	public void incrementTotalRoundsSuitMadeTrump(Suit s) {
		SuitStats suitStats = this.suitStats.get(s.toString());
		int totalRoundsSuitMadeTrump = suitStats.getTotalRoundsSuitMadeTrump() + 1;
		suitStats.setTotalRoundsSuitMadeTrump(totalRoundsSuitMadeTrump);
		this.commitToDisk();
	}
	
	public double getRateSuitMadeBidWhenTrump(Suit s) {
		int denom = this.suitStats.get(s.toString()).getTotalRoundsSuitMadeTrump();
		return denom == 0 ? 0 : this.suitStats.get(s.toString()).getTotalRoundsSuitAsTrumpMadeBid() * 1.0 / denom;
	}
	
	public void incrementTotalRoundsSuitMadeTrumpAndBidMade(Suit s) {
		SuitStats suitStats = this.suitStats.get(s.toString());
		int roundsSuitMadeTrumpAndBidMade = suitStats.getTotalRoundsSuitAsTrumpMadeBid() + 1;
		suitStats.setTotalRoundsSuitAsTrumpMadeBid(roundsSuitMadeTrumpAndBidMade);
		this.commitToDisk();
	}
	
	
	public void clearStatistics() {
		
		// Player 1 stats
		
		this.numGamesWon = 0;
		
		this.longestWinningStreak = 0;
		this.currentWinningStreak = 0;
		
		this.totalBidsWonP1 = 0;
		this.totalBidsMadeP1 = 0;
		
		// Whole game stats
		
		this.numGamesPlayed = 0;
		this.totalBids = 0;
		
		this.totalPointsBid = 0;
		this.totalPointsBidForMadeBids = 0;
		this.totalPointsBidForMissedBids = 0;
		
		this.suitStats = this.initializeSuitStats();
		
		this.commitToDisk();
	}
	
	@Override
	public String toString() {
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.format(0.912385);
		
		String rslt = "";
		
		rslt += "Player 1 Stats\n";
		rslt += "\n";
		rslt += "Number of Games Won: " + this.getNumGamesWon() + "\n";
		rslt += "Number of Games Played: " + this.getNumGamesPlayed() + "\n";
		rslt += "Win Percentage: " + Math.round(this.getWinPercentage()*100) + "%\n";
		rslt += "\n"; 
		rslt += "Current Winning Streak: " + this.getCurrentWinningStreak() + "\n";
		rslt += "Longest Winning Steak: " + this.getLongestWinningStreak() + "\n";
		rslt += "\n";
		rslt += "Average Bids Won per Game: " + this.getAverageBidsWonPerGame() + "\n";
		rslt += "Percent Bids Won: " +  Math.round(this.getPercentBidsWon()*100) + "%\n";
		rslt += "Percent of Time Made Bid: " +  Math.round(this.getMadeBidPercentage()*100) + "%\n";
		rslt += "\n";
		rslt += "Whole Game Stats\n";
		rslt += "\n";
		rslt += "Average Points per Bid: " + df.format(this.getAveragePointsPerBid()) + "\n";
		rslt += "Average Points Bid for Made Bid: " + df.format(this.getAveragePointsPerBidMade()) + "\n";
		rslt += "Average Points Bid for Missed Bid: " + df.format(this.getAveragePointsPerBidMissed()) + "\n";
		rslt += "\n";
		
		for (Suit s : Suit.getAllSuits()) {
			rslt += "Suit: " + s + "\n";
			rslt += "\tRate Suit Made Trump: " + Math.round(this.getRateSuitMadeTrump(s)*100) + "%\n";
			rslt += "\tRate Suit Made Bid When Trump: " + Math.round(this.getRateSuitMadeBidWhenTrump(s)*100) + "%\n";
		}
		
		return rslt;
	}
	
	/* (non-Javadoc)
	 * @see manager.states.AppState#getStateFileName()
	 */
	@Override
	public String getStateFileName() {
		return StatsState.STATE_FILE_NAME;
	}
	
}
