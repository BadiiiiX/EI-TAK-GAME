package fr.esiea.mali.core.model.state.history;

import fr.esiea.mali.core.model.move.Move;

public interface IHistory {

    void record(Move move);

    Move undo() throws IllegalStateException;

    int size();

}
