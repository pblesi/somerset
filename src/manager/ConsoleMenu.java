/**
 * 
 */
package manager;

import java.util.Arrays;

/**
 * @author Patrick Blesi
 *
 */
public abstract class ConsoleMenu {

	/**
	 * 
	 */
	public ConsoleMenu() {
		// TODO Auto-generated constructor stub
	}

	public static void clearScreen() {
		/*try {
			Runtime.getRuntime().exec("cls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		char c = '\n';
		int length = 25;
		char[] chars = new char[length];
		Arrays.fill(chars, c);
		System.out.print(String.valueOf(chars));
	}
	
}
