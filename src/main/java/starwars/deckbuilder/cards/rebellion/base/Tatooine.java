package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasOnReveal;
import starwars.deckbuilder.game.*;

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
