package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import board.Board;
import board.Piece;
import board.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	private Color currentPlayer;
	private int turn;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> capturedPieces = new ArrayList<Piece>();
	private List<Piece> piecesOnBoard = new ArrayList<Piece>();
	
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
	
	public boolean isInCheck() {     
		return check;
	}
	
	public boolean isCheckMate() {
		return checkMate;
	}

	private void setCheckMate(boolean checkMate) {
		this.checkMate = checkMate;
	}

	public Board getBoard() {
		return board;
	}
	
	public List<Piece> getCapturedPieces() {
		return capturedPieces;
	}
	
	public List<Piece> getPiecesOnBoard() {
		return piecesOnBoard;
	}
	
	public List<Piece> getPiecesOnBoard(Color color) {
		return piecesOnBoard.stream().filter(piece -> piece != null && ((ChessPiece) piece).getColor() == color).toList();
	}

	public ChessPiece[][] getPiecesMatrix() {
		ChessPiece[][] pieces = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				pieces[i][j] = (ChessPiece) board.getPiece(i, j);
			}
		}

		return pieces;
	}

	public King getKing(Color color) {
		for(Piece piece : piecesOnBoard) {
			if(piece instanceof King && ((King) piece).getColor() == color)
				return (King) piece;
		}
		
		throw new IllegalStateException("There is not a King of color " + color);
	}
	
	public boolean[][] getPossibleMoves(ChessPosition sourcePosition) {
		Position boardChessPosition = sourcePosition.toBoardPosition();
		validateSourcePosition(boardChessPosition);

		if(isInCheck())
			return ((ChessPiece)(board.getPiece(boardChessPosition))).inCheckPossibleMoves();
		else
			return board.getPiece(boardChessPosition).getPossibleMoves();
	}

	private void placeChessPiece(ChessPiece piece, char column, int row) {
		board.placePiece(piece, new ChessPosition(column, row).toBoardPosition());
		piecesOnBoard.add(piece);
	}

	public ChessPiece movePiece(ChessPosition sourceChessPosition, ChessPosition targetChessPosition) {
		Position sourceBoardPosition = sourceChessPosition.toBoardPosition();
		Position targetBoardPosition = targetChessPosition.toBoardPosition();

		validateSourcePosition(sourceBoardPosition);
		validateTargetPosition(sourceBoardPosition, targetBoardPosition);

		Piece capturedPiece = makeMove(sourceBoardPosition, targetBoardPosition);
		
		if(check)
			check = false;

		King enemyKing = getKing(opponentColor(currentPlayer));
		
		if(enemyKing.isCheckedPosition(enemyKing.getPosition()))
			check = true;
		
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
		
		if(capturedPiece != null) {
			piecesOnBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	public Piece removeFromBoard(Position position) {
		Piece piece = board.getPiece(position);
		board.removePiece(position);
		piecesOnBoard.remove(piece);
				
		return piece;
	}
	
	public void placeOnBoard(Piece piece, Position position) {
		board.placePiece(piece, position);
		piecesOnBoard.add(piece);
	}

	private void initialSetup() {
		placeChessPiece(new King(board, Color.WHITE, this), 'b', 7);
		placeChessPiece(new Rook(board, Color.WHITE, this), 'a', 1);
		placeChessPiece(new Rook(board, Color.WHITE, this), 'h', 1);

		placeChessPiece(new King(board, Color.BLACK, this), 'e', 8);
		placeChessPiece(new Rook(board, Color.BLACK, this), 'a', 8);
		placeChessPiece(new Rook(board, Color.BLACK, this), 'h', 8);
	}

	public Color opponentColor(Color color) {
		return color == Color.BLACK ? Color.WHITE : Color.BLACK;
	}
}
