package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class KelDorMystic extends NeutralGalaxyUnit implements HasAbility {
    public KelDorMystic(int id, Game game) {
        super(id, 2, 0, 0, 2, "Kel Dor Mystic", List.of(), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && (!owner.getHand().isEmpty() || !owner.getDiscard().isEmpty());
    }

    @Override
    public void applyAbility() {
        addExilePendingAction(owner, 1);
        moveToExile();
    }
}
