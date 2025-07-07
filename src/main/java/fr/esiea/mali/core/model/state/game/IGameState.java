package fr.esiea.mali.core.model.state.game;

import fr.esiea.mali.core.model.board.IBoard;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.history.IHistory;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing the current state of a Tak game.
 * Contains information about the board, players, current player, move history, and winner.
 * 
 * @author Mehdi A.
 */
public interface IGameState {

    /**
     * Gets the current game board.
     * 
     * @return the game board
     */
    IBoard getBoard();

    /**
     * Gets the list of players in the game.
     * 
     * @return the list of players
     */
    List<IPlayer> getPlayers();

    /**
     * Gets the player whose turn it currently is.
     * 
     * @return the current player
     */
    IPlayer getCurrentPlayer();

    /**
     * Sets the current player.
     * 
     * @param currentPlayer the player whose turn it is
     */
    void setCurrentPlayer(IPlayer currentPlayer);

    /**
     * Gets the history of moves in the game.
     * 
     * @return the game history
     */
    IHistory getHistory();

    /**
     * Gets the winner of the game, if any.
     * 
     * @return an Optional containing the winner, or empty if there is no winner yet
     */
    Optional<IPlayer> getWinner();

    /**
     * Sets the winner of the game.
     * 
     * @param winner the player who won the game
     */
    void setWinner(IPlayer winner);

}
