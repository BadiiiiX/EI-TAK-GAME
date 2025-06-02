package fr.esiea.mali.core.model.move;

import fr.esiea.mali.core.model.board.Position;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Représente un coup dans une partie de Tak.
 * Peut être soit : <br>
 *  - un placement : on ajoute une pièce neuve (flat, standing ou capstone) sur une case vide, <br>
 *  - un slide : on déplace une pile depuis une case source vers une ou plusieurs cases cibles (avec découpage).
 */
public final class Move {

    private final Position from;

    private final Position to;

    /**
     * Pour un slide : nombre de jetons pris de la pile source. <br>
     * Pour un placement : toujours 1 (le nombre de pièces neuves déposées).
     */
    private final int count;

    /**
     * Pour un slide : liste des quantités déposées case par case le long de la direction. <br>
     * Ex. : [2, 1] signifie qu’on dépose 2 jetons sur la première case, puis 1 jeton sur la seconde, etc... <br>
     * Pour un placement : vide.
     */
    private final List<Integer> drops;

    private final boolean isCapstone;

    private final boolean isStanding;

    private final boolean isPlacement;


    private Move(Position from,
                 Position to,
                 int count,
                 List<Integer> drops,
                 boolean isCapstone,
                 boolean isStanding,
                 boolean isPlacement) {
        if (to == null) {
            throw new IllegalArgumentException("La position cible (to) ne peut pas être null.");
        }
        this.from = from;
        this.to = to;
        this.count = count;
        this.drops = (drops == null ? Collections.emptyList() : List.copyOf(drops));
        this.isCapstone = isCapstone;
        this.isStanding = isStanding;
        this.isPlacement = isPlacement;
    }


    /**
     * Crée un Move de placement (flat ou standing).
     * @param to            position où l’on veut poser la nouvelle pièce <br>
     * @param standingStone true pour une stone debout, false pour une stone plate <br>
     * @return un Move configuré pour un placement (isPlacement=true, isCapstone=false, isStanding selon param)
     */
    public static Move placementStone(Position to, boolean standingStone) {
        return new Move(
                null,
                to,
                1,
                Collections.emptyList(),
                false,
                standingStone,
                true
        );
    }

    /**
     * Crée un Move de placement de capstone.
     * @param to position où l’on veut poser le capstone
     * @return un Move configuré pour un placement de capstone (isCapstone=true, isStanding=false)
     */
    public static Move placementCapstone(Position to) {
        Objects.requireNonNull(to, "Position cible ne peut pas être null");
        return new Move(
                null,
                to,
                1,
                Collections.emptyList(),
                true,
                false,
                true
        );
    }

    /**
     * Crée un Move de slide (déplacement d’une pile).
     * @param from   position source (nombre de jetons ≥ count)
     * @param to     position de la première case visée par le slide
     * @param count  nombre de jetons à transporter depuis la pile source
     * @param drops  liste des dépôts à chaque case (doit être non vide, somme(drops) == count)
     * @return un Move configuré pour un slide (isPlacement=false, isCapstone=false)
     */
    public static Move slide(Position from, Position to, int count, List<Integer> drops) {
        Objects.requireNonNull(from, "Position source ne peut pas être null");
        Objects.requireNonNull(to, "Position cible ne peut pas être null");
        if (count <= 0) {
            throw new IllegalArgumentException("count doit être strictement positif");
        }
        if (drops == null || drops.isEmpty()) {
            throw new IllegalArgumentException("drops ne peut pas être vide pour un slide");
        }
        int sum = drops.stream().mapToInt(Integer::intValue).sum();
        if (sum != count) {
            throw new IllegalArgumentException("La somme des elements de drops doit égaler count");
        }
        return new Move(
                from,
                to,
                count,
                drops,
                false,
                false,
                false
        );
    }

    public boolean isPlacement() {
        return isPlacement;
    }

    public boolean isSlide() {
        return !isPlacement;
    }

    public boolean isCapstoneMove() {
        return isPlacement && isCapstone;
    }

    public boolean isStandingStoneMove() {
        return isPlacement && isStanding && !isCapstone;
    }

    public boolean isFlatStoneMove() {
        return isPlacement && !isStanding && !isCapstone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;
        return count == move.count
                && isCapstone == move.isCapstone
                && isStanding == move.isStanding
                && isPlacement == move.isPlacement
                && Objects.equals(from, move.from)
                && Objects.equals(to, move.to)
                && Objects.equals(drops, move.drops);
    }

}