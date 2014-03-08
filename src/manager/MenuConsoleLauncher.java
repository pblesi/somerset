/**
 * 
 */
package manager;

import manager.states.CurrentGameState;

/**
 * @author Patrick Blesi
 *
 */
public final class MenuConsoleLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CurrentGameState cgs = CurrentGameState.getCurrentGameState();
		MainConsoleMenu mainMenu = new MainConsoleMenu(cgs);
		
		while(mainMenu.getKeepPlaying()) {
			mainMenu.handleMenuSelection();
		}

	}
	

	
}
