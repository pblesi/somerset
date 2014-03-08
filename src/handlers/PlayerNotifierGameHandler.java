/**
 * 
 */
package handlers;

import game.deck.Card;
import game.deck.Suit;
import game.Hand;
import game.Team;
import game.Trick;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import manager.states.CurrentGameState;
import players.InformedPlayer;
import players.Player;

/**
 * This class takes a list of players and notifies each player when the game
 * state has changes so that they can update their state of the game
 * accordingly.
 * 
 * @author Patrick Blesi
 * 
 */
public class PlayerNotifierGameHandler implements GameHandler {

	private final Collection<InformedPlayer> players;

	/**
	 * 
	 */
	public PlayerNotifierGameHandler(Collection<Player> players) {

		Collection<InformedPlayer> informedPlayers = new ArrayList<InformedPlayer>();

		for (Player p : players) {
			if (p instanceof InformedPlayer) {
				informedPlayers.add((InformedPlayer) p);
			}
		}

		this.players = informedPlayers;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleSetDealer(players.Player)
	 */
	@Override
	public void handleSetDealer(Player dealer) {
		for (InformedPlayer player : players) {
			player.notifyDealerSet(dealer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleCardDealtToPlayer(players.Player,
	 * game.Card)
	 */
	@Override
	public void handleCardDealtToPlayer(Player currentPlayer, Card c) {
		if (currentPlayer instanceof InformedPlayer) {
			((InformedPlayer) currentPlayer).notifyCardDealtToPlayer(c);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleBidCardDisplayed(game.Card)
	 */
	@Override
	public void handleBidCardDisplayed(Card c) {
		for (InformedPlayer player : players) {
			player.notifyBidCardDisplayed(c);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleDealPhaseCompleted(game.Hand)
	 */
	@Override
	public void handleDealPhaseCompleted(Hand<Card> bidHand) {
		for (InformedPlayer player : players) {
			player.notifyDealPhaseCompleted(bidHand);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handlePlayerPassedOnBid(players.Player)
	 */
	@Override
	public void handlePlayerPassedOnBid(Player p) {
		for (InformedPlayer player : players) {
			player.notifyPlayerPassedOnBid(p);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handlePlayerMadeBid(players.Player, int)
	 */
	@Override
	public void handlePlayerMadeBid(CurrentGameState cgs, String playerId, int bid) {
		for (InformedPlayer player : players) {
			player.notifyPlayerMadeBid(playerId, bid);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleBidPhaseCompleted(players.Player, int)
	 */
	@Override
	public void handleBidPhaseCompleted(CurrentGameState cgs, String winningPlayerId, int winningBid) {
		for (InformedPlayer player : players) {
			player.notifyBidPhaseCompleted(winningPlayerId, winningBid);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleAddedBidCardsToHand(players.Player)
	 */
	@Override
	public void handleAddedBidCardsToHand(Player winningPlayer) {
		for (InformedPlayer player : players) {
			player.notifyAddedBidCardsToHand(winningPlayer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handlePlayerDiscardedCard(players.Player,
	 * game.Card)
	 */
	@Override
	public void handlePlayerDiscardedCard(Player winningPlayer,
			Card discardedCard) {
		if (winningPlayer instanceof InformedPlayer) {
			((InformedPlayer) winningPlayer).notifyPlayerDiscardedCard(
					winningPlayer, discardedCard);
		}
		for (InformedPlayer p : players) {
			if (p != winningPlayer)
				p.notifyPlayerDiscardedCard(winningPlayer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleDiscardPhaseCompleted(players.Player)
	 */
	@Override
	public void handleDiscardPhaseCompleted(Player winningPlayer) {
		for (InformedPlayer player : players) {
			player.notifyDiscardPhaseCompleted(winningPlayer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleTrickLeadCardPlayed(game.Trick,
	 * players.Player, game.Card)
	 */
	@Override
	public void handleTrickLeadCardPlayed(CurrentGameState cgs) {
		Trick t = cgs.getTrickPhaseState().getTrick();
		Player p = cgs.getPlayer(cgs.getTrickPhaseState().getCurrentLeadId());
		Card firstCard = cgs.getTrickPhaseState().getFirstCard();
		for (InformedPlayer player : players) {
			player.notifyTrickLeadCardPlayed(t, p, firstCard);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleTrickCardPlayed(game.Trick,
	 * players.Player, game.Card)
	 */
	@Override
	public void handleTrickCardPlayed(Trick t, Player p, Card card) {
		for (InformedPlayer player : players) {
			player.notifyTrickCardPlayed(t, p, card);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleTrumpSet(game.Card.Suit)
	 */
	@Override
	public void handleTrumpSet(Suit trump) {
		for (InformedPlayer player : players) {
			player.notifyTrumpSet(trump);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handlePlayerWonTrick(game.Trick)
	 */
	@Override
	public void handlePlayerWonTrick(Trick t) {
		for (InformedPlayer player : players) {
			player.notifyPlayerWonTrick(t);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleTrickPhaseCompleted()
	 */
	@Override
	public void handleTrickPhaseCompleted() {
		for (InformedPlayer player : players) {
			player.notifyTrickPhaseCompleted();
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidWinningTeamMadeBid(players.Player, int, int)
	 */
	@Override
	public void handleBidWinningTeamMadeBid(Player bidWinningPlayer,
			int bidAmount, int pointsWon) {
		for (InformedPlayer player : players) {
			player.notifyBidWinningTeamMadeBid(bidWinningPlayer, bidAmount, pointsWon);
		}
	}

	/* (non-Javadoc)
	 * @see handlers.GameHandler#handleBidWinningTeamMissedBid(players.Player, int, int)
	 */
	@Override
	public void handleBidWinningTeamMissedBid(Player bidWinningPlayer,
			int bidAmount, int pointsWon) {
		for (InformedPlayer player : players) {
			player.notifyBidWinningTeamMissedBid(bidWinningPlayer, bidAmount, pointsWon);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleScorePhaseCompleted(java.util.List,
	 * game.Team, boolean)
	 */
	@Override
	public void handleScorePhaseCompleted(List<Team> teams,
			Team bidWinningTeam, boolean bidWinningTeamMadeBid) {
		for (InformedPlayer player : players) {
			player.notifyScorePhaseCompleted(teams, bidWinningTeam,
					bidWinningTeamMadeBid);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.GameHandler#handleGameWon(game.Team)
	 */
	@Override
	public void handleGameWon(Team winningTeam) {
		for (InformedPlayer player : players) {
			player.notifyGameWon(winningTeam);
		}
	}



}
