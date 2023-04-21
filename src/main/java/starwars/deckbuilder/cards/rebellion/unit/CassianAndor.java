package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.IsBountyHunter;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

import java.util.List;

public class CassianAndor extends RebelGalaxyUnit implements IsBountyHunter {
    public CassianAndor(int id, Game game) {
        super(id, 5, 5, 0, 0, "Cassian Andor", List.of(Trait.trooper), true, game);
    }

    @Override
    public void receiveBounty() {
        if (!owner.getOpponent().getHand().isEmpty()) {
            game.getPendingActions().add(PendingAction.of(Action.DiscardFromHand, true));
        }
    }

    @Override
    public int getTargetValue() {
        return 5;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addResources(3);
        game.getEmpire().addForce(2);
    }
}
