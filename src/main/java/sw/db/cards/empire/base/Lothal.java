package sw.db.cards.empire.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.game.CardLocation;
import sw.db.game.Game;

public class Lothal extends Base {

    public Lothal(int id, Game game) {
        super(id, Faction.empire, "Lothal", CardLocation.EmpireCurrentBase, null,
                game, game.getEmpire(), 8);
        game.getEmpire().setCurrentBase(this);
    }
}
