package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;
import sw.db.game.StaticEffect;

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
