/**
 * 
 */
package players;

import java.util.List;

import game.Hand;
import game.RuleSet;
import game.Team;
import game.Trick;
import game.deck.Card;
import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 */
public abstract class AbstractInformedPlayer extends Player implements InformedPlayer {

	protected AbstractInformedPlayer() { }
	
	/**
	 * @param name
	 * @param numPlayers
	 * @param ruleSet
	 */
	public AbstractInformedPlayer(String name, int numPlayers, RuleSet ruleSet) {
		super(name, numPlayers, ruleSet);
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyDealerSet(players.Player)
	 */
	@Override
	public void notifyDealerSet(Player dealer) {
		this.roundState.setDealer(dealer.getId());
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyCardDealtToPlayer(game.deck.Card)
	 */
	@Override
	public void notifyCardDealtToPlayer(Card c) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyBidCardDisplayed(game.deck.Card)
	 */
	@Override
	public void notifyBidCardDisplayed(Card c) {
		this.roundState.addBidCard(c);
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyDealPhaseCompleted(game.Hand)
	 */
	@Override
	public void notifyDealPhaseCompleted(Hand<Card> bidHand) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyPlayerPassedOnBid(players.Player)
	 */
	@Override
	public void notifyPlayerPassedOnBid(Player player) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyPlayerMadeBid(players.Player, int)
	 */
	@Override
	public void notifyPlayerMadeBid(String playerId, int bid) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyBidPhaseCompleted(players.Player, int)
	 */
	@Override
	public void notifyBidPhaseCompleted(String winningPlayerId, int winningBid) {
		this.roundState.setBidWinnerId(winningPlayerId);
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyAddedBidCardsToHand(players.Player)
	 */
	@Override
	public void notifyAddedBidCardsToHand(Player winningPlayer) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyPlayerDiscardedCard(Player winningPlayer) {
		// TODO Auto-generated method stub
	}
	
	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyPlayerDiscardedCard(players.Player, game.deck.Card)
	 */
	@Override
	public void notifyPlayerDiscardedCard(Player winningPlayer,	Card discardedCard) {
		this.roundState.addCardToDiscardedCards(discardedCard);
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyDiscardPhaseCompleted(players.Player)
	 */
	@Override
	public void notifyDiscardPhaseCompleted(Player winningPlayer) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyTrickLeadCardPlayed(game.Trick, players.Player, game.deck.Card)
	 */
	@Override
	public void notifyTrickLeadCardPlayed(Trick trick, Player player,
			Card firstCard) {
		this.roundState.addCardToDiscardedCards(firstCard);		
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyTrickCardPlayed(game.Trick, players.Player, game.deck.Card)
	 */
	@Override
	public void notifyTrickCardPlayed(Trick trick, Player player, Card firstCard) {
		this.roundState.addCardToDiscardedCards(firstCard);
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyTrumpSet(game.deck.Suit)
	 */
	@Override
	public void notifyTrumpSet(Suit trump) {
		this.roundState.setTrump(trump);
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyPlayerWonTrick(game.Trick)
	 */
	@Override
	public void notifyPlayerWonTrick(Trick trick) {
		// TODO Auto-generated method stub
		for (Card c : trick.getCards()) {
			this.roundState.addCardToDiscardedCards(c);
		}
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyTrickPhaseCompleted()
	 */
	@Override
	public void notifyTrickPhaseCompleted() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyBidWinningTeamMadeBid(players.Player, int, int)
	 */
	@Override
	public void notifyBidWinningTeamMadeBid(Player bidWinningPlayer,
			int bidAmount, int pointsWon) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyBidWinningTeamMissedBid(players.Player, int, int)
	 */
	@Override
	public void notifyBidWinningTeamMissedBid(Player bidWinningPlayer,
			int bidAmount, int pointsWon) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyScorePhaseCompleted(java.util.List, game.Team, boolean)
	 */
	@Override
	public void notifyScorePhaseCompleted(List<Team> teams,
			Team bidWinningTeam, boolean bidWinningTeamMadeBid) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see players.InformedPlayer#notifyGameWon(game.Team)
	 */
	@Override
	public void notifyGameWon(Team winningTeam) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see players.Player#getBid(players.Player, int)
	 */
	@Override
	public int getBid(String winningPlayerId, int currentBid) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see players.Player#discardCard()
	 */
	@Override
	public Card discardCard() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see players.Player#playLeadCard()
	 */
	@Override
	public Card playLeadCard() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see players.Player#playCard(game.Trick, game.deck.Suit, game.deck.Suit)
	 */
	@Override
	public Card playCard(Trick trick, Suit leadSuit, Suit trump) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see players.Player#isHumanPlayer()
	 */
	@Override
	public boolean isHumanPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

}
