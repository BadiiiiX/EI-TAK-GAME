package fr.esiea.mali;

import fr.esiea.mali.ui.controller.UIController;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Optionnel : régler le look-and-feel du système
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ignored) {}

        // Lancer l'UI sur l'Event Dispatch Thread
        SwingUtilities.invokeLater(UIController::new);
    }
}