package chess;

import board.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8)
			throw new ChessException("Invalid chess position: " + column + ", " + row);
			
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	public Position toBoardPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	public static ChessPosition fromBoardPosition(Position boardPosition) {
		return new ChessPosition((char)(boardPosition.getColumn() + 'a'), 8 - boardPosition.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}
}
