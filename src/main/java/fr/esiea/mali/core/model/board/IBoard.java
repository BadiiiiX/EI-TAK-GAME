package fr.esiea.mali.core.model.board;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.piece.IPiece;

import java.util.Deque;
import java.util.List;

/**
 * Interface décrivant le plateau de jeu Tak.
 * Un plateau est une grille N×N de piles de pièces (Deque<IPiece>).
 */
public interface IBoard {

    /**
     * @return la dimension du plateau (N pour un plateau N×N)
     */
    int getSize();

    List<List<Deque<IPiece>>> getBoard();

    /**
     * Vérifie si une position est à l'intérieur des limites (0 ≤ row,col < N).
     *
     * @param pos la position à tester
     * @return true si pos est valide
     */
    boolean isInside(Position pos);

    /**
     * Récupère la pile de pièces à la position donnée.
     *
     * @param pos la position cible
     * @return la Deque d'IPiece en (row, col)
     * @throws IndexOutOfBoundsException si pos n'est pas dans le plateau
     */
    Deque<IPiece> getStackAt(Position pos);

    /**
     * Liste les positions adjacentes valides (haut, bas, gauche, droite).
     *
     * @param pos la position de départ
     * @return liste de positions voisines
     */
    List<Position> getNeighbors(Position pos);

    /**
     * Retire un certain nombre de pièces du sommet de la pile et les renvoie ordonnées bas→haut.
     *
     * @param from  position source
     * @param count nombre de pièces à prendre
     * @return liste de pièces extraites
     */
    //List<IPiece> pickUpPieces(Position from, int count);

    /**
     * Pose successivement des pièces selon un schéma de drops, à partir d'une position et
     * en avançant dans une direction définie par Move.
     *
     * @param toDrop liste de pièces transportées (ordre bas→haut)
     * @param to     position de départ du dépôt
     * @param drops  liste des quantités déposées case par case
     */
    void dropPieces(List<IPiece> toDrop, Position to, List<Integer> drops);

    /**
     * Applique un Move complet : placement ou slide.
     *
     * @param move le coup à appliquer
     */
    void applyMove(Move move);

    /**
     * Clone profond du plateau, incluant toutes les piles et pièces.
     *
     * @return nouvelle instance indépendante
     */
    IBoard copy();

    /**
     * @return true si toutes les piles sont vides
     */
    boolean isEmpty();

    /**
     * @return le nombre total de pièces présentes sur le plateau
     */
    int totalPiecesOnBoard();
}

