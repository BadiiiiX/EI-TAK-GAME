package fr.esiea.mali.core.model.piece;

import fr.esiea.mali.core.model.team.TeamColor;

public class Stone extends AbstractPiece{

    public Stone(TeamColor color, PieceKind kind) {
        super(color, kind);

        if (kind == PieceKind.CAPSTONE) {
            throw new IllegalArgumentException("Stone can't be capstone.");
        }
    }
}
