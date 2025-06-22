package fr.esiea.mali.core.rule.impl.slide;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.model.team.TeamColor;
import fr.esiea.mali.core.rule.api.MoveValidator;
import fr.esiea.mali.core.rule.api.exceptions.InvalidMoveException;

import java.util.Deque;

/**
 * @Rule : We cannot slide a stack if we don't have the ownership
 */
public class SlideOwnershipRule implements MoveValidator {
    @Override
    public void validate(IGameState state, Move move) {
        if (!move.isSlide()) return;
        Deque<IPiece> stack = state.getBoard().getStackAt(move.getFrom());
        TeamColor actualColor = move.getAuthor().getColor();
        if (stack.isEmpty() || stack.peekLast().getColor() != actualColor) {
            throw new InvalidMoveException("Vous ne contrôlez pas la pile à " + move.getFrom());
        }
    }
}