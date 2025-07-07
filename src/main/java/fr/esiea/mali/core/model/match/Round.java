package fr.esiea.mali.core.model.match;

import fr.esiea.mali.core.model.match.exceptions.TimeLimitExceededException;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.PlayerId;
import fr.esiea.mali.core.model.player.event.PlayerWonRound;
import fr.esiea.mali.core.service.impl.IGame;

import java.util.HashMap;
import java.util.Map;

public class Round {
    private final int number;
    private final IGame game;
    private final MatchSettings matchSettings;
    private final Map<PlayerId, Integer> consecutivePasses = new HashMap<>();
    private long lastActionTimestamp;
    private boolean ended = false;

    public Round(int number, IGame game, MatchSettings matchSettings) {
        this.number = number;
        this.game = game;
        this.matchSettings = matchSettings;

        game.getTurnManager().getPlayers().forEach(p -> consecutivePasses.put(p.getId(), 0));
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
    }

    public void pass() {
        ensureNotEnded();

        consecutivePasses.merge(this.getCurrentPlayer().getId(), 1, Integer::sum);
        lastActionTimestamp = System.currentTimeMillis();

        ensureNotTooPassed(this.getCurrentPlayer().getId());
    }

    public void tick() {
        if (ended) return;
        if (System.currentTimeMillis() - lastActionTimestamp >= this.matchSettings.timePerTurnMs()) {
            pass();
            throw new TimeLimitExceededException("Time limit exceeded");
        }
    }

    public void end() {
        this.ended = true;
    }

    private IPlayer getCurrentPlayer() {
        return game.getState().getCurrentPlayer();
    }


    private void ensureNotEnded() {
        if (ended) {
            throw new IllegalStateException("Round " + number + " already ended");
        }
    }

    private void ensureNotTooPassed(PlayerId playerId) {
        if(consecutivePasses.getOrDefault(playerId, 0) >= 3) {

            var actualPlayer = this.getCurrentPlayer();
            var otherPlayer = this.getGame().getState().getPlayers().stream().filter(p -> !p.getId().equals(actualPlayer.getId())).findFirst().orElseThrow();

            this.game.getEventBus().post(new PlayerWonRound(otherPlayer, this.getGame().getState()));
        }
    }

    private void ensureNotTimedOut() {
        if (System.currentTimeMillis() - lastActionTimestamp >= this.matchSettings.timePerTurnMs()) {
            throw new TimeLimitExceededException("Time limit exceeded");
        }
    }

    public IGame getGame() {
        return game;
    }

    public long timeLeft() {
        long elapsed = System.currentTimeMillis() - lastActionTimestamp;
        return this.matchSettings.timePerTurnMs() - elapsed;
    }
}
