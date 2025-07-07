package fr.esiea.mali.core.model.match;

import fr.esiea.mali.core.model.player.PlayerId;

import java.util.HashMap;
import java.util.Map;

public class Scoreboard {

    private final Map<PlayerId, Integer> scores = new HashMap<>();

    public void addVictory(PlayerId playerId, int points) {
        scores.merge(playerId, points, Integer::sum);
    }

    public void addPenalty(PlayerId playerId, int points) {
        scores.merge(playerId, -points, Integer::sum);
    }

    public int getScore(PlayerId playerId) {
        return scores.getOrDefault(playerId, 0);
    }

    public Map<PlayerId, Integer> getAllScores() {
        return scores;
    }

}
