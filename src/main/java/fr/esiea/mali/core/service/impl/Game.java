package fr.esiea.mali.core.service.impl;

import fr.esiea.mali.core.model.board.IBoard;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.GameState;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.service.manager.TurnManager;

public class Game implements IGame {

    private final IBoard board;
    private IGameState state;
    private final TurnManager turnManager;

    public Game(IBoard board, TurnManager turnManager) {
        this.board = board;
        this.turnManager = turnManager;
        this.state = null;
    }

    @Override
    public void start() {
        this.state = new GameState(board.copy(), turnManager.getCurrentPlayer());
    }

    @Override
    public void playMove(Move move) {

        //TODO PROCESS PLAY (validation, board process...)

        state.getHistory().record(move);

        turnManager.nextPlayer();
        state.setCurrentPlayer(turnManager.getCurrentPlayer());

        //TODO CHECK WIN
    }

    @Override
    public void undo() {
        Move last = state.getHistory().undo();

        //TODO inverse move on board

        IPlayer prev = turnManager.previousPlayer();
        state.setCurrentPlayer(prev);

        //TODO refund methods
        if (last.isCapstoneMove()) {
            //prev.getInventory().refundCapstone();
        } else {
            //prev.getInventory().refundStone();
        }
    }

    @Override
    public IGameState getState() {
        // On retourne une copie pour préserver l’encapsulation
        return state;
    }
}
