package fr.esiea.mali.core.service.impl.events;

import fr.esiea.mali.core.event.IEvent;
import fr.esiea.mali.core.model.state.game.IGameState;

public record GameStartedEvent(IGameState gameState) implements IEvent {
}
