package fr.esiea.mali.core.model.state.history;

import fr.esiea.mali.core.model.move.Move;

/**
 * Interface representing the history of moves in a Tak game.
 * Provides functionality to record moves, undo moves, and check the size of the history.
 * 
 * @author Mehdi A.
 */
public interface IHistory {

    /**
     * Records a move in the history.
     * 
     * @param move the move to record
     */
    void record(Move move);

    /**
     * Undoes the last move in the history.
     * 
     * @return the move that was undone
     * @throws IllegalStateException if there are no moves to undo
     */
    Move undo() throws IllegalStateException;

    /**
     * Gets the number of moves in the history.
     * 
     * @return the size of the history
     */
    int size();

}
