package fr.esiea.mali.core.model.board;

public record Position(int row, int col) {

    public Position {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Position arguments must be positive");
        }
    }

    public Position translate(int row, int col) {
        return new Position(this.row + row, this.col + col);
    }
}
