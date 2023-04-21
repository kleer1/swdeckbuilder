package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.PurchaseAction;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

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
