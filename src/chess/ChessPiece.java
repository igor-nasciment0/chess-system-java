package chess;

import board.Board;
import board.Piece;
import board.Position;
import chess.pieces.King;

public abstract class ChessPiece extends Piece {

	private Color color;
	private ChessMatch match;

	public ChessPiece(Board board, Color color, ChessMatch match) {
		super(board);
		this.color = color;
		this.match = match;
	}

	public Color getColor() {
		return color;
	}

	public ChessMatch getMatch() {
		return match;
	}

	public boolean isThereOpponentPiece(int row, int column) {
		ChessPiece oponnentPiece = (ChessPiece) (getBoard().getPiece(row, column));

		return oponnentPiece != null && oponnentPiece.getColor() != color;
	}

	public boolean isThereOpponentPiece(Position position) {
		return isThereOpponentPiece(position.getRow(), position.getColumn());
	}

	public boolean[][] inCheckPossibleMoves() {
		boolean[][] possibleMoves = getPossibleMoves();
		boolean[][] filteredMoves = new boolean[possibleMoves.length][possibleMoves.length];

		int originalPositionRow = position.getRow();
		int originalPositionColumn = position.getColumn();

		King thisKing = match.getKing(color);

		for (int i = 0; i < possibleMoves.length; i++) {
			for (int j = 0; j < possibleMoves.length; j++) {
				if (possibleMoves[i][j]) {
					position.setValues(i, j);

					if (!thisKing.isChecked())
						filteredMoves[i][j] = true;
				}

				position.setValues(originalPositionRow, originalPositionColumn);
			}
		}

		return filteredMoves;
	}

	@Override
	public boolean isPossibleMove(Position position) {
		if (!match.isInCheck())
			return super.isPossibleMove(position);
		
		return inCheckPossibleMoves()[position.getRow()][position.getColumn()];
	}

	@Override
	public boolean isThereAnyPossibleMove() {
		if (!match.isInCheck())
			return super.isThereAnyPossibleMove();

		boolean[][] possibleMoves = inCheckPossibleMoves();

		for (boolean[] row : possibleMoves)
			for (boolean cell : row)
				if (cell)
					return true;

		return false;
	}
}
