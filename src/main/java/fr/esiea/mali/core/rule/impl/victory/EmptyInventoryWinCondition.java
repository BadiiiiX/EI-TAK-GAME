package fr.esiea.mali.core.rule.impl.victory;

import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.VictoryCondition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmptyInventoryWinCondition extends AbstractCountFlatWinCondition implements VictoryCondition {
    @Override
    public Optional<IPlayer> checkVictory(IGameState state) {

        if (!allEmpty(state.getCurrentPlayer())) {
            return Optional.empty();
        }

        List<Map.Entry<IPlayer, Long>> sorted = countFlatPlayed(state).entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .toList();

        System.out.println("winner ! 2");


        return Optional.of(sorted.getFirst().getKey());
    }

    private boolean allEmpty(IPlayer player) {
        return player.getInventory().getStoneCount() == 0 &&
                player.getInventory().getCapstoneCount() == 0;
    }
}
