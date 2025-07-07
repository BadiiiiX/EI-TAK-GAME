package fr.esiea.mali.core.model.match;

import fr.esiea.mali.core.model.board.BoardSize;

public record MatchSettings(int totalRounds, int penaltyPointsPerRound, long timePerTurnMs,
                            BoardSize boardSize) {

}
