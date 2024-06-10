/**
 * 
 */
package application;

import chess.ChessMatch;

/**
 * @author Igor
 *
 */
public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChessMatch match = new ChessMatch();
		UI.printBoard(match.getPieces());
	}

}
