package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

import java.util.List;

public class Snowspeeder extends RebelGalaxyUnit implements HasAbility {
    public Snowspeeder(int id, Game game) {
        super(id, 2, 2, 0, 0, "Snowspeeder", List.of(Trait.vehicle), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.getOpponent().getHand().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.DiscardFromHand, true));
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
