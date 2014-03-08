/**
 * 
 */
package game.util;

/**
 * @author Patrick Blesi
 *
 */
public class Constants {

	public static final int POINTS_TO_WIN = 65;
	public static final int NUM_BID_CARDS = 2;
	
	public static final String instructions = 
	"Somerset (Double Somerset for the pedantically inclined) is a card " +
	"game where the object is to take tricks and accumulate points.\n\n" +
	
	"How do you take tricks?\n" + 
	"One takes tricks by playing the highest value card in a trick. " + 
	"The highest card is the card with the highest value that follows suit " +
	"or the highest-valued trump card (if any trump cards were played)\n\n" +
	
	"How do you accumulate points?\n" +
	"There are two ways to accumulate points. (1) Win tricks; each trick is worth 1 " +
	"point. (2) Certain cards are worth extra points [ s/s, 1/2, 2/4, 3/6, 4/8, 5/10, and 10/12 ]. " +
	"If these cards are played in the trick that you win, then you get those points also.\n\n" +
	
	"Now for the logistics:\n" +
	"";
	
	private Constants() {
		// TODO Auto-generated constructor stub
	}

}
