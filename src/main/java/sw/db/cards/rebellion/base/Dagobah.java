package sw.db.cards.rebellion.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.CardLocation;
import sw.db.game.Game;

public class Dagobah extends Base implements HasOnReveal {
    public Dagobah(int id, Game game) {
        super(id, Faction.rebellion, "Dagobah", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 12);
    }

    @Override
    public void applyOnReveal() {
        addExilePendingAction(owner, 3);
    }
}
