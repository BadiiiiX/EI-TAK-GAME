package fr.esiea.mali.ui.frame;

import fr.esiea.mali.ui.controller.UIController;
import fr.esiea.mali.ui.pages.ConfigPage;
import fr.esiea.mali.ui.pages.GamePage;
import fr.esiea.mali.ui.pages.StartPage;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel container = new JPanel(cardLayout);

    private final StartPage  startPage;
    private final ConfigPage configPage;
    private GamePage   gamePage;

    public MainWindow(UIController controller) {
        super("Tak");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.startPage = new StartPage(controller);
        this.configPage = new ConfigPage(controller);
        container.add(startPage, "START");
        container.add(configPage, "CONFIG");

        add(container, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public StartPage  getStartPage()  { return startPage; }
    public ConfigPage getConfigPage() { return configPage; }
    public GamePage   getGamePage()   { return gamePage; }


    public void defineGamePage(UIController controller) {
        this.gamePage   = new GamePage(controller);
        container.add(gamePage,   "GAME");
    }

    public void showPage(String name) {
        cardLayout.show(container, name);
    }

}
