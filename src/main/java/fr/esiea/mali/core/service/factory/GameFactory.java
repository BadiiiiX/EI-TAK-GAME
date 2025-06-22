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

    public static Game create(BoardSize size, String firstPlayerName, String secondPlayerName) {

        Board board = BoardFactory.create(size);

        RuleSet ruleSet = RuleSet.defaultRules();
        RuleEngine engine = new RuleEngine(ruleSet);

        HumanPlayer firstPlayer = PlayerFactory.create(firstPlayerName, TeamColor.WHITE, size);
        HumanPlayer secondPlayer = PlayerFactory.create(secondPlayerName, TeamColor.BLACK, size);

        TurnManager turnManager = new TurnManager(Arrays.asList(firstPlayer, secondPlayer));
        EventBus  eventBus = new EventBus();

        return new Game(board, engine, turnManager, eventBus);
    }

}
