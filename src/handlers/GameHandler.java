/**
 * 
 */
package handlers;

import java.util.List;

import manager.states.CurrentGameState;
import players.Player;
import game.Hand;
import game.Team;
import game.Trick;
import game.deck.Card;
import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 */
public interface GameHandler {

	/**
	 * Triggered once a new dealer for the game is set.
	 * 
	 * @param dealerId The player id of the new dealer.
	 */
	public void handleSetDealer(Player dealer);

	/**
	 * Triggered once a card is dealt to a player's hand
	 * @param currentPlayer The player who receives the card.
	 * @param c The card given to the player.
	 */
	public void handleCardDealtToPlayer(Player currentPlayer, Card c);
	
	/**
	 * Triggered after cards are dealt and a bid card is revealed.
	 * 
	 * @param c The bid card being displayed
	 */
	public void handleBidCardDisplayed(Card c);
	
	/**
	 * Triggered once all cards are dealt to players and 
	 * both bid cards are revealed.
	 * 
	 * @param bidHand The set of cards to be bid on
	 */
	public void handleDealPhaseCompleted(Hand<Card> bidHand);
	
	/**
	 * Triggered when a player passes instead of bids
	 * 
	 * @param player The player that passes on the bid
	 */
	public void handlePlayerPassedOnBid(Player player);
	
	/**
	 * Triggered when a player makes a bid for the 
	 * bid hand
	 * 
	 * @param player The player that made the bid
	 * @param bid The amount the player bid
	 */
	public void handlePlayerMadeBid(CurrentGameState cgs, String playerId, int bid);
	
	/**
	 * Triggered when a player has won the bid
	 * 
	 * @param winningPlayer The player that won the bid
	 * @param winningBid The amount the player bid
	 */
	public void handleBidPhaseCompleted(CurrentGameState cgs, String winningPlayerId, int winningBid);
	
	/**
	 * Triggered when the bid cards have been added to 
	 * the winning player's hand
	 * 
	 * @param winningPlayer The player whose hand just received the
	 * bid hand.
	 */
	public void handleAddedBidCardsToHand(Player winningPlayer);
	
	/**
	 * Triggered when winningPlayer Discards his or her discardedCard
	 * 
	 * @param winningPlayer The player who is discarded
	 * @param discardedCard The card that is being discarded
	 */
	public void handlePlayerDiscardedCard(Player winningPlayer, Card discardedCard);
	
	/**
	 * Triggered when winningPlayer has discarded two cards
	 * 
	 * @param winningPlayer The player who has discarded two cards
	 */
	public void handleDiscardPhaseCompleted(Player winningPlayer);
	
	/**
	 * Triggered when player plays first card of the trick
	 * 
	 * @param trick The trick after the first card has been played
	 * @param player The player who played the first card
	 * @param firstCard The first card of the trick
	 */
	public void handleTrickLeadCardPlayed(CurrentGameState cgs);
	
	/**
	 * Triggered when player plays card of the trick
	 * 
	 * @param trick The trick after card was played
	 * @param player The player who played the card
	 * @param card The card that was played
	 */
	public void handleTrickCardPlayed(Trick trick, Player player, Card card);
	
	/**
	 * Triggered when trump is set for the round
	 * 
	 * @param trump The suit that is trump
	 */
	public void handleTrumpSet(Suit trump);
	
	/**
	 * Triggered once a trick has finished, a player has been
	 * determined and the points for the trick have been added 
	 * to the player
	 *  
	 * @param trick The trick that has just been won
	 */
	public void handlePlayerWonTrick(Trick trick);
	
	
	/**
	 * Triggered once the trick phase has completed
	 */
	public void handleTrickPhaseCompleted();
	
	/**
	 * Triggered once it is determined that the bid winning team has 
	 * made their bid.
	 * 
	 * @param bidWinningPlayer The player who won the bid
	 * @param bidAmount The amount the bidWinningPlayer bid
	 * @param pointsWon The amount of points the bidWinning Team earned during the trick phase
	 */
	public void handleBidWinningTeamMadeBid(Player bidWinningPlayer, int bidAmount, int pointsWon);
	
	/**
	 * 
	 * Triggered once it is determined that the bid winning team missed their bid.
	 * 
	 * @param bidWinningPlayer The player who won the bid
	 * @param bidAmount The amount the bidWinningPlayer bid
	 * @param pointsWon The amount of points the bidWinning Team earned during the trick phase
	 */
	public void handleBidWinningTeamMissedBid(Player bidWinningPlayer, int bidAmount, int pointsWon);
	
	/**
	 * Triggered once the score phase has completed
	 * 
	 * @param teams All the game's teams
	 * @param bidWinningTeam The team that won the bid
	 * @param bidWinningTeamMadeBid True if the bid 
	 * winning team made their bid
	 */
	public void handleScorePhaseCompleted(List<Team> teams, Team bidWinningTeam, 
										  boolean bidWinningTeamMadeBid);
		
	
	/**
	 * Triggered once team has won the game.
	 * 
	 * @param winningTeam The team that won the game.
	 */
	public void handleGameWon(Team winningTeam);
	
}
