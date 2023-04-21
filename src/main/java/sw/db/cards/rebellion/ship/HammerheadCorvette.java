package sw.db.cards.rebellion.ship;

import sw.db.cards.common.models.CapitalShip;
import sw.db.cards.common.models.HasAbility;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;

public class HammerheadCorvette extends RebelGalaxyShip implements HasAbility {
    public HammerheadCorvette(int id, Game game) {
        super(id, 4, 0, 2, 0, "Hammerhead Corvette", game, 4);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && (!owner.getOpponent().getShipsInPlay().isEmpty() ||
                game.getGalaxyRow().stream()
                        .anyMatch(pc -> pc instanceof CapitalShip && pc.getFaction() == owner.getOpponent().getFaction()));
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        moveToExile();
        game.getPendingActions().add(PendingAction.of(Action.HammerHeadAway));
    }
}
