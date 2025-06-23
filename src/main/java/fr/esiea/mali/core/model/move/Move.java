package fr.esiea.mali.core.model.move;

import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.player.IPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Représente un coup dans une partie de Tak :
 * - Placement : pose d’une pièce neuve (flat, standing ou capstone)
 * - Slide     : déplacement d’une pile (pickup + drops)
 */
public final class Move {

    private final IPlayer author;
    /**
     * Source du slide (null pour un placement).
     */
    private final Position from;
    /**
     * Cible du placement ou première case du slide.
     */
    private final Position to;
    /**
     * Nombre de pièces déplacées ou posées (1 pour un placement).
     */
    private final int count;
    /**
     * Séquence des dépôts pour un slide (vide pour un placement).
     */
    private final List<Integer> drops;
    /**
     * Type de placement, ou null si slide.
     */
    private final PieceKind placementType;

    private Move(
            IPlayer author,
            Position from,
            Position to,
            int count,
            List<Integer> drops,
            PieceKind placementType) {
        Objects.requireNonNull(to, "to ne peut pas être null");
        this.author = author;
        this.from = from;
        this.to = to;
        this.count = count;
        this.drops = (drops == null ? Collections.emptyList() : List.copyOf(drops));
        this.placementType = placementType;
    }

    // --- Factory methods ---

    public static Move placementStone(IPlayer player, Position to, boolean standing) {
        return new Move(
                player,
                null,
                to,
                1,
                Collections.emptyList(),
                standing ? PieceKind.STANDING : PieceKind.FLAT
        );
    }

    public static Move placementCapstone(IPlayer player, Position to) {
        return new Move(
                player,
                null,
                to,
                1,
                Collections.emptyList(),
                PieceKind.CAPSTONE
        );
    }

    public static Move slide(IPlayer player, Position from, Position to, int count, List<Integer> drops) {
        Objects.requireNonNull(player, "player ne peut pas être null");
        Objects.requireNonNull(from, "from ne peut pas être null");
        if (count <= 0) {
            throw new IllegalArgumentException("count doit être > 0");
        }
        if (drops == null || drops.isEmpty()) {
            throw new IllegalArgumentException("drops ne peut pas être vide");
        }

        int sum = drops.stream().mapToInt(Integer::intValue).sum();
        if (sum != count) {
            throw new IllegalArgumentException("somme(drops) doit == count");
        }

        return new Move(player, from, to, count, drops, null);
    }

    public Direction direction() {
        int dRow = Integer.signum(to.row() - from.row());
        int dCol = Integer.signum(to.col() - from.col());
        return new Direction(dRow, dCol);
    }

    public boolean isPlacement() {
        return placementType != null;
    }

    public boolean isSlide() {
        return placementType == null;
    }

    public boolean isFlatStoneMove() {
        return placementType == PieceKind.FLAT;
    }

    public boolean isStandingStoneMove() {
        return placementType == PieceKind.STANDING;
    }

    public boolean isCapstoneMove() {
        return placementType == PieceKind.CAPSTONE;
    }

    public IPlayer getAuthor() {
        return author;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public int getCount() {
        return count;
    }

    public List<Integer> getDrops() {
        return drops;
    }

    public PieceKind getPlacementType() {
        return placementType;
    }

    public List<Position> pathPositions() {
        List<Position> path = new ArrayList<>(drops.size());
        Position cur = from;
        Direction dir = direction();
        for (int i = 0; i < drops.size(); i++) {
            cur = cur.translate(dir.row(), dir.col());
            path.add(cur);
        }
        return path;
    }
}
