package starwars.deckbuilder.cards.empire.ship;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

public class GozantiCruiser extends EmpireGalaxyShip implements HasAbility {
    public GozantiCruiser(int id, Game game) {
        super(id, 3, 0, 2, 0, "Gozanti Cruiser", game, 3);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.getHand().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.DiscardFromHand, () -> owner.drawCards(1)));
    }
}
