package chess;

import board.Board;
import board.Piece;
import board.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] pieces = new ChessPiece[board.getRows()][board.getColumns()];
		
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				pieces[i][j] = (ChessPiece) board.getPiece(i, j);
			}
		}
		
		return pieces;
	}
	
	private void placeChessPiece(ChessPiece piece, char column, int row) {
		board.placePiece(piece, new ChessPosition(column, row).toBoardPosition());
	}
	
	public ChessPiece movePiece(ChessPosition sourceChessPosition, ChessPosition targetChessPosition) {
		Position sourceBoardPosition = sourceChessPosition.toBoardPosition();
		Position targetBoardPosition = targetChessPosition.toBoardPosition();	
		
		Piece capturedPiece = makeMove(sourceBoardPosition, targetBoardPosition);		
		
		return (ChessPiece) capturedPiece;
	}
	
	private void validateSourcePosition(Position boardPosition, ChessPosition chessPosition) {
		if(!board.thereIsAPiece(boardPosition))
			throw new ChessException("There is not a piece in source position: " + chessPosition);

		if(!board.getPiece(boardPosition).isThereAnyPossibleMove())
			throw new ChessException("There is not any possible move for piece in " + chessPosition);
	}
	
	private Piece makeMove(Position source, Position target) {		
		Piece movingPiece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(movingPiece, target);
		return capturedPiece;
	}
	
	private void initialSetup() {
		placeChessPiece(new King(board, Color.WHITE), 'e', 1);
		placeChessPiece(new Rook(board, Color.WHITE), 'a', 1);
		placeChessPiece(new Rook(board, Color.WHITE), 'h', 1);
		
		placeChessPiece(new King(board, Color.BLACK), 'e', 8);
		placeChessPiece(new Rook(board, Color.BLACK), 'a', 8);
		placeChessPiece(new Rook(board, Color.BLACK), 'h', 8);
	}
}
