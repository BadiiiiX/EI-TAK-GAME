package fr.esiea.mali.core.model.player;

public class PlayerInventory {

    private int capstone;
    private int stone;

    public PlayerInventory( int stone, int capstone) {
        this.stone = stone;
        this.capstone = capstone;
    }

    public int getCapstoneCount() {
        return capstone;
    }

    public int getStoneCount() {
        return stone;
    }

    public void useStone() {
        assert this.stone > 0;

        this.stone--;
    }

    public void useCapstone() {
        assert this.capstone > 0;

        this.capstone--;
    }

    public boolean hasStone() {
        return this.stone > 0;
    }

    public boolean hasCapstone() {
        return this.capstone > 0;
    }

}
