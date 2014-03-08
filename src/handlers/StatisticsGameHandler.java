/**
 * 
 */
package handlers;

import game.Hand;
import game.Team;
import game.Trick;
import game.deck.Card;
import game.deck.Suit;

import java.util.List;

import manager.states.CurrentGameState;
import manager.states.StatsState;
import players.Player;

/**
 * @author Patrick Blesi
 * 
 * Game Handlers should be implemented as a set instead of as a 
 * hierarchy of classes
 * 
 */
public class StatisticsGameHandler implements GameHandler {

	/**
	 * 
	 */
	public StatisticsGameHandler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleSetDealer(players.Player)
	 */
	@Override
	public void handleSetDealer(Player dealer) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleCardDealtToPlayer(players.Player, game.deck.Card)
	 */
	@Override
	public void handleCardDealtToPlayer(Player currentPlayer, Card c) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidCardDisplayed(game.deck.Card)
	 */
	@Override
	public void handleBidCardDisplayed(Card c) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleDealPhaseCompleted(game.Hand)
	 */
	@Override
	public void handleDealPhaseCompleted(Hand<Card> bidHand) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerPassedOnBid(players.Player)
	 */
	@Override
	public void handlePlayerPassedOnBid(Player player) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerMadeBid(manager.states.CurrentGameState, java.lang.String, int)
	 */
	@Override
	public void handlePlayerMadeBid(CurrentGameState cgs, String playerId,
			int bid) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidPhaseCompleted(manager.states.CurrentGameState, java.lang.String, int)
	 */
	@Override
	public void handleBidPhaseCompleted(CurrentGameState cgs,
			String winningPlayerId, int winningBid) {

		StatsState ss = StatsState.getStatsState();
		ss.incrementTotalBids();
		
		if (winningPlayerId.equals(cgs.getPlayerIds().get(0))) {
			ss.incrementTotalBidsWon();
		} 
		
		ss.increaseTotalPointsBid(winningBid);
		
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleAddedBidCardsToHand(players.Player)
	 */
	@Override
	public void handleAddedBidCardsToHand(Player winningPlayer) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerDiscardedCard(players.Player, game.deck.Card)
	 */
	@Override
	public void handlePlayerDiscardedCard(Player winningPlayer,
			Card discardedCard) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleDiscardPhaseCompleted(players.Player)
	 */
	@Override
	public void handleDiscardPhaseCompleted(Player winningPlayer) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrickLeadCardPlayed(manager.states.CurrentGameState)
	 */
	@Override
	public void handleTrickLeadCardPlayed(CurrentGameState cgs) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrickCardPlayed(game.Trick, players.Player, game.deck.Card)
	 */
	@Override
	public void handleTrickCardPlayed(Trick trick, Player player, Card card) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrumpSet(game.deck.Suit)
	 */
	@Override
	public void handleTrumpSet(Suit trump) {
		
		StatsState ss = StatsState.getStatsState();
		ss.incrementTotalRoundsSuitMadeTrump(trump);
		
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerWonTrick(game.Trick)
	 */
	@Override
	public void handlePlayerWonTrick(Trick trick) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrickPhaseCompleted()
	 */
	@Override
	public void handleTrickPhaseCompleted() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidWinningTeamMadeBid(players.Player, int, int)
	 */
	@Override
	public void handleBidWinningTeamMadeBid(Player bidWinningPlayer,
			int bidAmount, int pointsWon) {

		StatsState ss = StatsState.getStatsState();
		CurrentGameState cgs = CurrentGameState.getCurrentGameState();
		
		if (bidWinningPlayer.getId().equals(cgs.getPlayerIds().get(0))) {
			ss.incrementTotalBidsMadeP1();
		} 
		
		ss.incrementTotalBidsMade();
		ss.increaseTotalPointsBidForMadeBids(bidAmount);
		
		Suit trump = cgs.getTrump();
		ss.incrementTotalRoundsSuitMadeTrumpAndBidMade(trump);
		
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidWinningTeamMissedBid(players.Player, int, int)
	 */
	@Override
	public void handleBidWinningTeamMissedBid(Player bidWinningPlayer,
			int bidAmount, int pointsWon) {

		StatsState ss = StatsState.getStatsState();
		ss.increaseTotalPointsBidForMissedBids(bidAmount);
		
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleScorePhaseCompleted(java.util.List, game.Team, boolean)
	 */
	@Override
	public void handleScorePhaseCompleted(List<Team> teams,
			Team bidWinningTeam, boolean bidWinningTeamMadeBid) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleGameWon(game.Team)
	 */
	@Override
	public void handleGameWon(Team winningTeam) {
		
		StatsState ss = StatsState.getStatsState();
		ss.incrementNumGamesPlayed();
		
		CurrentGameState cgs = CurrentGameState.getCurrentGameState();
		
		if (winningTeam.containsPlayer(cgs.getPlayerIds().get(0))) {
			ss.incrementNumGamesWon();
			ss.setCurrentWinningStreak(ss.getCurrentWinningStreak()+1);
		} else {
			ss.setCurrentWinningStreak(0);
		}
		
	}

}
