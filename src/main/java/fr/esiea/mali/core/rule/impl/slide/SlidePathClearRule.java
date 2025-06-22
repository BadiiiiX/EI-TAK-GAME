package fr.esiea.mali.core.rule.impl.slide;

import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.move.Direction;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.MoveValidator;
import fr.esiea.mali.core.rule.api.exceptions.InvalidMoveException;

import java.util.Deque;
import java.util.List;

public class SlidePathClearRule implements MoveValidator {
    @Override
    public void validate(IGameState state, Move move) {
        if(!move.isSlide()) return;

        Direction direction = move.direction();
        Position current =  move.getFrom();

        List<Integer> drops = move.getDrops();
        for(int step = 0; step < drops.size(); step++){
            current = current.translate(direction.row(), direction.col());
            Deque<IPiece> stack = state.getBoard().getStackAt(current);

            if(step == drops.size() - 1) return;
            if(stack.isEmpty()) continue;

            PieceKind top = stack.peekLast().getKind();
            if(top != PieceKind.FLAT) {
                throw new InvalidMoveException("Slide bloqué par un " + top + " en position intermédiaire " + current);
            }
        }
    }
}
