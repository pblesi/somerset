/**
 * 
 */
package util;

/**
 * @author Patrick Blesi
 *
 * Generic tuple class
 *
 */
public class Tuple<X, Y> { 
	  
	  public X x; 
	  public Y y; 
	  
	  protected Tuple() { }
	  
	  public Tuple(X x, Y y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	  
	  @Override
	  public String toString() {
		  return "X: " + x + "\n" +
				 "Y: " + y + "\n";
	  }
}
