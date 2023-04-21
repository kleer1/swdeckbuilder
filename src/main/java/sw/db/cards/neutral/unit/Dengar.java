package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.IsBountyHunter;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class Dengar extends NeutralGalaxyUnit implements IsBountyHunter {
    public Dengar(int id, Game game) {
        super(id, 4, 4, 0, 0, "Dengar", List.of(Trait.bountyHunter), true, game);
    }

    @Override
    public void receiveBounty() {
        owner.addResources(2);
    }
}
