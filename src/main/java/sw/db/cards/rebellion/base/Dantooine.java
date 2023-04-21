package sw.db.cards.rebellion.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.game.CardLocation;
import sw.db.game.Game;

public class Dantooine extends Base {

    public Dantooine(int id, Game game) {
        super(id, Faction.rebellion, "Dantooine", CardLocation.RebelCurrentBase, null, game,
                game.getRebel(), 8);
        game.getRebel().setCurrentBase(this);
    }
}
