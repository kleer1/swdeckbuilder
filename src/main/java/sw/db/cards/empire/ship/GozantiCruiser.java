package sw.db.cards.empire.ship;

import sw.db.cards.common.models.HasAbility;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

public class GozantiCruiser extends EmpireGalaxyShip implements HasAbility {
    public GozantiCruiser(int id, Game game) {
        super(id, 3, 0, 2, 0, "Gozanti Cruiser", game, 3);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.getHand().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.DiscardFromHand, () -> owner.drawCards(1)));
    }
}
