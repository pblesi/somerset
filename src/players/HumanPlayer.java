/**
 * 
 */
package players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import game.RuleSet;
import game.Trick;
import game.deck.Card;
import game.deck.Suit;

/**
 * @author Patrick Blesi
 *
 */
public class HumanPlayer extends AbstractInformedPlayer {

	private InputStreamReader inputStreamReader;
	private BufferedReader bufferedReader;
	
	protected HumanPlayer() {
		inputStreamReader = new InputStreamReader(System.in);
		bufferedReader = new BufferedReader(inputStreamReader);
	}
	
	/**
	 * @param name The name of the player
	 * @param ruleType The ruleType for the game
	 */
	public HumanPlayer(String name, int numPlayers, RuleSet ruleType) {
		super(name, numPlayers, ruleType);
		
		this.gameState.playerType = PlayerType.HUMAN;
		this.gameState.playerDifficulty = PlayerDifficulty.NA;
		
		inputStreamReader = new InputStreamReader(System.in);
		bufferedReader = new BufferedReader(inputStreamReader);
		
	}

	/* (non-Javadoc)
	 * @see players.Player#getBid()
	 */
	@Override
	public int getBid(String winningPlayerId, int currentBid) {
		
		System.out.println("\n" + this + "\n");
		System.out.print("What would you like to bid (nums <= currentBid are a pass)? ");
		
		Integer intResponse = null;
		while (intResponse == null) {
			String response;
			try {
				response = bufferedReader.readLine();
			} catch (IOException e1) {
				System.out.println(e1);
				continue;
			}
			try {
				intResponse = Integer.parseInt(response);
			} catch (NumberFormatException nfe) {
				System.out.print("\nDoes not compute...Please enter another number: ");
				continue;
			}
		}
		
		return intResponse;
		
	}

	/* (non-Javadoc)
	 * @see players.Player#discardCard()
	 * 
	 */
	@Override
	public Card discardCard() {
		
		System.out.println("\n" + this + "\n");
		System.out.print("Which card would you like to discard? ");
		
		Card responseCard = null;
		Card discardCard = null;
		
		while (discardCard == null) {
			
			String response;
			try {
				response = bufferedReader.readLine();
			} catch (IOException e1) {
				System.out.println(e1);
				continue;
			}
		
			try {
				responseCard = new Card(response);
			} catch (Exception e) {
				System.out.print("\nDoes not compute...Please enter another card: ");
				continue;
			}
			
			if (!this.getHand().contains(responseCard)) {
				System.out.print("\nIt appears you do not have this card in your hand. Try again? ");
				continue;
			}
			
			if (this.gameState.ruleSet == RuleSet.KRAEMER &&
				!this.getHand().getNonPointCards().contains(responseCard)) {
				System.out.print("\nYou must choose a non red card to discard. Try again? ");
				continue;
			}
			
			discardCard = this.getHand().removeCard(responseCard);
					
		}
		
		return discardCard;
		
	}

	/* (non-Javadoc)
	 * @see players.Player#playLeadCard()
	 */
	@Override
	public Card playLeadCard() {

		System.out.println("\n" + this + "\n");
		System.out.print("Which card would you like to play? ");
		
		Card responseCard = null;
		Card playCard = null;
		
		while (playCard == null) {
			String response;
			
			try {
				response = bufferedReader.readLine();
			} catch (IOException e1) {
				System.out.println(e1);
				continue;
			}
		
			try {
				responseCard = new Card(response);
			} catch (Exception e) {
				System.out.print("\nDoes not compute...Please enter another card: ");
				continue;
			}
			
			if (!this.getHand().contains(responseCard)) {
				System.out.print("\nIt appears you do not have this card in your hand. Try again? ");
				continue;
			}
			
			playCard = this.getHand().removeCard(responseCard);
					
		}
		
		return playCard;
		
	}
	
	/* (non-Javadoc)
	 * @see players.Player#playCard()
	 */
	@Override
	public Card playCard(Trick trick, Suit leadSuit, Suit trump) {
		
		System.out.println(trick);		
		System.out.println("\n" + this + "\n");
		System.out.print("Which card would you like to play? ");
		
		Card responseCard = null;
		Card playCard = null;
		
		while (playCard == null) {
			String response;
			
			try {
				response = bufferedReader.readLine();
			} catch (IOException e1) {
				System.out.println(e1);
				continue;
			}
		
			try {
				responseCard = new Card(response);
			} catch (Exception e) {
				System.out.print("\nDoes not compute...Please enter another card: ");
				continue;
			}
			
			if (!this.getHand().contains(responseCard)) {
				System.out.print("\nIt appears you do not have this card in your hand. Try again? ");
				continue;
			}
			
			if (!this.getHand().getPlayableCards(leadSuit, trump).contains(responseCard)) {
				System.out.print("\nYou must follow suit. Try again? ");
				continue;
			}
			
			playCard = this.getHand().removeCard(responseCard);
					
		}
		
		return playCard;
		
	}

	/* (non-Javadoc)
	 * @see players.Player#isHumanPlayer()
	 */
	@Override
	public boolean isHumanPlayer() {
		return true;
	}
	
	public void close() {

		try {
			inputStreamReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
