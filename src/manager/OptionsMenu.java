/**
 * 
 */
package manager;

import java.util.List;

import util.PlayerInfo;
import game.RuleSet;

/**
 * @author Patrick Blesi
 *
 */
public interface OptionsMenu {

	RuleSet getRuleSet();
	List<PlayerInfo> getPlayerInfos();
	
}
