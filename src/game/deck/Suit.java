/**
 * 
 */
package game.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * @author Patrick Blesi
 *
 * This is an immutable type
 * 
 */
public class Suit {

	public String suit;
	public int value; // the integer representation of the suit, or 1 for the s/s

	protected Suit() { }
	
	public Suit(String name) {
		this.suit = name.length() > 2 ? getSuitFromName(name) : name.toLowerCase();
		this.value = getValueFromSuit(this.suit);
	}

	public boolean equals(Object anObject) {
		if ( this == anObject ) return true;
		if ( !(anObject instanceof Suit) ) return false;
		Suit object = (Suit) anObject;
		return this.suit.toLowerCase().equals(object.suit.toLowerCase());
	}

	public int hashCode() {
		return this.suit.hashCode();
	}

	@Override
	public String toString() {
		return this.suit;
	}

	public boolean isTrumpOrS(Suit trump) {
		return this.equals(trump) || this.equals(new Suit("s"));
	}
	
	private String getSuitFromName(String name) {

		try {
			return name.split("/")[1].toLowerCase();
		} catch (PatternSyntaxException pse) {
			System.out.println("Error splitting card name for card: " + name);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error splitting card name for card: " + name + " Name is in improper format.");
			throw new ArrayIndexOutOfBoundsException();
		}
		return "";

	}

	private int getValueFromSuit(String suit) {

		try {
			return suit.equals("s") ? 1 : Integer.parseInt(suit);
		} catch (NumberFormatException nfe) {
			System.out.println("Error parsing card name for suit: " + suit);
			return 0;
		}

	}
	
	public static List<Suit> getAllSuits() {
		
		List<Suit> suits = new ArrayList<Suit>();
		
		suits.add(new Suit("s"));
		suits.add(new Suit("0"));
		suits.add(new Suit("2"));
		suits.add(new Suit("4"));
		suits.add(new Suit("6"));
		suits.add(new Suit("8"));
		suits.add(new Suit("10"));
		suits.add(new Suit("12"));
		
		return suits;
		
	}

}


