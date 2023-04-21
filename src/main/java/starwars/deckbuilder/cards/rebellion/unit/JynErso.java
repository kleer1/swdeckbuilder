package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

import java.util.List;

public class JynErso extends RebelGalaxyUnit implements HasAbility {
    public JynErso(int id, Game game) {
        super(id, 4, 4, 0, 0, "Jyn Erso", List.of(Trait.scoundrel), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.getOpponent().getHand().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.revealOpponentsHand();
        if (owner.isForceWithPlayer()) {
            game.getPendingActions().add(PendingAction.of(Action.JynErsoTopDeck));
        }
    }

    @Override
    public int getTargetValue() {
        return 4;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addForce(3);
    }
}
