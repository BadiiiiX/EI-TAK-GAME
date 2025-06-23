package fr.esiea.mali.core.model.player;

import fr.esiea.mali.core.model.team.TeamColor;

public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(PlayerId id, TeamColor team, String name, PlayerInventory inventory) {
        super(id, team, name, inventory);
    }

}
