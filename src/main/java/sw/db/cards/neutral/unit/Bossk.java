package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.IsBountyHunter;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class Bossk extends NeutralGalaxyUnit implements IsBountyHunter {
    public Bossk(int id, Game game) {
        super(id, 3, 3, 0, 0, "Bossk", List.of(Trait.bountyHunter), true, game);
    }

    @Override
    public void receiveBounty() {
        owner.addForce(1);
    }
}
