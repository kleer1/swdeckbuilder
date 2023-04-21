package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;
import starwars.deckbuilder.game.StaticEffect;

import java.util.List;
import java.util.OptionalInt;

public class JawaScavenger extends NeutralGalaxyUnit implements HasAbility {
    public JawaScavenger(int id, Game game) {
        super(id, 1, 0, 2, 0, "Jawa Scavenger", List.of(), false, game);
    }

    @Override
    public boolean abilityActive() {
        if (!super.abilityActive()) {
            return false;
        }
        OptionalInt minOptional = game.getGalaxyDiscard().stream()
                .filter(pc -> pc.getFaction() == owner.getFaction() || pc.getFaction() == Faction.neutral)
                .mapToInt(PlayableCard::getCost).min();
        if (minOptional.isEmpty()) {
            return false;
        }
        return minOptional.getAsInt() <= owner.getResources();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        moveToExile();
        game.getPendingActions().add(PendingAction.of(Action.PurchaseCard));
        game.getStaticEffects().add(StaticEffect.PurchaseFromDiscard);
    }

}
