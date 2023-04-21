package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Card;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class Chewbacca extends RebelGalaxyUnit implements HasAbility {
    public Chewbacca(int id, Game game) {
        super(id, 4, 5, 0, 0, "Chewbacca", List.of(Trait.scoundrel), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && owner.getUnitsInPlay().stream()
                .anyMatch(c -> !(c instanceof Chewbacca) && c.isUnique());
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.drawCards(1);
    }

    @Override
    public int getTargetValue() {
        return 4;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addForce(3);
    }
}
