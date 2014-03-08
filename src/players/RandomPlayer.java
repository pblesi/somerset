/**
 * 
 */
package players;

import java.util.List;

import game.RuleSet;
import game.Trick;
import game.deck.Card;
import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 */
public class RandomPlayer extends Player {

	protected RandomPlayer() { }
	
	/**
	 * @param name
	 */
	public RandomPlayer(String name, int numPlayers, RuleSet ruleType) {
		super(name, numPlayers, ruleType);
		
		this.gameState.playerType = PlayerType.COMPUTER;
		this.gameState.playerDifficulty = PlayerDifficulty.EASY;
		
	}

	/* (non-Javadoc)
	 * @see players.Player#getBid()
	 */
	@Override
	public int getBid(String winningPlayerId, int currentBid) {
		return currentBid + ((int) (Math.random()*3-1));
	}

	/* (non-Javadoc)
	 * @see players.Player#discardCard()
	 * point cards cannot be removed
	 */
	@Override
	public Card discardCard() {
		List<Card> cardsToChooseFrom = this.getHand().getNonPointCards();
		int cardToRemove =  (int) (Math.random() * cardsToChooseFrom.size());
		Card discardCard = cardsToChooseFrom.get(cardToRemove);
		this.getHand().remove(discardCard);
		return discardCard;
	}

	/* (non-Javadoc)
	 * @see players.Player#playCard()
	 */
	@Override
	public Card playCard(Trick trick, Suit leadSuit, Suit trump) {
		List<Card> cardsToChooseFrom = this.getHand().getPlayableCards(leadSuit, trump);
		int cardToRemove =  (int) (Math.random() * cardsToChooseFrom.size());
		this.getHand().remove(cardsToChooseFrom.get(cardToRemove));
		return cardsToChooseFrom.get(cardToRemove);
	}

	/* (non-Javadoc)
	 * @see players.Player#playLeadCard()
	 */
	@Override
	public Card playLeadCard() {
		int cardToRemove =  (int) (Math.random() * this.getHand().size());
		return this.getHand().remove(cardToRemove);
	}

	/* (non-Javadoc)
	 * @see players.Player#isHumanPlayer()
	 */
	@Override
	public boolean isHumanPlayer() {
		return false;
	}

}
