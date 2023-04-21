package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

import java.util.List;

public class AtSt extends EmpireGalaxyUnit implements HasAbility {
    public AtSt(int id, Game game) {
        super(id, 4, 4, 0, 0, "AT-ST", List.of(Trait.vehicle), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.DiscardCardFromCenter));
    }

    @Override
    public int getTargetValue() {
        return 4;
    }

    @Override
    public void applyReward() {
        addExilePendingAction(game.getEmpire(), 2);
    }
}
