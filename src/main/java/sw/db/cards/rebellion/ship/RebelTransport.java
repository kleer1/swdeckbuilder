package sw.db.cards.rebellion.ship;

import sw.db.cards.common.models.HasChooseResourceOrRepair;
import sw.db.cards.common.models.ResourceOrRepair;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

public class RebelTransport extends RebelGalaxyShip implements HasChooseResourceOrRepair {
    public RebelTransport(int id, Game game) {
        super(id, 2, 0, 0, 0, "Rebel Transport", game, 2);
    }

    @Override
    public void applyChoice(ResourceOrRepair choice) {
        switch (choice) {
            case Repair -> owner.getCurrentBase().addDamage(-2);
            case Resources -> owner.addResources(1);
        }
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        if (owner.getCurrentBase().getCurrentDamage() > 0)
        {
            game.getPendingActions().add(PendingAction.of(Action.ChooseResourceOrRepair));
        } else {
            owner.addResources(1);
        }
    }
}
