package fr.esiea.mali.core.model.player;

import fr.esiea.mali.core.model.team.TeamColor;

public class AIPlayer extends AbstractPlayer {

    public AIPlayer(PlayerId id, TeamColor team, String name, PlayerInventory inventory) {
        super(id, team, name, inventory);
    }

}
