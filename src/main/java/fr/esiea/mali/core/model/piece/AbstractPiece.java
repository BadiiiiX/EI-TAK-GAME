package fr.esiea.mali.core.model.piece;

import fr.esiea.mali.core.model.team.TeamColor;

import java.util.Objects;

public abstract class AbstractPiece implements IPiece {

    protected final TeamColor color;
    protected final PieceKind kind;

    protected AbstractPiece(TeamColor color, PieceKind kind) {
        this.color = Objects.requireNonNull(color);
        this.kind = Objects.requireNonNull(kind);
    }

    @Override
    public TeamColor getColor() {
        return color;
    }

    @Override
    public PieceKind getKind() {
        return kind;
    }

    @Override
    public void flatten() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This piece cannot be flattened.");
    }
}
