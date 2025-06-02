package fr.esiea.mali.core.model.board;

import fr.esiea.mali.core.model.piece.IPiece;

import java.util.*;

public abstract class AbstractBoard implements IBoard{

    protected BoardSize size;
    protected List<List<Deque<IPiece>>> board;

    public AbstractBoard(BoardSize size) {
        this.size = size;
        this.board = fillBoard();
    }

    @Override
    public List<List<Deque<IPiece>>> getBoard() {
        return board;
    }

    @Override
    public int getSize() {
        return this.size.getSize();
    }

    @Override
    public Deque<IPiece> getTile(Position position) {
        return this.board.get(position.row()).get(position.col());
    }

    protected abstract List<List<Deque<IPiece>>>  fillBoard();
}
