package fr.esiea.mali.core.service.impl;

import fr.esiea.mali.core.event.EventBus;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.state.game.IGameState;

public interface IGame {

    void start();

    void playMove(Move move);

    void undo();

    IGameState getState();

    EventBus getEventBus();

}
