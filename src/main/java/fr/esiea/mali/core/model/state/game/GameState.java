package fr.esiea.mali.core.model.state.game;

import fr.esiea.mali.core.model.board.IBoard;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.history.History;
import fr.esiea.mali.core.model.state.history.IHistory;

import java.util.Objects;
import java.util.Optional;

public class GameState implements IGameState {

    private IBoard board;
    private IPlayer currentPlayer;
    private final IHistory history;
    private Optional<IPlayer> winner;

    public GameState(IBoard board, IPlayer currentPlayer) {
        this.setBoard(board);
        this.setCurrentPlayer(currentPlayer);
        this.history = new History();
        this.setWinner(Optional.empty());
    }

    private GameState(IBoard board,
                      IPlayer currentPlayer,
                      History history,
                      Optional<IPlayer> winner) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.history = history;
        this.winner = winner;
    }

    public IBoard getBoard() {
        return board;
    }

    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public IHistory getHistory() {
        return history;
    }

    public Optional<IPlayer> getWinner() {
        return winner;
    }

    public void setBoard(IBoard board) {
        this.board = Objects.requireNonNull(board).copy();
    }

    public void setCurrentPlayer(IPlayer player) {
        this.currentPlayer = Objects.requireNonNull(player);
    }

    public void setWinner(Optional<IPlayer> winner) {
        this.winner = Objects.requireNonNull(winner);
    }

}
