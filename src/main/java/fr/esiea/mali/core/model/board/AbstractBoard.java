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
    public Deque<IPiece> getStackAt(Position position) {
        if (!this.isInside(position)) {
            throw new IndexOutOfBoundsException();
        }
        return this.board.get(position.row()).get(position.col());
    }


    @Override
    public boolean isInside(Position pos) {
        return pos.row() >= 0 && pos.row() < this.getSize()
                && pos.col() >= 0 && pos.col() < this.getSize();
    }

    @Override
    public boolean isEmpty() {
        return this.board.stream()
                .flatMap(List::stream)
                .allMatch(Deque::isEmpty);
    }

    @Override
    public void dropPieces(List<IPiece> toDrop, Position to, List<Integer> drops) {
        Position current = to;
        int idx = 0;
        int dx = Integer.compare(drops.size() > 1 ? toDrop.get(1).hashCode() : 0, 0);
        int dy = Integer.compare(drops.size() > 1 ? toDrop.get(1).hashCode() : 0, 0);
        for (int d : drops) {
            Deque<IPiece> stack = getStackAt(current);
            for (int i = 0; i < d; i++) {
                stack.push(toDrop.get(idx++));
            }
            current = current.translate(dx, dy);
        }
    }

    @Override
    public int totalPiecesOnBoard() {
        return this.board.stream()
                .flatMap(List::stream)
                .mapToInt(Deque::size)
                .sum();
    }

    protected abstract List<List<Deque<IPiece>>>  fillBoard();
}
