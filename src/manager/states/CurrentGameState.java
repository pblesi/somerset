/**
 * 
 */
package manager.states;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import players.Player;
import game.Hand;
import game.RuleSet;
import game.Team;
import game.deck.Card;
import game.deck.Suit;
import game.phases.BidPhaseState;
import game.phases.CurrentPhase;
import game.phases.CurrentPose;
import game.phases.DiscardPhaseState;
import game.phases.TrickPhaseState;
import handlers.ConsoleGameHandler;

/**
 * @author Patrick Blesi
 *
 */
public class CurrentGameState extends AbstractAppState {

	protected static final String STATE_FILE_NAME = "current_game_state.yml";
	private static CurrentGameState currentGameState;
	
	public static CurrentGameState getCurrentGameState() {
		
		if (currentGameState != null) { return currentGameState; }
		
		String filePath = STATE_PATH + STATE_FILE_NAME;
		
		if (new File(filePath).exists()) {
			try {
				
				Yaml yaml = new Yaml();
				Charset charSet = Charset.defaultCharset();
				String savedState = AbstractAppState.readFile(filePath,	charSet);
				currentGameState = (CurrentGameState) yaml.load(savedState);	
			
			} catch (Exception e) {
				System.err.println(e.toString());
				currentGameState = new CurrentGameState(); // perhaps re think initializer
			}
		} else {
			currentGameState = new CurrentGameState(); // perhaps re think initializer
		}
		
		return currentGameState;
		
	}
	
	public static CurrentGameState getNewGameState(RuleSet ruleSet, List<Player> players) {
		currentGameState = new CurrentGameState(ruleSet, players);
		currentGameState.commitToDisk();
		return currentGameState;
	}
	
	public boolean currentGameExists;

	// Indicates position of current game
	public CurrentPhase currentPhase;
	public CurrentPose currentPose;
	
	// Global state
	public RuleSet ruleSet;
	public Map<String, Player> playerMap;
	public List<String> playerList; // keeps order of players
	public List<Team> teams;
	
	public String dealerId;
	
	// Deal State
	public Hand<Card> bidHand;
	
	// Bid State
	public BidPhaseState bidPhaseState;
	public int winningBid;
	public String bidWinningPlayerId;
	
	// Discard State
	public DiscardPhaseState discardPhaseState;
	
	// Trick State
	public TrickPhaseState trickPhaseState;
	public Suit trump;
	
	// Score State
	  // None
	
	protected CurrentGameState() {
		this.setCurrentGameExists(true);
		this.currentPhase = CurrentPhase.DEAL_PHASE;
		this.currentPose = CurrentPose.BID_UNINITIALIZED;
	}
	
	protected CurrentGameState(RuleSet ruleSet, List<Player> players) {
		
		this();
		this.ruleSet = ruleSet;
		this.setPlayers(players);
		
	}
	
	/**
	 * @return the currentGameExists
	 */
	public boolean currentGameExists() {
		return currentGameExists;
	}

	/**
	 * @param currentGameExists the currentGameExists to set
	 */
	public void setCurrentGameExists(boolean currentGameExists) {
		this.currentGameExists = currentGameExists;
	}
	
	/**
	 * @return the currentPhase
	 */
	public CurrentPhase getCurrentPhase() {
		return currentPhase;
	}

	/**
	 * @param currentPhase the currentPhase to set
	 */
	public void setCurrentPhase(CurrentPhase currentPhase) {
		this.currentPhase = currentPhase;
		this.commitToDisk();
	}

	/**
	 * @return the currentPose
	 */
	public CurrentPose getCurrentPose() {
		return currentPose;
	}

	/**
	 * @param currentPose the currentPose to set
	 */
	public void setCurrentPose(CurrentPose currentPose) {
		this.currentPose = currentPose;
		this.commitToDisk();
	}

	public BidPhaseState getBidPhaseState() {
		return this.bidPhaseState;
	}
	
	public void setBidPhaseState(BidPhaseState bidPhaseState) {
		this.bidPhaseState = bidPhaseState;
	}
	
	public DiscardPhaseState getDiscardPhaseState() {
		return this.discardPhaseState;
	}
	
