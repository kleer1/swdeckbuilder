package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class RodianGunslinger extends NeutralGalaxyUnit {
    public RodianGunslinger(int id, Game game) {
        super(id, 2, 2, 0, 0, "Rodian Gunslinger", List.of(Trait.bountyHunter), false, game);
    }

    @Override
    public int getAttack() {
        int atk = super.getAttack();
        if (game.getAttackTarget() != null && game.getAttackTarget().getLocation() == CardLocation.GalaxyRow) {
            atk += 2;
        }
        return atk;
    }
}
