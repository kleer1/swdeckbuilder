package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class Hwk290 extends NeutralGalaxyUnit implements HasAbility {
    public Hwk290(int id, Game game) {
        super(id, 4, 0, 4, 0, "HWK-290", List.of(Trait.transport), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && owner.getCurrentBase().getCurrentDamage() > 0;
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.getCurrentBase().addDamage(-4);
        moveToExile();
    }
}
