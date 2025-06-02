package fr.esiea.mali.core.model.board;

import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.team.TeamColor;

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
                .mapToObj(_ -> new ArrayList<Deque<IPiece>>(Collections.nCopies(size, new ArrayDeque<>())))
                .collect(Collectors.toList());
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

                    // Couleur : W ou B
                    char ownerInitial = top.getColor() == TeamColor.WHITE ? 'W' : 'B';

                    // Type du sommet : F, S ou C
                    PieceKind kind = top.getKind();
                    char kindInitial = switch (kind) {
                        case PieceKind.CAPSTONE -> 'C';
                        case PieceKind.STANDING -> 'S';
                        default -> 'F';
                    };

                    // Format fixe à 5 caractères : "[hXY]" avec h sur 2 caractères
                    sb.append(String.format("[%2d%c%c]", height, ownerInitial, kindInitial));
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}
