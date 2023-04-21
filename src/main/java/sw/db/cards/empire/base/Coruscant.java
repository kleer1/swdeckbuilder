package sw.db.cards.empire.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasAtStartOfTurn;
import sw.db.game.Action;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.PendingAction;

public class Coruscant extends Base implements HasAtStartOfTurn {
    public Coruscant(int id, Game game) {
        super(id, Faction.empire, "Coruscant", CardLocation.EmpireAvailableBases,
                game.getEmpire().getAvailableBases(), game, game.getEmpire(), 16);
    }

    @Override
    public void applyAtStartOfTurn() {
        if (game.getGalaxyDeck().size() >= 2) {
            game.getKnowsTopCardOfDeck().put(Faction.empire, 2);
            game.getPendingActions().add(PendingAction.of(Action.GalacticRule));
        }
    }
}
