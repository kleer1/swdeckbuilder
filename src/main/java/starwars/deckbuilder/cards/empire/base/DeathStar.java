package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.common.models.*;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

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
