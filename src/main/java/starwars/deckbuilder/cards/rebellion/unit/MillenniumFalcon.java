package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;
import starwars.deckbuilder.game.StaticEffect;
import starwars.deckbuilder.cards.common.models.Card;
import starwars.deckbuilder.cards.common.models.HasReturnToHandAbility;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.common.models.Trait;

import java.util.List;

public class MillenniumFalcon extends RebelGalaxyUnit implements HasReturnToHandAbility {
    public MillenniumFalcon(int id, Game game) {
        super(id, 7, 5, 2, 0, "Millennium Falcon", List.of(Trait.transport), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && owner.getDiscard().stream().anyMatch(Card::isUnique);
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.ReturnCardToHand));
    }

    @Override
    public int getTargetValue() {
        return 7;
    }

    @Override
    public void applyReward() {
        game.getPendingActions().add(PendingAction.of(Action.PurchaseCard));
        game.getStaticEffects().add(StaticEffect.NextFactionPurchaseIsFree);
    }

    @Override
    public boolean isValidTarget(PlayableCard card) {
        return card.isUnique();
    }
}
