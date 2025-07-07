package fr.esiea.mali.core.rule.api;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.state.game.IGameState;

/**
 * Interface defining rules for capturing pieces in the Tak game.
 * Implementations determine how pieces are captured after a move.
 * 
 * @author Mehdi A.
 */
public interface CaptureRule {

    /**
     * Applies capture rules after a move has been made.
     * Modifies the game state to reflect any captures that occurred.
     * 
     * @param state the current game state
     * @param move the move that was just played
     */
    void applyCapture(IGameState state, Move move);

}
