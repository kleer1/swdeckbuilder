package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.StaticEffect;

import java.util.List;

public class TwilekSmuggler extends NeutralGalaxyUnit implements HasAbility {
    public TwilekSmuggler(int id, Game game) {
        super(id, 3, 0, 3, 0, "Twi'lek Smuggler", List.of(Trait.scoundrel), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getStaticEffects().add(StaticEffect.BuyNextToTopOfDeck);
    }
}
