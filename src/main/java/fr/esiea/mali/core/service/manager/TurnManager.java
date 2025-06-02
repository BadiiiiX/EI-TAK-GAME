package fr.esiea.mali.core.service.manager;

import fr.esiea.mali.core.model.player.IPlayer;

import java.util.List;

public class TurnManager {
    private final List<IPlayer> players;
    private int currentIndex = 0;
    private int turnNumber = 1;

    public TurnManager(List<IPlayer> players) {
        if (players == null || players.size() != 2) {
            throw new IllegalArgumentException("deux joueurs requis");
        }
        this.players = players;
    }

    public IPlayer getCurrentPlayer() {
        return players.get(currentIndex);
    }

    public IPlayer nextPlayer() {
        currentIndex = (currentIndex + 1) % players.size();
        if (currentIndex == 0) {
            turnNumber++;
        }
        return getCurrentPlayer();
    }

    public IPlayer previousPlayer() {
        if (currentIndex == 0) {
            turnNumber = Math.max(1, turnNumber - 1);
            currentIndex = players.size() - 1;
        } else {
            currentIndex--;
        }
        return getCurrentPlayer();
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
