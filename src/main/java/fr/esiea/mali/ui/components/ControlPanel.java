package fr.esiea.mali.ui.components;

import fr.esiea.mali.core.model.match.Scoreboard;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.PlayerId;
import fr.esiea.mali.ui.controller.UIController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.LinkedHashMap;

public class ControlPanel extends JPanel {
    private final JLabel currentPlayerLabel = new JLabel("Current: —");
    private final JLabel timerLabel         = new JLabel("00:00");
    private final JButton passButton        = new JButton("Pass");

    private final UIController controller;

    private final Map<PlayerId, JLabel> scoreLabels = new LinkedHashMap<>();
    private final Map<PlayerId, JLabel> passLabels  = new LinkedHashMap<>();
    private final Map<PlayerId, Integer> passCount  = new LinkedHashMap<>();

    public ControlPanel(UIController controller) {

        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Controls"));
        setPreferredSize(new Dimension(200, 0));

        // SECTION: current player & timer
        currentPlayerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentPlayerLabel.setFont(currentPlayerLabel.getFont().deriveFont(Font.BOLD, 14f));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel.setFont(timerLabel.getFont().deriveFont(Font.PLAIN, 12f));

        add(Box.createVerticalStrut(10));
        add(currentPlayerLabel);
        add(Box.createVerticalStrut(5));
        add(timerLabel);
        add(Box.createVerticalStrut(15));

        // SECTION: pass button
        passButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(passButton);
        passButton.addActionListener(e -> controller.onPassRequested());
        add(Box.createVerticalStrut(15));

        // SECTION: scores & passes per player
        JPanel scoresPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        scoresPanel.setBorder(BorderFactory.createTitledBorder("Scores / Passes"));
        for (IPlayer player : this.controller.getPlayers()) {
            JLabel lbl = new JLabel(player.getName() + ": 0 pts");
            lbl.setFont(lbl.getFont().deriveFont(Font.PLAIN, 12f));
            scoreLabels.put(player.getId(), lbl);
            scoresPanel.add(lbl);
            passCount.put(player.getId(), 0);

            JLabel passLbl = new JLabel("Passes: 0");
            passLbl.setFont(passLbl.getFont().deriveFont(Font.ITALIC, 11f));
            passLabels.put(player.getId(), passLbl);
            scoresPanel.add(passLbl);
        }
        add(scoresPanel);
        add(Box.createVerticalGlue());
    }

    public void updateCurrentPlayer(String name) {
        currentPlayerLabel.setText("Current: " + name);
    }

    public void updateTimer(long timeLeftMs) {
        long secs = timeLeftMs / 1000;
        long mins = secs / 60;
        secs %= 60;
        timerLabel .setText(String.format("%02d:%02d", mins, secs));
    }

    public void updateScores(Scoreboard scoreboard) {
        this.controller.getPlayers().forEach(player -> {
            JLabel lbl = scoreLabels.get(player.getId());
            if (lbl != null) {
                lbl.setText(player.getName() + ": " + scoreboard.getScore(player.getId()) + " pts");
            }
        });
    }

    public void updatePassCount(PlayerId pid) {
        JLabel lbl = passLabels.get(pid);

        passCount.merge(pid, 1, Integer::sum);
        if (lbl != null) {
            lbl.setText("Passes: " + passCount.get(pid));
        }
    }

    public JButton getPassButton() {
        return passButton;
    }
}
