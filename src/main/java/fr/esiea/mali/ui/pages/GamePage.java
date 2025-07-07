package fr.esiea.mali.ui.pages;

import fr.esiea.mali.core.model.match.Scoreboard;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.PlayerId;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.model.team.TeamColor;
import fr.esiea.mali.ui.components.BoardPanel;
import fr.esiea.mali.ui.components.ControlPanel;
import fr.esiea.mali.ui.components.InventoryPanel;
import fr.esiea.mali.ui.controller.UIController;

import javax.swing.*;
import java.awt.*;

public class GamePage extends JPanel {

    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private final InventoryPanel whiteInventory = new InventoryPanel();
    private final InventoryPanel blackInventory = new InventoryPanel();

    public GamePage(UIController controller) {
        super(new BorderLayout(5,5));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        boardPanel   = new BoardPanel(controller);
        controlPanel = new ControlPanel(controller);

        add(whiteInventory,    BorderLayout.NORTH);
        add(boardPanel,BorderLayout.CENTER);
        add(blackInventory, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.EAST);
    }

    public void updateBoard(IGameState state) {

        boardPanel.updateState(state);

        IPlayer current = state.getCurrentPlayer();
        controlPanel.updateCurrentPlayer(current.getName());

        var whitePlayer = state.getPlayers().stream().filter(player -> player.getColor() == TeamColor.WHITE).findFirst();
        var blackPlayer = state.getPlayers().stream().filter(player -> player.getColor() == TeamColor.BLACK).findFirst();

        if(whitePlayer.isPresent() && blackPlayer.isPresent()) {
            whiteInventory.update(whitePlayer.get());
            blackInventory.update(blackPlayer.get());
        }

    }

    public void updateTimer(long timeLeftMs) {
        controlPanel.updateTimer(timeLeftMs);
    }

    public void updatePass(PlayerId pid) {
        controlPanel.updatePassCount(pid);
    }

    public void updateScores(Scoreboard scoreboard) {
        controlPanel.updateScores(scoreboard);
    }
}
