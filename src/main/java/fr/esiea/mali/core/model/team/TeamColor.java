package fr.esiea.mali.core.model.team;

public enum TeamColor {
    BLACK(255, 255, 255),
    WHITE(0, 0, 0);

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

    @Override
    public String toString() {
        return (this.equals(TeamColor.BLACK)) ? "B" : "W";
    }
}
