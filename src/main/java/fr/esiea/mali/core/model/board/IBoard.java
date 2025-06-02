package fr.esiea.mali.core.model.board;

import fr.esiea.mali.core.model.piece.IPiece;

import java.util.Deque;
import java.util.List;

public interface IBoard {

    int getSize();
    List<List<Deque<IPiece>>> getBoard();
    Deque<IPiece> getTile(Position position);
    IBoard copy();
}
