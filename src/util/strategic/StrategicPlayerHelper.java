/**
 * 
 */
package util.strategic;

import java.util.Collections;
import java.util.List;

import players.states.GameState;
import players.states.StrategicRoundState;
import util.Tuple;
import util.comparators.IntrinsicOrderCardComparator;
import util.comparators.PointIOCardComparator;
import game.Hand;
import game.RuleSet;
import game.Trick;
import game.deck.Card;
import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 */
public class StrategicPlayerHelper {
	
	public static Tuple<Suit, Integer> getIdealTrumpAndBid(GameState gameState, StrategicRoundState strategicRoundState) {
		
		// Play a fake game with each trump, and return the most points of any trump.
		// Both "my player" and "other players" will be strategic players
		
		// Trump -> numPoints earned in the round
		Tuple<Suit, Integer> bestTuple = new Tuple<Suit, Integer>(new Suit("s"), -1);
		
		for (Suit s : strategicRoundState.getMyHand().getAllUniqueSuits()) {
			
			double bidSum = 0;
			int numBidsToAverage = 20;
			
			for (int i = 0; i < numBidsToAverage; i++) {
				MockRound mr = new MockRound(gameState, strategicRoundState, new Tuple<Suit, Integer>(s, 0)); // bid value doesn't matter here
				mr.executeRound();
				bidSum += mr.getNumPointsWon();
			}
			
			int averageBid = (int) (bidSum / numBidsToAverage);
			
			if (averageBid > bestTuple.y) {
				bestTuple = new Tuple<Suit, Integer>(s, averageBid);
			}
			
		}
		
		return bestTuple;
		
	}
	
	// Assumptions:
	// oughtOught will be lead or played // of little consequence
	// No masking strategy implemented in discarding two worst cards
	// Could pick smallest unbalanced cards or the like
	// Very simple implementation
	public static Card getBestDiscardCard(GameState gameState, StrategicRoundState strategicRoundState, Suit suggestedTrump) {
		
		// Discard lowest valued card according to intrinsic ordering
		
		Hand<Card> myHand = strategicRoundState.getMyHand();
		Collections.sort(myHand, new IntrinsicOrderCardComparator(suggestedTrump, gameState.ruleSet, true, true));
		return myHand.get(0);
		
	}

	// Assumptions:
	// oughtOught will be lead or played
	// Play highest trump based on intrinsic ordering if sure winner, else lowest point, lowest trump card
	public static Card getBestFirstCardFirstTrick(GameState gameState, StrategicRoundState strategicRoundState, Suit suggestedTrump) {
		
		String myId = gameState.id;
		
		RuleSet ruleSet = gameState.ruleSet;
		boolean oughtOughtLead = strategicRoundState.getMyHand().contains(new Card("0/0")); 
		boolean oughtOughtPlayed = oughtOughtLead; // || strategicRoundState.getOtherHands().contains(new Card("0/0")); // Conservative Estimate
		
		AnnotatedHand annotatedHand = strategicRoundState.getAnnotatedHand();
		Collections.sort(annotatedHand, new IntrinsicOrderCardComparator(suggestedTrump, ruleSet, oughtOughtLead, oughtOughtPlayed));
		
		// Return sure winner or lowest non-point trump
		
		if (annotatedHand.get(annotatedHand.size()-1).isInPlayersHand(myId)) {
			return annotatedHand.get(annotatedHand.size()-1);
		} else {
			List<Card> myTrumpCards = annotatedHand.getAllCardsInPlayersHand(myId).getAllTrumpCards(suggestedTrump);
			Collections.sort(myTrumpCards, new PointIOCardComparator(suggestedTrump, gameState.ruleSet, true, true)); // two true's don't matter
			return myTrumpCards.get(0);
		}
		
	}
	
