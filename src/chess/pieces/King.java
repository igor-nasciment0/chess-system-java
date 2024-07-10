package chess.pieces;

import board.Board;
import board.Piece;
import board.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color, ChessMatch match) {
		super(board, color, match);
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		boolean isOneHouseDistant = position.getRow() - this.position.getRow() <= 1
				&& position.getRow() - this.position.getRow() >= -1
				&& position.getColumn() - this.position.getColumn() <= 1
				&& position.getColumn() - this.position.getColumn() >= -1;

		ChessPiece pieceAtPosition = (ChessPiece) getBoard().getPiece(position);

		return isOneHouseDistant && (pieceAtPosition == null || pieceAtPosition.getColor() != getColor());
	}

	@Override
	public boolean[][] getPossibleMoves() {
		boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

		for (int i = 0; i < possibleMoves.length; i++) {
			for (int j = 0; j < possibleMoves.length; j++) {

				possibleMoves[i][j] = canMove(new Position(i, j)) && !isCheckedPosition(new Position(i, j));
			}
		}

		return possibleMoves;
	}
	
	public boolean isChecked() {
		return isCheckedPosition(position);
	}

	public boolean isCheckedPosition(Position position) {
		ChessMatch match = getMatch();  
		
		Piece pieceAtPosition = match.removeFromBoard(position);
		
		for (Piece opponentPiece : match.getPiecesOnBoard(match.opponentColor(this.getColor()))) {
			if (opponentPiece instanceof King) {
				if(isOpponentKingClose(position, (King) opponentPiece)) {
					match.placeOnBoard(pieceAtPosition, position);
					return true;
				}
			}			
			else if (opponentPiece.getPossibleMoves()[position.getRow()][position.getColumn()]) {
				match.placeOnBoard(pieceAtPosition, position);
				return true;
			}				
		}
		
		match.placeOnBoard(pieceAtPosition, position);

		return false;
	}

	private boolean isOpponentKingClose(Position position, King enemyKing) {
		return enemyKing.canMove(position);
	}
}
