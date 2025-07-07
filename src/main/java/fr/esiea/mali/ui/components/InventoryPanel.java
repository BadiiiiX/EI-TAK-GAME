package fr.esiea.mali.ui.components;

import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.PlayerInventory;
import fr.esiea.mali.ui.icons.SpriteManager;

import javax.swing.*;
import java.awt.*;

public class InventoryPanel extends JPanel {
    private final JLabel nameLabel   = new JLabel();
    private final JLabel flatLabel   = new JLabel();
    private final JLabel capLabel    = new JLabel();

    public InventoryPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        nameLabel .setFont(nameLabel.getFont().deriveFont(Font.BOLD, 14f));
        add(nameLabel);
        add(flatLabel);
        add(capLabel);
    }

    public void update(IPlayer player) {
        PlayerInventory inv = player.getInventory();
        nameLabel .setText(player.getName());
        flatLabel .setIcon(new ImageIcon(SpriteManager.getSprite(PieceKind.FLAT, player.getColor())));
        flatLabel .setText("×" + inv.getStoneCount());
        capLabel  .setIcon(new ImageIcon(SpriteManager.getSprite(PieceKind.CAPSTONE, player.getColor())));
        capLabel  .setText("×" + inv.getCapstoneCount());
        revalidate();
        repaint();
    }
}