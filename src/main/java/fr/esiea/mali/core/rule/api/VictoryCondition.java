package fr.esiea.mali.core.rule.api;

import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.IGameState;

import java.util.Optional;

public interface VictoryCondition {

    Optional<IPlayer> checkVictory(IGameState state);
    
}
