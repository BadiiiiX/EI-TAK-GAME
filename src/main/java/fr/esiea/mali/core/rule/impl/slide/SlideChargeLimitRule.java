package fr.esiea.mali.core.rule.impl.slide;

import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.MoveValidator;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.rule.api.exceptions.InvalidMoveException;

/**
 * @Rule : We cannot exceed the charge limit (max == board size).
 */
public class SlideChargeLimitRule implements MoveValidator {
    @Override
    public void validate(IGameState state, Move move) {
        if (!move.isSlide()) return;
        int max = state.getBoard().getSize();
        if (move.getCount() > max) {
            throw new InvalidMoveException("Too much charge");
        }
    }
}