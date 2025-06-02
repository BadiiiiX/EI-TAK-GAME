package fr.esiea.mali.core.service.factory;

import fr.esiea.mali.core.model.board.Board;
import fr.esiea.mali.core.model.board.BoardSize;

public class BoardFactory {
    public static Board create(BoardSize boardSize) {
        return new Board(boardSize);
    }
}
