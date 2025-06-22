package fr.esiea.mali.core.service.factory;

import fr.esiea.mali.core.model.piece.Capstone;
import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.piece.Stone;
import fr.esiea.mali.core.model.team.TeamColor;

public class PieceFactory {

    public static IPiece createPlacementPiece(
            TeamColor owner,
            PieceKind kind
    ) {
        if (kind == PieceKind.CAPSTONE) {
            return createCapstone(owner);
        } else {
            return createStone(owner, kind);
        }
    }

    public static Stone createStone(TeamColor color, PieceKind kind) {
        return new Stone(color, kind);
    }

    public static Capstone createCapstone(TeamColor color) {
        return new Capstone(color);
    }
}
