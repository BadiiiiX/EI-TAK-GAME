package fr.esiea.mali.core.model.piece;

import fr.esiea.mali.core.model.team.TeamColor;

public class Capstone extends AbstractPiece {
    public Capstone(TeamColor color) {
        super(color, PieceKind.CAPSTONE);
    }

    @Override
    public void flatten() {
        //TODO logique d'aplatissement ?
        throw new UnsupportedOperationException("flatten non implémentée ici");
    }
}
