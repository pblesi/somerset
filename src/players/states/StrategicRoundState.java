/**
 * 
 */
package players.states;

import game.Hand;
import game.deck.Card;
import game.deck.Deck;
import game.deck.Suit;
import util.Tuple;
import util.strategic.AnnotatedCard;
import util.strategic.AnnotatedHand;

/**
 * Keeps the state of the round for strategic purposes.
 * This object should be persisted until the end of the 
 * round, and then reinstantiated like RoundState
 * 
 * @author Patrick Blesi
 *
 */
public class StrategicRoundState extends RoundState {

	public String playersId;
	
	// Contains myHand + otherHands annotated to indicate their location
	public AnnotatedHand annotatedHand;
	
	public Tuple<Suit, Integer> suggestedTrumpAndBid;
	
	// Something to remember who played what cards in what situation
	// to learn what cards they can and can't play	
	
	protected StrategicRoundState() { }
	
	/**
	 * 
	 */
	public StrategicRoundState(String playersName, String playersId) {
		super(playersName);
		
		this.playersId = playersId;
		
		this.annotatedHand = new AnnotatedHand();
		
		// Assume all cards are in otherHands before they are in mine
		for (Card c : new Deck()) {
			annotatedHand.add(new AnnotatedCard("u_unknown", c));
		}
		
	}
	
	public AnnotatedHand getAnnotatedHand() {
		return AnnotatedHand.deepCopyAnnotatedHand(annotatedHand);
	}
	
	public Tuple<Suit, Integer> getSuggestedTrumpAndBid() {
		return suggestedTrumpAndBid;
	}
	
	public void setSuggestedTrumpAndBid(Tuple<Suit, Integer> suggestedTrumpAndBid) {
		this.suggestedTrumpAndBid = suggestedTrumpAndBid;
	}
	
	public Hand<Card> getMyHand() {
		Hand<Card> h = annotatedHand.getAllCardsInPlayersHand(playersId);
		return Hand.deepCopyHand(h);	
	}
	
	public Hand<Card> getOtherHands() {
		Hand<Card> h = annotatedHand.getAllCardsInOthersHands(playersId);
		return Hand.deepCopyHand(h);
	}
	
	@Override
	public void addCardToHand(Card c) {
		super.addCardToHand(c);
		AnnotatedCard ac = annotatedHand.getCard(new AnnotatedCard(c));
		ac.setLabel("p_" + playersId);
	}
	
	@Override
	public void addHandToHand(Hand<Card> hand) {
		super.addHandToHand(hand);
		annotatedHand.changeHandLabels(hand, "p_" + playersId);
	}
	
	@Override
	public void addBidCard(Card c) {
		super.addBidCard(c);
		AnnotatedCard ac = annotatedHand.getCard(new AnnotatedCard(c));
		ac.setLabel("b_bidHand");
	}
	
	@Override
	public void setBidWinnerId(String bidWinnerId) {
		super.setBidWinnerId(bidWinnerId);
		
		for (Card c : bidCards) {
			AnnotatedCard ac = annotatedHand.getCard(new AnnotatedCard(c));
			ac.setLabel("p_" + bidWinnerId);
		}
	}
	
	
	@Override
	public void addCardToDiscardedCards(Card c) {
		super.addCardToDiscardedCards(c);
		annotatedHand.remove(c);
	}
	
}
