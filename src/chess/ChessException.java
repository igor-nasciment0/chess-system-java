package chess;

public class ChessException extends RuntimeException {

	public ChessException(String message) {
		super("ChessException: " + message);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
