package sw.db.cards.empire.base;

import sw.db.cards.common.models.*;
import sw.db.game.Action;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.PendingAction;

public class DeathStar extends Base implements HasAbility, HasAtStartOfTurn {

    public DeathStar(int id, Game game) {
        super(id, Faction.empire, "Death Star", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 16);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && location == CardLocation.EmpireCurrentBase && owner.getResources() >= 4 &&
                (!game.getRebel().getShipsInPlay().isEmpty() || game.getGalaxyRow().stream().anyMatch(c -> c instanceof CapitalShip));
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.FireWhenReady));
    }

    @Override
    public void applyAtStartOfTurn() {
        abilityUsed = false;
    }
}
