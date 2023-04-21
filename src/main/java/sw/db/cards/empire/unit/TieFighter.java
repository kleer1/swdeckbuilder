package sw.db.cards.empire.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.IsTargetable;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class TieFighter extends EmpireGalaxyUnit implements HasAbility, IsTargetable {
    public TieFighter(int id, Game game) {
        super(id, 1, 2, 0, 0,"Tie Fighter", List.of(Trait.fighter), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.getShipsInPlay().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.drawCards(1);
    }

    @Override
    public int getTargetValue() {
        return 1;
    }

    @Override
    public void applyReward() {
        game.getRebel().addResources(1);
    }
}
