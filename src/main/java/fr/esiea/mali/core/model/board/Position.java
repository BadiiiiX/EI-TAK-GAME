package fr.esiea.mali.core.model.board;

import java.util.ArrayList;
import java.util.List;

public record Position(int row, int col) {

    public Position {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Position arguments must be positive");
        }
    }

    public Position translate(int row, int col) {
        return new Position(this.row + row, this.col + col);
    }

    public List<Position> getNeighbours(BoardSize boardSize) {
        int size = boardSize.getSize();
        List<Position> neighbours = new ArrayList<>();

        if(this.row > 0) {
            neighbours.add(new Position(this.row - 1, this.col));
        }

        if(this.row < size - 1) {
            neighbours.add(new Position(this.row + 1, this.col));
        }

        if(this.col > 0) {
            neighbours.add(new Position(this.row, this.row + 1));
        }

        if(this.col < size - 1) {
            neighbours.add(new Position(this.row, this.row + 1));
        }

        return neighbours;
    }
}
