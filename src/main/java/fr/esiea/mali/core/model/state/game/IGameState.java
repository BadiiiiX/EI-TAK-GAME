package fr.esiea.mali.core.model.state.game;

import fr.esiea.mali.core.model.board.IBoard;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.history.IHistory;

import java.util.Optional;

public interface IGameState {

    IBoard getBoard();
    IPlayer getCurrentPlayer();
    void setCurrentPlayer(IPlayer currentPlayer);

    IHistory getHistory();

    Optional<IPlayer> getWinner();
    void setWinner(Optional<IPlayer> winner);

}
