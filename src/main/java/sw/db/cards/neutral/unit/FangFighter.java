package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.PurchaseAction;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class FangFighter extends NeutralGalaxyUnit implements PurchaseAction {
    public FangFighter(int id, Game game) {
        super(id, 3, 3, 0, 0, "Fang Fighter", List.of(Trait.fighter), false, game);
    }

    @Override
    public void applyPurchaseAction() {
        moveToHand();
        if (owner.isForceWithPlayer()) {
            owner.drawCards(1);
        }
    }
}
