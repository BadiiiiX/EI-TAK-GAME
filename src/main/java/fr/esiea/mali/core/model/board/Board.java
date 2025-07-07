package fr.esiea.mali.core.model.board;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.service.factory.PieceFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board extends AbstractBoard{


    public Board(BoardSize size) {
        super(size);
    }

    @Override
    protected List<List<Deque<IPiece>>> fillBoard() {
        int size = this.size.getSize();
        return IntStream.range(0, size)
                .mapToObj(_ -> IntStream.range(0, size)
                        .mapToObj(_ -> (Deque<IPiece>) new ArrayDeque<IPiece>())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Position> getNeighbors(Position pos) {
        return pos.getNeighbours(this.size);
    }

    @Override
    public void applyMove(Move move) {
        if(move.isPlacement()) {
            this.applyPlacement(move);
        } else {
            this.applySlide(move);
        }
    }

    private void applyPlacement(Move move) {
        IPiece piece = PieceFactory.createPlacementPiece(
                move.getAuthor().getColor(),
                move.getPlacementType()
        );

        Position to = move.getTo();
        Deque<IPiece> stack = this.getStackAt(to);
        stack.push(piece);
    }

    private void applySlide(Move move) {

        Position cur = move.getFrom();
        int dRow = Integer.signum(move.getTo().row() - cur.row());
        int dCol = Integer.signum(move.getTo().col() - cur.col());

        Deque<IPiece> src = getStackAt(move.getFrom());
        List<IPiece> carried = new ArrayList<>();
        for (int i = 0; i < move.getCount(); i++) {
            carried.add(src.removeLast());
        }

        int idx = 0;
        for (int drop : move.getDrops()) {
            cur = cur.translate(dRow, dCol);
            if (!isInside(cur)) {
                throw new IllegalStateException("Slide out of bounds at " + cur);
            }
            Deque<IPiece> stack = getStackAt(cur);

            for (int i = 0; i < drop; i++) {
                stack.addLast(carried.get(idx++));
            }
        }
    }

    @Override
    public IBoard copy() {
        Board clone = new Board(this.size);

        for (int row = 0; row < this.size.getSize(); row++) {
            for (int column = 0; column < this.size.getSize(); column++) {
                Deque<IPiece> originalStack = this.board.get(row).get(column);
                Deque<IPiece> newStack = clone.board.get(row).get(column);

                for (IPiece piece : originalStack) {
                    newStack.addLast(piece);
                }
            }
        }

        return clone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < size.getSize(); r++) {
            for (int c = 0; c < size.getSize(); c++) {
                Deque<IPiece> stack = board.get(r).get(c);
                if (stack.isEmpty()) {
                    sb.append("[   ]");
                } else {
                    IPiece top = stack.peekLast();
                    int height = stack.size();

                    sb.append(String.format("[%2d%s%s]", height, top.getColor(), top.getKind()));
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}
