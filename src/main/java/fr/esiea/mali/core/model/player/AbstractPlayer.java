package fr.esiea.mali.core.model.player;

import fr.esiea.mali.core.model.team.TeamColor;

public abstract class AbstractPlayer implements IPlayer {

    protected final PlayerId id;
    protected TeamColor color;
    protected String name;
    protected PlayerInventory inventory;

    public AbstractPlayer(PlayerId id, TeamColor color, String name, PlayerInventory inventory) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.inventory = inventory;
    }

    @Override
    public PlayerId getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public PlayerInventory getInventory() {
        return inventory;
    }

    @Override
    public TeamColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HumanPlayer other)) return false;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
