package fr.esiea.mali.ui.components;

import fr.esiea.mali.core.model.board.Position;
import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.piece.IPiece;
import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.model.team.TeamColor;
import fr.esiea.mali.ui.controller.UIController;
import fr.esiea.mali.ui.icons.SpriteManager;
import fr.esiea.mali.ui.icons.StoneIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardPanel extends JPanel {
    private static final int PADDING = 20;
    private static final Color LIGHT = new Color(240, 217, 181);
    private static final Color DARK = new Color(181, 136, 99);
    private static final Color SEL = new Color(255, 255, 0, 128);

    private final Map<String, Image> spriteCache = new HashMap<>();


    private final UIController controller;
    private IGameState state;
    private Position from, to;

    public BoardPanel(UIController controller) {
        this.controller = controller;
        this.state = controller.getMatch().getCurrentRound().getGame().getState();

        BoardPanel component = this;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state = controller.getMatch().getCurrentRound().getGame().getState();
                int n = state.getBoard().getSize();
                int w = getWidth() - 2 * PADDING;
                int h = getHeight() - 2 * PADDING;
                int cell = Math.min(w, h) / n;
                int col = (e.getX() - PADDING) / cell;
                int row = (e.getY() - PADDING) / cell;
                if (row < 0 || row >= n || col < 0 || col >= n) return;

                Position clicked = new Position(row, col);
                if (from == null) {
                    from = clicked;
                } else {
                    to = clicked;
                    Move move = null;
                    if (state.getBoard().getStackAt(from).isEmpty()) {
                        move = askPlacementDialog(from, component);
                    } else {
                        move = askSlideDialog(from, to, state.getBoard().getSize(), component);
                    }
                    if (move != null) {
                        controller.onMoveRequested(move);
                    }
                    from = to = null;
                }
                repaint();
            }
        });
    }

    private Move askPlacementDialog(Position pos, Component parent) {
        // Récupère le joueur courant pour déterminer la couleur
        var player = controller.getMatch()
                .getCurrentRound()
                .getGame()
                .getState()
                .getCurrentPlayer();
        TeamColor color = player.getColor();

        // Charge les vrais sprites depuis resources/icons/...
        ImageIcon flatIcon = new ImageIcon(SpriteManager.getSprite(PieceKind.FLAT, color));
        ImageIcon standingIcon = new ImageIcon(SpriteManager.getSprite(PieceKind.STANDING, color));
        ImageIcon capstoneIcon = new ImageIcon(SpriteManager.getSprite(PieceKind.CAPSTONE, color));

        // Création des boutons avec icône + texte
        JToggleButton flatBtn = new JToggleButton("Flat", flatIcon);
        JToggleButton standBtn = new JToggleButton("Standing", standingIcon);
        JToggleButton capBtn = new JToggleButton("Capstone", capstoneIcon);

        // Texte sous l’icône, et style
        for (JToggleButton b : List.of(flatBtn, standBtn, capBtn)) {
            b.setHorizontalTextPosition(SwingConstants.CENTER);
            b.setVerticalTextPosition(SwingConstants.BOTTOM);
            b.setPreferredSize(new Dimension(80, 100));
            b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            b.setFocusPainted(false);
            b.setContentAreaFilled(true);
        }
        flatBtn.setSelected(true);

        // Groupement pour sélection unique
        ButtonGroup group = new ButtonGroup();
        group.add(flatBtn);
        group.add(standBtn);
        group.add(capBtn);

        // Mise en page
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(flatBtn);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(standBtn);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(capBtn);
        panel.add(Box.createHorizontalGlue());
        panel.setBorder(BorderFactory.createTitledBorder("Place a piece at " + pos));

        // Affichage modal
        int res = JOptionPane.showConfirmDialog(
                parent, panel, "Choose Piece Type",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (res != JOptionPane.OK_OPTION) {
            return null;
        }

        // Construit le Move selon la sélection
        if (capBtn.isSelected()) {
            return Move.placementCapstone(player, pos);
        } else if (standBtn.isSelected()) {
            return Move.placementStone(player, pos, true);
        } else {
            return Move.placementStone(player, pos, false);
        }
    }


    private Move askSlideDialog(Position from,
                                Position to,
                                int maxCarry,
                                Component parent) {
        IGameState state = controller.getMatch()
                .getCurrentRound()
                .getGame()
                .getState();
        // Récupère la pile source et formate-la en chaîne
        var srcStack = state.getBoard().getStackAt(from).stream()
                .map(p -> p.getColor() + "-" + p.getKind())
                .toList();
        String stackDesc = srcStack.isEmpty()
                ? "<empty>"
                : String.join(" → ", srcStack);

        // Spinner pour le nombre à prendre (1 à maxCarry)
        SpinnerNumberModel countModel = new SpinnerNumberModel(1, 1, maxCarry, 1);
        JSpinner countSpinner = new JSpinner(countModel);
        ((JSpinner.DefaultEditor) countSpinner.getEditor())
                .getTextField().setColumns(3);

        // Champ texte pour la séquence de drops, ex. "2,1,1"
        JTextField dropsField = new JTextField("1", 10);

        // Construction du panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
                "Slide from " + from + " to " + to
        ));

        // Affiche la pile actuelle
        JLabel stackLabel = new JLabel("Source stack: " + stackDesc);
        stackLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(stackLabel);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Max carry: " + maxCarry));
        panel.add(Box.createVerticalStrut(5));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row1.add(new JLabel("Count to take:"));
        row1.add(countSpinner);
        panel.add(row1);
        panel.add(Box.createVerticalStrut(5));

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row2.add(new JLabel("Drops (comma‐sep):"));
        row2.add(dropsField);
        panel.add(row2);

        // Affichage de la boîte de dialogue
        int res = JOptionPane.showConfirmDialog(
                parent, panel, "Slide Pieces",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (res != JOptionPane.OK_OPTION) {
            return null;
        }

        // Lecture et validation des valeurs
        int count = (Integer) countSpinner.getValue();
        String text = dropsField.getText().trim();
        String[] tokens = text.split("\\s*,\\s*");
        List<Integer> drops = new ArrayList<>();
        try {
            int sum = 0;
            for (String t : tokens) {
                int v = Integer.parseInt(t);
                if (v < 1) throw new NumberFormatException("Each drop must be ≥1");
                drops.add(v);
                sum += v;
            }
            if (sum != count) {
                throw new IllegalArgumentException(
                        "Sum of drops (" + sum + ") must equal count (" + count + ")"
                );
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Invalid input:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }

        // Construire et renvoyer le Move
        IPlayer player = state.getCurrentPlayer();
        return Move.slide(player, from, to, count, drops);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        state = controller.getMatch().getCurrentRound().getGame().getState();
        int n = state.getBoard().getSize();
        int w = getWidth() - 2 * PADDING;
        int h = getHeight() - 2 * PADDING;
        int cell = Math.min(w, h) / n;
        Graphics2D g2 = (Graphics2D) g;

        // draw checkerboard
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                g2.setColor((r + c) % 2 == 0 ? LIGHT : DARK);
                g2.fillRect(PADDING + c * cell, PADDING + r * cell, cell, cell);
            }
        }

        // highlight from/to
        g2.setColor(SEL);
        if (from != null) g2.fillRect(PADDING + from.col() * cell, PADDING + from.row() * cell, cell, cell);
        if (to != null) g2.fillRect(PADDING + to.col() * cell, PADDING + to.row() * cell, cell, cell);

        // draw pieces
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                List<IPiece> stack = state.getBoard().getStackAt(new Position(r, c)).stream().toList();
                if (stack.isEmpty()) continue;
                var top = stack.getFirst();
                drawPiece(g2, top.getKind(), top.getColor(), r, c, cell);
            }
        }
    }

    private void drawPiece(Graphics2D g2, PieceKind kind, TeamColor col,
                           int row, int colIndex, int cell) {
        Image img = SpriteManager.getSprite(kind, col);
        int targetSize = cell - cell / 8;
        Image scaled = img.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
        int x = PADDING + colIndex * cell + (cell - targetSize) / 2;
        int y = PADDING + row * cell + (cell - targetSize) / 2;
        g2.drawImage(scaled, x, y, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    /**
     * Refresh after MovePlayedEvent
     */
    public void updateState(IGameState newState) {
        this.state = newState;
        this.from = this.to = null;
        repaint();
    }
}
