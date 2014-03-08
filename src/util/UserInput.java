/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Patrick Blesi
 *
 */
public final class UserInput {

	private InputStreamReader inputStreamReader;
	private BufferedReader bufferedReader;
	
	/**
	 * 
	 */
	public UserInput() {

		inputStreamReader = new InputStreamReader(System.in);
		bufferedReader = new BufferedReader(inputStreamReader);
		
	}

	public Integer getInteger() {
		return getInteger(Integer.MIN_VALUE, Integer.MAX_VALUE, false);
	}
	
	public Integer getInteger(int minVal, int maxVal, boolean allowDefault) {
		
		// Generate warning if minVal == Integer.MIN_VALUE
		
		Integer responseInt = null, returnInt = null;
		while (returnInt == null) {
			
			String response;
			
			try {
				response = bufferedReader.readLine();
			} catch (IOException e1) {
				System.out.println(e1);
				continue;
			}
			
			if (allowDefault && response.equals("")) {
				returnInt = Integer.MIN_VALUE;
			} else {
			
				try {
					responseInt = Integer.parseInt(response);
				} catch (NumberFormatException nfe) {
					System.out.print("\nDoes not compute...Please enter another number: ");
					continue;
				}
				
				if (responseInt < minVal || responseInt > maxVal) {
					System.out.print("\nDoes not compute...Please enter another number: ");
					continue;
				}
				
				returnInt = responseInt;
				
			}
			
		}
		
		return returnInt;
		
	}
	
	public boolean getYesNo() {
		
		while (true) {
			
			String s = this.getString().toLowerCase();
			
			if (s.equals("y") || s.equals("yes")) {
				return true;
			} else if (s.equals("n") || s.equals("no") || s.equals("")) {
				return false;
			} else {
				System.out.print("\nDoes not compute...Please enter No or Yes: ");
				continue;
			}
			
		}
		
	}
	
	public String getString() {
		
		String response = null;
		
		while (response == null) {
			
			try {
				response = bufferedReader.readLine();
			} catch (IOException e1) {
				System.out.println(e1);
				continue;
			}
			
		}
		
		return response;
		
	}
	
	public void close() {
		try {
			inputStreamReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
