package chess;

import board.Board;
import board.Piece;
import board.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}	
	
	public boolean isThereOpponentPiece(int row, int column) {
		ChessPiece oponnentPiece = (ChessPiece)(getBoard().getPiece(position));
		
		return oponnentPiece != null && oponnentPiece.getColor() != color;
	}
	
	public boolean isThereOpponentPiece(Position position) {
		return isThereOpponentPiece(position.getRow(), position.getColumn());
	}
}
