package sw.db.cards.empire.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.CardLocation;
import sw.db.game.Game;

public class Mustafar extends Base implements HasOnReveal {

    public Mustafar(int id, Game game) {
        super(id, Faction.empire, "Mustafar", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 14);
    }

    @Override
    public void applyOnReveal() {
        game.getEmpire().addForce(4);
    }
}
