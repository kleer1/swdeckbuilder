package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class GeneralVeers extends EmpireGalaxyUnit implements HasAbility {
    public GeneralVeers(int id, Game game) {
        super(id, 4, 4, 0, 0, "General Veers", List.of(Trait.officer), true, game);
    }

    @Override
    public int getTargetValue() {
        return 4;
    }

    @Override
    public void applyReward() {
        game.getRebel().addForce(3);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && (owner.getUnitsInPlay().stream()
                .anyMatch(c -> c.getTraits().contains(Trait.trooper) || c.getTraits().contains(Trait.vehicle)));
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.drawCards(1);
    }
}
