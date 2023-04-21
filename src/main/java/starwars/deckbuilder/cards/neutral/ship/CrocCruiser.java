package starwars.deckbuilder.cards.neutral.ship;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

public class CrocCruiser extends NeutralGalaxyShip implements HasAbility {
    public CrocCruiser(int id, Game game) {
        super(id, 3, 0, 1, "C-ROC Cruiser", game, 3);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.getHand().isEmpty() && owner.getCurrentBase() != null &&
                owner.getCurrentBase().getCurrentDamage() > 0;
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.DiscardFromHand, () -> owner.getCurrentBase().addDamage(-3)));
    }
}
