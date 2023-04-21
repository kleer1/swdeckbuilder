package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.common.models.IsBountyHunter;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class BobaFett extends EmpireGalaxyUnit implements IsBountyHunter {
    public BobaFett(int id, Game game) {
        super(id, 5, 5, 0, 0, "Boba Fett", List.of(Trait.bountyHunter), true, game);
    }

    @Override
    public int getTargetValue() {
        return 5;
    }

    @Override
    public void applyReward() {
        game.getRebel().addResources(3);
        game.getRebel().addForce(2);
    }

    @Override
    public void receiveBounty() {
        owner.drawCards(1);
    }
}
