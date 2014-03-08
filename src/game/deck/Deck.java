/**
 * 
 */
package game.deck;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Patrick Blesi
 *
 */
public class Deck implements Iterable<Card> {

	private final Deque<Card> cards;
	
	public Deck() {
		List<Card> cardList = getCards();
		cards = getShuffledDeck(cardList);
	}
	
	public Card drawCard() {
		return cards.pop();
	}
	
	public int numCards() {
		return cards.size();
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (Card c : cards) {
			sb.append(i + ". ");
			sb.append(c.toString());
			sb.append("\n");
			i++;
		}
		
		return sb.toString();
		
	}
	
	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}
	
	private static List<Card> getCards() {
		
		List<Card> cards = new LinkedList<Card>();
		
		for (Suit s : Suit.getAllSuits()) {
			cards.addAll(Card.getAllCardsInSuit(s));
		}
		
		return cards;
		
	}
	
	private static Deque<Card> getShuffledDeck(List<Card> cardList) {
		
		Deque<Card> newDeque = new LinkedList<Card>();
		
		while (cardList.size() > 0)
		{
			int cardToTake = (int) (Math.random() * cardList.size());
			newDeque.addLast(cardList.remove(cardToTake));	
		}
		
		return newDeque;
	}
	
	/**
	 * Gets a list of all cards in a deck that could be played given
	 * a suit that must followed and trump.  Cards which fall outside
	 * this range could possibly be played, but they could not possibly
	 * win the trick (with the exception of the 0/0) which may or may
	 * not be included in this set. If the 0/0 is played, then doubles
	 * could potentially win so they would likely be included, subject
	 * to rules and if the 0/0 is lead.
	 * 
	 * @param s The suit that must be followed
	 * @param trump The current trump
	 * @return A list of cards that could possibly 
	 */
	public static List<Card> getAllPossiblePlayableCards(Suit s, Suit trump) {
		
		List<Card> cardList = new LinkedList<Card>();
		Suit sSuit = new Suit("s");
		
		cardList.addAll(Card.getAllCardsInSuit(s));
		
		// Conditional check prevents adding same card twice
		if (!s.equals(sSuit)) {
			cardList.addAll(Card.getAllCardsInSuit(sSuit));
		}
		
		// Conditional check prevents adding same card twice
		if (!trump.equals(s) && !trump.equals(sSuit)) {
			cardList.addAll(Card.getAllCardsInSuit(trump));	
		}
		
		// 0/0 and corresponding doubles are ignored
		
		return cardList;
		
	}
	
}
