package fr.esiea.mali.core.model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerInventoryTest {

    private PlayerInventory inventory;

    private final int INITIAL_STONES = 50;
    private final int INITIAL_CAPSTONES = 2;

    @BeforeEach
    public void setUp() {
        inventory = new PlayerInventory(INITIAL_STONES, INITIAL_CAPSTONES);
    }

    @Test
    void initialCountsAreCorrect() {
        assertEquals(INITIAL_STONES, inventory.getStoneCount());
        assertEquals(INITIAL_CAPSTONES, inventory.getCapstoneCount());
        assertTrue(inventory.hasStone());
        assertTrue(inventory.hasCapstone());
    }

    @Test
    void useStoneDecrementsAndReturnsTrueUntilZero() {
        for (int i = 0; i < INITIAL_STONES; i++) {
            assertTrue(inventory.hasStone());
            inventory.useStone();
        }
        assertFalse(inventory.hasStone());
        assertThrows(AssertionError.class, inventory::useStone);
        assertEquals(0, inventory.getStoneCount());
        assertEquals(INITIAL_CAPSTONES, inventory.getCapstoneCount());
    }

    @Test
    void useCapstoneDecrementsAndReturnsTrueUntilZero() {
        for (int i = 0; i < INITIAL_CAPSTONES; i++) {
            assertTrue(inventory.hasCapstone());
            inventory.useCapstone();
        }
        assertFalse(inventory.hasCapstone());
        assertThrows(AssertionError.class, () -> inventory.useCapstone());
        assertEquals(0, inventory.getCapstoneCount());
        assertEquals(INITIAL_STONES, inventory.getStoneCount());
    }
}
