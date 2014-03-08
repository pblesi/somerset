/**
 * 
 */
package handlers;

import java.util.List;

import manager.states.CurrentGameState;
import players.Player;
import game.deck.Card;
import game.deck.Suit;
import game.Hand;
import game.Team;
import game.Trick;

/**
 * @author Patrick Blesi
 *
 */
public class ConsoleGameHandler implements GameHandler {

	/**
	 * @param players
	 */
	public ConsoleGameHandler() { }

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleSetDealer(players.Player)
	 */
	@Override
	public void handleSetDealer(Player p) {
		System.out.println("Dealer: " + p.gameState.name);
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleCardDealtToPlayer(players.Player, game.Card)
	 */
	@Override
	public void handleCardDealtToPlayer(Player currentPlayer, Card c) {
		// Nothing necessary to do in console mode
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidCardDisplayed(game.Card)
	 */
	@Override
	public void handleBidCardDisplayed(Card c) {
		// Nothing necessary to do in console mode
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleDealPhaseCompleted(game.Hand)
	 */
	@Override
	public void handleDealPhaseCompleted(Hand<Card> bidHand) {
		System.out.println("Bid Cards:\n" + bidHand.toString() + "\n\n");
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerPassedOnBid(players.Player)
	 */
	@Override
	public void handlePlayerPassedOnBid(Player p) {
		System.out.println(p.gameState.name + " Passed.");
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerMadeBid(players.Player, int)
	 */
	@Override
	public void handlePlayerMadeBid(CurrentGameState cgs, String playerId, int bid) {
		System.out.println(cgs.getPlayer(playerId).gameState.name + " bid " + bid + ".");
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidPhaseCompleted(players.Player, int)
	 */
	@Override
	public void handleBidPhaseCompleted(CurrentGameState cgs, String winningPlayerId, int winningBid) {
		Player winningPlayer = cgs.getPlayer(winningPlayerId);
		System.out.println(winningPlayer.gameState.name + " won the bid with " + winningBid + " points.\n");
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleAddedBidCardsToHand(players.Player)
	 */
	@Override
	public void handleAddedBidCardsToHand(Player winningPlayer) {
		// Nothing necessary to do in console mode
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerDiscardedCard(players.Player, game.Card)
	 */
	@Override
	public void handlePlayerDiscardedCard(Player winningPlayer,
			Card discardedCard) {
		// Nothing necessary to do in console mode
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleDiscardPhaseCompleted(players.Player)
	 */
	@Override
	public void handleDiscardPhaseCompleted(Player winningPlayer) {
		System.out.println(winningPlayer.gameState.name + " discarded two cards.\n");
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrickLeadCardPlayed(game.Trick, players.Player, game.Card)
	 */
	@Override
	public void handleTrickLeadCardPlayed(CurrentGameState cgs) {
		// Nothing necessary to do in console mode
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrickCardPlayed(game.Trick, players.Player, game.Card)
	 */
	@Override
	public void handleTrickCardPlayed(Trick t, Player p, Card card) {
		// Nothing necessary to do in console mode
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrumpSet(game.Card.Suit)
	 */
	@Override
	public void handleTrumpSet(Suit trump) {
		System.out.println("Trump: " + trump);
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerWonTrick(game.Trick)
	 */
	@Override
	public void handlePlayerWonTrick(Trick t) {
		System.out.println(t);
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrickPhaseCompleted()
	 */
	@Override
	public void handleTrickPhaseCompleted() {
		// Nothing necessary to do in console mode
	}
	
	@Override 
	public void handleBidWinningTeamMadeBid(Player bidWinningPlayer, int bidAmount, int pointsWon) {
		System.out.println(bidWinningPlayer.getName() + " got " + pointsWon + 
					       " points and made his / her bid of " + bidAmount + "\n");
	}
	
	@Override 
	public void handleBidWinningTeamMissedBid(Player bidWinningPlayer, int bidAmount, int pointsWon) {
		System.out.println(bidWinningPlayer.getName() + " got " + pointsWon + 
					       " points and missed his / her bid of " + bidAmount + 
					       " by " + (bidAmount - pointsWon) + " points.\n");
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleScorePhaseCompleted(java.util.List, game.Team, boolean)
	 */
	@Override
	public void handleScorePhaseCompleted(List<Team> teams,
			Team bidWinningTeam, boolean bidWinningTeamMadeBid) {
		System.out.println("Updated Score: ");
		System.out.println(getScoreCard(teams));
	}
	
	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleGameWon(game.Team)
	 */
	@Override
	public void handleGameWon(Team winningTeam) {
		System.out.println(winningTeam.getTeamName() + " Win!");
	}

	private static String getScoreCard(List<Team> teams) {

		StringBuffer retVal = new StringBuffer();

		for (Team t : teams) {
			retVal.append(t.toString());
		}
		return retVal.toString();
	}
	
}
