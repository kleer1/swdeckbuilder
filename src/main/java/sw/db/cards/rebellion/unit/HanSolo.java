package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class HanSolo extends RebelGalaxyUnit implements HasAbility {
    public HanSolo(int id, Game game) {
        super(id, 5, 3, 2, 0, "Han Solo", List.of(Trait.scoundrel), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        if (owner.getUnitsInPlay().stream().anyMatch(u -> u instanceof MillenniumFalcon)) {
            owner.drawCards(2);
        } else {
            owner.drawCards(1);
        }
    }

    @Override
    public int getTargetValue() {
        return 5;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addResources(3);
        game.getEmpire().addForce(2);
    }
}
