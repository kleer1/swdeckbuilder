package sw.db.cards.empire.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.cards.empire.base.DeathStar;
import sw.db.game.Game;

import java.util.List;

public class DirectorKrennic extends EmpireGalaxyUnit implements HasAbility {
    public DirectorKrennic(int id, Game game) {
        super(id, 5, 3, 2, 0, "Director Krennic", List.of(Trait.officer), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        int amountToDraw = 1;
        if (owner.getCurrentBase() instanceof DeathStar) {
            amountToDraw = 2;
        }
        owner.drawCards(amountToDraw);
    }

    @Override
    public int getTargetValue() {
        return 5;
    }

    @Override
    public void applyReward() {
        game.getRebel().addResources(3);
        game.getRebel().addForce(2);
    }
}
