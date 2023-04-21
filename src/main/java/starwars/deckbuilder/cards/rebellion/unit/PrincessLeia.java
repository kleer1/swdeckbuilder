package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;
import starwars.deckbuilder.game.StaticEffect;

import java.util.List;

public class PrincessLeia extends RebelGalaxyUnit implements HasAbility {
    public PrincessLeia(int id, Game game) {
        super(id, 6, 2, 2, 2, "Princess Leia", List.of(Trait.officer), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && game.getGalaxyRow().stream().anyMatch(c -> c.getFaction() == Faction.rebellion);
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        if (owner.isForceWithPlayer()) {
            game.getStaticEffects().add(StaticEffect.BuyNextToTopOfDeck);
        }
        game.getStaticEffects().add(StaticEffect.NextFactionPurchaseIsFree);
        game.getPendingActions().add(PendingAction.of(Action.PurchaseCard));
    }

    @Override
    public int getTargetValue() {
        return 6;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addResources(3);
        game.getEmpire().addForce(3);
    }
}
