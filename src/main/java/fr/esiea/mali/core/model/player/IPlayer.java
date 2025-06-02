package fr.esiea.mali.core.model.player;

import fr.esiea.mali.core.model.team.TeamColor;

public interface IPlayer {

    String getName();

    TeamColor getColor();

    PlayerInventory getInventory();

}
