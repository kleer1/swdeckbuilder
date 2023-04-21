package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;
import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.IsTargetable;
import starwars.deckbuilder.cards.common.models.Trait;

import java.util.List;

public class TieBomber extends EmpireGalaxyUnit implements HasAbility, IsTargetable {
    public TieBomber(int id, Game game) {
        super(id, 2, 2, 0, 0, "Tie Bomber", List.of(Trait.fighter),false, game);
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
        return 2;
    }

    @Override
    public void applyReward() {
        addExilePendingAction(game.getRebel(), 1);
    }
}
