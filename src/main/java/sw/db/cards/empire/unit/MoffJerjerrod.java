package sw.db.cards.empire.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

import java.util.List;

public class MoffJerjerrod extends EmpireGalaxyUnit implements HasAbility {
    public MoffJerjerrod(int id, Game game) {
        super(id, 4, 2, 2, 0, "Moff Jerjerrod", List.of(Trait.officer), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !game.getGalaxyDeck().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.lookAtTopCardOfDeck(owner.getFaction());
        if (owner.isForceWithPlayer()) {
            game.getPendingActions().add(PendingAction.of(Action.SwapTopCardOfDeck));
        }
    }

    @Override
    public int getTargetValue() {
        return 4;
    }

    @Override
    public void applyReward() {
        game.getRebel().addForce(3);
    }
}
