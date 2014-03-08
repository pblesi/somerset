/**
 * 
 */
package players.states;

import game.Hand;
import game.deck.Card;
import game.deck.Suit;

/**
 * This class contains a player's
 * state that does not persist between rounds
 * such as if they are the dealer, their hand,
 * the number of points they've earned this 
 * round, etc.
 * 
 * @author Patrick Blesi
 *
 */
public class RoundState {

	// The name of the player who corresponds to this roundState
	public String playersName;
		
	public Hand<Card> hand; // The player's hand
	public int numPoints;    // This is the number of points the player 
							 // has won during the current trick phase.
							 // this number should be cleared at the end
							 // of each round.
	
	// indicates whether the player passed during bid phase
	public boolean passed;
	// indicates whether a user wants to continue to bid
	// this should be set to false at the end of the bid phase.
	public boolean alwaysPass; 
	
	// Something to have a memory of what cards are being bid on
	public Hand<Card> bidCards; 
	
	// Something to have a memory of who won the bid cards
	public String bidWinnerId;
	
	public Suit trump;
	
	public String dealerId;  // Not currently used
	
	// Something to have a memory of what cards have already been
	// played
	public Hand<Card> discardedCards;	
	
	protected RoundState() { }
	
	public RoundState(String playersName) {
		this.playersName = playersName;
		this.hand = new Hand<Card>();
		this.bidCards = new Hand<Card>();
		this.discardedCards = new Hand<Card>();
		this.numPoints = 0;
		this.passed = false;
		this.alwaysPass = false;
	}
	
	public String getDealerId() {
		return this.dealerId;
	}
	
	public Suit getTrump() {
		return this.trump;
	}
	
	public Hand<Card> getHand() {
		return this.hand;
	}
	
	public Hand<Card> getDiscardedCards() {
		return this.discardedCards;
	}
	
	public Hand<Card> getBidCards() {
		return this.bidCards;
	}
	
	public String getBidWinnerId() {
		return this.bidWinnerId;
	}
	
	/* Deal Phase */
	
	// Methods for remembering game state for higher deduction
	public void addBidCard(Card c) {
		bidCards.add(c);
	}
	
	/* Bid Phase */
	
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	
	public void setAlwaysPass(boolean passed) {
		this.alwaysPass = passed;
	}
	
	public boolean alwaysPass() {
		return this.alwaysPass;
	}
	
	public boolean passed() {
		return this.alwaysPass || this.passed;
	}
	
	// Methods for remembering game state for higher deduction
	
	public void setDealer(String dealerId) {
		this.dealerId = dealerId;
	}
	
	public void setBidWinnerId(String bidWinnerId) {
		this.bidWinnerId = bidWinnerId;
	}
	
	/* Discard Phase */
	
	public void addCardToHand(Card c) {
		hand.add(c);
	}

	public void addHandToHand(Hand<Card> hand) {
		this.hand.addHand(hand);
	}
	
	public void addCardToDiscardedCards(Card c) {
		this.discardedCards.add(c);
	}
	
	/* Trick Phase */
	
	public void setTrump(Suit trump) {
		this.trump = trump;
	}
	
	public boolean hasCards() {
		return this.hand.size() > 0;
	}
	
	public void addPoints(int points) {
		this.numPoints += points;
	}
	
	/* Score Phase */
	
	public int getNumberOfPoints() {
		return this.numPoints;
	}
	
	/* Other */
	
	@Override
	public String toString() {
		return this.hand.toString();
	}

}
