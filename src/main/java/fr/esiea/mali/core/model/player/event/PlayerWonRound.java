package fr.esiea.mali.core.model.player.event;

import fr.esiea.mali.core.event.IEvent;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.PlayerInventory;
import fr.esiea.mali.core.model.state.game.IGameState;

import java.util.Objects;


public record PlayerWonRound(IPlayer player,
                             IGameState gameState) implements IEvent {

    public int prize() {
        int boardSize = gameState.getBoard().getSize();
        int basePts   = boardSize * boardSize;

        // on récupère l’autre joueur
        IPlayer opponent = gameState.getPlayers().stream()
                .filter(p -> !Objects.equals(p.getId(), player.getId()))
                .findFirst()
                .orElseThrow();

        PlayerInventory inv = opponent.getInventory();
        int remaining = inv.getStoneCount()
                + inv.getCapstoneCount();

        return basePts + remaining;
    }
}
