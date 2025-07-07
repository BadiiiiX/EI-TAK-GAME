package fr.esiea.mali.ui.icons;

import fr.esiea.mali.core.model.piece.PieceKind;

import javax.swing.*;
import java.awt.*;

public class StoneIcon implements Icon {
    private final PieceKind kind;
    private final int size = 48;

    public StoneIcon(PieceKind kind) {
        this.kind = kind;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color fill = Color.LIGHT_GRAY;
        g2.setColor(fill);
        switch (kind) {
            case FLAT:
                g2.fillOval(x, y, size, size);
                g2.setColor(Color.DARK_GRAY);
                g2.drawOval(x, y, size, size);
                break;
            case STANDING:
                Polygon tri = new Polygon(
                        new int[]{x + size / 2, x, x + size},
                        new int[]{y, y + size, y + size},
                        3
                );
                g2.fill(tri);
                g2.setColor(Color.DARK_GRAY);
                g2.draw(tri);
                break;
            case CAPSTONE:
                g2.fillRect(x, y, size, size);
                g2.setColor(Color.DARK_GRAY);
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(x, y, size, size);
                break;
        }
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}
