/**
 * 
 */
package players;

import java.util.List;

import game.Hand;
import game.Team;
import game.Trick;
import game.deck.Card;
import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 */
public interface InformedPlayer {

	/* Deal Phase */
	
	public void notifyDealerSet(Player dealer);
	
	public void notifyCardDealtToPlayer(Card c);
	
	public void notifyBidCardDisplayed(Card c);
	
	public void notifyDealPhaseCompleted(Hand<Card> bidHand);
	
	/* Bid Phase */
	
	public void notifyPlayerPassedOnBid(Player player);
	
	public void notifyPlayerMadeBid(String playerId, int bid);
	
	public void notifyBidPhaseCompleted(String winningPlayerId, int winningBid);
	
	/* Discard Phase */
	
	public void notifyAddedBidCardsToHand(Player winningPlayer);
	
	public void notifyPlayerDiscardedCard(Player winningPlayer);
	
	public void notifyPlayerDiscardedCard(Player winningPlayer, Card discardedCard);
	
	public void notifyDiscardPhaseCompleted(Player winningPlayer);
	
	/* Trick Phase */
	
	public void notifyTrickLeadCardPlayed(Trick trick, Player player, Card firstCard);
	
	public void notifyTrickCardPlayed(Trick trick, Player player, Card firstCard);
	
	public void notifyTrumpSet(Suit trump);
	
	public void notifyPlayerWonTrick(Trick trick);
	
	public void notifyTrickPhaseCompleted();
	
	/* Score Phase */
	
	public void notifyBidWinningTeamMadeBid(Player bidWinningPlayer, int bidAmount, int pointsWon);
	
	public void notifyBidWinningTeamMissedBid(Player bidWinningPlayer, int bidAmount, int pointsWon);
	
	public void notifyScorePhaseCompleted(List<Team> teams, 
			Team bidWinningTeam, boolean bidWinningTeamMadeBid);
	
	/* Other */
	
	public void notifyGameWon(Team winningTeam);
	
}
