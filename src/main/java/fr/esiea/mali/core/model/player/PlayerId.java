package fr.esiea.mali.core.model.player;

import java.util.UUID;

public final class PlayerId {
    private final UUID id;

    public PlayerId() {
        this(UUID.randomUUID());
    }

    public PlayerId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerId other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
