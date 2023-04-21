package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

import java.util.List;

public class DurosSpy extends RebelGalaxyUnit implements HasAbility {
    public DurosSpy(int id, Game game) {
        super(id, 2, 0, 2, 0, "Duros Spy", List.of(Trait.trooper), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && (!owner.getOpponent().getHand().isEmpty() || !owner.doesPlayerHaveFullForce());
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        if (owner.getOpponent().getHand().isEmpty()) {
            owner.addForce(1);
        } else if (owner.doesPlayerHaveFullForce()) {
            game.getPendingActions().add(PendingAction.of(Action.DiscardFromHand, true));
        } else {
            owner.addForce(1);
            game.getPendingActions().add(PendingAction.of(Action.DurosDiscard, () -> owner.addForce(-1), true));
        }
    }

    @Override
    public int getTargetValue() {
        return 2;
    }

    @Override
    public void applyReward() {
        addExilePendingAction(game.getEmpire(), 1);
    }
}
