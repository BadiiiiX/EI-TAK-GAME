package fr.esiea.mali.core.model.state.history;

import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.move.Move;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {

    @Test
    void recordAndUndoSingleMove() {
        History history = new History();
        Move m1 = Move.placementStone(new Position(0, 0), true);

        assertFalse(history.canUndo());
        history.record(m1);
        assertTrue(history.canUndo());
        Move undone = history.undo();
        assertEquals(m1, undone);
        assertFalse(history.canUndo());
    }

    @Test
    void undoEmptyHistoryThrows() {
        History history = new History();
        assertThrows(IllegalStateException.class, history::undo);
    }

    @Test
    void recordedMovesPreserveOrder() {
        History history = new History();
        Move m1 = Move.placementStone(new Position(0, 0), false);
        Move m2 = Move.placementCapstone(new Position(1, 1));
        Move m3 = Move.slide(new Position(0, 0), new Position(0, 2), 1, List.of(1, 0));

        history.record(m1);
        history.record(m2);
        history.record(m3);

        assertEquals(m3, history.undo());
        assertEquals(m2, history.undo());
        assertEquals(m1, history.undo());
        assertFalse(history.canUndo());
    }

}
