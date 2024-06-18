package chess.pieces;

import java.util.concurrent.atomic.AtomicInteger;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] getPossibleMoves() {
		boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

		AtomicInteger rookRow = new AtomicInteger(position.getRow());
		AtomicInteger rookColumn = new AtomicInteger(position.getColumn());
		
		countPossibleMoves(rookRow, rookColumn, possibleMoves, () -> rookRow.set(rookRow.get() + 1)); // bottom
		countPossibleMoves(rookRow, rookColumn, possibleMoves, () -> rookColumn.set(rookColumn.get() + 1)); // right
		countPossibleMoves(rookRow, rookColumn, possibleMoves, () -> rookRow.set(rookRow.get() - 1)); // top
		countPossibleMoves(rookRow, rookColumn, possibleMoves, () -> rookColumn.set(rookColumn.get() - 1)); // left

		return possibleMoves;
	}

	private void countPossibleMoves(AtomicInteger row, AtomicInteger column, boolean[][] possibleMoves, Runnable callback) {

		int initialRow = row.get();
		int initialColumn = column.get();

		while (true) {
			callback.run();
			
			if (!getBoard().positionExists(row.get(), column.get()))
				break;

			if (isThereOpponentPiece(row.get(), column.get())) {
				possibleMoves[row.get()][column.get()] = true;
				break;
			}

			if (getBoard().thereIsAPiece(row.get(), column.get())) {
				possibleMoves[row.get()][column.get()] = false;
				break;
			}

			possibleMoves[row.get()][column.get()] = true;
		}
		
		row.set(initialRow);
		column.set(initialColumn);
	}
}
