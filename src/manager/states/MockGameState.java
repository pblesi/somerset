/**
 * 
 */
package manager.states;

import java.nio.charset.Charset;

import org.yaml.snakeyaml.Yaml;

/**
 * @author Patrick Blesi
 *
 */
public class MockGameState extends CurrentGameState {

	public static MockGameState getClonedGameState() {
		CurrentGameState cgs = CurrentGameState.getCurrentGameState();
		cgs.commitToDisk();
		try {
			Yaml yaml = new Yaml();
			Charset charSet = Charset.defaultCharset();
			String savedState = AbstractAppState.readFile(STATE_PATH + STATE_FILE_NAME,	charSet);
			String oldClassRegex = "^\\!\\!manager\\.states\\.CurrentGameState";
			String replacementString = "!!manager.states.MockGameState";
			savedState = savedState.replaceFirst(oldClassRegex, replacementString);
			return (MockGameState) yaml.load(savedState);	
		} catch (Exception e) {
			// Log error
			// shouldn't happen
			return new MockGameState(); // perhaps re think initializer
		}
	}
	
	/**
	 * 
	 */
	protected MockGameState() {
		
	}

	@Override
	public void commitToDisk() {
		
	}
	
}
