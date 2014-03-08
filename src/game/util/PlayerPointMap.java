/**
 * 
 */
package game.util;

import java.util.HashMap;

/**
 * @author Patrick Blesi
 *
 */
public final class PlayerPointMap {

	public static final HashMap<Integer, Integer> PLAYER_POINTS = 
			new HashMap<Integer, Integer>();
	static {
		PLAYER_POINTS.put(4, 24);
		PLAYER_POINTS.put(6, 20);
	}
	

}
