package fr.esiea.mali.core.model.board;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.piece.IPiece;

import java.util.Deque;
import java.util.List;

/**
 * Interface describing the Tak game board.
 * A board is an N×N grid of piece stacks (Deque<IPiece>).
 * 
 * @author Mehdi A.
 */
public interface IBoard {

    /**
     * Returns the dimension of the board.
     * 
     * @return the size of the board (N for an N×N board)
     */
    int getSize();

    /**
     * Returns the internal representation of the board.
     * 
     * @return the 2D list of piece stacks
     */
    List<List<Deque<IPiece>>> getBoard();

    /**
     * Checks if a position is inside the board boundaries (0 ≤ row,col < N).
     *
     * @param pos the position to check
     * @return true if the position is valid
     */
    boolean isInside(Position pos);

    /**
     * Retrieves the stack of pieces at the given position.
     *
     * @param pos the target position
     * @return the Deque of IPiece at (row, col)
     * @throws IndexOutOfBoundsException if the position is not on the board
     */
    Deque<IPiece> getStackAt(Position pos);

    /**
     * Lists the valid adjacent positions (up, down, left, right).
     *
     * @param pos the starting position
     * @return list of neighboring positions
     */
    List<Position> getNeighbors(Position pos);

    /**
     * Successively places pieces according to a drop pattern, starting from a position and
     * moving in a direction defined by Move.
     *
     * @param toDrop list of carried pieces (bottom to top order)
     * @param to     starting position for the drop
     * @param drops  list of quantities to drop on each square
     */
    void dropPieces(List<IPiece> toDrop, Position to, List<Integer> drops);

    /**
     * Applies a complete Move: placement or slide.
     *
     * @param move the move to apply
     */
    void applyMove(Move move);

    /**
     * Creates a deep clone of the board, including all stacks and pieces.
     *
     * @return new independent instance
     */
    IBoard copy();

    /**
     * Checks if the board is empty.
     * 
     * @return true if all stacks are empty
     */
    boolean isEmpty();

    /**
     * Counts the total number of pieces on the board.
     * 
     * @return the total number of pieces present on the board
     */
    int totalPiecesOnBoard();
}
