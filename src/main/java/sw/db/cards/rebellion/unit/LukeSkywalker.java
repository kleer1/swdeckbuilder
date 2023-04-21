package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

import java.util.List;

public class LukeSkywalker extends RebelGalaxyUnit implements HasAbility {
    public LukeSkywalker(int id, Game game) {
        super(id, 8, 6, 0, 2, "Luke Skywalker", List.of(Trait.jedi), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && owner.isForceWithPlayer() && !owner.getOpponent().getShipsInPlay().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.LukeDestroyShip));
    }

    @Override
    public int getTargetValue() {
        return 8;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addResources(4);
        game.getEmpire().addForce(4);
    }
}
