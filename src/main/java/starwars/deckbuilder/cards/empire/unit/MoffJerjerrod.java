package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

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
