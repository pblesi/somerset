/**
 * 
 */
package game.phases;

/**
 * @author Patrick Blesi
 *
 */
public enum CurrentPose {
	
	// Deal Poses
		// None
	
	// Bid Poses
		BID_UNINITIALIZED,
		BID_INITIALIZED,
		
	// Discard Poses
		DCRD_UNINITIALIZED,
		DCRD_DEAL_BID,
		DCRD_DCRD_CARDS,
	
	// Trick Poses
		TRCK_UNINITIALIZED,
		TRCK_INITIALIZED
	
	// Score Poses
	
}
