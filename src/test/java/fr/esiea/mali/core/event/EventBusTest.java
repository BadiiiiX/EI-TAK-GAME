package fr.esiea.mali.core.event;

import fr.esiea.mali.core.event.events.GameEndedEvent;
import fr.esiea.mali.core.event.events.GameStartedEvent;
import fr.esiea.mali.core.model.board.Board;
import fr.esiea.mali.core.model.board.BoardSize;
import fr.esiea.mali.core.model.player.HumanPlayer;
import fr.esiea.mali.core.model.player.PlayerInventory;
import fr.esiea.mali.core.model.state.game.GameState;
import fr.esiea.mali.core.model.team.TeamColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class EventBusTest {

    private EventBus bus;
    private GameState gameState;

    @BeforeEach
    public void setUp() {
        bus = new EventBus();
        Board board = new Board(BoardSize.MEDIUM);
        PlayerInventory playerInventory = new PlayerInventory(BoardSize.MEDIUM.getInitialStones(), BoardSize.MEDIUM.getInitialCapstones());
        HumanPlayer basePlayer = new HumanPlayer(TeamColor.BLACK, "testUser", playerInventory);
        gameState = new GameState(board, basePlayer);
    }

    @Test
    void postingWithoutListenersDoesNothing() {
        bus.post(new GameStartedEvent(gameState));
    }

    @Test
    void singleListenerReceivesCorrectEvent() {
        AtomicReference<GameStartedEvent> received = new AtomicReference<>();
        bus.register(GameStartedEvent.class, received::set);

        var evt = new GameStartedEvent(gameState);

        bus.post(evt);
        assertSame(evt, received.get());
    }

    @Test
    void listenerForOneTypeDoesNotReceiveOtherType() {
        AtomicBoolean moveFlag = new AtomicBoolean(false);
        bus.register(GameEndedEvent.class, _ -> moveFlag.set(true));

        bus.post(new GameStartedEvent(gameState));

        assertFalse(moveFlag.get());
    }

    @Test
    void multipleListenersReceiveEvent() {
        List<GameStartedEvent> received = new ArrayList<>();
        var event = new GameStartedEvent(gameState);

        bus.register(GameStartedEvent.class, received::add);
        bus.register(GameStartedEvent.class, received::add);
        bus.post(event);

        assertEquals(2, received.size());
        assertTrue(received.stream().allMatch(e -> e == event));
    }
}
