package fr.esiea.mali.core.rule.api;

import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.IGameState;

import java.util.Optional;

/**
 * Interface defining victory conditions in the Tak game.
 * Implementations determine when a player has won the game.
 * 
 * @author Mehdi A.
 */
public interface VictoryCondition {

    /**
     * Checks if any player has met the victory conditions.
     * 
     * @param state the current game state to check
     * @return an Optional containing the winning player, or empty if no player has won yet
     */
    Optional<IPlayer> checkVictory(IGameState state);

}