	public static Card getBestTrickLeadCard(GameState gameState, StrategicRoundState strategicRoundState) {
		
		String myId = gameState.id;
		
		RuleSet ruleSet = gameState.ruleSet;
		boolean oughtOughtLead = strategicRoundState.getMyHand().contains(new Card("0/0")); 
		boolean oughtOughtPlayed = oughtOughtLead; // || strategicRoundState.getOtherHands().contains(new Card("0/0")); // Conservative Estimate
		
		Suit trump = strategicRoundState.getTrump();
		
		AnnotatedHand annotatedHand = strategicRoundState.getAnnotatedHand();
		Collections.sort(annotatedHand, new IntrinsicOrderCardComparator(trump, ruleSet, oughtOughtLead, oughtOughtPlayed));
		
		// Find and return a sure winner
		
		for (Suit s : Suit.getAllSuits()) {
			List<AnnotatedCard> allPlayableCards = annotatedHand.getAllWinnableCards(s, trump, oughtOughtLead, oughtOughtPlayed, ruleSet);
			if (allPlayableCards.size() > 0) {
				AnnotatedCard bestPlayableCard = allPlayableCards.get(allPlayableCards.size()-1);
				if (bestPlayableCard.isInPlayersHand(myId)) {
					return bestPlayableCard; 
				}	
			}
		}
		
		// No sure winners found, return lowest point, lowest card in my hand
		
		Hand<Card> myHand = annotatedHand.getAllCardsInPlayersHand(myId);
		Collections.sort(myHand, new PointIOCardComparator(trump, gameState.ruleSet, oughtOughtLead, oughtOughtPlayed)); 
		return myHand.get(0);
		
	}
	
	public static Card getBestCardToPlay(GameState gameState, StrategicRoundState strategicRoundState, Trick trick) {
		
		String myId = gameState.id;
		
		Suit trump = strategicRoundState.getTrump();
		List<Card> myPlayableCards = strategicRoundState.getMyHand().getPlayableCards(trick.getLeadSuit(), trump);
		
		RuleSet ruleSet = gameState.ruleSet;
		boolean oughtOughtLead = trick.oughtOughtLead(); 		
		boolean oughtOughtPlayed = trick.oughtOughtPlayed(); // || strategicRoundState.getMyHand().contains(new Card("0/0")); // Conservative
		
		// Conditional based on if I am last person in trick or not
		/** This is broken ! **
		if (!trick.amLastPlayerToPlay(gameState.numPlayers)) {
			oughtOughtPlayed |= strategicRoundState.getOtherHands().contains(new Card("0/0"));
		}
		*/
		
		AnnotatedHand annotatedHand = strategicRoundState.getAnnotatedHand(); 
		Collections.sort(annotatedHand, new IntrinsicOrderCardComparator(trump, ruleSet, oughtOughtLead, oughtOughtPlayed));
		
		AnnotatedHand allWinnableCards = annotatedHand.getAllWinnableCards(trick.getLeadSuit(), trump, oughtOughtLead, oughtOughtPlayed, ruleSet);
		
		Card[] trickCards = trick.getOrderedCardList();
		allWinnableCards = allWinnableCards.truncateWinnableCards(trickCards[trickCards.length-1], trump, oughtOughtLead, oughtOughtPlayed, ruleSet);
		
		// No playable cards can take trick. return my smallest-point, smallest-valued card (unless partner sure-win)
		if (allWinnableCards.size() == 0) {
			Collections.sort(myPlayableCards, new PointIOCardComparator(trump, gameState.ruleSet, oughtOughtLead, oughtOughtPlayed)); 
			return myPlayableCards.get(0);
		}
		
		// Play lowest sure-win
		for (int i = allWinnableCards.size()-1; i > 0; i--) {
			
			if (i == 0 && allWinnableCards.get(i).isInPlayersHand(myId)) {
				// We've reached the lowest card that's still a winner, 
				return allWinnableCards.get(i);
			}
			
			if (allWinnableCards.get(i).isInPlayersHand(myId)) {
				
				if (allWinnableCards.get(i-1).isInPlayersHand(myId)) {
					// lower sure winner exists
					continue;
				} else {
					// return first found sure winner
					return allWinnableCards.get(i); 	
				}
				
			}
			
		}
		
		// No sure winners, play highest card that can win
		Hand<Card> myWinnableHand = allWinnableCards.getAllCardsInPlayersHand(myId);
		if (myWinnableHand.size() > 0) {
			return myWinnableHand.get(myWinnableHand.size()-1);
		} else {
			// Play lowest card since no winners for me.
			Collections.sort(myPlayableCards, new PointIOCardComparator(trump, gameState.ruleSet, oughtOughtLead, oughtOughtPlayed)); 
			try {
				return myPlayableCards.get(0);  // Has Caused exception	
			} catch (Exception e){
				System.err.println(e);
				e.printStackTrace();
				throw e;
			}
			
		}
	
	}
	
}
