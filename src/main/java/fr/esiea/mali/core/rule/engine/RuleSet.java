package fr.esiea.mali.core.rule.engine;

import fr.esiea.mali.core.rule.api.CaptureRule;
import fr.esiea.mali.core.rule.api.MoveValidator;
import fr.esiea.mali.core.rule.api.VictoryCondition;
import fr.esiea.mali.core.rule.impl.capture.CapstoneFlattenRule;
import fr.esiea.mali.core.rule.impl.placement.PlacementOnEmptyRule;
import fr.esiea.mali.core.rule.impl.slide.SlideCapstoneFlipRule;
import fr.esiea.mali.core.rule.impl.slide.SlideChargeLimitRule;
import fr.esiea.mali.core.rule.impl.slide.SlideOwnershipRule;
import fr.esiea.mali.core.rule.impl.slide.SlidePathClearRule;
import fr.esiea.mali.core.rule.impl.victory.ApplyWinCondition;

import java.util.ArrayList;
import java.util.List;

public class RuleSet {
    private final List<MoveValidator> validators = new ArrayList<>();
    private final List<CaptureRule> captureRules = new ArrayList<>();
    private final List<VictoryCondition> victoryRules = new ArrayList<>();

    public RuleSet addValidator(MoveValidator v) {
        validators.add(v);
        return this;
    }

    public RuleSet addCaptureRule(CaptureRule c) {
        captureRules.add(c);
        return this;
    }

    public RuleSet addVictoryCondition(VictoryCondition v) {
        victoryRules.add(v);
        return this;
    }

    public List<MoveValidator> getValidators() {
        return List.copyOf(validators);
    }

    public List<CaptureRule> getCaptureRules() {
        return List.copyOf(captureRules);
    }

    public List<VictoryCondition> getVictoryConditions() {
        return List.copyOf(victoryRules);
    }

    public static RuleSet defaultRules() {
        return new RuleSet()
                .addValidator(new PlacementOnEmptyRule())

                .addValidator(new SlideOwnershipRule())
                .addValidator(new SlideChargeLimitRule())
                .addValidator(new SlidePathClearRule())
                .addValidator(new SlideCapstoneFlipRule())

                .addCaptureRule(new CapstoneFlattenRule())

                .addVictoryCondition(new ApplyWinCondition());
    }
}
