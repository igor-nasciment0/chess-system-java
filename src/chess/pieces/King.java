package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {
		boolean isOneHouseDistant = 
				position.getRow() - this.position.getRow() <= 1 &&
				position.getRow() - this.position.getRow() >= -1 &&
				position.getColumn() - this.position.getColumn() <= 1 &&
				position.getColumn() - this.position.getColumn() >= -1;
				
		ChessPiece pieceAtPosition = (ChessPiece)getBoard().getPiece(position);
						
		return isOneHouseDistant && (pieceAtPosition == null || pieceAtPosition.getColor() != getColor());
	}

	@Override
	public boolean[][] getPossibleMoves() {
		boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		for(int i = 0; i < possibleMoves.length; i++) {
			for(int j = 0; j < possibleMoves.length; j++) {
				possibleMoves[i][j] = canMove(new Position(i, j));
			}
		}
		
		return possibleMoves;
	}
}
