package fr.esiea.mali.ui.controller;

import fr.esiea.mali.core.event.EventBus;
import fr.esiea.mali.core.model.board.BoardSize;
import fr.esiea.mali.core.model.match.MatchSettings;
import fr.esiea.mali.core.model.match.events.PlayerPassedEvent;
import fr.esiea.mali.core.model.match.exceptions.TimeLimitExceededException;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.move.events.MovePlayedEvent;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.PlayerId;
import fr.esiea.mali.core.model.player.event.PlayerWonRound;
import fr.esiea.mali.core.rule.api.exceptions.InvalidMoveException;
import fr.esiea.mali.core.service.impl.Match;
import fr.esiea.mali.ui.frame.MainWindow;
import fr.esiea.mali.ui.pages.GamePage;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UIController {

    private final MainWindow window;
    private Match match;

    private javax.swing.Timer swingTimer;

    public UIController() {
        this.window = new MainWindow(this);
        this.onShowStart();
    }

    public void onShowConfig() {
        window.showPage("CONFIG");
    }

    public void onShowStart() {
        window.showPage("START");
    }

    public Match getMatch() {
        return match;
    }

    public void onConfigConfirmed(String name1,
                                  String name2,
                                  BoardSize size,
                                  int totalRounds,
                                  long timePerTurn,
                                  int penaltyPoints) {

        MatchSettings settings = new MatchSettings(totalRounds, penaltyPoints, timePerTurn, size);
        this.match = new Match(name1, name2, settings);

        match.startNextRound();

        this.window.defineGamePage(this);
        window.showPage("GAME");
        GamePage gamePage = window.getGamePage();

        swingTimer = new javax.swing.Timer(1000, e -> {
            try {
                match.getCurrentRound().tick();
            } catch (TimeLimitExceededException ex) {
                this.match.getCurrentRound().getGame().getEventBus().post(new PlayerPassedEvent(this.match.getCurrentRound().getGame().getState().getCurrentPlayer().getId()));
            }
            long left = match.getCurrentRound().timeLeft();
            SwingUtilities.invokeLater(() -> gamePage.updateTimer(left));
        });
        swingTimer.start();

        EventBus bus = match.getCurrentRound().getGame().getEventBus();

        bus.register(MovePlayedEvent.class, ev ->
                SwingUtilities.invokeLater(() -> gamePage.updateBoard(ev.gameState()))
        );
        bus.register(PlayerPassedEvent.class, ev -> {
            SwingUtilities.invokeLater(() -> {

                match.getCurrentRound().pass();
                match.getScoreboard().addPenalty(ev.playerId(), this.match.getSettings().penaltyPointsPerRound());
                match.getCurrentRound().getGame().changeTurn(null);
                gamePage.updateScores(match.getScoreboard());

                gamePage.updatePass(ev.playerId());
            });
        });

        bus.register(PlayerWonRound.class, ev ->
                SwingUtilities.invokeLater(() -> {
                    if (ev.gameState().getWinner().isPresent()) {
                        this.pauseTimer();
                        JOptionPane.showMessageDialog(window,
                                "Round finished! Winner = " +
                                        ev.gameState().getWinner().get().getName()
                        );
                    }
                    gamePage.updateScores(match.getScoreboard());

                    this.resumeTimer();

                    if (match.hasNextRound()) {
                        match.startNextRound();
                    } else {

                        Map<PlayerId, Integer> scores = match.getScoreboard().getAllScores();

                        Map<String, Integer> userScores = new HashMap<>();

                        scores.forEach((key, value) -> this.getPlayers().stream().filter(p -> p.getId().equals(key)).findFirst().ifPresent(p -> userScores.put(p.getName(), value)));


                        this.pauseTimer();

                        JOptionPane.showMessageDialog(window,
                                "Match over! Final scores:\n" +
                                        userScores.entrySet().stream()
                                                .map(e -> e.getKey() + ": " + e.getValue())
                                                .collect(Collectors.joining("\n"))

                        );

                        this.onShowStart();

                    }
                })
        );

        gamePage.updateBoard(match.getCurrentRound().getGame().getState());
        gamePage.updateScores(match.getScoreboard());
    }

    public IPlayer getCurrentPlayer() {
        return match.getCurrentRound().getGame()
                .getState()
                .getCurrentPlayer();
    }

    public List<IPlayer> getPlayers() {
        return match.getCurrentRound().getGame()
                .getState()
                .getPlayers();
    }

    public void onMoveRequested(Move move) {
        try {
            match.getCurrentRound().playMove(move);
        } catch (InvalidMoveException e) {
            JOptionPane.showMessageDialog(
                    window,
                    e.getMessage(),
                    "Invalid Move",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void pauseTimer()  { if (swingTimer != null) swingTimer.stop(); }
    public void resumeTimer() { if (swingTimer != null) swingTimer.start(); }

    public void onPassRequested() {
        this.getMatch().getCurrentRound().getGame().getEventBus().post(new PlayerPassedEvent(this.getCurrentPlayer().getId()));
    }
}
