package fr.esiea.mali.core.rule.engine;

import fr.esiea.mali.core.model.move.Move;
import fr.esiea.mali.core.model.player.IPlayer;
import fr.esiea.mali.core.model.state.game.IGameState;
import fr.esiea.mali.core.rule.api.CaptureRule;
import fr.esiea.mali.core.rule.api.MoveValidator;
import fr.esiea.mali.core.rule.api.VictoryCondition;

import java.util.Optional;

public class RuleEngine {
    private final RuleSet ruleSet;

    public RuleEngine(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    private void validateMoves(IGameState state, Move move) {
        for (MoveValidator validator : ruleSet.getValidators()) {
            validator.validate(state, move);
        }
    }

    private void applyCaptures(IGameState state, Move move) {
        for (CaptureRule captureRule : ruleSet.getCaptureRules()) {
            captureRule.applyCapture(state, move);
        }
    }

    private void processVictory(IGameState state) {
        for (VictoryCondition victoryCondition : ruleSet.getVictoryConditions()) {
            Optional<IPlayer> hasWinner = victoryCondition.checkVictory(state);
            hasWinner.ifPresent(state::setWinner);
        }
    }

    public void processMove(IGameState state, Move move) {
        this.validateMoves(state, move);

        state.getBoard().applyMove(move);

        this.applyCaptures(state, move);

        this.processVictory(state);
    }
}
