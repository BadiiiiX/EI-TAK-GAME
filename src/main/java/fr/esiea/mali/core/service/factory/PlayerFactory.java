package fr.esiea.mali.core.service.factory;

import fr.esiea.mali.core.model.player.HumanPlayer;
import fr.esiea.mali.core.model.board.BoardSize;
import fr.esiea.mali.core.model.player.PlayerInventory;
import fr.esiea.mali.core.model.team.TeamColor;

public class PlayerFactory {
    public static HumanPlayer create(String name, TeamColor color, BoardSize boardSize) {
        PlayerInventory inventory = new PlayerInventory(boardSize.getInitialStones(), boardSize.getInitialCapstones());

        System.out.println(inventory.getCapstoneCount());

        return new HumanPlayer(color, name, inventory);
    }
}
