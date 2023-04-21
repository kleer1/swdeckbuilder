package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class YWing extends RebelGalaxyUnit implements HasAbility {
    public YWing(int id, Game game) {
        super(id, 1, 2, 0, 0, "Y Wing", List.of(Trait.fighter), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() &&
                (game.getEmpire().getCurrentBase() != null || !game.getEmpire().getShipsInPlay().isEmpty());
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        if (game.getEmpire().getCurrentBase() != null) {
            game.getEmpire().getCurrentBase().addDamage(2);
        } else {
            game.assignDamageToBase(2, game.getEmpire());
        }
        moveToExile();
    }

    @Override
    public int getTargetValue() {
        return 1;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addResources(1);
    }
}
