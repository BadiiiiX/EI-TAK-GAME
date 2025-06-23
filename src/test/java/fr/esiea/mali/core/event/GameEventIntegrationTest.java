package fr.esiea.mali.core.event;

import fr.esiea.mali.core.model.player.PlayerId;
import fr.esiea.mali.core.service.impl.events.GameStartedEvent;
import fr.esiea.mali.core.model.board.Board;
import fr.esiea.mali.core.model.board.BoardSize;
import fr.esiea.mali.core.model.player.HumanPlayer;
import fr.esiea.mali.core.model.player.PlayerInventory;
import fr.esiea.mali.core.model.team.TeamColor;
import fr.esiea.mali.core.rule.engine.RuleEngine;
import fr.esiea.mali.core.rule.engine.RuleSet;
import fr.esiea.mali.core.service.impl.Game;
import fr.esiea.mali.core.service.manager.TurnManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameEventIntegrationTest {

    private EventBus bus;
    private Game game;

    @BeforeEach
    public void setUp() {
        bus = new EventBus();

        Board board = new Board(BoardSize.SMALL);
        HumanPlayer p1 = new HumanPlayer(new PlayerId(), TeamColor.WHITE, "firstPlayer", new PlayerInventory(10,1));
        HumanPlayer p2 = new HumanPlayer(new PlayerId(), TeamColor.BLACK, "secondPlayer", new PlayerInventory(10,1));
        TurnManager tm = new TurnManager(Arrays.asList(p1,p2));
        RuleEngine ruleEngine = new RuleEngine(RuleSet.defaultRules());

        game = new Game(board, ruleEngine, tm, bus);
    }

    @Test
    void startEmitsGameStartedEvent() {
        AtomicInteger count = new AtomicInteger(0);
        bus.register(GameStartedEvent.class, _ -> count.incrementAndGet());

        game.start();
        assertEquals(1, count.get());
    }
}
