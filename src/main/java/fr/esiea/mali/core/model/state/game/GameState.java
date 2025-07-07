package fr.esiea.mali.core.model.state.game;

import fr.esiea.mali.core.model.board.IBoard;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.history.History;
import fr.esiea.mali.core.model.state.history.IHistory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GameState implements IGameState {

    private IBoard board;
    private IPlayer currentPlayer;
    private final List<IPlayer> players;
    private final IHistory history;
    private IPlayer winner = null;

    public GameState(IBoard board, List<IPlayer> players, IPlayer currentPlayer) {
        this.setBoard(board);
        this.setCurrentPlayer(currentPlayer);
        this.history = new History();
        this.players = players;
    }

    public IBoard getBoard() {
        return board;
    }

    public List<IPlayer> getPlayers() {
        return this.players;
    }

    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public IHistory getHistory() {
        return history;
    }

    public Optional<IPlayer> getWinner() {
        return Optional.ofNullable(winner);
    }

    public void setBoard(IBoard board) {
        this.board = Objects.requireNonNull(board).copy();
    }

    public void setCurrentPlayer(IPlayer player) {
        this.currentPlayer = Objects.requireNonNull(player);
    }

    public void setWinner(IPlayer winner) {
        this.winner = winner;
    }

}
