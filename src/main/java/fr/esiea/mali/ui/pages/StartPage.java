package fr.esiea.mali.ui.pages;

import fr.esiea.mali.ui.controller.UIController;

import javax.swing.*;
import java.awt.*;

public class StartPage extends JPanel {

    public StartPage(UIController controller) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Titre centré en haut
        JLabel title = new JLabel("Tak — The Game", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 36f));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        add(center, BorderLayout.CENTER);

        // Panel des boutons en bas
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnStart = new JButton("Start");
        JButton btnQuit  = new JButton("Quit");

        btnStart.setPreferredSize(new Dimension(120, 40));
        btnQuit .setPreferredSize(new Dimension(120, 40));

        btnStart.addActionListener(e -> controller.onShowConfig());
        btnQuit .addActionListener(e -> System.exit(0));

        bottom.add(btnStart);
        bottom.add(btnQuit);
        add(bottom, BorderLayout.SOUTH);
    }

}
