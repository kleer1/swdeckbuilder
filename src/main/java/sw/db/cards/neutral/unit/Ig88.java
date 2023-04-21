package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.IsBountyHunter;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

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
