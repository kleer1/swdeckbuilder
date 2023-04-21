package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.IsBountyHunter;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

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
