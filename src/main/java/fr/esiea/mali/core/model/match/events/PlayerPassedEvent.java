package fr.esiea.mali.core.model.match.events;

import fr.esiea.mali.core.event.IEvent;
import fr.esiea.mali.core.model.player.PlayerId;

public record PlayerPassedEvent(PlayerId playerId) implements IEvent {
}
