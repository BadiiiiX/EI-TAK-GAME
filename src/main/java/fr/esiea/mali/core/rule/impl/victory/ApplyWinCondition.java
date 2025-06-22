package fr.esiea.mali.core.rule.impl.victory;

import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.VictoryCondition;

import java.util.Optional;

public class ApplyWinCondition implements VictoryCondition {
    @Override
    public Optional<IPlayer> checkVictory(IGameState state) {
        // TODO: rechercher alignements de flat stones
        return Optional.empty();
    }
}
