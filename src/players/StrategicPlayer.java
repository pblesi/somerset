/**
 * 
 */
package players;

import util.Tuple;
import util.strategic.StrategicPlayerHelper;
import game.RuleSet;
import game.deck.Card;
import game.deck.Suit;
import game.Trick;

/**
 * Strategic Player employs a number of strategies
 * in order to optimize its game play.
 * 
 * Strategic player associates a ranking
 * with each card in its hand that changes 
 * as the game develops.
 * 
 * Strategic player keeps track of what cards
 * have already been played so that it can calculate
 * the associated value of its cards.
 * 
 * This player has a mental state which keeps track
 * of what cards still are in play (can be probabilistically
 * if discard cards are unknown), and constraints imposed
 * on each player's hand based on what they have played
 * during each trick.
 * 
 * 
 * 
 * @author Patrick Blesi
 *
 */
public class StrategicPlayer extends AbstractInformedPlayer {
	
	protected StrategicPlayer() { }
	
	/**
	 * @param name
	 * @param ruleType
	 */
	public StrategicPlayer(String name, int numPlayers, RuleSet ruleType) {
		super(name, numPlayers, ruleType);
		
		this.gameState.playerType = PlayerType.COMPUTER;
		this.gameState.playerDifficulty = PlayerDifficulty.HARD;
		
	}

	public StrategicPlayer(String name, int numPlayers, RuleSet ruleType, Tuple<Suit, Integer> bestTuple) {
		super(name, numPlayers, ruleType);
		this.roundState.setSuggestedTrumpAndBid(bestTuple);
	}
	
	/* (non-Javadoc)
	 * @see players.Player#getBid(players.Player, int)
	 */
	@Override
	public int getBid(String winningPlayerId, int currentBid) {
		
		int marginOfError = -2; // allows us to be wrong in our point estimate
		
		if (roundState.getSuggestedTrumpAndBid() == null) {
			roundState.setSuggestedTrumpAndBid(StrategicPlayerHelper.getIdealTrumpAndBid(gameState, roundState));
		}
		
		int bidDiff = roundState.getSuggestedTrumpAndBid().y + marginOfError - currentBid; 
		if (bidDiff > 0) {
			return (int) currentBid + 1 + ((int) (Math.random() * bidDiff));
		} else {
			return -1;
		}
		
	}

	/* (non-Javadoc)
	 * @see players.Player#discardCard()
	 */
	@Override
	public Card discardCard() {
		
		if (roundState.getSuggestedTrumpAndBid() == null) {
			roundState.setSuggestedTrumpAndBid(StrategicPlayerHelper.getIdealTrumpAndBid(gameState, roundState));
		}
		
		Suit suggestedTrump = roundState.getSuggestedTrumpAndBid().x;
		
		Card cardToDiscard = StrategicPlayerHelper.getBestDiscardCard(gameState, roundState, suggestedTrump);
		
		return this.getHand().removeCard(cardToDiscard);
		
	}

	/* (non-Javadoc)
	 * @see players.Player#playLeadCard()
	 */
	@Override
	public Card playLeadCard() {

		Card bestLeadCard;
		
		if (this.roundState.getTrump() == null) {
			
			if (roundState.getSuggestedTrumpAndBid() == null) {
				roundState.setSuggestedTrumpAndBid(StrategicPlayerHelper.getIdealTrumpAndBid(gameState, roundState));
			}
			
			Suit suggestedTrump = roundState.getSuggestedTrumpAndBid().x;
			
			bestLeadCard = StrategicPlayerHelper.getBestFirstCardFirstTrick(gameState, roundState, suggestedTrump);
					
		} else {
			bestLeadCard = StrategicPlayerHelper.getBestTrickLeadCard(gameState, roundState);
		}
		
		return this.getHand().removeCard(bestLeadCard);
		
	}

	/* (non-Javadoc)
	 * @see players.Player#playCard(game.Trick, game.Card.Suit, game.Card.Suit)
	 */
	@Override
	public Card playCard(Trick trick, Suit leadSuit, Suit trump) {
		Card bestPlayCard = StrategicPlayerHelper.getBestCardToPlay(gameState, roundState, trick);
		return this.getHand().removeCard(bestPlayCard);
	}

	/* (non-Javadoc)
	 * @see players.Player#isHumanPlayer()
	 */
	@Override
	public boolean isHumanPlayer() {
		return false;
	}
	
}
