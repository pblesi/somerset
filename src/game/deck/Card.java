/**
 * 
 */
package game.deck;

import java.util.LinkedList;
import java.util.List;

import util.exceptions.UnkownCardTypeException;
import util.strategic.AnnotatedCard;

/**
 * @author Patrick Blesi
 *
 */
public class Card implements Comparable<Card> {

	// Change implementation to support serialization
	public String name;
	public int numberOfPoints;
	public Suit suit;
	public CardNumber number;
	
	protected Card() { }
	
	public Card(String name) {
		
		this.name = name;
		this.suit = new Suit(this.name);
		this.number = new CardNumber(this.name);
		this.numberOfPoints = numPointsFromSuitAndCardNumber(this.suit, this.number);
		
	}	
	
	public Card(Suit s, CardNumber cn) {
		
		this.name = cn.number + "/" + s.suit;
		this.suit = s;
		this.number = cn;
		this.numberOfPoints = numPointsFromSuitAndCardNumber(this.suit, this.number);
		
	}
	
	public boolean isTrumpOrS(Suit trump) {
		return suit.isTrumpOrS(trump);
	}
	
	public boolean isDouble() {
		return suit.suit.equals(number.number);
	}
	
	public boolean isOughtOught() {
		return this.suit.suit.equals("0");
	}
	
	public int suitValDiff() {
		return this.suit.value - this.number.value;
	}
	
	@Override
	public boolean equals(Object anObject) {
		if ( this == anObject) return true;
		if ( !(anObject instanceof Card) ) return false;
		Card object = (Card) anObject;
		return this.name.equals(object.name);
	}
	
	@Override 
	public int hashCode() {
		return this.name.hashCode();
	}
	
	@Override
	public int compareTo(Card c) {
		int suitComparison = this.suit.value - c.suit.value; 
		return suitComparison == 0 ? this.number.value - c.number.value : suitComparison;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	private static int numPointsFromSuitAndCardNumber(Suit s, CardNumber cn) {
		
		if (s.equals(new Suit("s")) && cn.equals(new CardNumber("s"))) {
			return 3;
		}
		
		if (s.value / 2 == cn.value) {
			if (cn.value >= 1 && cn.value <= 3) {
				return 1;
			}
			
			if (cn.value >= 4 && cn.value <= 6) {
				return 2;
			}	
		}
		
		return 0;
		
	}
	
	@SuppressWarnings("unchecked")
 	public static <U extends Card> U deepCopyCard(U c) {

		if (c instanceof AnnotatedCard) {
			return (U) new AnnotatedCard((AnnotatedCard) c);
		} else if (c instanceof Card) {
			return (U) new Card(c.suit, c.number);
		}
		throw new UnkownCardTypeException("Did you forget to specify a handler for this card type?");
		
	}
	
	public static List<Card> getAllCardsInSuit(Suit s) {
		
		List<Card> cardList = new LinkedList<Card>();
		
		if (s.equals(new Suit("s"))) {
			cardList.add(new Card("s/s"));
		} else {
			for (int i = 0; i <= s.value; i++) {
				cardList.add(new Card(s, new CardNumber(i)));
			}
		}
		
		return cardList;
	}
	
}
