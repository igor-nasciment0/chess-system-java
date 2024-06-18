package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(8 - i);

			for (int j = 0; j < pieces[1].length; j++) {
				printPiece(pieces[i][j], false);
			}

			System.out.println("");
		}

		System.out.println("  a b c d e f g h");
	}

	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(8 - i);

			for (int j = 0; j < pieces[1].length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}

			System.out.println("");
		}

		System.out.println("  a b c d e f g h");
	}

	private static void printPiece(ChessPiece piece, boolean isPossibleMove) {
		System.out.print(" ");
		
		if(isPossibleMove)
			System.out.print(TerminalColors.ANSI_BLUE_BACKGROUND);

		if (piece == null)
			System.out.print("-" + TerminalColors.ANSI_RESET);

		else {
			if (piece.getColor() == Color.WHITE)
				System.out.print(TerminalColors.ANSI_WHITE + piece + TerminalColors.ANSI_RESET);
			else
				System.out.print(TerminalColors.ANSI_YELLOW + piece + TerminalColors.ANSI_RESET);
		}
	}

	public static ChessPosition readChessPosition(Scanner input) {
		try {
			String boardHouse = input.nextLine();

			char column = boardHouse.charAt(0);
			Integer row = Integer.parseInt(boardHouse.substring(1));

			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Invalid input position. Valid chess positions are a1 to h8.");
		}

	}

	public static void clearScreen() {
		System.out.println(System.lineSeparator().repeat(10));
	}
}
