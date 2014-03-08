/**
 * 
 */
package players;

import manager.states.CurrentGameState;
import players.states.GameState;
import players.states.StrategicRoundState;
import game.Hand;
import game.RuleSet;
import game.Trick;
import game.deck.Card;
import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 */
public abstract class Player {
	
	public GameState gameState;
	
	public StrategicRoundState roundState; // holds state of current round
	
	protected Player() { }
	
	public Player(String name, int numPlayers, RuleSet ruleSet) {
		this.gameState = new GameState(name, numPlayers, ruleSet);
		this.roundState = new StrategicRoundState(name, this.gameState.id);
	}
	
	/* Game State Methods */
	
	public String getId() {
		return this.gameState.id;
	}
	
	public String getName() {
		return this.gameState.name;
	}
	
	public PlayerType getPlayerType() {
		return this.gameState.playerType;
	}
	
	public PlayerDifficulty getPlayerDifficulty() {
		return this.gameState.playerDifficulty;
	}
	
	public void setLeftPlayer(String playerId) {
		this.gameState.setLeftPlayer(playerId);
	}
	
	public String getPlayerToLeft() {
		return this.gameState.getLeftPlayer();
	}
	
	/* Round State Methods */
	
	protected Hand<Card> getHand() {
		return this.roundState.getHand();
	}
	
	/* Deal Phase */
	
	public void addCardToHand(Card card) {
		this.roundState.addCardToHand(card);
	}
	
	/* Bid Phase */
	
	public boolean passed() {
		return this.roundState.passed();
	}

	public boolean alwaysPass() {
		return this.roundState.alwaysPass();
	}
	
	public void setPassed(boolean passed) {
		this.roundState.setPassed(passed);
	}
	
	public void setAlwaysPass(boolean passed) {
		this.roundState.setAlwaysPass(passed);
	}

	/* Discard Phase */
	
	public void addHandToHand(Hand<Card> hand) {
		this.roundState.addHandToHand(hand);
	}
	
	/* Trick Phase */
	
	public boolean hasCards() {
		return this.roundState.hasCards();
	}
	
	public void addPoints(int points) {
		this.roundState.addPoints(points);
	}
	
	/* Score Phase */
	
	public int getNumberOfPoints() {
		return this.roundState.getNumberOfPoints();
	}
	
	/* Abstract Player Methods */
	
	public abstract int getBid(String winningPlayerId, int currentBid);
	public abstract Card discardCard(); /* used only during discard phase by bid winner */
	public abstract Card playLeadCard();
	public abstract Card playCard(Trick trick, Suit leadSuit, Suit trump);
	public abstract boolean isHumanPlayer();
	
	/* Other */
	
	/**
	 * Clears the round state for each player
	 * 
	 * @param players The players playing the round
	 * @param dealer The dealer for the current round
	 */
	public static void initializePlayersForRound(CurrentGameState cgs) {
		for (String playerId : cgs.getPlayerIds()) {
			Player p = cgs.getPlayer(playerId);
			p.roundState = new StrategicRoundState(p.getName(), p.getId());
		}
	}
	
	@Override
	public String toString() {
		
		String retString = gameState.name + "\n";
		
		if (this.isHumanPlayer()) {
			retString += this.roundState.getHand().toString() + "\n";
		}
		
		return retString;
		
	}

	/**
	 * 
	 * @param players all players to test
	 * @return true if every player in players has at least 1 card.
	 */
	public static boolean anyPlayerHasCards(CurrentGameState cgs) {
		
		for (String playerId : cgs.getPlayerIds()) {
			if (cgs.getPlayer(playerId).hasCards()) {
				return true;
			}
		}
		return false;
		
	}
	
}
