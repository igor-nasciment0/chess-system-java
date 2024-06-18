package chess;

import java.util.ArrayList;

import board.Board;
import board.Piece;
import board.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	private Color currentPlayer;
	private int turn;
	
	private ArrayList<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8);
		currentPlayer = Color.WHITE;
		turn = 1;
		initialSetup();
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public int getTurn() {
		return turn;
	}
	
	public ArrayList<Piece> getCapturedPieces() {
		return capturedPieces;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] pieces = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				pieces[i][j] = (ChessPiece) board.getPiece(i, j);
			}
		}

		return pieces;
	}

	public boolean[][] getPossibleMoves(ChessPosition sourcePosition) {
		Position boardChessPosition = sourcePosition.toBoardPosition();
		validateSourcePosition(boardChessPosition);

		return board.getPiece(boardChessPosition).getPossibleMoves();
	}

	private void placeChessPiece(ChessPiece piece, char column, int row) {
		board.placePiece(piece, new ChessPosition(column, row).toBoardPosition());
	}

	public ChessPiece movePiece(ChessPosition sourceChessPosition, ChessPosition targetChessPosition) {
		Position sourceBoardPosition = sourceChessPosition.toBoardPosition();
		Position targetBoardPosition = targetChessPosition.toBoardPosition();

		validateSourcePosition(sourceBoardPosition);

		validateTargetPosition(sourceBoardPosition, targetBoardPosition);

		Piece capturedPiece = makeMove(sourceBoardPosition, targetBoardPosition);

		nextTurn();

		return (ChessPiece) capturedPiece;
	}

	private void validateSourcePosition(Position sourcePosition) {
		if (!board.thereIsAPiece(sourcePosition))
			throw new ChessException(
					"There is not a piece in source position: " + ChessPosition.fromBoardPosition(sourcePosition));

		Color pieceColor = ((ChessPiece) board.getPiece(sourcePosition)).getColor();

		if (pieceColor != currentPlayer)
			throw new ChessException("Enemy piece selected. Current turn: " + currentPlayer);

		if (!board.getPiece(sourcePosition).isThereAnyPossibleMove())
			throw new ChessException(
					"There is not any possible move for piece in " + ChessPosition.fromBoardPosition(sourcePosition));
	}

	private void validateTargetPosition(Position sourcePosition, Position targetPosition) {
		if (!board.getPiece(sourcePosition).isPossibleMove(targetPosition))
			throw new ChessException("Invalid target position: " + ChessPosition.fromBoardPosition(targetPosition));

	}

	private void nextTurn() {
		if (currentPlayer == Color.WHITE) {
			currentPlayer = Color.BLACK;

		} else {
			currentPlayer = Color.WHITE;
			turn++;
		}
	}

	private Piece makeMove(Position source, Position target) {
		Piece movingPiece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		
		board.placePiece(movingPiece, target);
		
		if(capturedPiece != null)
			capturedPieces.add(capturedPiece);
		
		return capturedPiece;
	}

	private void initialSetup() {
		placeChessPiece(new King(board, Color.WHITE), 'b', 7);
		placeChessPiece(new Rook(board, Color.WHITE), 'a', 1);
		placeChessPiece(new Rook(board, Color.WHITE), 'h', 1);

		placeChessPiece(new King(board, Color.BLACK), 'e', 8);
		placeChessPiece(new Rook(board, Color.BLACK), 'a', 8);
		placeChessPiece(new Rook(board, Color.BLACK), 'h', 8);
	}
}
