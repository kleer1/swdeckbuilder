package sw.db.cards.rebellion.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.Action;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.PendingAction;

public class Tatooine extends Base implements HasOnReveal {
    public Tatooine(int id, Game game) {
        super(id, Faction.rebellion, "Tatooine", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 14);
    }

    @Override
    public void applyOnReveal() {
        if (game.getGalaxyDiscard().stream().anyMatch(c -> c.getFaction() == Faction.rebellion)) {
            game.getPendingActions().add(PendingAction.of(Action.ANewHope1));
        }
    }
}
