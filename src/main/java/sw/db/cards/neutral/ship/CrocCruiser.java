package sw.db.cards.neutral.ship;

import sw.db.cards.common.models.HasAbility;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

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
