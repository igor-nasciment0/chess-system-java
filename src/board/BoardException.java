package board;

public class BoardException extends RuntimeException {

	public BoardException(String message) {
		super("BoardException: " + message);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
