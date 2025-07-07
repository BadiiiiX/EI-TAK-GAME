package fr.esiea.mali.core.model.move.events;

import fr.esiea.mali.core.event.IEvent;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.state.game.IGameState;

public record MovePlayedEvent(Move move, IGameState gameState) implements IEvent {
    public Move move() {
        return move;
    }

    public IGameState gameState() {
        return gameState;
    }
}