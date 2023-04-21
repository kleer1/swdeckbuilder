package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.IsBountyHunter;
import sw.db.cards.common.models.Trait;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

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
