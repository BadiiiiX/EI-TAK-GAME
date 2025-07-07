package fr.esiea.mali.ui.pages;

import fr.esiea.mali.core.model.board.BoardSize;
import fr.esiea.mali.core.model.team.TeamColor;
import fr.esiea.mali.ui.controller.UIController;
import fr.esiea.mali.ui.validation.NonEmptyVerifier;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ConfigPage extends JPanel {
    private final JTextField name1Field = new JTextField(12);
    private final JTextField name2Field = new JTextField(12);
    private final JSpinner roundsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 20, 1));
    private final JComboBox<BoardSize> sizeCombo = new JComboBox<>(BoardSize.values());
    private final JSpinner timeSpinner = new JSpinner(new SpinnerNumberModel(30, 1, 6_00, 1));
    private final JSpinner penaltySpinner = new JSpinner(new SpinnerNumberModel(30, 0, 300, 5));

    private final NonEmptyVerifier nonE = new NonEmptyVerifier();

    public ConfigPage(UIController controller) {
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel pageTitle = new JLabel("Configuration de la Partie", SwingConstants.CENTER);
        pageTitle.setFont(pageTitle.getFont().deriveFont(Font.BOLD, 24f));
        add(pageTitle, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1, 0, 20));
        center.add(createPlayersPanel());
        center.add(createSettingsPanel(controller));
        add(center, BorderLayout.CENTER);
    }

    private JLabel requiredLabel(String text) {
        JLabel lbl = new JLabel(text + " *");
        lbl.setForeground(Color.RED);
        return lbl;
    }

    private JPanel createPlayersPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Players",
                TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints gbc = createGbc();
        // Player 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        p.add(requiredLabel("Player 1 Name:"), gbc);
        gbc.gridx = 1;
        p.add(name1Field, gbc);
        gbc.gridx = 2;
        p.add(createColorDot(TeamColor.WHITE), gbc);

        // Player 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        p.add(requiredLabel("Player 2 Name:"), gbc);
        gbc.gridx = 1;
        p.add(name2Field, gbc);
        gbc.gridx = 2;
        p.add(createColorDot(TeamColor.BLACK), gbc);

        name1Field.setInputVerifier(nonE);
        name2Field.setInputVerifier(nonE);

        return p;
    }

    private JPanel createSettingsPanel(UIController controller) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Match Settings",
                TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints gbc = createGbc();
        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row++;
        p.add(new JLabel("Board Size:"), gbc);
        gbc.gridx = 1;
        sizeCombo.setSelectedItem(BoardSize.MEDIUM);
        p.add(sizeCombo, gbc);

        // Total rounds
        gbc.gridx = 0;
        gbc.gridy = row++;
        p.add(new JLabel("Total Rounds:"), gbc);
        gbc.gridx = 1;
        p.add(roundsSpinner, gbc);

        // Time per turn
        gbc.gridx = 0;
        gbc.gridy = row++;
        p.add(new JLabel("Time per Turn (s):"), gbc);
        gbc.gridx = 1;
        p.add(timeSpinner, gbc);

        // Penalty
        gbc.gridx = 0;
        gbc.gridy = row++;
        p.add(new JLabel("Penalty (time exceeded):"), gbc);
        gbc.gridx = 1;
        p.add(penaltySpinner, gbc);

        // Boutons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton cancelBtn = new JButton("Cancel");
        JButton okBtn = new JButton("OK");
        buttons.add(cancelBtn);
        buttons.add(okBtn);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        p.add(buttons, gbc);

        // Listeners
        okBtn.addActionListener(e -> {

            if(!nonE.verify(name1Field) || !nonE.verify(name2Field)) {
                return;
            }

            String p1 = name1Field.getText().trim();
            String p2 = name2Field.getText().trim();
            BoardSize size = (BoardSize) sizeCombo.getSelectedItem();
            int rounds = (int) roundsSpinner.getValue();
            long timePerTurnMs = (int) timeSpinner.getValue() * 1000L;
            int penaltyPts = (int) penaltySpinner.getValue();
            controller.onConfigConfirmed(p1, p2, size, rounds, timePerTurnMs, penaltyPts);
        });
        cancelBtn.addActionListener(e -> controller.onShowStart());

        return p;
    }

    private JLabel createColorDot(TeamColor tc) {
        JLabel dot = new JLabel();
        dot.setOpaque(true);
        dot.setBackground(tc.toAWT());
        dot.setPreferredSize(new Dimension(16, 16));
        dot.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return dot;
    }

    private GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        return gbc;
    }
}