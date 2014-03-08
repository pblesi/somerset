/**
 * 
 */
package game.phases;

import manager.states.CurrentGameState;
import players.Player;
import game.Hand;
import game.deck.Card;
import game.deck.Deck;
import game.util.Constants;
import handlers.GameHandler;

/**
 * @author Patrick Blesi
 *
 * This phase takes a deck and a set of players
 * and distributes the cards out to the players.
 *
 */
public class DealPhase {
	
	private final CurrentGameState cgs;
	private final GameHandler gameHandler;
	
	private final Deck deck;     // input
	
	public DealPhase(CurrentGameState cgs, GameHandler gameHandler) {
		
		this.cgs = cgs;
		this.gameHandler = gameHandler;
		
		this.deck = new Deck();
		
	}
	
	/** 
	 * Deals out the cards to all players and 
	 * sets the bid hand. 
	 */
	public void executeDealPhase() {
		
		Player currentPlayer = cgs.getPlayer(cgs.getDealerId());
		Card tempCard;
		
		while (deck.numCards() > Constants.NUM_BID_CARDS) {
			currentPlayer = cgs.getPlayer(currentPlayer.getPlayerToLeft());
			tempCard = deck.drawCard();
			currentPlayer.addCardToHand(tempCard);
			gameHandler.handleCardDealtToPlayer(currentPlayer, tempCard);
		}
		
		Hand<Card> bidHand = new Hand<Card>();
		
		while (deck.numCards() > 0) {
			tempCard = deck.drawCard();
			bidHand.add(tempCard);
			gameHandler.handleBidCardDisplayed(tempCard);	
		}
		
		cgs.setBidHand(bidHand);
		gameHandler.handleDealPhaseCompleted(bidHand);
		
	}
	
}
