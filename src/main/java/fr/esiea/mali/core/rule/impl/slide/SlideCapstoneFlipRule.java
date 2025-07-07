package fr.esiea.mali.core.rule.impl.slide;

import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.MoveValidator;
import fr.esiea.mali.core.rule.api.exceptions.InvalidMoveException;

import java.util.Deque;
import java.util.List;

public class SlideCapstoneFlipRule implements MoveValidator {

    @Override
    public void validate(IGameState state, Move move) {
        if(!move.isSlide() || !move.isCapstoneMove()) return;

        List<Position> path = move.pathPositions();

        Position finalPos = path.getLast();
        Deque<IPiece> stack = state.getBoard().getStackAt(finalPos);
        if(!stack.isEmpty() && stack.peekLast().getKind() == PieceKind.STANDING) {
            int lastDrop = move.getDrops().get(path.size() - 1);

            if(lastDrop != 1) {
                throw new InvalidMoveException("Only a capstone can be flipped on a standing piece.");
            }
        }
    }
}
