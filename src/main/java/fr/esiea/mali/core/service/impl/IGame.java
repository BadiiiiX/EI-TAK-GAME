package fr.esiea.mali.core.service.impl;

import fr.esiea.mali.core.event.EventBus;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.service.manager.TurnManager;

/**
 * Interface representing a Tak game.
 * Provides methods to control the game flow, manage moves, and access game components.
 * 
 * @author Mehdi A.
 */
public interface IGame {

    /**
     * Starts the game.
     * Initializes the game state and prepares for the first move.
     */
    void start();

    /**
     * Plays a move in the game.
     * 
     * @param move the move to be played
     */
    void playMove(Move move);

    /**
     * Undoes the last move in the game.
     * Reverts the game state to before the last move was played.
     */
    void undo();

    /**
     * Gets the current state of the game.
     * 
     * @return the current game state
     */
    IGameState getState();

    /**
     * Gets the event bus used for game events.
     * 
     * @return the event bus
     */
    EventBus getEventBus();

    /**
     * Gets the turn manager that controls player turns.
     * 
     * @return the turn manager
     */
    TurnManager getTurnManager();

    /**
     * Changes the turn after a move has been played.
     * 
     * @param move the move that was just played
     */
    void changeTurn(Move move);
}
