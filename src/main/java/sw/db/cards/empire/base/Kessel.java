package sw.db.cards.empire.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.CardLocation;
import sw.db.game.Game;

public class Kessel extends Base implements HasOnReveal {

    public Kessel(int id,Game game) {
        super(id, Faction.empire, "Kessel", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 12);
    }

    @Override
    public void applyOnReveal() {
        addExilePendingAction(owner, 3);
    }
}
