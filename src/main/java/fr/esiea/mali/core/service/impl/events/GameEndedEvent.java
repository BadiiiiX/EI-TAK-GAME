package fr.esiea.mali.core.service.impl.events;

import fr.esiea.mali.core.event.IEvent;
import fr.esiea.mali.core.model.state.game.IGameState;

public record GameEndedEvent(IGameState gameState) implements IEvent {
}
