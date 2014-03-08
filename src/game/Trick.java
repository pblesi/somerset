/**
 * 
 */
package game;

import game.deck.Card;
import game.deck.Suit;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import manager.states.CurrentGameState;
import players.Player;
import util.comparators.trickcomparators.LeadOughtOughtTComparator;
import util.comparators.trickcomparators.OughtOughtTComparator;
import util.comparators.trickcomparators.StandardTComparator;
import util.comparators.trickcomparators.TrickComparator;

/**
 * @author Patrick Blesi
 *
 */
public class Trick {
	
	public Suit trump;
	
	// Map from Card to PlayerId who's card it is
	public Map<Card, String> cards;
	public Card leadCard;

	//private TrickComparator trickComparator;

	public boolean winnerCalculated; // used for printing purposes
	
	public String winner;
	public int numPoints;
	
	protected Trick() { }
	
	public Trick(Suit trump) {
		this.trump = trump;
		cards = new LinkedHashMap<Card, String>();
	}
	
	public Suit getLeadSuit() {
		return this.leadCard.suit;
	}
	
	public String getWinner() {
		return this.winner;
	}
	
	public int getNumPoints() {
		return this.numPoints;
	}
	
	public Set<Card> getCards() {
		return cards.keySet();
	}
	
	/**
	 * @return Cards ordered by comparator from least to greatest
	 */
	public Card[] getOrderedCardList() {
		
		TrickComparator tc = null;
		if (leadCard.isOughtOught()) {
			tc = new LeadOughtOughtTComparator(trump, leadCard.suit);
		} else if (cards.keySet().contains(new Card("0/0"))) {
			tc = new OughtOughtTComparator(trump, leadCard.suit);
		} else {
			tc = new StandardTComparator(trump, leadCard.suit);
		}
		
		Card[] orderedCardList = cards.keySet().toArray(new Card[cards.size()]);
		Arrays.sort(orderedCardList, tc);
		
		return orderedCardList;
		
	}
	
	public void add(Card c, String playerId) {
		if (cards.size() == 0) {
			this.leadCard = c;
		}
		cards.put(c, playerId);
	}
	
	public boolean oughtOughtLead() {
		return this.leadCard.isOughtOught();
	}
	
	public boolean oughtOughtPlayed() {
		return this.cards.keySet().contains(new Card("0/0"));
	}
	
	/**
	 * 
	 * @param numPlayers The number of players in the game
	 * @return true if all players have added a card to the trick
	 * except for 1 
	 */
	public boolean amLastPlayerToPlay(int numPlayers) {
		return this.cards.size() == numPlayers - 1;
	}
	
	/**
	 * Should be called after all cards are added to the trick.
	 * Can be called intermittently through out trick (before all cards are added)
	 */
	public void calculateWinner() {
		Card[] orderedCards = this.getOrderedCardList();
		
		winner = cards.get(orderedCards[orderedCards.length-1]);
		numPoints = calcNumPoints(cards.keySet());
		
		winnerCalculated = true;
	}
	
	@Override
	public String toString() {
		StringBuffer retval = new StringBuffer();
		
		CurrentGameState cgs = CurrentGameState.getCurrentGameState();
		
		if (this.leadCard != null) {
			retval.append("Lead: ");
			Player leadPlayer = cgs.getPlayer(cards.get(this.leadCard));
			retval.append(leadPlayer.getName() + " " + this.leadCard);
			retval.append("\n");	
		}
		
		if (winnerCalculated) {
			retval.append("Winner: ");
			Player winner = cgs.getPlayer(this.winner);
			retval.append(winner.getName() + " ");
			Card[] orderedCards = this.getOrderedCardList();
			retval.append(orderedCards[orderedCards.length-1]);
			retval.append("\n");
		}
		
		retval.append("Trump: ");
		retval.append(this.trump);
		retval.append("\n");
		
		retval.append("Trick: ");
		int i = 0;
		
		for(Map.Entry<Card,String> trickCard : cards.entrySet()) {
			Player cardPlayer = cgs.getPlayer(trickCard.getValue());
			retval.append(cardPlayer.getName());
			retval.append(" : ");
			retval.append(trickCard.getKey().toString());
			if (i < cards.size()-1) {
				retval.append(", ");	
			} else {
				retval.append(" ");
			}
			i++;
		}
		retval.append("\n");
		
		if (winnerCalculated) {
			retval.append("Ordered Trick: ");
			for(Card trickCard : getOrderedCardList()) {
				retval.append(trickCard.toString() + " ");
			}
			retval.append("\n");
		
			retval.append("Num Points: ");
			retval.append(this.numPoints);			
		} else {
			/*
			retval.append("Current Leader: ");
			retval.append(trickCard.getValue().gameState.name);
			retval.append(" : ");
			retval.append(trickCard.getKey().toString());
			*/
		}
		retval.append("\n");
		
		return retval.toString();
	}
	
	private static int calcNumPoints(Set<Card> cards) {
		int numPoints = 1; // each trick is worth 1 point
		for (Card c : cards) {
			numPoints += c.numberOfPoints;
		}
		return numPoints;
	}
	
}
