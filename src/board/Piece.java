package board;

public abstract class Piece {

	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}

	public abstract boolean[][] getPossibleMoves();

	public boolean isPossibleMove(Position position) {
		return getPossibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] possibleMoves = getPossibleMoves();

		for (boolean[] row : possibleMoves) {
			for (boolean movementCell : row) {
				if (movementCell)
					return true;
			}
		}

		return false;
	}
}
