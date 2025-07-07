package fr.esiea.mali.core.service.impl;

import fr.esiea.mali.core.event.EventBus;
import fr.esiea.mali.core.model.match.MatchSettings;
import fr.esiea.mali.core.model.match.Round;
import fr.esiea.mali.core.model.match.Scoreboard;
import fr.esiea.mali.core.model.match.events.PlayerWonPoints;
import fr.esiea.mali.core.model.player.HumanPlayer;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.event.PlayerWonRound;
import fr.esiea.mali.core.model.team.TeamColor;
import fr.esiea.mali.core.service.factory.GameFactory;
import fr.esiea.mali.core.service.factory.PlayerFactory;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private final Scoreboard scoreboard = new Scoreboard();
    private final MatchSettings settings;
    private final List<Round> rounds = new ArrayList<>();
    private final EventBus sharedBus = new EventBus();
    private int currentRound = 0;


    private final IPlayer firstPlayer;
    private final IPlayer secondPlayer;

    public Match(String firstName, String secondName, MatchSettings settings) {
        this.settings = settings;
        this.firstPlayer = PlayerFactory.create(firstName, TeamColor.WHITE, this.settings.boardSize());
        this.secondPlayer = PlayerFactory.create(secondName, TeamColor.BLACK, this.settings.boardSize());
    }

    public boolean hasNextRound() {
        return this.currentRound + 1 <= this.settings.totalRounds();
    }

    public void startNextRound() {

        if (!hasNextRound()) {
            throw new IllegalStateException("No more rounds in this match");
        }

        IGame game = GameFactory.create(this.settings.boardSize(), (HumanPlayer) firstPlayer, (HumanPlayer) secondPlayer, sharedBus);

        Round round = new Round(++this.currentRound, game, this.settings);
        this.rounds.add(round);

        round.start();

        EventBus bus = game.getEventBus();

        bus.register(PlayerWonPoints.class, ev -> this.scoreboard.addVictory(ev.playerId(), ev.score()));
        bus.register(PlayerWonRound.class, ev -> this.scoreboard.addVictory(ev.player().getId(), ev.prize()));
    }

    public Round getCurrentRound() {
        return this.rounds.get(this.currentRound - 1);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public MatchSettings getSettings() {
        return settings;
    }
}
