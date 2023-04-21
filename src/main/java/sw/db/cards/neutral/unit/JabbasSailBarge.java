package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.HasReturnToHandAbility;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.common.models.Trait;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

import java.util.List;

public class JabbasSailBarge extends NeutralGalaxyUnit implements HasReturnToHandAbility {
    public JabbasSailBarge(int id, Game game) {
        super(id, 7, 4, 3, 0, "Jabbas Sail Barge", List.of(Trait.vehicle), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && owner.getDiscard().stream().anyMatch(pc -> pc.getTraits().contains(Trait.bountyHunter));
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.ReturnCardToHand));
    }

    @Override
    public boolean isValidTarget(PlayableCard card) {
        return card.getTraits().contains(Trait.bountyHunter);
    }
}
