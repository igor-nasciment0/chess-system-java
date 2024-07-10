[1mdiff --git a/src/application/Program.java b/src/application/Program.java[m
[1mindex 42b929c..867a976 100644[m
[1m--- a/src/application/Program.java[m
[1m+++ b/src/application/Program.java[m
[36m@@ -28,7 +28,7 @@[m [mpublic class Program {[m
 		while (true) {[m
 			try {[m
 				UI.clearScreen();[m
[31m-				UI.printBoard(match.getPieces());[m
[32m+[m				[32mUI.printMatch(match);[m
 				[m
 				System.out.print("Source: ");				[m
 				ChessPosition source = UI.readChessPosition(input);[m
[1mdiff --git a/src/application/UI.java b/src/application/UI.java[m
[1mindex 12b7d71..eeaaa4a 100644[m
[1m--- a/src/application/UI.java[m
[1m+++ b/src/application/UI.java[m
[36m@@ -1,14 +1,28 @@[m
 package application;[m
 [m
[32m+[m[32mimport java.util.Arrays;[m
 import java.util.InputMismatchException;[m
[32m+[m[32mimport java.util.List;[m
 import java.util.Scanner;[m
 [m
[32m+[m[32mimport board.Piece;[m
[32m+[m[32mimport chess.ChessMatch;[m
 import chess.ChessPiece;[m
 import chess.ChessPosition;[m
 import chess.Color;[m
 [m
 public class UI {[m
 [m
[32m+[m	[32mpublic static void printMatch(ChessMatch match) {[m
[32m+[m		[32mprintBoard(match.getPieces());[m
[32m+[m		[32mSystem.out.println();[m
[32m+[m[41m		[m
[32m+[m		[32mprintCapturedPieces(match.getCapturedPieces());[m
[32m+[m[41m		[m
[32m+[m		[32mSystem.out.println("Turn: " + match.getTurn());[m
[32m+[m		[32mSystem.out.println("Waiting player: " + match.getCurrentPlayer());[m
[32m+[m	[32m}[m
[32m+[m
 	public static void printBoard(ChessPiece[][] pieces) {[m
 		for (int i = 0; i < pieces.length; i++) {[m
 			System.out.print(8 - i);[m
[36m@@ -39,8 +53,8 @@[m [mpublic class UI {[m
 [m
 	private static void printPiece(ChessPiece piece, boolean isPossibleMove) {[m
 		System.out.print(" ");[m
[31m-		[m
[31m-		if(isPossibleMove)[m
[32m+[m
[32m+[m		[32mif (isPossibleMove)[m
 			System.out.print(TerminalColors.ANSI_BLUE_BACKGROUND);[m
 [m
 		if (piece == null)[m
[36m@@ -54,6 +68,28 @@[m [mpublic class UI {[m
 		}[m
 	}[m
 [m
[32m+[m	[32mprivate static void printCapturedPieces(List<Piece> capturedPieces) {[m
[32m+[m		[32mList<Piece> capturedWhitePieces = capturedPieces.stream()[m
[32m+[m				[32m.filter(piece -> ((ChessPiece) piece).getColor() == Color.WHITE).toList();[m
[32m+[m[41m		[m
[32m+[m		[32mList<Piece> capturedBlackPieces = capturedPieces.stream()[m
[32m+[m				[32m.filter(piece -> ((ChessPiece) piece).getColor() == Color.BLACK).toList();[m
[32m+[m[41m		[m
[32m+[m		[32mSystem.out.println("Captured Pieces: ");[m
[32m+[m[41m		[m
[32m+[m		[32mSystem.out.print("White: ");[m
[32m+[m		[32mSystem.out.print(TerminalColors.ANSI_WHITE);[m
[32m+[m		[32mSystem.out.println(Arrays.toString(capturedWhitePieces.toArray()));[m
[32m+[m[41m		[m
[32m+[m		[32mSystem.out.print(TerminalColors.ANSI_RESET);[m
[32m+[m[41m		[m
[32m+[m		[32mSystem.out.print("Black: ");[m
[32m+[m		[32mSystem.out.print(TerminalColors.ANSI_YELLOW);[m
[32m+[m		[32mSystem.out.println(Arrays.toString(capturedBlackPieces.toArray()));[m
[32m+[m[41m		[m
[32m+[m		[32mSystem.out.println(TerminalColors.ANSI_RESET);[m
[32m+[m	[32m}[m
[32m+[m
 	public static ChessPosition readChessPosition(Scanner input) {[m
 		try {[m
 			String boardHouse = input.nextLine();[m
[1mdiff --git a/src/chess/ChessMatch.java b/src/chess/ChessMatch.java[m
[1mindex 164a51e..4d33a41 100644[m
[1m--- a/src/chess/ChessMatch.java[m
[1m+++ b/src/chess/ChessMatch.java[m
[36m@@ -1,5 +1,7 @@[m
 package chess;[m
 [m
[32m+[m[32mimport java.util.ArrayList;[m
[32m+[m
 import board.Board;[m
 import board.Piece;[m
 import board.Position;[m
[36m@@ -9,12 +11,30 @@[m [mimport chess.pieces.Rook;[m
 public class ChessMatch {[m
 [m
 	private Board board;[m
[31m-[m
[32m+[m	[32mprivate Color currentPlayer;[m
[32m+[m	[32mprivate int turn;[m
[32m+[m[41m	[m
[32m+[m	[32mprivate ArrayList<Piece> capturedPieces = new ArrayList<>();[m
[32m+[m[41m	[m
 	public ChessMatch() {[m
 		board = new Board(8, 8);[m
[32m+[m		[32mcurrentPlayer = Color.WHITE;[m
[32m+[m		[32mturn = 1;[m
 		initialSetup();[m
 	}[m
 [m
[32m+[m	[32mpublic Color getCurrentPlayer() {[m
[32m+[m		[32mreturn currentPlayer;[m
[32m+[m	[32m}[m
[32m+[m
[32m+[m	[32mpublic int getTurn() {[m
[32m+[m		[32mreturn turn;[m
[32m+[m	[32m}[m
[32m+[m[41m	[m
[32m+[m	[32mpublic ArrayList<Piece> getCapturedPieces() {[m
[32m+[m		[32mreturn capturedPieces;[m
[32m+[m	[32m}[m
[32m+[m
 	public ChessPiece[][] getPieces() {[m
 		ChessPiece[][] pieces = new ChessPiece[board.getRows()][board.getColumns()];[m
 [m
[36m@@ -30,10 +50,10 @@[m [mpublic class ChessMatch {[m
 	public boolean[][] getPossibleMoves(ChessPosition sourcePosition) {[m
 		Position boardChessPosition = sourcePosition.toBoardPosition();[m
 		validateSourcePosition(boardChessPosition);[m
[31m-		[m
[32m+[m
 		return board.getPiece(boardChessPosition).getPossibleMoves();[m
 	}[m
[31m-	[m
[32m+[m
 	private void placeChessPiece(ChessPiece piece, char column, int row) {[m
 		board.placePiece(piece, new ChessPosition(column, row).toBoardPosition());[m
 	}[m
[36m@@ -43,11 +63,13 @@[m [mpublic class ChessMatch {[m
 		Position targetBoardPosition = targetChessPosition.toBoardPosition();[m
 [m
 		validateSourcePosition(sourceBoardPosition);[m
[31m-		[m
[32m+[m
 		validateTargetPosition(sourceBoardPosition, targetBoardPosition);[m
 [m
 		Piece capturedPiece = makeMove(sourceBoardPosition, targetBoardPosition);[m
 [m
[32m+[m		[32mnextTurn();[m
[32m+[m
 		return (ChessPiece) capturedPiece;[m
 	}[m
 [m
[36m@@ -56,6 +78,11 @@[m [mpublic class ChessMatch {[m
 			throw new ChessException([m
 					"There is not a piece in source position: " + ChessPosition.fromBoardPosition(sourcePosition));[m
 [m
[32m+[m		[32mColor pieceColor = ((ChessPiece) board.getPiece(sourcePosition)).getColor();[m
[32m+[m
[32m+[m		[32mif (pieceColor != currentPlayer)[m
[32m+[m			[32mthrow new ChessException("Enemy piece selected. Current turn: " + currentPlayer);[m
[32m+[m
 		if (!board.getPiece(sourcePosition).isThereAnyPossibleMove())[m
 			throw new ChessException([m
 					"There is not any possible move for piece in " + ChessPosition.fromBoardPosition(sourcePosition));[m
[36m@@ -67,10 +94,25 @@[m [mpublic class ChessMatch {[m
 [m
 	}[m
 [m
[32m+[m	[32mprivate void nextTurn() {[m
[32m+[m		[32mif (currentPlayer == Color.WHITE) {[m
[32m+[m			[32mcurrentPlayer = Color.BLACK;[m
[32m+[m
[32m+[m		[32m} else {[m
[32m+[m			[32mcurrentPlayer = Color.WHITE;[m
[32m+[m			[32mturn++;[m
[32m+[m		[32m}[m
[32m+[m	[32m}[m
[32m+[m
 	private Piece makeMove(Position source, Position target) {[m
 		Piece movingPiece = board.removePiece(source);[m
 		Piece capturedPiece = board.removePiece(target);[m
[32m+[m[41m		[m
 		board.placePiece(movingPiece, target);[m
[32m+[m[41m		[m
[32m+[m		[32mif(capturedPiece != null)[m
[32m+[m			[32mcapturedPieces.add(capturedPiece);[m
[32m+[m[41m		[m
 		return capturedPiece;[m
 	}[m
 [m
