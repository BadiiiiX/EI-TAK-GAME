package fr.esiea.mali.core.service.manager;

import fr.esiea.mali.core.model.player.HumanPlayer;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.player.PlayerInventory;
import fr.esiea.mali.core.model.team.TeamColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    private IPlayer pWhite;
    private IPlayer pBlack;
    private TurnManager turnManager;

    @BeforeEach
    void setUp() {
        pWhite = new HumanPlayer(TeamColor.WHITE, "First" ,new PlayerInventory(10, 1));
        pBlack = new HumanPlayer(TeamColor.BLACK, "Second", new PlayerInventory(10, 1));
        turnManager = new TurnManager(List.of(pWhite, pBlack));
    }

    @Test
    void initialCurrentPlayerIsFirstInList() {
        assertEquals(pWhite.getName(), turnManager.getCurrentPlayer().getName());
        assertEquals(1, turnManager.getTurnNumber());
    }

    @Test
    void nextPlayerCyclesAndIncrementsTurnNumber() {
        assertEquals(pWhite, turnManager.getCurrentPlayer());

        IPlayer next = turnManager.nextPlayer();
        assertEquals(pBlack, next);
        assertEquals(1, turnManager.getTurnNumber());

        next = turnManager.nextPlayer();
        assertEquals(pWhite, next);

        assertEquals(2, turnManager.getTurnNumber());
    }

    @Test
    void previousPlayerGoesBackCorrectlyAndDecrementsTurnNumber() {
        turnManager.nextPlayer();
        turnManager.nextPlayer();
        assertEquals(2, turnManager.getTurnNumber());
        assertEquals(pWhite, turnManager.getCurrentPlayer());

        IPlayer prev = turnManager.previousPlayer();
        assertEquals(pBlack, prev);
        assertEquals(1, turnManager.getTurnNumber());

        prev = turnManager.previousPlayer();
        assertEquals(pWhite, prev);
        assertEquals(1, turnManager.getTurnNumber());
    }

    @Test
    void constructorRequiresAtLeastTwoPlayers() {
        assertThrows(IllegalArgumentException.class, () -> new TurnManager(List.of(pWhite)));
        assertThrows(IllegalArgumentException.class, () -> new TurnManager(null));
    }
}
