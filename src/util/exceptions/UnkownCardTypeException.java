/**
 * 
 */
package util.exceptions;

/**
 * @author Patrick Blesi
 *
 */
public class UnkownCardTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6219663446996510052L;

	/**
	 * 
	 */
	public UnkownCardTypeException() {
	}

	/**
	 * @param arg0
	 */
	public UnkownCardTypeException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public UnkownCardTypeException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public UnkownCardTypeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public UnkownCardTypeException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
