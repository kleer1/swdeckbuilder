package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

import java.util.List;

public class LandoCalrissian extends NeutralGalaxyUnit implements HasAbility {
    public LandoCalrissian(int id, Game game) {
        super(id, 6, 3, 3, 0, "Lando Calrissian", List.of(Trait.scoundrel), true, game);
    }

    @Override
    public boolean abilityActive() {
        boolean base = super.abilityActive();
        boolean hasDrawAbleCard = owner.getDeck().size() + owner.getDiscard().size() > 0;
        boolean oppCanDiscard = owner.isForceWithPlayer() && !owner.getOpponent().getHand().isEmpty();
        return base && (hasDrawAbleCard || oppCanDiscard);
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.drawCards(1);
        if (owner.isForceWithPlayer() && !owner.getOpponent().getHand().isEmpty()) {
            game.getPendingActions().add(PendingAction.of(Action.DiscardFromHand, true));
        }
    }
}
