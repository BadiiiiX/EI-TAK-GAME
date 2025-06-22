package fr.esiea.mali.core.rule.impl.capture;

import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.CaptureRule;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.service.factory.PieceFactory;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * Règle : une capstone aplatit les pierres debout sur la case cible.
 */
public class CapstoneFlattenRule implements CaptureRule {
    @Override
    public void applyCapture(IGameState state, Move move) {
        if (!move.isSlide() || !move.isCapstoneMove()) return;

        List<Position> path = move.pathPositions();
        Position finalPos = path.getLast();

        Deque<IPiece> stack = state.getBoard().getStackAt(finalPos);
        if(stack.isEmpty()) return;

        IPiece capstone = stack.removeLast();

        if (!stack.isEmpty() && stack.peekLast().getKind() == PieceKind.STANDING) {
            IPiece stand = stack.removeLast();
            stack.addLast(PieceFactory.createStone(stand.getColor(), PieceKind.FLAT));
        }

        stack.addLast(capstone);
    }
}