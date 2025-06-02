package fr.esiea.mali.core.model.board;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    void constructorNegativeCoordinatesShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Position(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Position(0, -5));
    }

    @Test
    void translateDistance() {
        Position p = new Position(2, 2);
        Position right = p.translate(0, 1);
        Position up = p.translate(-1, 0);

        assertEquals(new Position(2, 3), right);
        assertEquals(new Position(1, 2), up);
    }
}
