package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class XWing extends RebelGalaxyUnit implements HasAbility {
    public XWing(int id, Game game) {
        super(id, 3, 3, 0, 0, "X-Wing", List.of(Trait.fighter), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && owner.isForceWithPlayer();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.drawCards(1);
    }

    @Override
    public int getTargetValue() {
        return 3;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addResources(3);
    }
}
