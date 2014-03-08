/**
 * 
 */
package game.deck;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * @author Patrick Blesi
 * 
 * This is an immutable type
 *
 */
public class CardNumber implements Comparable<CardNumber> {

	public String number;
	public int value;

	protected CardNumber() { }
	
	public CardNumber(String name) {
		this.number = name.length() > 2 ? getNumberFromName(name) : name;
		this.value = getValueFromNumber(this.number);
	}
	
	public CardNumber(int value) {
		this.value = value;
		this.number = new Integer(value).toString();
	}
	
	public boolean equals(Object anObject) {
		if ( this == anObject ) return true;
		if ( !(anObject instanceof CardNumber) ) return false;
		CardNumber object = (CardNumber) anObject;
		return this.number.toLowerCase().equals(object.number.toLowerCase());
	}
	
	public int hashCode() {
		return this.number.hashCode();
	}
	
	@Override
	public int compareTo(CardNumber cn) {
		return this.value - cn.value;
	}
	
	private String getNumberFromName(String name) {
		
		String nameValue = "0";
		try {
			nameValue = name.split("/")[0].toLowerCase();
		} catch (PatternSyntaxException pse) {
			System.out.println("Error splitting card name for card: " + name);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error splitting card name for card: " + name + " Name is in improper format.");
		}
		
		return nameValue;
		
	}
	
	private int getValueFromNumber(String number) {
		
		try {
			return number.equals("s") ? -1 : Integer.parseInt(number);
		} catch (NumberFormatException nfe) {
			System.out.println("Error parsing card name for card number: " + number);
			return 0;
		}
		
	}

	public static List<CardNumber> getAllCardNumbers(Suit s) {
		
		List<CardNumber> cardNumbers = new LinkedList<CardNumber>();
		
		if (s.suit.equals("s")) {
			cardNumbers.add(new CardNumber("s"));
		} else {
			for (int i = 0; i <= s.value; i++) {
				cardNumbers.add(new CardNumber(new Integer(i).toString()));
			}
		}
		
		return cardNumbers;
		
	}
	
}
