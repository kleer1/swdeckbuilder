package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

import java.util.List;

public class JabbaTheHutt extends NeutralGalaxyUnit implements HasAbility {
    public JabbaTheHutt(int id, Game game) {
        super(id, 8, 2, 2, 2, "Jabba The Hutt", List.of(Trait.scoundrel), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.getHand().isEmpty() && owner.getDiscard().size() + owner.getDeck().size() > 0;
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.JabbaExile, () -> {
            if (owner.isForceWithPlayer()) {
                owner.drawCards(2);
            } else {
                owner.drawCards(1);
            }
        }));
    }
}
