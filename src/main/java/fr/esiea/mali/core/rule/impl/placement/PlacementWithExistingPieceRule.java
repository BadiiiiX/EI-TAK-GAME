package fr.esiea.mali.core.rule.impl.placement;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.MoveValidator;
import fr.esiea.mali.core.rule.api.exceptions.InvalidMoveException;

/**
 * @Rule : Placement can be operated only if player has item
 */
public class PlacementWithExistingPieceRule implements MoveValidator {
    @Override
    public void validate(IGameState state, Move move) {
        if (!move.isPlacement()) return;

        var item = move.getPlacementType();
        var player = move.getAuthor();

        var value = switch (item) {
            case FLAT, STANDING -> player.getInventory().getStoneCount() >= 1;
            case CAPSTONE -> player.getInventory().getCapstoneCount() >= 1;
        };

        if(!value) {
            throw new InvalidMoveException("You don't this item to place.");
        }

    }
}
