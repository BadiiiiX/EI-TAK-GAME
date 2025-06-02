package fr.esiea.mali.core.model.player;

import fr.esiea.mali.core.model.team.TeamColor;

public abstract class AbstractPlayer implements IPlayer {

    protected TeamColor color;
    protected String name;
    protected PlayerInventory inventory;

    public AbstractPlayer(TeamColor color, String name, PlayerInventory inventory) {
        this.color = color;
        this.name = name;
        this.inventory = inventory;
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

}