	public void setDiscardPhaseState(DiscardPhaseState dps) {
		this.discardPhaseState = dps;
	}
	
	public Player getPlayer(String id) {
		return this.playerMap.get(id);
	}
	
	public int getNumPlayers() {
		return this.playerList.size();
	}
	
	/**
	 * @return the playerList
	 */
	public List<String> getPlayerIds() {
		return playerList;
	}

	public Collection<Player> getPlayers() {
		return this.playerMap.values();
	}
	
	/**
	 * @return the ruleSet
	 */
	public RuleSet getRuleSet() {
		return ruleSet;
	}

	/**
	 * @param ruleSet the ruleSet to set
	 */
	public void setRuleSet(RuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}
	
	public List<Team> getTeams() {
		return this.teams;
	}
	
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	public String getDealerId() {
		return dealerId;
	}
	
	public void updateDealer() {
		this.dealerId = getPlayer(dealerId).getPlayerToLeft();
	}
	
	/**
	 * @return the bidHand
	 */
	public Hand<Card> getBidHand() {
		return bidHand;
	}

	/**
	 * @param bidHand the bidHand to set
	 */
	public void setBidHand(Hand<Card> bidHand) {
		this.bidHand = bidHand;
	}

	/**
	 * @return the winningBid
	 */
	public int getWinningBid() {
		return winningBid;
	}

	/**
	 * @param winningBid the winningBid to set
	 */
	public void setWinningBid(int winningBid) {
		this.winningBid = winningBid;
	}

	/**
	 * @return the id of the player that won the bid
	 */
	public String getBidWinningPlayerId() {
		return bidWinningPlayerId;
	}

	/**
	 * @param bidWinningPlayerId the id of the player that won the bid
	 */
	public void setBidWinningPlayerId(String winningPlayerId) {
		this.bidWinningPlayerId = winningPlayerId;
	}

	public TrickPhaseState getTrickPhaseState() {
		return this.trickPhaseState;
	}
	
	public void setTrickPhaseState(TrickPhaseState trickPhaseState) {
		this.trickPhaseState = trickPhaseState;
	}
	
	/**
	 * @return the trump
	 */
	public Suit getTrump() {
		return trump;
	}

	/**
	 * @param trump the trump to set
	 */
	public void setTrump(Suit trump) {
		this.trump = trump;
	}
	
	public void setPlayers(List<Player> players) {
		this.playerMap = new HashMap<String, Player>();
		this.playerList = new LinkedList<String>();
		
		for (Player p : players) {
			playerMap.put(p.getId(), p);
			playerList.add(p.getId());
		}
		
		this.teams = Team.initializeTeams(players);
		this.dealerId = players.get(players.size()-1).getId();
	}
	
	/* (non-Javadoc)
	 * @see manager.states.AppState#getStateFileName()
	 */
	@Override
	public String getStateFileName() {
		return CurrentGameState.STATE_FILE_NAME;
	}
	
	public void printCurrentGameState() {
		
		ConsoleGameHandler cgh = new ConsoleGameHandler();

		cgh.handleScorePhaseCompleted(teams, null, false);
		cgh.handleSetDealer(this.getPlayer(this.getDealerId()));
		System.out.println();
		
		if (this.getCurrentPhase() == CurrentPhase.BID_PHASE) {
			cgh.handleDealPhaseCompleted(this.getBidHand());
			cgh.handlePlayerMadeBid(this, this.bidPhaseState.winningPlayerId, this.bidPhaseState.winningBid);
		}
		
		if (this.getCurrentPhase() == CurrentPhase.DISCARD_PHASE) {
			cgh.handleBidPhaseCompleted(this, this.getBidWinningPlayerId(), this.getWinningBid());
		}
		
		if (this.getCurrentPhase() == CurrentPhase.TRICK_PHASE) {
			cgh.handleBidPhaseCompleted(this, this.getBidWinningPlayerId(), this.getWinningBid());
			if (this.getTrump() != null) {
				cgh.handleTrumpSet(getTrump());
			}
			if (this.getTrickPhaseState() != null && 
					this.getTrickPhaseState().trick != null) {
				System.out.println("\nCurrent Trick: ");
				System.out.println(this.getTrickPhaseState().trick);
			}
		}		
	}
	
}
