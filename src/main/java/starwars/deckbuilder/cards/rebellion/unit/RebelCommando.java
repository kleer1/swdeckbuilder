package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;
import starwars.deckbuilder.game.StaticEffect;

import java.util.Collections;
import java.util.List;

public class RebelCommando extends RebelGalaxyUnit implements HasAbility {
    public RebelCommando(int id, Game game) {
        super(id, 3, 3, 0, 0, "Rebel Commando", List.of(Trait.trooper), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.getOpponent().getHand().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        if (owner.isForceWithPlayer()) {
            Collections.shuffle(owner.getOpponent().getHand());
            owner.getOpponent().getHand().get(0).moveToDiscard();
            if (game.getStaticEffects().contains(StaticEffect.Yavin4Effect) &&
                    owner.getOpponent().getCurrentBase() != null) {
                owner.getOpponent().getCurrentBase().addDamage(2);
            }
        } else {
            game.getPendingActions().add(PendingAction.of(Action.DiscardFromHand, true));
        }
     }

    @Override
    public int getTargetValue() {
        return 3;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addForce(2);
    }
}
