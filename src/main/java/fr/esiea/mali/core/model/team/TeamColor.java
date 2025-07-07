package fr.esiea.mali.core.model.team;

import java.awt.*;

public enum TeamColor {
    BLACK(0, 0, 0),
    WHITE(255, 255, 255);

    TeamColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    private final int red;
    private final int green;
    private final int blue;

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public Color toAWT() {
        return new Color(this.red, this.green, this.blue);
    }

    @Override
    public String toString() {
        return (this.equals(TeamColor.BLACK)) ? "B" : "W";
    }
}
