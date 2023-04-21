package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.IsBountyHunter;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class Ig88 extends NeutralGalaxyUnit implements IsBountyHunter {
    public Ig88(int id, Game game) {
        super(id, 5, 5, 0, 0, "IG-88", List.of(Trait.bountyHunter, Trait.droid), true, game);
    }

    @Override
    public void receiveBounty() {
        addExilePendingAction(owner, 1);
    }
}
