package fr.esiea.mali.core.service.impl;

import fr.esiea.mali.core.event.EventBus;
import fr.esiea.mali.core.model.board.BoardSize;
import fr.esiea.mali.core.model.match.Round;
import fr.esiea.mali.core.model.match.Scoreboard;
import fr.esiea.mali.core.model.match.events.PlayerPassedEvent;
import fr.esiea.mali.core.model.match.events.PlayerWonPoints;
import fr.esiea.mali.core.model.player.HumanPlayer;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.PlayerId;
import fr.esiea.mali.core.model.team.TeamColor;
import fr.esiea.mali.core.service.factory.GameFactory;
import fr.esiea.mali.core.service.factory.PlayerFactory;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private final BoardSize boardSize;
    private final int totalRounds;
    private final Scoreboard scoreboard = new Scoreboard();
    private final List<Round> rounds = new ArrayList<>();
    private int currentRound = 0;


    private final IPlayer firstPlayer;
    private final IPlayer secondPlayer;

    public Match(BoardSize size, String firstName, String secondName, int totalRounds) {
        this.totalRounds = totalRounds;
        this.boardSize = size;
        this.firstPlayer = PlayerFactory.create(firstName, TeamColor.WHITE, this.boardSize);
        this.secondPlayer = PlayerFactory.create(secondName, TeamColor.WHITE, this.boardSize);
    }

    public void startNextRound() {
        if (this.currentRound + 1 >= totalRounds) {
            throw new IllegalStateException("No more rounds in this match");
        }
        IGame game = GameFactory.create(this.boardSize, (HumanPlayer) firstPlayer, (HumanPlayer) secondPlayer);

        Round round = new Round(++this.currentRound, game, 50000); //TODO define time limit in setting class
        this.rounds.add(round);

        round.start();

        EventBus bus = game.getEventBus();
        bus.register(PlayerPassedEvent.class, ev -> managePlayerPassedEvent(ev.playerId()));
        bus.register(PlayerWonPoints.class, ev -> this.scoreboard.addVictory(ev.playerId(), ev.score()));
    }

    private void managePlayerPassedEvent(PlayerId playerId) {
        this.scoreboard.addPenalty(playerId, 30); //todo set penalty val in setting class
    }
}
