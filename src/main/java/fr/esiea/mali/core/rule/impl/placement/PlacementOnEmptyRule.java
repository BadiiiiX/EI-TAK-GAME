package fr.esiea.mali.core.rule.impl.placement;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.MoveValidator;
import fr.esiea.mali.core.rule.api.exceptions.InvalidMoveException;

/**
 * @Rule : Placement can be operated only on an empty stack
 */
public class PlacementOnEmptyRule implements MoveValidator {
    @Override
    public void validate(IGameState state, Move move) {
        if (!move.isPlacement()) return;
        if (!state.getBoard().getStackAt(move.getTo()).isEmpty()) {
            throw new InvalidMoveException("Stack is not empty.");
        }
    }
}
