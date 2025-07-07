package fr.esiea.mali.core.service.impl;

import fr.esiea.mali.core.event.EventBus;
import fr.esiea.mali.core.model.move.events.MovePlayedEvent;
import fr.esiea.mali.core.model.player.event.PlayerWonRound;
import fr.esiea.mali.core.service.impl.events.GameEndedEvent;
import fr.esiea.mali.core.service.impl.events.GameStartedEvent;
import fr.esiea.mali.core.model.board.IBoard;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.GameState;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.engine.RuleEngine;
import fr.esiea.mali.core.service.manager.TurnManager;

public class Game implements IGame {

    private final IBoard board;
    private IGameState state;
    private final RuleEngine ruleEngine;
    private final TurnManager turnManager;
    private final EventBus bus;

    public Game(IBoard board, RuleEngine ruleEngine, TurnManager turnManager, EventBus bus) {
        this.board = board;
        this.ruleEngine = ruleEngine;
        this.turnManager = turnManager;
        this.state = null;
        this.bus = bus;
    }

    @Override
    public void start() {
        this.state = new GameState(board.copy(), turnManager.getPlayers(), turnManager.getCurrentPlayer());
        bus.post(new GameStartedEvent(this.getState()));
    }

    public void changeTurn(Move move) {
        turnManager.nextPlayer();
        state.setCurrentPlayer(turnManager.getCurrentPlayer());


        bus.post(new MovePlayedEvent(move, state));
    }

    @Override
    public void playMove(Move move) {

        ruleEngine.processMove(state, move);

        state.getWinner().ifPresent(winner -> {
            bus.post(new PlayerWonRound(winner, state));
            bus.post(new GameEndedEvent(state));
        });

        state.getHistory().record(move);


        changeTurn(move);
    }

    @Override
    public void undo() {
        Move last = state.getHistory().undo();

        //TODO inverse move on board

        IPlayer prev = turnManager.previousPlayer();
        state.setCurrentPlayer(prev);

        if (last.isCapstoneMove()) {
            prev.getInventory().refundCapstone();
        } else {
            prev.getInventory().refundStone();
        }
    }

    @Override
    public IGameState getState() {
        return state;
    }

    @Override
    public EventBus getEventBus() {
        return this.bus;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }
}
