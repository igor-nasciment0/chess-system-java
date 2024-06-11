/**
 * 
 */
package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPosition;

/**
 * @author Igor
 *
 */
public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		ChessMatch match = new ChessMatch();
		
		while(true) {
			UI.printBoard(match.getPieces());
			System.out.print("Source: ");
			ChessPosition source = UI.readChessPosition(input);
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPosition(input);
			
			match.movePiece(source, target);
		}
	}

}
