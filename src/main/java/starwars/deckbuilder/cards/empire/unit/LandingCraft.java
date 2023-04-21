package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.common.models.HasChooseResourceOrRepair;
import starwars.deckbuilder.cards.common.models.ResourceOrRepair;
import starwars.deckbuilder.cards.common.models.IsTargetable;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

import java.util.List;

public class LandingCraft extends EmpireGalaxyUnit implements IsTargetable, HasChooseResourceOrRepair {
    public LandingCraft(int id, Game game) {
        super(id, 4, 0, 0, 0, "Landing Craft", List.of(Trait.transport), false, game);
    }

    @Override
    public void applyChoice(ResourceOrRepair choice) {
        if (choice == ResourceOrRepair.Repair) {
            owner.getCurrentBase().addDamage(-4);
        } else if (choice == ResourceOrRepair.Resources) {
            owner.addResources(4);
        }
    }

    @Override
    public int getTargetValue() {
        return 4;
    }

    @Override
    public void applyReward() {
        game.getRebel().addResources(4);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        if (owner.getCurrentBase().getCurrentDamage() > 0) {
            game.getPendingActions().add(PendingAction.of(Action.ChooseResourceOrRepair));
        } else {
            owner.addResources(4);
        }
    }
}
