/**
 * 
 */
package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
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

		while (true) {
			try {
				UI.clearScreen();
				UI.printBoard(match.getPieces());
				
				System.out.print("Source: ");				
				ChessPosition source = UI.readChessPosition(input);
				UI.clearScreen();
				UI.printBoard(match.getPieces(), match.getPossibleMoves(source));
				
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(input);

				ChessPiece capturedPiece = match.movePiece(source, target);
			} 
			catch (ChessException e) {
				handleException(e, input);
			} 
			catch(InputMismatchException e) {
				handleException(e, input);
			}
		}
	}
	
	private static void handleException(Throwable e, Scanner input) {
		System.out.println(e.getMessage());
		System.out.println("Press enter to continue...");
		input.nextLine();
	}
}
