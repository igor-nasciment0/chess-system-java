package board;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1)
			throw new BoardException("The quantity of rows and columns in the board must be greater than 1");

		this.rows = rows;
		this.columns = columns;
		this.pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece getPiece(int row, int column) {
		if (!positionExists(row, column)) {
			Position wrongPosition = new Position(row, column);
			throw new BoardException("Position " + wrongPosition + " does not exist in the board");
		}

		return pieces[row][column];
	}

	public Piece getPiece(Position position) {
		return getPiece(position.getRow(), position.getColumn());
	}

	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece in position " + position);
		}

		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	public Piece removePiece(Position position) {
		if(getPiece(position) == null)
			return null;
		
		Piece piece = getPiece(position);
		piece.position = null;
		
		pieces[position.getRow()][position.getColumn()] = null;
		return piece;
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean thereIsAPiece(Position position) {
		return getPiece(position) != null;
	}
}
