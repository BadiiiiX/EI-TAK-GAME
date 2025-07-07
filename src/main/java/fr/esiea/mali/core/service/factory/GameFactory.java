package fr.esiea.mali.core.service.factory;

import fr.esiea.mali.core.event.EventBus;
import fr.esiea.mali.core.model.board.Board;
import fr.esiea.mali.core.model.board.BoardSize;
import fr.esiea.mali.core.model.player.HumanPlayer;
import fr.esiea.mali.core.model.team.TeamColor;
import fr.esiea.mali.core.rule.engine.RuleEngine;
import fr.esiea.mali.core.rule.engine.RuleSet;
import fr.esiea.mali.core.service.impl.Game;
import fr.esiea.mali.core.service.manager.TurnManager;

import java.util.Arrays;

public class GameFactory {

    public static Game create(BoardSize size, HumanPlayer firstPlayer, HumanPlayer secondPlayer, EventBus eventBus) {
        Board board = BoardFactory.create(size);

        RuleSet ruleSet = RuleSet.defaultRules();
        RuleEngine engine = new RuleEngine(ruleSet);

        TurnManager turnManager = new TurnManager(Arrays.asList(firstPlayer, secondPlayer));

        return new Game(board, engine, turnManager, eventBus);
    }

    public static Game create(BoardSize size, HumanPlayer firstPlayer, HumanPlayer secondPlayer) {
        EventBus eventBus = new EventBus();

        return GameFactory.create(size, firstPlayer, secondPlayer, eventBus);
    }

    public static Game create(BoardSize size, String firstPlayer, String secondPlayer, EventBus eventBus) {
        HumanPlayer playerOne = PlayerFactory.create(firstPlayer, TeamColor.WHITE, size);
        HumanPlayer playerTwo = PlayerFactory.create(secondPlayer, TeamColor.BLACK, size);

        return GameFactory.create(size, playerOne, playerTwo, eventBus);
    }

    public static Game create(BoardSize size, String firstPlayer, String secondPlayer) {
        EventBus eventBus = new EventBus();

        return GameFactory.create(size, firstPlayer, secondPlayer, eventBus);
    }

}
