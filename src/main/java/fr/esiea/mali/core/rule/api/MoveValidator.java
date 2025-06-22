package fr.esiea.mali.core.rule.api;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.state.game.IGameState;

public interface MoveValidator {

    void validate(IGameState state, Move move);

}
