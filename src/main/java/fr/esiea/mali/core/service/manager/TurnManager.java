package fr.esiea.mali.core.service.manager;

import fr.esiea.mali.core.model.player.IPlayer;

import java.util.List;

/**
 * Manager class responsible for handling player turns in the Tak game.
 * Keeps track of the current player, turn number, and provides methods to advance or revert turns.
 * 
 * @author Mehdi A.
 */
public class TurnManager {
    private final List<IPlayer> players;
    private int currentIndex = 0;
    private int turnNumber = 1;

    /**
     * Creates a new TurnManager with the specified players.
     * 
     * @param players the list of players in the game
     * @throws IllegalArgumentException if players is null or does not contain exactly 2 players
     */
    public TurnManager(List<IPlayer> players) {
        if (players == null || players.size() != 2) {
            throw new IllegalArgumentException("deux joueurs requis");
        }
        this.players = players;
    }

    /**
     * Gets the list of players in the game.
     * 
     * @return the list of players
     */
    public List<IPlayer> getPlayers() {
        return players;
    }

    /**
     * Gets the player whose turn it currently is.
     * 
     * @return the current player
     */
    public IPlayer getCurrentPlayer() {
        return players.get(currentIndex);
    }

    /**
     * Advances to the next player's turn.
     * Cycles through the players in order.
     * 
     * @return the next player
     */
    public IPlayer nextPlayer() {
        this.currentIndex = (currentIndex + 1) % players.size();
        return getCurrentPlayer();
    }

    /**
     * Reverts to the previous player's turn.
     * Decreases the turn number if going back to the last player.
     * 
     * @return the previous player
     */
    public IPlayer previousPlayer() {
        if (currentIndex == 0) {
            turnNumber = Math.max(1, turnNumber - 1);
            currentIndex = players.size() - 1;
        } else {
            currentIndex--;
        }
        return getCurrentPlayer();
    }

    /**
     * Gets the current turn number.
     * 
     * @return the turn number
     */
    public int getTurnNumber() {
        return turnNumber;
    }
}
