package fr.esiea.mali.core.model.piece;

import fr.esiea.mali.core.model.team.TeamColor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    @Test
    void stoneCannotBeCapStoneShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Stone(TeamColor.WHITE, PieceKind.CAPSTONE));
    }

    @Test
    void stoneAndCapstoneInstantiation() {
        IPiece flat = new Stone(TeamColor.WHITE, PieceKind.FLAT);
        IPiece stand  = new Stone(TeamColor.WHITE, PieceKind.STANDING);
        IPiece capstone = new Capstone(TeamColor.WHITE);

        assertSame(PieceKind.FLAT, flat.getKind());
        assertSame(PieceKind.STANDING, stand.getKind());
        assertSame(PieceKind.CAPSTONE, capstone.getKind());
    }

}
