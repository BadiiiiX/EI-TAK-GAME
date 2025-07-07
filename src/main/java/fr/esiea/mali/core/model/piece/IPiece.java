package fr.esiea.mali.core.model.piece;

import fr.esiea.mali.core.model.team.TeamColor;

/**
 * Interface representing a game piece in the Tak game.
 * Each piece has a color and a specific kind (flat stone, standing stone, or capstone).
 * 
 * @author Mehdi A.
 */
public interface IPiece {
    /**
     * Gets the color of the piece.
     * 
     * @return the team color of the piece
     */
    TeamColor getColor();

    /**
     * Gets the kind of the piece.
     * 
     * @return the kind of piece (flat, standing, or capstone)
     */
    PieceKind getKind();

    /**
     * Flattens a standing stone into a flat stone.
     * 
     * @throws UnsupportedOperationException if the piece cannot be flattened (e.g., it's already flat or is a capstone)
     */
    void flatten() throws UnsupportedOperationException;
}
