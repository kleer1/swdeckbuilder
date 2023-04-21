package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

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
