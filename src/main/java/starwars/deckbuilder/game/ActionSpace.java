package starwars.deckbuilder.game;

import lombok.Getter;
import starwars.deckbuilder.cards.common.models.ResourceOrRepair;
import starwars.deckbuilder.cards.common.models.Stats;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum ActionSpace {
    CardAction(0, 139),
    PassTurn(140),
    DeclineAction(141),
    ChooseStatAttack(142, Stats.Attack),
    ChooseStatResource(143, Stats.Resources),
    ChooseStatForce(144, Stats.Force),
    ChooseResource(145, ResourceOrRepair.Resources),
    ChooseRepair(146, ResourceOrRepair.Repair),
    AttackNeutralCard(147),
    ConfirmAttackers(148),
//    BuyGalaxyDiscard(149)
    ;

    public static int SIZE = 149;


    private int minRange;
    private int maxRange;
    private Stats stats;
    private ResourceOrRepair resourceOrRepair;

    ActionSpace(int minRange) {
        this.minRange = minRange;
        this.maxRange = minRange;
    }

    ActionSpace(int minRange, Stats stats) {
        this.minRange = minRange;
        this.maxRange = minRange;
        this.stats = stats;
    }

    ActionSpace(int minRange, ResourceOrRepair resourceOrRepair) {
        this.minRange = minRange;
        this.maxRange = minRange;
        this.resourceOrRepair = resourceOrRepair;
    }

    ActionSpace(int minRange, int maxRange) {
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    private static List<ActionSpace> values = Arrays.stream(values()).collect(Collectors.toList());

    public static ActionSpace getActionSpaceByIndex(int index) {
        return values.stream().filter(as -> index >= as.minRange && index <= as.maxRange).findFirst().orElse(null);
    }
}
