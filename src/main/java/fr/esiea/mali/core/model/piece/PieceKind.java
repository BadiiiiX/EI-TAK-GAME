package fr.esiea.mali.core.model.piece;

public enum PieceKind {
    FLAT,
    STANDING,
    CAPSTONE;

    @Override
    public String toString() {
        return switch (this) {
            case FLAT -> "F";
            case STANDING -> "S";
            case CAPSTONE -> "C";
        };
    }
}
