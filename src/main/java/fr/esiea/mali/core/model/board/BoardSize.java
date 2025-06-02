package fr.esiea.mali.core.model.board;

public enum BoardSize {
    TINY   (3, 10, 0),
    SMALL  (4, 15, 0),
    MEDIUM (5, 21, 0),
    LARGE  (6, 30, 1),
    HUGE   (8, 50, 2);

    private final int size;
    private final int initialStones;
    private final int initialCapstones;

    BoardSize(int gridSize, int stones, int capstones) {
        this.size = gridSize;
        this.initialStones = stones;
        this.initialCapstones = capstones;
    }

    public int getSize()       { return size; }
    public int getInitialStones()  { return initialStones; }
    public int getInitialCapstones() { return initialCapstones; }
}
