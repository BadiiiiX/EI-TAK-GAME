package fr.esiea.mali.core.model.piece;

import fr.esiea.mali.core.model.team.TeamColor;

public interface IPiece {
    TeamColor getColor();

    PieceKind getKind();

    void flatten() throws UnsupportedOperationException;
}
