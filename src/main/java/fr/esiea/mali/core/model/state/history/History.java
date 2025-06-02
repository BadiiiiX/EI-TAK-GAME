package fr.esiea.mali.core.model.state.history;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.state.history.IHistory;

import java.util.ArrayDeque;
import java.util.Deque;

public class History  implements IHistory {
    private final Deque<Move> moves;

    public History() {
        this.moves = new ArrayDeque<>();
    }

    public void record(Move move) {
        if (move == null) {
            throw new IllegalArgumentException("Move ne peut pas être null");
        }
        moves.push(move);
    }

    public boolean canUndo() {
        return !moves.isEmpty();
    }

    public Move undo() throws IllegalStateException {
        if (!canUndo()) {
            throw new IllegalStateException("Aucun coup à annuler");
        }
        return moves.pop();
    }

    public int size() {
        return moves.size();
    }
}
