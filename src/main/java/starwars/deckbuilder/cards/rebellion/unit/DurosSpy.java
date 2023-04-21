package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

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
