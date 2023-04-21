package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.Unit;

import java.util.List;

public class OuterRimPilot extends Unit implements HasAbility {
    public OuterRimPilot(int id, Game game) {
        super(id, 2, 0, 2, 0, Faction.neutral, "Outer Rim Pilot", List.of(), false,
                null, CardLocation.OuterRimPilots, game.getOuterRimPilots(), game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.doesPlayerHaveFullForce();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.addForce(1);
        moveToExile();
    }
}
