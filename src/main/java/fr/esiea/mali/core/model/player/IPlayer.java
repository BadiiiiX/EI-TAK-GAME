package fr.esiea.mali.core.model.player;

import fr.esiea.mali.core.model.team.TeamColor;

/**
 * Interface representing a player in the Tak game.
 * Each player has a unique ID, name, color, and an inventory of pieces.
 * 
 * @author Mehdi A.
 */
public interface IPlayer {

    /**
     * Gets the unique identifier of the player.
     * 
     * @return the player's ID
     */
    PlayerId getId();

    /**
     * Gets the display name of the player.
     * 
     * @return the player's name
     */
    String getName();

    /**
     * Gets the team color of the player.
     * 
     * @return the player's color
     */
    TeamColor getColor();

    /**
     * Gets the player's inventory of game pieces.
     * 
     * @return the player's inventory
     */
    PlayerInventory getInventory();

}
