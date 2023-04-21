package sw.db.cards.rebellion.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.CardLocation;
import sw.db.game.Game;

public class Alderaan extends Base implements HasOnReveal {
    public Alderaan(int id, Game game) {
        super(id, Faction.rebellion, "Alderaan", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 14);
    }

    @Override
    public void applyOnReveal() {
        owner.addForce(4);
    }
}
