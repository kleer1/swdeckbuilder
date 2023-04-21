package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class XWing extends RebelGalaxyUnit implements HasAbility {
    public XWing(int id, Game game) {
        super(id, 3, 3, 0, 0, "X-Wing", List.of(Trait.fighter), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && owner.isForceWithPlayer();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.drawCards(1);
    }

    @Override
    public int getTargetValue() {
        return 3;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addResources(3);
    }
}
