package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.PurchaseAction;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class QuarrenMercenary extends NeutralGalaxyUnit implements PurchaseAction {
    public QuarrenMercenary(int id, Game game) {
        super(id, 4, 4, 0, 0, "Quarren Mercenary", List.of(Trait.trooper), false, game);
    }

    @Override
    public void applyPurchaseAction() {
        addExilePendingAction(owner, owner.isForceWithPlayer() ? 2 : 1);
    }
}
