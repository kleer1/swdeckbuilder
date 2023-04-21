package starwars.deckbuilder.cards.neutral.ship;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.HasChooseResourceOrRepair;
import starwars.deckbuilder.cards.common.models.ResourceOrRepair;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

public class NebulonBFrigate extends NeutralGalaxyShip implements HasAbility, HasChooseResourceOrRepair {
    public NebulonBFrigate(int id, Game game) {
        super(id, 5, 0, 0, "Nebulon-B Frigate", game, 5);
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
            owner.addResources(3);
        }
    }

    @Override
    public void applyChoice(ResourceOrRepair choice) {
        if (choice == ResourceOrRepair.Repair) {
            owner.getCurrentBase().addDamage(-3);
        } else if (choice == ResourceOrRepair.Resources) {
            owner.addResources(3);
        }
    }
}
