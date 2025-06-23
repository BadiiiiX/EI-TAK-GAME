package fr.esiea.mali.core.model.match;

import fr.esiea.mali.core.model.match.events.PlayerPassedEvent;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.PlayerId;
import fr.esiea.mali.core.service.impl.IGame;

import java.util.HashMap;
import java.util.Map;

public class Round {
    private final int number;
    private final IGame game;
    private final long timeLimitMs;
    private final Map<PlayerId, Integer> consecutivePasses = new HashMap<>();
    private long lastActionTimestamp;
    private boolean ended = false;

    public Round(int number, IGame game, long timeLimitMs) {
        this.number = number;
        this.game = game;
        this.timeLimitMs = timeLimitMs;
    }

    public void start() {
        game.start();
        this.lastActionTimestamp = System.currentTimeMillis();

    }

    public void playMove(Move move) throws IllegalStateException {
        this.ensureNotEnded();
        this.ensureNotTimedOut();

        game.playMove(move);
        this.lastActionTimestamp = System.currentTimeMillis();

        consecutivePasses.put(this.getCurrentPlayer().getId(), 0);
    }

    public void pass() {
        ensureNotEnded();
        ensureNotTimedOut();

        consecutivePasses.merge(this.getCurrentPlayer().getId(), 1, Integer::sum);
        lastActionTimestamp = System.currentTimeMillis();
        this.game.getEventBus().post(new PlayerPassedEvent(this.getCurrentPlayer().getId()));  // au lieu de addPenalty()
        checkEndOfRound();
    }

    public void tick() {
        if (ended) return;
        if (System.currentTimeMillis() - lastActionTimestamp >= timeLimitMs) {
            pass();
        }
    }

    public void end() {
        this.ended = true;
    }

    private IPlayer getCurrentPlayer() {
        return game.getState().getCurrentPlayer();
    }

    public void checkEndOfRound() {
        if (consecutivePasses.getOrDefault(this.getCurrentPlayer().getId(), 0) >= 3) {
            this.end();
        }
    }

    private void ensureNotEnded() {
        if (ended) {
            throw new IllegalStateException("Round " + number + " already ended");
        }
    }

    private void ensureNotTimedOut() {
        if (System.currentTimeMillis() - lastActionTimestamp >= timeLimitMs) {
            throw new IllegalStateException("Time limit exceeded for round " + number);
        }
    }

}
