package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;
import starwars.deckbuilder.game.StaticEffect;
import starwars.deckbuilder.cards.common.models.HasReturnToHandAbility;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.common.models.Trait;

import java.util.List;

public class AtAt extends EmpireGalaxyUnit implements HasReturnToHandAbility {
    public AtAt(int id, Game game) {
        super(id, 6, 6, 0, 0, "AT-AT", List.of(Trait.vehicle), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && owner.getDiscard().stream().anyMatch(c -> c.getTraits().contains(Trait.trooper));
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.ReturnCardToHand));
    }

    @Override
    public int getTargetValue() {
        return 6;
    }

    @Override
    public void applyReward() {
        game.getPendingActions().add(PendingAction.of(Action.PurchaseCard));
        game.getStaticEffects().add(StaticEffect.NextFactionPurchaseIsFree);
    }

    @Override
    public boolean isValidTarget(PlayableCard card) {
        return card.getTraits().contains(Trait.trooper);
    }
}
