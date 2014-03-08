/**
 * 
 */
package manager.states;

/**
 * @author Patrick Blesi
 *
 */
public class SuitStats {
	
	public String suit;
	public int totalRoundsSuitMadeTrump;
	public int totalRoundsSuitAsTrumpMadeBid;
	
	public SuitStats() {
		
	}
	
	public SuitStats(String suit) {
		this.suit = suit;
	}
	
	/**
	 * @return the suit
	 */
	public String getSuit() {
		return suit;
	}
	
	/**
	 * @return the totalRoundsSuitMadeTrump
	 */
	public int getTotalRoundsSuitMadeTrump() {
		return totalRoundsSuitMadeTrump;
	}

	/**
	 * @param totalRoundsSuitMadeTrump the totalRoundsSuitMadeTrump to set
	 */
	public void setTotalRoundsSuitMadeTrump(int totalRoundsSuitMadeTrump) {
		this.totalRoundsSuitMadeTrump = totalRoundsSuitMadeTrump;
	}

	/**
	 * @return the totalRoundsSuitAsTrumpMadeBid
	 */
	public int getTotalRoundsSuitAsTrumpMadeBid() {
		return totalRoundsSuitAsTrumpMadeBid;
	}

	/**
	 * @param totalRoundsSuitAsTrumpMadeBid the totalRoundsSuitAsTrumpMadeBid to set
	 */
	public void setTotalRoundsSuitAsTrumpMadeBid(int totalRoundsSuitAsTrumpMadeBid) {
		this.totalRoundsSuitAsTrumpMadeBid = totalRoundsSuitAsTrumpMadeBid;
	}

}
