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
import players.Player;

/**
 * @author Patrick Blesi
 *
 */
public class GameHandlerSet implements GameHandler {

	private final List<GameHandler> gameHandlers;
	
	/**
	 * 
	 */
	public GameHandlerSet(List<GameHandler> gameHandlers) {
		this.gameHandlers = gameHandlers;
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleSetDealer(players.Player)
	 */
	@Override
	public void handleSetDealer(Player dealer) {
		for (GameHandler gh : gameHandlers) {
			gh.handleSetDealer(dealer);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleCardDealtToPlayer(players.Player, game.deck.Card)
	 */
	@Override
	public void handleCardDealtToPlayer(Player currentPlayer, Card c) {
		for (GameHandler gh : gameHandlers) {
			gh.handleCardDealtToPlayer(currentPlayer, c);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidCardDisplayed(game.deck.Card)
	 */
	@Override
	public void handleBidCardDisplayed(Card c) {
		for (GameHandler gh : gameHandlers) {
			gh.handleBidCardDisplayed(c);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleDealPhaseCompleted(game.Hand)
	 */
	@Override
	public void handleDealPhaseCompleted(Hand<Card> bidHand) {
		for (GameHandler gh : gameHandlers) {
			gh.handleDealPhaseCompleted(bidHand);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerPassedOnBid(players.Player)
	 */
	@Override
	public void handlePlayerPassedOnBid(Player player) {
		for (GameHandler gh : gameHandlers) {
			gh.handlePlayerPassedOnBid(player);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerMadeBid(manager.states.CurrentGameState, java.lang.String, int)
	 */
	@Override
	public void handlePlayerMadeBid(CurrentGameState cgs, String playerId,
			int bid) {
		for (GameHandler gh : gameHandlers) {
			gh.handlePlayerMadeBid(cgs, playerId, bid);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidPhaseCompleted(manager.states.CurrentGameState, java.lang.String, int)
	 */
	@Override
	public void handleBidPhaseCompleted(CurrentGameState cgs,
			String winningPlayerId, int winningBid) {
		for (GameHandler gh : gameHandlers) {
			gh.handleBidPhaseCompleted(cgs, winningPlayerId, winningBid);;
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleAddedBidCardsToHand(players.Player)
	 */
	@Override
	public void handleAddedBidCardsToHand(Player winningPlayer) {
		for (GameHandler gh : gameHandlers) {
			gh.handleAddedBidCardsToHand(winningPlayer);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerDiscardedCard(players.Player, game.deck.Card)
	 */
	@Override
	public void handlePlayerDiscardedCard(Player winningPlayer,
			Card discardedCard) {
		for (GameHandler gh : gameHandlers) {
			gh.handlePlayerDiscardedCard(winningPlayer, discardedCard);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleDiscardPhaseCompleted(players.Player)
	 */
	@Override
	public void handleDiscardPhaseCompleted(Player winningPlayer) {
		for (GameHandler gh : gameHandlers) {
			gh.handleDiscardPhaseCompleted(winningPlayer);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrickLeadCardPlayed(manager.states.CurrentGameState)
	 */
	@Override
	public void handleTrickLeadCardPlayed(CurrentGameState cgs) {
		for (GameHandler gh : gameHandlers) {
			gh.handleTrickLeadCardPlayed(cgs);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrickCardPlayed(game.Trick, players.Player, game.deck.Card)
	 */
	@Override
	public void handleTrickCardPlayed(Trick trick, Player player, Card card) {
		for (GameHandler gh : gameHandlers) {
			gh.handleTrickCardPlayed(trick, player, card);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrumpSet(game.deck.Suit)
	 */
	@Override
	public void handleTrumpSet(Suit trump) {
		for (GameHandler gh : gameHandlers) {
			gh.handleTrumpSet(trump);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handlePlayerWonTrick(game.Trick)
	 */
	@Override
	public void handlePlayerWonTrick(Trick trick) {
		for (GameHandler gh : gameHandlers) {
			gh.handlePlayerWonTrick(trick);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleTrickPhaseCompleted()
	 */
	@Override
	public void handleTrickPhaseCompleted() {
		for (GameHandler gh : gameHandlers) {
			gh.handleTrickPhaseCompleted();
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidWinningTeamMadeBid(players.Player, int, int)
	 */
	@Override
	public void handleBidWinningTeamMadeBid(Player bidWinningPlayer,
			int bidAmount, int pointsWon) {
		for (GameHandler gh : gameHandlers) {
			gh.handleBidWinningTeamMadeBid(bidWinningPlayer, bidAmount, pointsWon);;
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidWinningTeamMissedBid(players.Player, int, int)
	 */
	@Override
	public void handleBidWinningTeamMissedBid(Player bidWinningPlayer,
			int bidAmount, int pointsWon) {
		for (GameHandler gh : gameHandlers) {
			gh.handleBidWinningTeamMissedBid(bidWinningPlayer, bidAmount, pointsWon);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleScorePhaseCompleted(java.util.List, game.Team, boolean)
	 */
	@Override
	public void handleScorePhaseCompleted(List<Team> teams,
			Team bidWinningTeam, boolean bidWinningTeamMadeBid) {
		for (GameHandler gh : gameHandlers) {
			gh.handleScorePhaseCompleted(teams, bidWinningTeam, bidWinningTeamMadeBid);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleGameWon(game.Team)
	 */
	@Override
	public void handleGameWon(Team winningTeam) {
		for (GameHandler gh : gameHandlers) {
			gh.handleGameWon(winningTeam);
		}
	}

}
