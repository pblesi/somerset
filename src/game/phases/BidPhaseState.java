/**
 * 
 */
package game.phases;

import players.Player;

/**
 * @author Patrick Blesi
 *
 */
public class BidPhaseState {

	public int winningBid;
	public String winningPlayerId;
	
	public int currentPlayerBid;
	public String currentPlayerId;
	
	protected BidPhaseState() { }
	
	/**
	 * 
	 */
	public BidPhaseState(Player dealer) {
		this.winningBid = 0;
		this.winningPlayerId = dealer.getId();
		this.currentPlayerBid = 0;
		this.currentPlayerId = dealer.getPlayerToLeft();
	}

}
