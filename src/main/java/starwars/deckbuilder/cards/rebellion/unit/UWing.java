package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class UWing extends RebelGalaxyUnit implements HasAbility {
    public UWing(int id, Game game) {
        super(id, 4, 0, 3, 0, "U-Wing", List.of(Trait.fighter), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && owner.isForceWithPlayer() &&
                owner.getCurrentBase() != null && owner.getCurrentBase().getCurrentDamage() > 0;
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.getCurrentBase().addDamage(-3);
    }

    @Override
    public int getTargetValue() {
        return 4;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addResources(4);
    }
}
